package com.mukss.eventweb.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukss.eventweb.entities.Membership;
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
    public Iterable<Membership> findAll() {
        return membershipRepository.findAll();
    }

    @Override
    public Optional<Membership> findById(long id) {
        return membershipRepository.findById(id);
    }

    @Override
    public Membership save(Membership membership) {
        return membershipRepository.save(membership);
    }

    @Override
    public void deleteById(long id) {
    	membershipRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
    	membershipRepository.deleteAll();
    }
}