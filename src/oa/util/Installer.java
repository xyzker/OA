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

//��ʼ����������Ա��Ȩ��
@Component
public class Installer {
	
	@Resource
	private SessionFactory sessionFactory;
	
	@Resource
	private IPrivilegeService privilegeService;

	@Transactional
	public void install(){
		Session session = sessionFactory.getCurrentSession();
		
		//���泬������Ա�û�
		User user = new User();
		user.setLoginName("admin");
		user.setName("��������Ա");
		user.setPassword(DigestUtils.md5Hex("admin"));
		session.save(user);
		
		//����Ȩ������
		Privilege menu, menu1, menu2, menu3;
		
		menu = new Privilege("ϵͳ����", null, null);
		menu1 = new Privilege("��λ����", "/role/list", menu);
		menu2 = new Privilege("���Ź���", "/department/list", menu);
		menu3 = new Privilege("�û�����", "/user/list", menu);
		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		session.save(menu3);
		
		session.save(new Privilege("��λ�б�", "/role/list", menu1));
		session.save(new Privilege("��λɾ��", "/role/delete", menu1));
		session.save(new Privilege("��λ���", "/role/addUI", menu1));
		session.save(new Privilege("��λ�޸�", "/role/editUI", menu1));
		session.save(new Privilege("��λ����", "/role/saveOrUpdate", menu1));
		
		session.save(new Privilege("�����б�", "/department/list", menu2));
		session.save(new Privilege("����ɾ��", "/department/delete", menu2));
		session.save(new Privilege("�������", "/department/addUI", menu2));
		session.save(new Privilege("�����޸�", "/department/editUI", menu2));
		session.save(new Privilege("���Ÿ���", "/department/saveOrUpdate", menu2));

		session.save(new Privilege("�û��б�", "/user/list", menu3));
		session.save(new Privilege("�û�ɾ��", "/user/delete", menu3));
		session.save(new Privilege("�û����", "/user/addUI", menu3));
		session.save(new Privilege("�û��޸�", "/user/editUI", menu3));
		session.save(new Privilege("�û�����", "/user/saveOrUpdate", menu3));
		session.save(new Privilege("��ʼ������", "/user/initPassword", menu3));
		
		Privilege parent = privilegeService.get(2);
		Privilege privilege = new Privilege("����Ȩ��","/role/setPrivilegeUI",parent);
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