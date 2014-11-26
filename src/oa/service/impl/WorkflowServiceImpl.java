package oa.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import oa.service.IWorkflowService;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("workflowService")
@Transactional
public class WorkflowServiceImpl implements IWorkflowService {
	
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

	/**�������̶���*/
	public void saveNewDeploy(File file, String filename) {
		try {
			//��File���͵��ļ�ת����ZipInputStream��
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
			repositoryService.createDeployment()//�����������
							.name(filename)//��Ӳ�������
							.addZipInputStream(zipInputStream)//
							.deploy();//��ɲ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**��ѯ���������Ϣ����Ӧ��act_re_deployment��*/
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery()//������������ѯ
							.orderByDeploymenTime().asc()//
							.list();
		return list;
	}
	
	/**��ѯ���̶������Ϣ����Ӧ��act_re_procdef��*/
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//�������̶����ѯ
							.orderByProcessDefinitionVersion().asc()//
							.list();
		return list;
	}
	
}
