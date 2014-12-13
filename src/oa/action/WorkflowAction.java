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
	
	private String outcome;	//���ߵ�����
	private String comment; //��ע��Ϣ
	
	private String message;	//������Ϣ
	
	private Map<String, Object> acs = new HashMap<String, Object>();
	
	/**
	 * ���������ҳ��ʾ
	 */
	@Action(value="/workflow/deployHome")
	public String deployHome(){
		//1:��ѯ���������Ϣ����Ӧ��act_re_deployment��
		depList = getWorkflowService().findDeploymentList();
		//2:��ѯ���̶������Ϣ����Ӧ��act_re_procdef��
		pdList = getWorkflowService().findProcessDefinitionList();
		return SUCCESS;
	}
	
	/**
	 * ��������
	 */
	@Action(value="/workflow/newdeploy",results={@Result(type="redirectAction", 
			location="deployHome")})
	public String newdeploy(){
		//��ɲ���
		workflowService.saveNewDeploy(file,filename);
		return SUCCESS;
	}
	
	/**
	 * �鿴����ͼ
	 * @throws Exception 
	 */
	@Action(value="/workflow/viewImage")
	public String viewImage() throws Exception{
		//��ȡ��Դ�ļ���act_ge_bytearray������ԴͼƬ������InputStream
		InputStream in = workflowService.findImageInputStream(getDeploymentId(),getImageName());
		//��response�����ȡ�����
		OutputStream out = ServletActionContext.getResponse().getOutputStream();
		//���������е����ݶ�ȡ������д���������
		for(int b=-1;(b=in.read())!=-1;){
			out.write(b);
		}
		out.close();
		in.close();
		//��ͼд��ҳ���ϣ��������д
		return null;
	}
	
	/**
	 * ɾ��������Ϣ
	 */
	@Action(value="/workflow/delDeployment", results={@Result(type="redirectAction", 
			location="deployHome")})
	public String delDeployment(){
		workflowService.deleteDeploymentById(deploymentId);
		return SUCCESS;
	}
	
	// ��������
	@Action(value="/workflow/startProcess", results={@Result(type="redirectAction", 
			location="listTask")})
	public String startProcess(){
		//�������״̬����������ʵ����������������ʵ������ҵ��
		workflowService.saveStartProcess(leaveBillId);
		message = "complete";
		return SUCCESS;
	}
	
	/**
	 * ���������ҳ��ʾ
	 */
	@Action(value="/workflow/listTask")
	public String listTask(){
		//1����Session�л�ȡ��ǰ�û���½��
		String loginName = ((User)ActionContext.getContext().getSession().get("user")).getLoginName();
		//2��ʹ�õ�ǰ�û�����ѯ����ִ�е��������ȡ��ǰ����ļ���List<Task>
		taskList = workflowService.findTaskListByName(loginName); 
		return SUCCESS;
	}
	
	/**
	 * �������
	 */
	@Action(value="/workflow/viewTaskForm")
	public String viewTaskForm(){
		/**һ��ʹ������ID��������ٵ�ID���Ӷ���ȡ��ٵ���Ϣ*/
		leaveBill = workflowService.findLeaveBillByTaskId(taskId);
		/**������֪����ID����ѯProcessDefinitionEntiy���󣬴Ӷ���ȡ��ǰ�������֮����������ƣ������õ�List<String>������*/
		outcomeList = workflowService.findOutComeListByTaskId(taskId);
		/**������ѯ������ʷ����˵������Ϣ��������ǰ�������ˣ�����List<Comment>*/
		commentList = workflowService.findCommentByTaskId(taskId);
		return SUCCESS;
	}
	
	/**
	 * �ύ����
	 */
	@Action(value="/workflow/submitTask", results={@Result(type="redirectAction", 
			location="listTask")})
	public String submitTask(){
		workflowService.saveSubmitTask(taskId, outcome, comment, leaveBill.getId());
		message = "complete";
		return "listTask";
	}

	// �鿴��ʷ����ע��Ϣ
	@Action(value="/workflow/viewHisComment")
	public String viewHisComment(){
		//1��ʹ����ٵ�ID����ѯ��ٵ����󣬽�������õ�ջ����֧�ֱ�����
		leaveBill = leaveBillService.get(leaveBill.getId());
		//2��ʹ����ٵ�ID����ѯ��ʷ����ע��Ϣ
		commentList = workflowService.findCommentByLeaveBillId(leaveBill.getId());
		return "viewHisComment";
	}
	
	/**
	 * �鿴��ǰ����ͼ���鿴��ǰ��ڵ㣬��ʹ�ú�ɫ�Ŀ��ע��
	 */
	@Action(value="/workflow/viewCurrentImage")
	public String viewCurrentImage(){
		/**һ���鿴����ͼ*/
		//1����ȡ����ID����ȡ�������ʹ����������ȡ���̶���ID����ѯ���̶������
		ProcessDefinition pd = workflowService.findProcessDefinitionByTaskId(taskId);
		//  /workflow/viewImage?deploymentId=<s:property value='deploymentId'/>&imageName=<s:property value='imageName'/>
		deploymentId =  pd.getDeploymentId();
		imageName = pd.getDiagramResourceName();
		/**�����鿴��ǰ�����ȡ���ڻ��Ӧ������x,y,width,height����4��ֵ��ŵ�Map<String,Object>��*/
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
