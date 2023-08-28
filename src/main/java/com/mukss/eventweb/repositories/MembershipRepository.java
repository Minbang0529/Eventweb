package com.mukss.eventweb.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mukss.eventweb.entities.MembershipUser;

public interface MembershipRepository extends CrudRepository<MembershipUser, Long> {
    public Optional<MembershipUser> findByuserName(String userName);
}

