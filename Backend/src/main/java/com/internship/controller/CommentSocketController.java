package com.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.internship.model.Comment;

@Controller
public class CommentSocketController {
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	public void sendComment(Comment comment) {
		messagingTemplate.convertAndSend(
				"/topic/comments/"+comment.getPost().getPostid(),
				comment
			);
	}
}
