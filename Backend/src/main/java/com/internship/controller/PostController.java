package com.internship.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.internship.Repository.PostRepo;
import com.internship.model.Post;
import com.internship.model.PostDTO;
import com.internship.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

	
	@Autowired
	private PostService postService;


    @Autowired
    private PostRepo postRepo;
	
	
	@PostMapping
	public ResponseEntity<Post> createPost(
			@RequestParam String content,
			@RequestParam(required = false) String imageUrl,
			@RequestParam(required = false) String vedioUrl) {
		return ResponseEntity.ok(postService.createPost(content,imageUrl,vedioUrl));
	}
	
	@GetMapping("/feed")
	public List<PostDTO> getFeed(){
		return postService.getFeed();
	}
	@GetMapping("hashtag/{tag}")
	public List<Post> getPostByHashtag(@PathVariable String tag){
		return postRepo.findPostsByHashtag(tag.toLowerCase());
	}
	
}
