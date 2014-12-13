package oa.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import oa.model.LeaveBill;
import oa.model.User;
import oa.service.ILeaveBillService;
import oa.service.IWorkflowService;
import oa.util.ValidateUtil;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

@Component("workflowService")
@Transactional
public class WorkflowServiceImpl implements IWorkflowService {
	
	@Resource
	private ILeaveBillService leaveBillService;
	
	@Resource
	private RepositoryService repositoryService;
	
	@Resource
	private RuntimeService runtimeService;
	
	@Resource
	private TaskService taskService;
	
	@Resource
	private FormService formService;
	
	@Resource
	private HistoryService historyService;

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public FormService getFormService() {
		return formService;
	}

	public void setFormService(FormService formService) {
		this.formService = formService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public void setLeaveBillService(ILeaveBillService leaveBillService) {
		this.leaveBillService = leaveBillService;
	}

	public ILeaveBillService getLeaveBillService() {
		return leaveBillService;
	}

	/**部署流程定义*/
	public void saveNewDeploy(File file, String filename) {
		try {
			//将File类型的文件转化成ZipInputStream流
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
			repositoryService.createDeployment()//创建部署对象
							.name(filename)//添加部署名称
							.addZipInputStream(zipInputStream)//
							.deploy();//完成部署
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**查询部署对象信息，对应表（act_re_deployment）*/
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery()//创建部署对象查询
							.orderByDeploymenTime().asc()//
							.list();
		return list;
	}
	
	/**查询流程定义的信息，对应表（act_re_procdef）*/
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//创建流程定义查询
							.orderByProcessDefinitionVersion().asc()//
							.list();
		return list;
	}
	
	/**使用部署对象ID和资源图片名称，获取图片的输入流*/
	public InputStream findImageInputStream(String deploymentId,
			String imageName) {
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}
	
	/**根据部署ID级联删除部署*/
	public void deleteDeploymentById(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
	}

	/**更新请假状态，启动流程实例，让启动的流程实例关联业务*/
	public void saveStartProcess(int leaveBillId) {
		//1：获取请假单ID，使用请假单ID，查询请假单的对象LeaveBill
		LeaveBill leaveBill = leaveBillService.get(leaveBillId);
		//2：更新请假单的请假状态从0变成1（初始录入-->审核中）
		leaveBill.setState(1);
		leaveBillService.saveOrUpdate(leaveBill);
		//3：使用当前对象获取到流程定义的key（对象的名称就是流程定义的key）
		String key = leaveBill.getClass().getSimpleName();
		/**
		 * 4：从Session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人
			    * inputUser是流程变量的名称，
			    * 获取的办理人是流程变量的值
		 */
		Map<String, Object> variables = new HashMap<String,Object>();
		variables.put("inputUser", ((User)ActionContext.getContext().getSession().get("user")).getLoginName());//表示惟一用户
		/**
		 * 5：	(1)使用流程变量设置字符串（格式：LeaveBill.id的形式），通过设置，让启动的流程（流程实例）关联业务
   				(2)使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段），让启动的流程（流程实例）关联业务
		 */
		//格式：LeaveBill.id的形式（使用流程变量）
		String objId = key+"."+leaveBillId;
		variables.put("objId", objId);
		//6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		runtimeService.startProcessInstanceByKey(key,objId,variables);
		
	}

	/**使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task>*/
	public List<Task> findTaskListByName(String loginName) {
		List<Task> list = taskService.createTaskQuery()
						.taskCandidateOrAssigned(loginName)// 备选或者指定
						//.taskAssignee(loginName)//指定个人任务查询
						.orderByTaskCreateTime().asc()//
						.list();
		return list;
	}
	
	/**使用任务ID，查找请假单ID，从而获取请假单信息*/
	public LeaveBill findLeaveBillByTaskId(String taskId) {
		//1：使用任务ID，查询任务对象Task
		Task task = taskService.createTaskQuery()//
						.taskId(taskId)//使用任务ID查询
						.singleResult();
		//2：使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
						.processInstanceId(processInstanceId)//使用流程实例ID查询
						.singleResult();
		//4：使用流程实例对象获取BUSINESS_KEY
		String buniness_key = pi.getBusinessKey();
		//5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
		int id = 0;
		if(ValidateUtil.isValid(buniness_key)){
			//截取字符串，取buniness_key小数点的第2个值
			id = Integer.parseInt(buniness_key.split("\\.")[1]);
		}
		//查询请假单对象
		//使用hql语句：from LeaveBill o where o.id=1
		LeaveBill leaveBill = leaveBillService.get(id);
		return leaveBill;
	}
	
	/**已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
	public List<String> findOutComeListByTaskId(String taskId) {
		//返回存放连线的名称集合
		List<String> list = new ArrayList<String>();
		//1:使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//2：获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//3：查询ProcessDefinitionEntiy对象（对应bpmn文件）
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		//使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(processInstanceId)//使用流程实例ID查询
					.singleResult();
		//获取当前活动的id
		String activityId = pi.getActivityId();
		//4：获取当前的活动
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		//5：获取当前活动完成之后连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if(pvmList!=null && pvmList.size()>0){
			for(PvmTransition pvm:pvmList){
				String name = (String) pvm.getProperty("name");
				if(ValidateUtil.isValid(name)){
					list.add(name);
				}
				else{
					list.add("默认提交");
				}
			}
		}
		return list;
	}
	
	/**获取批注信息，传递的是当前任务ID，获取历史任务ID对应的批注*/
	public List<Comment> findCommentByTaskId(String taskId) {
		List<Comment> list = new ArrayList<Comment>();
		//使用当前的任务ID，查询当前流程对应的历史任务ID
		//使用当前任务ID，获取当前任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//使用任务ID查询
				.singleResult();
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
//		//使用流程实例ID，查询历史任务，获取历史任务对应的每个任务ID
//		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()//历史任务表查询
//						.processInstanceId(processInstanceId)//使用流程实例ID查询
//						.list();
//		//遍历集合，获取每个任务ID
//		if(htiList!=null && htiList.size()>0){
//			for(HistoricTaskInstance hti:htiList){
//				//任务ID
//				String htaskId = hti.getId();
//				//获取批注信息
//				List<Comment> taskList = taskService.getTaskComments(htaskId);//对用历史完成后的任务ID
//				list.addAll(taskList);
//			}
//		}
		list = taskService.getProcessInstanceComments(processInstanceId);
		return list;
	}
	
	/**指定连线的名称完成任务*/
	public void saveSubmitTask(String taskId, String outcome, String comment,
			Integer leaveBillId) {
		//获取当前用户名
		String loginName = ((User)ActionContext.getContext().getSession().get("user")).getLoginName();
		/**
		 * 1：在完成之前，添加一个批注信息，向act_hi_comment表中添加数据，用于记录对当前申请人的一些审核信息
		 */
		//使用任务ID，查询任务对象，获取流程流程实例ID
		Task task = taskService.createTaskQuery()//
						.taskId(taskId)//使用任务ID查询
						.singleResult();
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		/**
		 * 注意：添加批注的时候，由于Activiti底层代码是使用：
		 * 		String userId = Authentication.getAuthenticatedUserId();
			    CommentEntity comment = new CommentEntity();
			    comment.setUserId(userId);
			  所有需要从Session中获取当前登录人，作为该任务的办理人（审核人），对应act_hi_comment表中的User_ID的字段，不过不添加审核人，该字段为null
			 所以要求，添加配置执行使用Authentication.setAuthenticatedUserId();添加当前任务的审核人
		 * */
		Authentication.setAuthenticatedUserId(loginName);
		taskService.addComment(taskId, processInstanceId, comment);
		/**
		 * 2：如果连线的名称是“默认提交”，那么就不需要设置，如果不是，就需要设置流程变量
		 * 在完成任务之前，设置流程变量，按照连线的名称，去完成任务
				 流程变量的名称：outcome
				 流程变量的值：连线的名称
		 */
		Map<String, Object> variables = new HashMap<String,Object>();
		if(outcome!=null && !outcome.equals("默认提交")){
			variables.put("outcome", outcome);
		}

		//3：使用任务ID，完成当前人的个人任务，同时设置流程变量，并拾取任务
		taskService.claim(taskId, loginName);
		taskService.complete(taskId, variables);
		//4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		
		/**
		 * 5：在完成任务之后，判断流程是否结束
   			如果流程结束了，更新请假单表的状态从1变成2（审核中-->审核完成）
		 */
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
						.processInstanceId(processInstanceId)//使用流程实例ID查询
						.singleResult();
		//流程结束了
		if(pi==null){
			//更新请假单表的状态从1变成2（审核中-->审核完成）
			LeaveBill leaveBill = leaveBillService.get(leaveBillId);
			leaveBill.setState(2);
			leaveBillService.saveOrUpdate(leaveBill);
		}
	}
	
	/**使用请假单ID，查询历史批注信息*/
	public List<Comment> findCommentByLeaveBillId(Integer leaveBillId) {
		//使用请假单ID，查询请假单对象
		LeaveBill leaveBill = leaveBillService.get(leaveBillId);
		//获取对象的名称
		String objectName = leaveBill.getClass().getSimpleName();
		//组织流程表中的字段中的值
		String objId = objectName+"."+leaveBillId;
		
		/**1:使用历史的流程实例查询，返回历史的流程实例对象，获取流程实例ID*/
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()//对应历史的流程实例表
						.processInstanceBusinessKey(objId)//使用BusinessKey字段查询
						.singleResult();
		//流程实例ID
		String processInstanceId = hpi.getId();
		/**2:使用历史的流程变量查询，返回历史的流程变量的对象，获取流程实例ID*/
//		HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()//对应历史的流程变量表
//						.variableValueEquals("objId", objId)//使用流程变量的名称和流程变量的值查询
//						.singleResult();
//		//流程实例ID
//		String processInstanceId = hvi.getProcessInstanceId();
		List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
		return list;
	}
	
	/**一：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象*/
	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
		//使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//查询流程定义的对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//创建流程定义查询对象，对应表act_re_procdef 
					.processDefinitionId(processDefinitionId)//使用流程定义ID查询
					.singleResult();
		return pd;
	}
	
	/**
	 * 二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中
		 map集合的key：表示坐标x,y,width,height
		 map集合的value：表示坐标对应的值
	 */
	public Map<String, Object> findCoordingByTask(String taskId) {
		//存放坐标
		Map<String, Object> map = new HashMap<String,Object>();
		//使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//获取流程定义的ID
		String processDefinitionId = task.getProcessDefinitionId();
		//获取流程定义的实体对象（对应.bpmn文件中的数据）
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
		//流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//创建流程实例查询
					.processInstanceId(processInstanceId)//使用流程实例ID查询
					.singleResult();
		//获取当前活动的ID
		String activityId = pi.getActivityId();
		//获取当前活动对象
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);//活动ID
		//获取坐标
		map.put("x", activityImpl.getX());
		map.put("y", activityImpl.getY());
		map.put("width", activityImpl.getWidth());
		map.put("height", activityImpl.getHeight());
		return map;
	}
	
}
