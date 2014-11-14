package oa.service;

import java.util.List;

import oa.model.User;

public interface IUserService extends IService<User> {
	public List<User> findAllUsersWithRoles();
	public User getUserWithRoles(int id);
	public User getUser(String loginName, String password);
	public User getUserAllInformation(int id);
}
