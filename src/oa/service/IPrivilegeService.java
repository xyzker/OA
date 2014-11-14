package oa.service;

import java.util.List;

import oa.model.Privilege;

public interface IPrivilegeService extends IService<Privilege> {
	
	public List<Privilege> findTopListWithChildren();

	public List<String> getAllPrivilegeUrls();

}
