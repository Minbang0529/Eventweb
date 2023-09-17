package com.mukss.eventweb.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mukss.eventweb.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findByuserName(String userName);
    
    public List<User> findBymembership(String membership);
}