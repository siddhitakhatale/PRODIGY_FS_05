package com.internship.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long postid;
	private String content;
	private String imageUrl;
	private String videoUrl;
	
	@ManyToOne
	private User user;
	
	private LocalDateTime createdAt=LocalDateTime.now();
	
	@ManyToMany 
	@JoinTable(
			name="post_hashtags",
			joinColumns = @JoinColumn(name="post_id"),
			inverseJoinColumns=@JoinColumn(name = "hashtag_id")
	)
	private Set<Hashtag> hashtags=new HashSet<>();
	
    @OneToMany(mappedBy = "post")
    private List<Comment> comments=new ArrayList<>();
    
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    @JsonManagedReference
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Set<Hashtag> getHashtags() {
		return hashtags;
	}
	public void setHashtags(Set<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}
	public Post() {
		
	}
	
	public Post(Long postid, String content, String imageUrl, String videoUrl, User user, LocalDateTime createdAt,
			Set<Hashtag> hashtags) {
		this.postid = postid;
		this.content = content;
		this.imageUrl = imageUrl;
		this.videoUrl = videoUrl;
		this.user = user;
		this.createdAt = createdAt;
		this.hashtags = hashtags;
	}
	public Long getPostid() {
		return postid;
	}
	public void setPostid(Long postid) {
		this.postid = postid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
