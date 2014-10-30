package oa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/*
 * ��λ
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
	
}
