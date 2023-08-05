package com.mukss.eventweb.services;

import com.mukss.eventweb.entities.Role;

public interface RoleService {
	public long count();
	
	public Role save(Role role);
}
