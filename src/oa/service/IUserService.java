package oa.service;

import java.util.List;

import oa.model.User;

public interface IUserService extends IService<User> {
	public List<User> findAllUsersWithRoles();
	public User getUserWithRoles(int id);
}
