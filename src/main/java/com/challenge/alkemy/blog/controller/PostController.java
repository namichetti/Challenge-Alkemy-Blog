package com.challenge.alkemy.blog.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.challenge.alkemy.blog.model.entity.Post;
import com.challenge.alkemy.blog.model.service.IPostService;

@Controller
@RequestMapping("/posts")
@SessionAttributes("post")
public class PostController {
	
    Logger logger = LoggerFactory.getLogger(PostController.class);
    
	
	@Autowired
	private IPostService postService;

	@GetMapping("/")
	public String getAll(Model model) {
		List<Post> posts = this.postService.getAllOrderByDateDesc();
		model.addAttribute("posts",posts);
		return "index";
	}
	

	@GetMapping("/{id}")
	public String getById(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Post post = null;
		
		if(id <=0) {
			flash.addFlashAttribute("danger", "Post does not exist.");
			return "redirect:/posts";
		}
		
		try {
			post = this.postService.getById(id);
		}catch(Exception e) {
			this.logger.error("Error: " + e.getMessage());
			flash.addFlashAttribute("danger", "There was a problem trying to get the Post.");
			
		}
		
		if(post == null) {
			flash.addFlashAttribute("danger", "Post does not exist.");
			return "redirect:/posts";
		}
		
		model.addAttribute("post",post);
		return "read";
	}
	
	@DeleteMapping("/{id}")
	public String deleteById(@PathVariable Long id, RedirectAttributes flash) {	
	Post post = null;
	
		if( id <=0) {
			flash.addFlashAttribute("danger", "Post does not exist.");
			return "redirect:/posts/";
		}
		
		try {
			post = this.postService.getById(id);
			if(post == null) {
				flash.addFlashAttribute("danger", "Post does not exist.");
				return "redirect:/posts/";
			}
			this.postService.deleteById(post.getId());
		}catch(Exception e) {
			this.logger.error("Error: " + e.getMessage());
			flash.addFlashAttribute("danger", "There was a problem trying to delete the Post.");
			return "redirect:/posts/";
		}
				
		flash.addFlashAttribute("success", "Post deleted successfully!");
		return "redirect:/posts/";
	}
	
	@PostMapping("/")
	public String save(@Valid Post post, BindingResult result,RedirectAttributes flash, @RequestParam("file") MultipartFile image, SessionStatus session) {
		if(result.hasErrors()) {
			return "form";
		}
		
		if(!image.isEmpty() || image.getOriginalFilename().length()>0) {
			//Generamos un String random para concatenar con el nombre de la imagen y, por ende que sea única. 
			String uniqueImagenName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
			// Ruta Relativa: Concatena "Uploads" con el nombre de la imagen.
			Path rootPath = Paths.get("uploads").resolve(uniqueImagenName); 
			
			// Ruta Absoluta.
			Path absolutPath = rootPath.toAbsolutePath();
			try {
				Files.copy(image.getInputStream(), absolutPath);
				post.setImage(uniqueImagenName);
			} catch (IOException e) {
				this.logger.error("Error: " + e.getMessage());
			}
		}
		
		String message = post.getId() != null ? "Post updated successfully!":"Post created successfully!";
		
		try {
			this.postService.save(post);
		}catch(Exception e) {
			this.logger.error("Error: " + e.getMessage());
			flash.addFlashAttribute("danger", "There was a problem trying to create the Post");
			return "redirect:/posts/";
		}
		flash.addFlashAttribute("success",message);	
		session.setComplete();
		return "redirect:/posts/";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		Post post = new Post();
		model.addAttribute("post",post);
		return "form";
	}
	
	@PatchMapping("/form/{id}")
	public String update(@PathVariable Long id,RedirectAttributes flash, Model model) {
		Post recoverPost = null;
		
		if(id <=0) {
			flash.addFlashAttribute("danger", "Post does not exist.");
			return "redirect:/posts/";
		}
		
		try {
			recoverPost = this.postService.getById(id);
		}catch(Exception e) {
			this.logger.error("Error: " + e.getMessage());
			flash.addFlashAttribute("danger", "There was a problem trying to update the Post.");
			return "redirect:/posts/";
		}
		
		if(recoverPost == null) {
			flash.addFlashAttribute("danger", "Post does not exist.");
			return "redirect:/posts/";
		}
		
		model.addAttribute("post", recoverPost);
		return "form";
	}
}
