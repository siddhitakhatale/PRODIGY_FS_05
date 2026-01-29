package com.internship.service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.Repository.HashtagRepo;
import com.internship.Repository.UserRepo;
import com.internship.model.Hashtag;
import com.internship.model.User;

@Service
public class TagService {
	private static final Pattern USER_PATTERN=Pattern.compile("@([a-zA-Z0-9_]+)");
	private static final Pattern HASHTAG_PATTERN=Pattern.compile("#([a-zA-Z0-9_]+)");
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private HashtagRepo hashtagRepo;
	
	public Set<User> extractMentionedUsers(String content){
		Set<User> users=new HashSet<>();
		Matcher matcher=USER_PATTERN.matcher(content);
		while(matcher.find()) {
			String username=matcher.group(1);
			userRepo.findByUsername(username).ifPresent(users::add);
		}
		return users;
	}
	public Set<Hashtag> extractHashtags(String content){
		Set<Hashtag> hashtags=new HashSet<>();
		Matcher matcher=HASHTAG_PATTERN.matcher(content);
		
		while(matcher.find()) {
			String tagName=matcher.group().toLowerCase();
			Hashtag hashtag=hashtagRepo.findByName(tagName)
					.orElseGet(() -> {
						Hashtag newTag=new Hashtag();
						newTag.setName(tagName);
						return hashtagRepo.save(newTag);
					});
			hashtags.add(hashtag);
		}
		return hashtags;
	}
}
