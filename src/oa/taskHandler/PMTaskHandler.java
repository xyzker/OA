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
 * 项目经理任务分配
 */
public class PMTaskHandler implements TaskListener {

	private static final long serialVersionUID = -6362821209334486353L;
	
	public void notify(DelegateTask delegateTask) {
		//从web中获取spring容器
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
		IRoleService roleService = (IRoleService) ac.getBean("roleService");
		Set<User> users = roleService.findAllPM();
		for(User u :users){
			delegateTask.addCandidateUser(u.getLoginName());
		}
	}


}
