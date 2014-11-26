package oa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * 部门
 */
@Entity
public class Department {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String description;
	
	@OneToMany(mappedBy = "department")
	private Set<User> users = new HashSet<User>();
	@ManyToOne
	private Department parent;
	@OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
	@OrderBy("id asc")		//每次查询时先根据id排序
	private Set<Department> children =  new HashSet<Department>();
	
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
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Department getParent() {
		return parent;
	}
	public void setParent(Department parent) {
		this.parent = parent;
	}
	public Set<Department> getChildren() {
		return children;
	}
	public void setChildren(Set<Department> children) {
		this.children = children;
	}
	
}
