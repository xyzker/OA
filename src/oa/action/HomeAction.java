package oa.action;

import javax.annotation.Resource;

import oa.model.User;
import oa.service.IWorkflowService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("oa")
public class HomeAction extends ActionSupport  {
	private static final long serialVersionUID = -6486564633921033953L;
	
	/**����������Ŀ*/
	private int taskListSize;
	
	@Resource
	private IWorkflowService workflowService;
	
	@Action(value="/home/index")
	public String index(){
		return SUCCESS;
	}
	
	@Action(value="/home/top")
	public String top(){
		User user = (User)ActionContext.getContext().getSession().get("user");
		if(user != null){
			taskListSize = workflowService.findTaskListByName(user.getLoginName()).size(); 
		}
		return SUCCESS;
	}
	
	@Action(value="/home/left")
	public String left(){
		return SUCCESS;
	}
	
	@Action(value="/home/right")
	public String right(){
		return SUCCESS;
	}
	
	@Action(value="/home/bottom")
	public String bottom(){
		return SUCCESS;
	}

	public void setWorkflowService(IWorkflowService workflowService) {
		this.workflowService = workflowService;
	}

	public IWorkflowService getWorkflowService() {
		return workflowService;
	}

	public void setTaskListSize(int taskListSize) {
		this.taskListSize = taskListSize;
	}

	public int getTaskListSize() {
		return taskListSize;
	}

}
