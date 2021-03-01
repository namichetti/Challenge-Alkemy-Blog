package com.challenge.alkemy.blog;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.challenge.alkemy.blog.model.Category;
import com.challenge.alkemy.blog.model.dao.IPostDao;
import com.challenge.alkemy.blog.model.entity.Post;
import com.challenge.alkemy.blog.model.service.IPostService;

@RunWith(SpringRunner.class)
@SpringBootTest
class BlogApplicationTests {

	@MockBean
	private IPostDao postDao;
	
	@Autowired
	private IPostService postService;
	
	//Success test getAllOrderByDateDesc length from postService.
	@Test
	void getSuccessListPostTest() {
		
		when(this.postDao.getAllOrderByDateDesc())
		.thenReturn(Stream.of(new Post("Title 1","Body 1",Category.MUSIC), new Post("Title 2","Body 2",Category.NOVEL))
	    .collect(Collectors.toList()));

		assertEquals(2, this.postService.getAllOrderByDateDesc().size());
	}
	
	//Failed test getAllOrderByDateDesc length from postService.
	@Test
	void getFailedListPostTest() {
		
		when(this.postDao.getAllOrderByDateDesc())
		.thenReturn(Stream.of(new Post("Title 1","Body 1",Category.MUSIC), new Post("Title 2","Body 2",Category.NOVEL))
	    .collect(Collectors.toList()));

		assertEquals(3, this.postService.getAllOrderByDateDesc().size());
	}
	
	//Test failed deleteById because of 1L != 8L.
	@Test
	public void getFailedPostTest() {
		this.postService.getById(1L);
        verify(this.postDao, times(1)).findById(8L);

	}
	
	//Success test deleteById because of 1L == 1L.
	@Test
	public void getSuccessPostTest() {
		this.postService.getById(1L);
        verify(this.postDao, times(1)).findById(1L);

	}

	//Success test save(post)
	@Test
	public void saveSuccesPostTest() {
		Post post = new Post(1l,"Title","Body");
		when(this.postDao.save(post)).thenReturn(post);
		
		assertEquals(post, this.postService.save(post));
	}
	
	//Test failed deleteById because of post != null.
	@Test
	public void saveFailedPostTest() {
		Post post = new Post(1l,"Title","Body");
		when(this.postDao.save(post)).thenReturn(null);
		
		assertEquals(post, this.postService.save(null));
	}
	
	
	//Success test deleteById because of 1L == 1L.
	@Test
	public void deleteSuccessPostTest() {
		this.postService.deleteById(1L);
        verify(this.postDao, times(1)).deleteById(1L);

	}
	
	//Test failed deleteById because of 1L != 8L.
	@Test
	public void deleteFailedPostTest() {
		this.postService.deleteById(1L);
        verify(this.postDao, times(1)).deleteById(8L);

	}
	

}
