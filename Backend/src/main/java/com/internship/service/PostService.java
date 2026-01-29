package com.internship.service;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.internship.Repository.LikeRepository;
import com.internship.Repository.MentionRepo;
import com.internship.Repository.PostRepo;
import com.internship.Repository.UserRepo;
import com.internship.model.Hashtag;
import com.internship.model.Mention;
import com.internship.model.Post;
import com.internship.model.PostDTO;
import com.internship.model.User;

@Service
public class PostService {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private MentionRepo mentionRepo;
	
	@Autowired
	private NotificationService notificationService;

	@Autowired
	private LikeRepository likeRepository;
	public Post createPost(String content, String imageUrl, String vedioUrl) {
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		String username=auth.getName();
		User user=userRepo.findByUsername(username).orElseThrow();
		
		Post post=new Post();
		post.setContent(content);
		post.setImageUrl(imageUrl);
		post.setVideoUrl(vedioUrl);
		post.setUser(user);
		
		Set<Hashtag> hashtags=tagService.extractHashtags(content);
		post.setHashtags(hashtags);
		
		Post savedPost=postRepo.save(post);
		
		Set<User> mentionedUsers=tagService.extractMentionedUsers(content);
		for(User mentioned:mentionedUsers) {
			Mention mention=new Mention();
			mention.setMentionedUser(mentioned);
			mention.setPost(savedPost);
			mentionRepo.save(mention);
			
			notificationService.createNotification(user, mentioned, "MENTION", user.getUsername()+"mentioned you in a post");
		}
		return savedPost;
	}

	public List<PostDTO> getFeed() {
		return postRepo.findAllByOrderByCreatedAtDesc()
				.stream()
				.map(this::mapToDTO)
				.toList();
	}
	private PostDTO mapToDTO(Post post) {
		PostDTO dto=new PostDTO();
		dto.setPostid(post.getPostid());
		dto.setContent(post.getContent());
		dto.setImageUrl(post.getImageUrl());
		dto.setVideoUrl(post.getVideoUrl());
		dto.setUser(post.getUser());
		dto.setComments(post.getComments());
		dto.setLikesCount(likeRepository.countByPost(post));
		return dto;
	}


}
