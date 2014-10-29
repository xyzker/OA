package oa.service;

import java.util.List;

import oa.model.Department;

public interface IDepartmentService extends IService<Department> {
	public List<Department> findTopList();
	
	public List<Department> findChildren(Department parent);
	/**
	 * 得到树结构的所有部门
	 */
	public List<Department> getAllDepartments();
	
}
