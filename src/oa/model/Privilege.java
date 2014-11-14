package oa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * 权限
 */
@Entity
public class Privilege {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String url;
	
	@ManyToMany(mappedBy = "privileges")
	private Set<Role> roles = new HashSet<Role>();
	
	@ManyToOne
	private Privilege parent;
	@OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
	@OrderBy("id asc")		//每次查询时先根据id排序
	private Set<Privilege> children = new HashSet<Privilege>();
	
	public Privilege(){
		
	}
	
	public Privilege(String name, String url, Privilege parent) {
		super();
		this.name = name;
		this.url = url;
		this.parent = parent;
	}
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Privilege getParent() {
		return parent;
	}
	public void setParent(Privilege parent) {
		this.parent = parent;
	}
	public Set<Privilege> getChildren() {
		return children;
	}
	public void setChildren(Set<Privilege> children) {
		this.children = children;
	}
	
}
