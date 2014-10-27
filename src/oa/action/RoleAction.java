package oa.action;

import java.util.List;

import oa.model.Role;
import oa.service.IRoleService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("oa")
public class RoleAction extends ActionSupport {
	private static final long serialVersionUID = 3203227168651374872L;
	
	private IRoleService roleService;
	private List<Role> roleList;
	private Role role;
	
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
		roleService.saveOrUpdate(role);
		return SUCCESS;
	}
	
	@Action(value="/role/delete", results={@Result(type="redirectAction", location="list")})
	public String delete(){
		roleService.deleteById(role.getId());
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

}
