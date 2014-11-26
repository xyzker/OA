package oa.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import oa.service.IWorkflowService;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("oa")
public class WorkflowAction extends ActionSupport {
	private static final long serialVersionUID = -2666703414436290540L;
	
	@Resource
	private IWorkflowService workflowService;
	
	private List<Deployment> depList = new ArrayList<Deployment>();
	private List<ProcessDefinition> pdList =  new ArrayList<ProcessDefinition>();
	
	private File file;
	private String filename;
	
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

}
