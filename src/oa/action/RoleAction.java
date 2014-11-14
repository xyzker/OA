package oa.action;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import oa.model.Privilege;
import oa.model.Role;
import oa.service.IPrivilegeService;
import oa.service.IRoleService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("oa")
public class RoleAction extends ActionSupport {
	private static final long serialVersionUID = 3203227168651374872L;
	
	@Resource
	private IRoleService roleService;
	private List<Role> roleList;
	private Role role;
	
	@Resource
	private IPrivilegeService privilegeService;
	private Integer[] privilegeIds;
	private List<Privilege> privilegeList;
	
	private String roleName;
	private String roleDescription;
	
	@Action(value="/role/list")
	public String list(){
		roleList = roleService.findAllEntities();
		return SUCCESS;
	}
	
	@Action(value="/role/addUI", results={@Result(location="/WEB-INF/content/role/saveUI.jsp")})
	public String addUI(){
		return SUCCESS;
	}
	
	@Action(value="/role/editUI", results={@Result(location="/WEB-INF/content/role/saveUI.jsp")})
	public String editUI(){
		role = roleService.get(role.getId());
		return SUCCESS;
	}
	
	@Action(value="/role/saveOrUpdate", results={@Result(type="redirectAction", location="list")})
	public String saveOrUpdate(){
		if(role.getId() == 0){		//此时为添加新用户
			roleService.saveOrUpdate(role);
		}
		roleService.saveOrUpdate(role.getId(), roleName, roleDescription);
		return SUCCESS;
	}
	
	@Action(value="/role/delete", results={@Result(type="redirectAction", location="list")})
	public String delete(){
		role = roleService.get(role.getId());
		roleService.delete(role);
		return SUCCESS;
	}
	
	@Action(value="/role/setPrivilegeUI")
	public String setPrivilegeUI(){
		setPrivilegeList(privilegeService.findAllEntities());
		role = roleService.getRoleWithPrivileges(role.getId());
		
		if(role.getPrivileges() != null){
			int index = 0;
			privilegeIds = new Integer[role.getPrivileges().size()];
			for(Privilege privilege : role.getPrivileges()){
				privilegeIds[index++] = privilege.getId();
			}
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value="/role/setPrivilege", results={@Result(type="redirectAction", location="list")})
	public String setPrivilege(){
		role = roleService.get(role.getId());
		
		setPrivilegeList(privilegeService.getByIds(privilegeIds));
		if(getPrivilegeList() != null){
			role.setPrivileges(new HashSet(getPrivilegeList()));
		} else {
			role.setPrivileges(null);
		}
		roleService.saveOrUpdate(role);
		return SUCCESS;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void setPrivilegeIds(Integer[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	public Integer[] getPrivilegeIds() {
		return privilegeIds;
	}


	public void setPrivilegeService(IPrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	public IPrivilegeService getPrivilegeService() {
		return privilegeService;
	}

	public void setPrivilegeList(List<Privilege> privilegeList) {
		this.privilegeList = privilegeList;
	}

	public List<Privilege> getPrivilegeList() {
		return privilegeList;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

}
