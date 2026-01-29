package com.internship.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship.model.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long>{

}
