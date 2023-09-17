package com.mukss.eventweb.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mukss.eventweb.entities.Role;
import com.mukss.eventweb.entities.User;

public interface RoleRepository extends CrudRepository<Role, Long> {
	public Optional<Role> findByname(String roleName);
}
