package com.mukss.eventweb.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mukss.eventweb.entities.Event;
import com.mukss.eventweb.repositories.EventRepository;

@Service
public class EventServiceImpl implements EventService{
	
	@Autowired
	private EventRepository eventRepository;

	@Override
	public long count() {
		return eventRepository.count();
	}

	@Override
	public Iterable<Event> findAll() {
		return eventRepository.findAll();
	}

	@Override
	public Optional<Event> findById(long id) {
		return eventRepository.findById(id);
	}

	@Override
	public Event save(Event event) {
		return eventRepository.save(event);
	}
	
//	@Override
//	public Event saveImg(MultipartFile imgFile) {
//		return eventRepository.saveImg(imgFile);
//	}
	
	@Override
	public void deleteById(long id) {
		eventRepository.deleteById(id);
	}
	
	@Override
	public void deleteAll() {
		eventRepository.deleteAll();
	}
}
