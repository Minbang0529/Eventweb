package com.mukss.eventweb.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mukss.eventweb.entities.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
	// orders post by descending order of uploaded date
	// Spring magic!
	// enable later
	// Iterable<Post> findAllbyOrderByTimeUploadedDesc();
}
