package com.mukss.eventweb.services;

import java.util.Optional;
import com.mukss.eventweb.entities.Membership;

public interface MembershipService {
    public long count();

    public Iterable<Membership> findAll();

    public Optional<Membership> findById(long id);

    public Membership save(Membership membership);

    public void deleteById(long id);

    public void deleteAll();
}
