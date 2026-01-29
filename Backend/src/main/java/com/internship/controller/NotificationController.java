package com.internship.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.internship.model.Notification;
import com.internship.service.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping
	public List<Notification> getAllNotification(){
		return notificationService.getAllNotifications();
	}
	
	@PutMapping("/{id}/read")
	public void markAsRead(@PathVariable Long id) {
		notificationService.markAsRead(id);
	}
	
	@GetMapping("/unread-count")
	public long getUnreadCount() {
		return notificationService.getUnReadCount();
	}
}
