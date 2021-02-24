package com.challenge.alkemy.blog.dao;

import org.springframework.data.repository.CrudRepository;

import com.challenge.alkemy.blog.entity.Post;

public interface IPostDao extends CrudRepository<Post, Long>{

}
