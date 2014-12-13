package oa.service.impl;

import java.util.Set;

import oa.model.Privilege;
import oa.model.Role;
import oa.model.User;
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
	
	/**
	 * �ҵ����е���Ŀ����
	 */
	public Set<User> findAllPM() {
		Role role = (Role) this.uniqueResult("from Role r where r.name = ?", "��Ŀ����");
		return role.getUsers();
	}
	
	/**
	 * �ҵ����е��ܾ���
	 */
	public Set<User> findAllCEO() {
		Role role = (Role) this.uniqueResult("from Role r where r.name = ?", "�ܾ���");
		return role.getUsers();
	}
	
	
}
