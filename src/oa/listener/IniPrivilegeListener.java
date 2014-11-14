package oa.listener;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import oa.model.Privilege;
import oa.service.IPrivilegeService;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/*
 * ��ʼ��Ȩ�޼���������spring��ʼ����ɺ���������.
 */
@Component
@SuppressWarnings("unchecked")
public class IniPrivilegeListener implements ApplicationListener, ServletContextAware {
	
	@Resource
	private IPrivilegeService privilegeService;
	
	private ServletContext servletContext;

	public void onApplicationEvent(ApplicationEvent arg0) {
		//������ˢ���¼�
		if(arg0 instanceof ContextRefreshedEvent){
			List<Privilege> topPrivilegeList = privilegeService.findTopListWithChildren();
			if(servletContext != null){
				servletContext.setAttribute("topPrivilegeList", topPrivilegeList);
				System.out.println("��ʼ������Ȩ�޵�Application��!!!!");
			}
			
			List<String> allPrivilegeUrls = privilegeService.getAllPrivilegeUrls();
			servletContext.setAttribute("allPrivilegeUrls", allPrivilegeUrls);
		}
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void setPrivilegeService(IPrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	public IPrivilegeService getPrivilegeService() {
		return privilegeService;
	}

}
