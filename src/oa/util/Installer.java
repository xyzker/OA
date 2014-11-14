/*package oa.util;

import javax.annotation.Resource;

import oa.model.Privilege;
import oa.service.IPrivilegeService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//初始化超级管理员与权限
@Component
public class Installer {
	
	@Resource
	private SessionFactory sessionFactory;
	
	@Resource
	private IPrivilegeService privilegeService;

	@Transactional
	public void install(){
		Session session = sessionFactory.getCurrentSession();
		
		//保存超级管理员用户
		User user = new User();
		user.setLoginName("admin");
		user.setName("超级管理员");
		user.setPassword(DigestUtils.md5Hex("admin"));
		session.save(user);
		
		//保存权限数据
		Privilege menu, menu1, menu2, menu3;
		
		menu = new Privilege("系统管理", null, null);
		menu1 = new Privilege("岗位管理", "/role/list", menu);
		menu2 = new Privilege("部门管理", "/department/list", menu);
		menu3 = new Privilege("用户管理", "/user/list", menu);
		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		session.save(menu3);
		
		session.save(new Privilege("岗位列表", "/role/list", menu1));
		session.save(new Privilege("岗位删除", "/role/delete", menu1));
		session.save(new Privilege("岗位添加", "/role/addUI", menu1));
		session.save(new Privilege("岗位修改", "/role/editUI", menu1));
		session.save(new Privilege("岗位更新", "/role/saveOrUpdate", menu1));
		
		session.save(new Privilege("部门列表", "/department/list", menu2));
		session.save(new Privilege("部门删除", "/department/delete", menu2));
		session.save(new Privilege("部门添加", "/department/addUI", menu2));
		session.save(new Privilege("部门修改", "/department/editUI", menu2));
		session.save(new Privilege("部门更新", "/department/saveOrUpdate", menu2));

		session.save(new Privilege("用户列表", "/user/list", menu3));
		session.save(new Privilege("用户删除", "/user/delete", menu3));
		session.save(new Privilege("用户添加", "/user/addUI", menu3));
		session.save(new Privilege("用户修改", "/user/editUI", menu3));
		session.save(new Privilege("用户更新", "/user/saveOrUpdate", menu3));
		session.save(new Privilege("初始化密码", "/user/initPassword", menu3));
		
		Privilege parent = privilegeService.get(2);
		Privilege privilege = new Privilege("设置权限","/role/setPrivilegeUI",parent);
		session.save(privilege);
		
	}
	
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		Installer installer = (Installer) ac.getBean("installer");
		installer.install();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setPrivilegeService(IPrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	public IPrivilegeService getPrivilegeService() {
		return privilegeService;
	}

}
*/