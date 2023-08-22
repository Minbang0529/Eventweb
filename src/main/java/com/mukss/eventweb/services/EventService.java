package com.mukss.eventweb.services;

import java.util.Optional;

import com.mukss.eventweb.entities.Event;

import org.springframework.web.multipart.MultipartFile;

public interface EventService {

public long count();
	
	public Iterable<Event> findAll();
	
	public Optional<Event> findById(long id);
	
	public Event save(Event event);
	
	public Event saveImg(MultipartFile imgFile);
	
	public void deleteById(long id);
	
	public void deleteAll();
	
}
