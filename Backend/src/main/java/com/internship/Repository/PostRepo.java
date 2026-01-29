package com.internship.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.internship.model.Post;

public interface PostRepo extends JpaRepository<Post, Long>{

	List<Post> findAllByOrderByCreatedAtDesc();
	
	@Query("SELECT p FROM Post p JOIN p.hashtags h WHERE h.name = :tag")
	List<Post> findPostsByHashtag(@Param("tag") String tag);
}
