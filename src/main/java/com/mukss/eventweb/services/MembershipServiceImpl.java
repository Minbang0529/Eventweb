package com.mukss.eventweb.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukss.eventweb.entities.MembershipUser;
import com.mukss.eventweb.repositories.MembershipRepository;

@Service
public class MembershipServiceImpl implements MembershipService {
	
	@Autowired
	private MembershipRepository membershipRepository;

	@Override
	public long count() {
		return membershipRepository.count();
	}

	@Override
	public Iterable<MembershipUser> findAll() {
		return membershipRepository.findAll();
	}

	@Override
	public Optional<MembershipUser> findById(long id) {
		return membershipRepository.findById(id);
	}

	@Override
	public MembershipUser save(MembershipUser user) {
		return membershipRepository.save(user);
	}

	@Override
	public void updateUser(MembershipUser user, long id) {
		MembershipUser userInDB = membershipRepository.findById(id).get();
		userInDB.setFirstName(user.getFirstName());
		userInDB.setLastName(user.getLastName());
		userInDB.setUserName(user.getUserName());
		userInDB.setPassword(user.getPassword());
		userInDB.setEmail(user.getEmail());
		userInDB.setEnabled(user.isEnabled());
//		userInDB.setRoles(user.getRoles());
		membershipRepository.save(userInDB);
	}

	@Override
	public Optional<MembershipUser> findByName(String userName) {
		return membershipRepository.findByuserName(userName);
	}



}
