package oa.service;

import java.util.Set;

import oa.model.Role;
import oa.model.User;

public interface IRoleService extends IService<Role> {
	
	public Role getRoleWithPrivileges(int id);

	public void saveOrUpdate(int roleId, String roleName, String roleDescription);

	public Set<User> findAllPM();

	public Set<User> findAllCEO();
}
