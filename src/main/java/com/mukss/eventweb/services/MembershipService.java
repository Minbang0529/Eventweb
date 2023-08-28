package com.mukss.eventweb.services;

import java.util.Optional;

import com.mukss.eventweb.entities.MembershipUser;

public interface MembershipService {
	
	public long count();
	
	public Iterable<MembershipUser> findAll();
	
	public Optional<MembershipUser> findById(long id);
	
	public Optional<MembershipUser> findByName(String userName);
	
	public MembershipUser save(MembershipUser user);
	
	public void updateUser(MembershipUser user, long id);
}


