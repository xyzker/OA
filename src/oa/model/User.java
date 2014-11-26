package oa.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * �û�
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
	
	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
	private List<LeaveBill> leaveBills = new ArrayList<LeaveBill>();
	
	private String loginName; // ��¼��
	private String password; // ����
	private String name; // ��ʵ����
	private String gender; // �Ա�
	private String phoneNumber; // �绰����
	private String email; // �����ʼ�
	private String description; // ˵��
	
	/**
	 * ����Ȩ�����Ƽ���û��Ƿ����ĳȨ��
	 */
	public boolean hasPrivilegeByName(String name){
		//��������Ա�����е�Ȩ��
		if(isAdmin()){
			return true;
		}
		
		//��ͨ�û�
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
	 * ����Ȩ��url����û��Ƿ����ĳȨ��
	 */
	public boolean hasPrivilegeByUrl(String url){
		//��������Ա�����е�Ȩ��
		if(isAdmin()){
			return true;
		}
		
		//��ͨ�û�
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
	 * ����Ƿ��ǹ���Ա 
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

	public void setLeaveBills(List<LeaveBill> leaveBills) {
		this.leaveBills = leaveBills;
	}

	public List<LeaveBill> getLeaveBills() {
		return leaveBills;
	}
	
	
}
