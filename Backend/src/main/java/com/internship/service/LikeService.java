package com.internship.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.internship.Repository.LikeRepository;
import com.internship.Repository.PostRepo;
import com.internship.Repository.UserRepo;
import com.internship.model.Like;
import com.internship.model.Post;
import com.internship.model.User;

@Service
public class LikeService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private LikeRepository likeRepository;
	
	
	@Autowired
	private NotificationService notificationService;
	
	public int likePost(Long postid) {
		
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		
		String username=auth.getName();
		
		User user=userRepo.findByUsername(username).orElseThrow();
		Post post=postRepo.findById(postid).orElseThrow();
		if(!likeRepository.existsByUserAndPost(user,post)) {
			Like like=new Like();
			like.setUser(user);
			like.setPost(post);
			likeRepository.save(like);
			
			notificationService.createNotification(user, post.getUser(), "LIKE", user.getUsername()+" : "+"liked your post");
		}
		return likeRepository.countByPost(post);
	}

}
