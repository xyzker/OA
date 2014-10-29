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
		walkDepartmentTreeList(topList, "��", list);
		return list;
	}
	
	/**
	 * �������������ѱ������Ĳ�����Ϣ�ŵ�ָ���ļ�����
	 * 
	 * @param topList
	 */
	private void walkDepartmentTreeList(Collection<Department> topList, String prefix, List<Department> list) {
		for (Department top : topList) {
			// ����
			Department copy = new Department(); // ʹ�ø�������Ϊԭ������Session��
			copy.setId(top.getId());
			copy.setName(prefix + top.getName());
			list.add(copy); // �Ѹ�����ӵ�ͬһ��������

			// ����
			walkDepartmentTreeList(top.getChildren(), "��" + prefix, list); // ʹ��ȫ�ǵĿո�
		}
	}
	
}
