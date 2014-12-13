package oa.service.impl;

import java.util.List;

import oa.model.Privilege;
import oa.model.Role;
import oa.model.User;
import oa.service.IUserService;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userService")
@Transactional
public class UserServiceImpl extends ServiceImpl<User> implements IUserService {

	public List<User> findAllUsersWithRoles() {
		List<User> users = this.findAllEntities();
		for(User user : users){
			for(Role role : user.getRoles()){
				role.getId();
			}
		}
		return users;
	}

	public User getUserWithRoles(int id) {
		User user =  dao.uniqueResult("from User u where u.id = ?", id);
		for(Role role : user.getRoles()){
			role.getId();
		}
		return user;
	}

	public User getUser(String loginName, String password) {
		return dao.uniqueResult("from User u where u.loginName = ? and u.password = ?", loginName, password);
	}

	public User getUserAllInformation(int id) {
		User user =  dao.uniqueResult("from User u where u.id = ?", id);
		for(Role role : user.getRoles()){
			for(Privilege privilege : role.getPrivileges()){
				privilege.getId();
			}
		}
		return user;
	}
}
