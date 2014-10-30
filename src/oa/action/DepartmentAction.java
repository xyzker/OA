package oa.action;

import java.util.List;

import javax.annotation.Resource;

import oa.model.Department;
import oa.service.IDepartmentService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("oa")
public class DepartmentAction extends ActionSupport {
	private static final long serialVersionUID = 3203227168651374872L;
	
	@Resource
	private IDepartmentService departmentService;
	private List<Department> departmentList;
	private Department department;
	private Department parent;
	
	@Action(value="/department/list")
	public String list(){
		try{
			parent = departmentService.get(parent.getId());
			if(parent == null){
				departmentList = departmentService.findTopList();
				return SUCCESS;
			}
			departmentList = departmentService.findChildren(parent);
			return SUCCESS;
		}catch(NullPointerException e){
			departmentList = departmentService.findTopList();
			return SUCCESS;
		}
	}
	
	@Action(value="/department/addUI", results={@Result(location="/WEB-INF/content/department/saveUI.jsp")})
	public String addUI(){
		departmentList = departmentService.getAllDepartments();
		return SUCCESS;
	}
	
	@Action(value="/department/editUI", results={@Result(location="/WEB-INF/content/department/saveUI.jsp")})
	public String editUI(){
		departmentList = departmentService.getAllDepartments();
		department = departmentService.get(department.getId());
		parent = department.getParent();
		return SUCCESS;
	}
	
	@Action(value="/department/saveOrUpdate", results={@Result(type="redirectAction", location="list", 
			params = {"parent.id", "${parent.id}"})})
	public String saveOrUpdate(){
		if(parent.getId() != -1){
			parent = departmentService.get(parent.getId());
			department.setParent(parent);
		}
		departmentService.saveOrUpdate(department);
		return SUCCESS;
	}
	
	@Action(value="/department/delete", results={@Result(type="redirectAction", location="list", 
			params = {"parent.id", "${parent.id}"})})
	public String delete(){
		department = departmentService.get(department.getId());
		departmentService.delete(department);
		return SUCCESS;
	}

	public IDepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public List<Department> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public Department getParent() {
		return parent;
	}

}
