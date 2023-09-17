package com.mukss.eventweb.exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String username;
	
	public UserNotFoundException(String username) {
		super("Could not find user " + username);

		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}
	
}
