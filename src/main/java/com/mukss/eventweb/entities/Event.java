package com.mukss.eventweb.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="events")
public class Event {
	
	@Id
	@Column(name = "event_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private LocalDateTime timeUploaded;
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String content;
	
	private String location;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public LocalDateTime getTimeUploaded() {
		return timeUploaded;
	}

	public void setTimeUploaded(LocalDateTime timeUploaded) {
		this.timeUploaded = timeUploaded;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
}