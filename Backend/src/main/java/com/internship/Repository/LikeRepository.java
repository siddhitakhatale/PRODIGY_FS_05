package com.internship.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship.model.Like;
import com.internship.model.Post;
import com.internship.model.User;

public interface LikeRepository extends JpaRepository<Like, Long>{

	boolean existsByUserAndPost(User user, Post post);

	int countByPost(Post post);

}
