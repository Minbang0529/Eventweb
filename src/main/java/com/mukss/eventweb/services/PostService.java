package com.mukss.eventweb.services;

import java.util.Optional;

import com.mukss.eventweb.entities.Post;

public interface PostService {
	
	public long count();
	
	public Iterable<Post> findAll();
	
	public Optional<Post> findById(long id);
	
	public Post save(Post post);
	
	public void deleteById(long id);
	
	public void deleteAll();
}
