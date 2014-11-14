package oa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;

/*
 * 用户
 */
@Entity
public class User {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	private Department department;
	@ManyToMany
	@JoinTable(name="user_role", joinColumns = {@JoinColumn(name = "user_id")}, 
			inverseJoinColumns = {@JoinColumn(name = "role_id")})
	@OrderBy("id asc")
	private Set<Role> roles = new HashSet<Role>();
	
	private String loginName; // 登录名
	private String password; // 密码
	private String name; // 真实姓名
	private String gender; // 性别
	private String phoneNumber; // 电话号码
	private String email; // 电子邮件
	private String description; // 说明
	
	/**
	 * 根据权限名称检查用户是否具有某权限
	 */
	public boolean hasPrivilegeByName(String name){
		//超级管理员有所有的权限
		if(isAdmin()){
			return true;
		}
		
		//普通用户
		for(Role role : roles){
			for(Privilege privilege : role.getPrivileges()){
				if(privilege.getName().equals(name)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 根据权限url检查用户是否具有某权限
	 */
	public boolean hasPrivilegeByUrl(String url){
		//超级管理员有所有的权限
		if(isAdmin()){
			return true;
		}
		
		//普通用户
		for(Role role : roles){
			for(Privilege privilege : role.getPrivileges()){
				if(url.equals(privilege.getUrl())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 检查是否是管理员 
	 */
	public boolean isAdmin(){
		return "admin".equals(loginName);	
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
