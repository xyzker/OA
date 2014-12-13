package oa.taskHandler;

import java.util.Set;

import oa.model.User;
import oa.service.IRoleService;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *	�ܾ���������� 
 */
public class CEOTaskHandler implements TaskListener {

	private static final long serialVersionUID = 5299101141623571287L;
	
	public void notify(DelegateTask delegateTask) {
		//��web�л�ȡspring����
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
		IRoleService roleService = (IRoleService) ac.getBean("roleService");
		Set<User> users = roleService.findAllCEO();
		for(User u :users){
			delegateTask.addCandidateUser(u.getLoginName());
		}
		
	}

}
