package com.mukss.eventweb.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mukss.eventweb.entities.Event;

public interface EventRepository extends CrudRepository<Event, Long>{
	
}
