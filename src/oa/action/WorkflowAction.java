package oa.action;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import oa.service.IWorkflowService;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.struts2.ServletActionContext;
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
	
	private String deploymentId;
	private String imageName;
	
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

}
