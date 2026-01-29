package com.internship.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.internship.Repository.CommentRepo;
import com.internship.Repository.PostRepo;
import com.internship.Repository.UserRepo;
import com.internship.controller.CommentSocketController;
import com.internship.model.Comment;
import com.internship.model.Post;
import com.internship.model.User;

@Service
public class CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private CommentSocketController socketController;
	
	public Comment addComment(Long postid, String content) {
		
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		String username=auth.getName();
		User user=userRepo.findByUsername(username).orElseThrow();
		Post post=postRepo.findById(postid).orElseThrow();
		
		Comment comment=new Comment();
		comment.setContent(content);
		comment.setUser(user);
		comment.setPost(post);
		
		Comment saved =commentRepo.save(comment);
		socketController.sendComment(saved);
		
		notificationService.createNotification(user, post.getUser()
				, "COMMENT", user.getUsername()+" : "+"commented on your post");
		return saved;
	}

}
