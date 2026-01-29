package com.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.internship.model.Comment;
import com.internship.model.Post;
import com.internship.service.CommentService;

@RestController
@CrossOrigin
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/posts/{postid}/comment")
	public Post addComment(@PathVariable Long postid,@RequestBody String content) {
		Comment comment= commentService.addComment(postid,content);
		return comment.getPost();
	}
}
