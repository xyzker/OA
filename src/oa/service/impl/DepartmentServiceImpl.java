package oa.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import oa.model.Department;
import oa.service.IDepartmentService;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("departmentService")
@Transactional
public class DepartmentServiceImpl extends ServiceImpl<Department> implements IDepartmentService {

	public List<Department> findChildren(Department parent) {
		return dao.list("from Department d where d.parent = ?", parent);
	}

	public List<Department> findTopList() {
		return dao.list("from Department d where d.parent is null");
	}
	
	public List<Department> getAllDepartments() {
		List<Department> topList = this.findTopList();
		List<Department> list = new ArrayList<Department>();
		walkDepartmentTreeList(topList, "┣", list);
		return list;
	}
	
	/**
	 * 遍历部门树，把遍历出的部门信息放到指定的集合中
	 * 
	 * @param topList
	 */
	private void walkDepartmentTreeList(Collection<Department> topList, String prefix, List<Department> list) {
		for (Department top : topList) {
			// 顶点
			Department copy = new Department(); // 使用副本，因为原对象在Session中
			copy.setId(top.getId());
			copy.setName(prefix + top.getName());
			list.add(copy); // 把副本添加到同一个集合中

			// 子树
			walkDepartmentTreeList(top.getChildren(), "　" + prefix, list); // 使用全角的空格
		}
	}
	
}
