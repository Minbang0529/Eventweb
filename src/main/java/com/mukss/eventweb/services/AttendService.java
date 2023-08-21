package com.mukss.eventweb.services;

import java.util.Optional;

import com.mukss.eventweb.entities.Attend;

public interface AttendService {
    public long count();

    public Iterable<Attend> findAll();

    public Optional<Attend> findById(long id);

    public Attend save(Attend attend);

    public void deleteById(long id);

    public void deleteAll();
}
