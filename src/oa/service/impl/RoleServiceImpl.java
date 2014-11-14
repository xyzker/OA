package oa.service.impl;

import oa.model.Privilege;
import oa.model.Role;
import oa.service.IRoleService;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("roleService")
@Transactional
public class RoleServiceImpl extends ServiceImpl<Role> implements IRoleService {

	public Role getRoleWithPrivileges(int id) {
		Role role = this.get(id);
		for(Privilege privilege : role.getPrivileges()){
			privilege.getId();
		}
		return role;
	}

	public void saveOrUpdate(int roleId, String roleName, String roleDescription) {
		dao.executeUpdateByHQL("update Role r set r.name = ?, r.description = ? where r.id = ? ", 
				roleName, roleDescription, roleId);
	}
	
	
}
