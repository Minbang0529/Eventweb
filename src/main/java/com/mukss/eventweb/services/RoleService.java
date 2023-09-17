package com.mukss.eventweb.services;

import java.util.Optional;

import com.mukss.eventweb.entities.Role;

public interface RoleService {
	public long count();
	
	public Role save(Role role);
	
	public Optional<Role> findByname(String name);
}
