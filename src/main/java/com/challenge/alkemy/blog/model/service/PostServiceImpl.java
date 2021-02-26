package com.challenge.alkemy.blog.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.alkemy.blog.model.dao.IPostDao;
import com.challenge.alkemy.blog.model.entity.Post;

@Service
public class PostServiceImpl implements IPostService{
	
	@Autowired
	private IPostDao postDao;


	@Override
	@Transactional(readOnly=true)
	public List<Post> getAllOrderByDateDesc() {
		//return (List<Post>)this.postDao.findAll();
		return (List<Post>)this.postDao.getAllOrderByDateDesc();
	}
	
	@Override
	@Transactional
	public Post save(Post post) {
		return this.postDao.save(post);
	}

	@Override
	@Transactional(readOnly=true)
	public Post getById(Long id) {
		return this.postDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.postDao.deleteById(id);
	}

}
