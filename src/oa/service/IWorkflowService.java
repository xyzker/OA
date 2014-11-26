package oa.service;

import java.io.File;
import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

public interface IWorkflowService {

	public List<Deployment> findDeploymentList();

	public List<ProcessDefinition> findProcessDefinitionList();

	public void saveNewDeploy(File file, String filename);

}
