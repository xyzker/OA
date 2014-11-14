package oa.service.impl;
import java.util.List;

import oa.model.Privilege;
import oa.service.IPrivilegeService;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("privilegeService")
@Transactional
public class PrivilegeServiceImpl extends ServiceImpl<Privilege> implements IPrivilegeService {

	public List<Privilege> findTopListWithChildren() {
		List<Privilege> privileges = dao.list("from Privilege p where p.parent is null");
		for(Privilege privilege : privileges){
			for(Privilege privilege2 : privilege.getChildren()){
				for(Privilege privilege3 : privilege2.getChildren()){
					privilege3.getId();
				}
			}
		}
		return privileges;
	}

	@SuppressWarnings("unchecked")
	public List<String> getAllPrivilegeUrls() {
		return (List<String>)dao.createQuery("select distinct p.url from Privilege p " +
				"where p.url is not null").list(); //去掉了重复的URL
	}
	
	
}
