package oa.action;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oa.model.LeaveBill;
import oa.model.User;
import oa.service.ILeaveBillService;
import oa.service.IWorkflowService;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("oa")
public class WorkflowAction extends ActionSupport {
	private static final long serialVersionUID = -2666703414436290540L;
	
	@Resource
	private IWorkflowService workflowService;
	@Resource
	private ILeaveBillService leaveBillService;
	
	private List<Deployment> depList = new ArrayList<Deployment>();
	private List<ProcessDefinition> pdList =  new ArrayList<ProcessDefinition>();
	private List<Task> taskList = new ArrayList<Task>();
	
	private File file;
	private String filename;
	
	private String deploymentId;
	private String imageName;
	
	private int leaveBillId;
	private String taskId;
	private LeaveBill leaveBill;
	
	private List<String> outcomeList = new ArrayList<String>();
	private List<Comment> commentList = new ArrayList<Comment>();
	
	private String outcome;	//连线的名称
	private String comment; //批注信息
	
	private String message;	//辅助信息
	
	private Map<String, Object> acs = new HashMap<String, Object>();
	
	/**
	 * 部署管理首页显示
	 */
	@Action(value="/workflow/deployHome")
	public String deployHome(){
		//1:查询部署对象信息，对应表（act_re_deployment）
		depList = getWorkflowService().findDeploymentList();
		//2:查询流程定义的信息，对应表（act_re_procdef）
		pdList = getWorkflowService().findProcessDefinitionList();
		return SUCCESS;
	}
	
	/**
	 * 发布流程
	 */
	@Action(value="/workflow/newdeploy",results={@Result(type="redirectAction", 
			location="deployHome")})
	public String newdeploy(){
		//完成部署
		workflowService.saveNewDeploy(file,filename);
		return SUCCESS;
	}
	
	/**
	 * 查看流程图
	 * @throws Exception 
	 */
	@Action(value="/workflow/viewImage")
	public String viewImage() throws Exception{
		//获取资源文件表（act_ge_bytearray）中资源图片输入流InputStream
		InputStream in = workflowService.findImageInputStream(getDeploymentId(),getImageName());
		//从response对象获取输出流
		OutputStream out = ServletActionContext.getResponse().getOutputStream();
		//将输入流中的数据读取出来，写到输出流中
		for(int b=-1;(b=in.read())!=-1;){
			out.write(b);
		}
		out.close();
		in.close();
		//将图写到页面上，用输出流写
		return null;
	}
	
	/**
	 * 删除部署信息
	 */
	@Action(value="/workflow/delDeployment", results={@Result(type="redirectAction", 
			location="deployHome")})
	public String delDeployment(){
		workflowService.deleteDeploymentById(deploymentId);
		return SUCCESS;
	}
	
	// 启动流程
	@Action(value="/workflow/startProcess", results={@Result(type="redirectAction", 
			location="listTask")})
	public String startProcess(){
		//更新请假状态，启动流程实例，让启动的流程实例关联业务
		workflowService.saveStartProcess(leaveBillId);
		message = "complete";
		return SUCCESS;
	}
	
	/**
	 * 任务管理首页显示
	 */
	@Action(value="/workflow/listTask")
	public String listTask(){
		//1：从Session中获取当前用户登陆名
		String loginName = ((User)ActionContext.getContext().getSession().get("user")).getLoginName();
		//2：使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task>
		taskList = workflowService.findTaskListByName(loginName); 
		return SUCCESS;
	}
	
	/**
	 * 打开任务表单
	 */
	@Action(value="/workflow/viewTaskForm")
	public String viewTaskForm(){
		/**一：使用任务ID，查找请假单ID，从而获取请假单信息*/
		leaveBill = workflowService.findLeaveBillByTaskId(taskId);
		/**二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
		outcomeList = workflowService.findOutComeListByTaskId(taskId);
		/**三：查询所有历史审核人的审核信息，帮助当前人完成审核，返回List<Comment>*/
		commentList = workflowService.findCommentByTaskId(taskId);
		return SUCCESS;
	}
	
	/**
	 * 提交任务
	 */
	@Action(value="/workflow/submitTask", results={@Result(type="redirectAction", 
			location="listTask")})
	public String submitTask(){
		workflowService.saveSubmitTask(taskId, outcome, comment, leaveBill.getId());
		message = "complete";
		return "listTask";
	}

	// 查看历史的批注信息
	@Action(value="/workflow/viewHisComment")
	public String viewHisComment(){
		//1：使用请假单ID，查询请假单对象，将对象放置到栈顶，支持表单回显
		leaveBill = leaveBillService.get(leaveBill.getId());
		//2：使用请假单ID，查询历史的批注信息
		commentList = workflowService.findCommentByLeaveBillId(leaveBill.getId());
		return "viewHisComment";
	}
	
	/**
	 * 查看当前流程图（查看当前活动节点，并使用红色的框标注）
	 */
	@Action(value="/workflow/viewCurrentImage")
	public String viewCurrentImage(){
		/**一：查看流程图*/
		//1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
		ProcessDefinition pd = workflowService.findProcessDefinitionByTaskId(taskId);
		//  /workflow/viewImage?deploymentId=<s:property value='deploymentId'/>&imageName=<s:property value='imageName'/>
		deploymentId =  pd.getDeploymentId();
		imageName = pd.getDiagramResourceName();
		/**二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中*/
		acs = workflowService.findCoordingByTask(taskId);
		return SUCCESS;
	}
	
	public void setWorkflowService(IWorkflowService workflowService) {
		this.workflowService = workflowService;
	}

	public IWorkflowService getWorkflowService() {
		return workflowService;
	}

	public void setDepList(List<Deployment> depList) {
		this.depList = depList;
	}

	public List<Deployment> getDepList() {
		return depList;
	}

	public void setPdList(List<ProcessDefinition> pdList) {
		this.pdList = pdList;
	}

	public List<ProcessDefinition> getPdList() {
		return pdList;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setLeaveBillId(int leaveBillId) {
		this.leaveBillId = leaveBillId;
	}

	public int getLeaveBillId() {
		return leaveBillId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setLeaveBill(LeaveBill leaveBill) {
		this.leaveBill = leaveBill;
	}

	public LeaveBill getLeaveBill() {
		return leaveBill;
	}

	public void setOutcomeList(List<String> outcomeList) {
		this.outcomeList = outcomeList;
	}

	public List<String> getOutcomeList() {
		return outcomeList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setLeaveBillService(ILeaveBillService leaveBillService) {
		this.leaveBillService = leaveBillService;
	}

	public ILeaveBillService getLeaveBillService() {
		return leaveBillService;
	}

	public void setAcs(Map<String, Object> acs) {
		this.acs = acs;
	}

	public Map<String, Object> getAcs() {
		return acs;
	}

}
