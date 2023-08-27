package com.mukss.eventweb.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukss.eventweb.entities.Attend;
import com.mukss.eventweb.repositories.AttendRepository;

@Service
public class AttendServiceImpl implements AttendService {

    @Autowired
    private AttendRepository attendRepository;

    @Override
    public long count() {
        return attendRepository.count();
    }

    @Override
    public Iterable<Attend> findAll() {
        return attendRepository.findAll();
    }

    @Override
    public Optional<Attend> findById(long id) {
        return attendRepository.findById(id);
    }

    @Override
    public Attend save(Attend attend) {
        return attendRepository.save(attend);
    }

    @Override
    public void deleteById(long id) {
        attendRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        attendRepository.deleteAll();
    }
}