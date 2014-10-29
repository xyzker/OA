package oa.service;

import java.util.List;

import oa.model.Department;

public interface IDepartmentService extends IService<Department> {
	public List<Department> findTopList();
	
	public List<Department> findChildren(Department parent);
	/**
	 * �õ����ṹ�����в���
	 */
	public List<Department> getAllDepartments();
	
}
