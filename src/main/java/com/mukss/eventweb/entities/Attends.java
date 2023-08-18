package com.mukss.eventweb.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="attends")
public class Attends {
	
	@Id
	@Column(name = "attends_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private LocalDateTime timeUploaded;
	
//	@JsonFormat(shape = JsonFormat.Shape.STRING)
//	private LocalDateTime lastEdited;
	
	private String bankname;
	
	// TODO: change this to event
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="post_id", nullable=false)
	private Post post;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User user;
	
	// 3 states - pending/approved/rejected
	private String state;

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

//	public LocalDateTime getLastEdited() {
//		return lastEdited;
//	}
//
//	public void setLastEdited(LocalDateTime lastEdited) {
//		this.lastEdited = lastEdited;
//	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
}
