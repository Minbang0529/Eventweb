package com.mukss.eventweb.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mukss.eventweb.entities.Event;

import com.mukss.eventweb.repositories.EventRepository;

public interface EventRepository extends CrudRepository<Event, Long>{
	
}
