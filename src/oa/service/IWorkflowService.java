package oa.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import oa.model.LeaveBill;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

public interface IWorkflowService {

	public List<Deployment> findDeploymentList();

	public List<ProcessDefinition> findProcessDefinitionList();

	public void saveNewDeploy(File file, String filename);

	public InputStream findImageInputStream(String deploymentId,
			String imageName);

	public void deleteDeploymentById(String deploymentId);

	public void saveStartProcess(int leaveBillId);

	public List<Task> findTaskListByName(String loginName);

	public LeaveBill findLeaveBillByTaskId(String taskId);

	public List<String> findOutComeListByTaskId(String taskId);

	public List<Comment> findCommentByTaskId(String taskId);

	public void saveSubmitTask(String taskId, String outcome, String comment,
			Integer leaveBillId);

	public List<Comment> findCommentByLeaveBillId(Integer id);

	public ProcessDefinition findProcessDefinitionByTaskId(String taskId);

	public Map<String, Object> findCoordingByTask(String taskId);

}
