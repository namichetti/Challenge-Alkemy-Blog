package com.challenge.alkemy.blog.model.service;

import java.util.List;

import com.challenge.alkemy.blog.model.entity.Post;

public interface IPostService {

	public List<Post> getAllOrderByDateDesc();
	public Post save(Post post);
	public Post getById(Long id);
	public void deleteById(Long id);
}
