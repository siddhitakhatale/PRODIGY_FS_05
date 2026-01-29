package com.internship.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Mention {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User mentionedUser;
	
	@ManyToOne
	private Post post;
	
	public Mention() {
		
	}

	public Mention(Long id, User mentionedUser, Post post) {
		this.id = id;
		this.mentionedUser = mentionedUser;
		this.post = post;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getMentionedUser() {
		return mentionedUser;
	}

	public void setMentionedUser(User mentionedUser) {
		this.mentionedUser = mentionedUser;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
}
