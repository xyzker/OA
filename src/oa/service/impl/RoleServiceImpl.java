package oa.service.impl;

import oa.model.Role;
import oa.service.IRoleService;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("roleService")
@Transactional
public class RoleServiceImpl extends ServiceImpl<Role> implements IRoleService {
	
	
}
