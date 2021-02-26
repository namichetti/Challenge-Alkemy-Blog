package com.challenge.alkemy.blog.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.challenge.alkemy.blog.model.entity.Post;

public interface IPostDao extends CrudRepository<Post, Long>{

	@Query("SELECT p FROM Post p ORDER BY p.creationDate DESC")
	public List<Post> getAllOrderByDateDesc();
}
