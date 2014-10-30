package oa.test;

import javax.annotation.Resource;

import oa.model.Role;
import oa.model.User;
import oa.service.IRoleService;
import oa.service.IUserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Transactional
public class ModelTest {
	
	@Resource
	private IUserService userService;
	
	@Resource
	private IRoleService roleService;
	
	@Test
	public void testModel(){
		User u = new User();
		u.setName("hehe");
		Role r = new Role();
		r.setName("xixi");
		u.getRoles().add(r);
		userService.saveOrUpdate(u);
		
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}
	
}
