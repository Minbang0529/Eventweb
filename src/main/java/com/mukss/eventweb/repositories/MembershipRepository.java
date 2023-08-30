package com.mukss.eventweb.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mukss.eventweb.entities.Membership;

public interface MembershipRepository extends CrudRepository<Membership, Long> {

}