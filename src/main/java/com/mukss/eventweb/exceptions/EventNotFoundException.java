package com.mukss.eventweb.exceptions;

public class EventNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	private long id;

	public EventNotFoundException(long id) {
		super("Could not find event " + id);

		this.id = id;
	}

	public long getId() {
		return id;
	}
}
