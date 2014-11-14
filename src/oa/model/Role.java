package oa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

/*
 * ¸ÚÎ»
 */
@Entity
public class Role {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String description;
	
	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new HashSet<User>();
	
	@ManyToMany
	@JoinTable(name="role_privilege", joinColumns = {@JoinColumn(name = "role_id")}, 
			inverseJoinColumns = {@JoinColumn(name = "privilege_id")})
	@OrderBy("id asc")
	private Set<Privilege> privileges = new HashSet<Privilege>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	
}
