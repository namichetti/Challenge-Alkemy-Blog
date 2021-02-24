package com.challenge.alkemy.blog.service;

import java.util.List;

import com.challenge.alkemy.blog.entity.Post;

public interface IPostService {

	public List<Post> getAll();
	public Post save(Post post);
	public Post getById(Long id);
	public void deleteById(Long id);
}
