package oa.service;

import oa.model.Role;

public interface IRoleService extends IService<Role> {
	
	public Role getRoleWithPrivileges(int id);

	public void saveOrUpdate(int roleId, String roleName, String roleDescription);
}
