package oa.action;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import oa.model.Department;
import oa.model.Role;
import oa.model.User;
import oa.service.IDepartmentService;
import oa.service.IRoleService;
import oa.service.IUserService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.commons.codec.digest.DigestUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("oa")
public class UserAction extends ActionSupport {
	private static final long serialVersionUID = 201441411977604149L;
	
	@Resource
	private IUserService userService;
	private List<User> userList;
	private User user;
	private Integer[] roleIds;
	
	@Resource
	private IDepartmentService departmentService;
	private List<Department> departmentList;
	private Department department;
	
	@Resource
	private IRoleService roleService;
	private List<Role> roleList;
	
	@Action(value="/user/list")
	public String list(){
		userList = userService.findAllUsersWithRoles();
		return SUCCESS;
	}
	
	@Action(value="/user/addUI", results={@Result(location="/WEB-INF/content/user/saveUI.jsp")})
	public String addUI(){
		departmentList = departmentService.getAllDepartments();
		roleList = roleService.findAllEntities();
		return SUCCESS;
	}
	
	@Action(value="/user/editUI", results={@Result(location="/WEB-INF/content/user/saveUI.jsp")})
	public String editUI(){
		departmentList = departmentService.getAllDepartments();
		roleList = roleService.findAllEntities();
		
		user = userService.getUserWithRoles(user.getId());
		if(user.getDepartment() != null){
			department = user.getDepartment();
		}
		if(user.getRoles() != null){
			int index = 0;
			roleIds = new Integer[user.getRoles().size()];
			for(Role role : user.getRoles()){
				getRoleIds()[index++] = role.getId();
			}
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value="/user/saveOrUpdate", results={@Result(type="redirectAction", location="list")})
	public String saveOrUpdate(){
		if(department.getId() != -1){
			department = departmentService.get(department.getId());
			user.setDepartment(department);
		} else {
			user.setDepartment(null);
		}
		
		roleList = roleService.getByIds(getRoleIds());
		if(roleList != null){
			user.setRoles(new HashSet(roleList));
		} else {
			user.setRoles(null);
		}
		
		if(user.getPassword() == null || user.getPassword().equals("")){
			String md5Password = DigestUtils.md5Hex("1234");
			user.setPassword(md5Password);	//初始化密码为1234
		}
		
		userService.saveOrUpdate(user);
		return SUCCESS;
	}
	
	@Action(value="/user/delete", results={@Result(type="redirectAction", location="list")})
	public String delete(){
		user = userService.get(user.getId());
		userService.delete(user);
		return SUCCESS;
	}
	
	@Action(value="/user/initPassword", results={@Result(type="redirectAction", location="list")})
	public String initPassword(){
		user = userService.get(user.getId());
		String md5Password = DigestUtils.md5Hex("1234");
		user.setPassword(md5Password);	//初始化密码为1234
		userService.saveOrUpdate(user);
		return SUCCESS;
	}
	
	@Action(value="/user/loginUI")
	public String loginUI(){
		return SUCCESS;
	}
	
	@Action(value="/user/login", results = {@Result(type="redirectAction", params = {
			"actionName","index","namespace","/"}), @Result(name="input",  location=
				"/WEB-INF/content/user/loginUI.jsp")})
	public String login(){
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		user = userService.getUser(user.getLoginName(), user.getPassword());
		if(user != null){
			user = userService.getUserAllInformation(user.getId());
			ActionContext.getContext().getSession().put("user", user);
			return SUCCESS;
		}
		else {
			addActionError("用户名或密码不正确！");
			return INPUT;
		}
	}
	
	@Action(value="/user/logout")
	public String logout(){
		ActionContext.getContext().getSession().put("user", null);
		return SUCCESS;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public IDepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

	public List<Department> getDepartmentList() {
		return departmentList;
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

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Department getDepartment() {
		return department;
	}

	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}

	public Integer[] getRoleIds() {
		return roleIds;
	}


}
