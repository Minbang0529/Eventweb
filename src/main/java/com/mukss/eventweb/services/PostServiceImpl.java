package com.mukss.eventweb.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukss.eventweb.entities.Post;
import com.mukss.eventweb.repositories.PostRepository;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public long count() {
		return postRepository.count();
	}

	@Override
	public Iterable<Post> findAll() {
		return postRepository.findAll();
	}

	@Override
	public Optional<Post> findById(long id) {
		return postRepository.findById(id);
	}

	@Override
	public Post save(Post post) {
		return postRepository.save(post);
	}
	
	@Override
	public void deleteById(long id) {
		postRepository.deleteById(id);
	}
	
	@Override
	public void deleteAll() {
		postRepository.deleteAll();
	}
}
