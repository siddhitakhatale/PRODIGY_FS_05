package com.internship.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.internship.Repository.NotificationRepo;
import com.internship.Repository.UserRepo;
import com.internship.model.Notification;
import com.internship.model.NotificationDTO;
import com.internship.model.User;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepo notificationRepo;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private UserRepo userRepo;
	
	public void createNotification(
			User sender,
			User receiver,
			String type,
			String message
	) {
		if(sender.getId().equals(receiver.getId())) {
			return;
		}
		Notification notification=new Notification();
		notification.setSender(sender);
		notification.setReceiver(receiver);
		notification.setType(type);
		notification.setMessage(message);
		notificationRepo.save(notification);
		NotificationDTO notificationDTO=new NotificationDTO(type,message);
		
		simpMessagingTemplate.convertAndSendToUser(receiver.getUsername()
				, "/queue/notifications"
				, notificationDTO);
	}

	public List<Notification> getAllNotifications() {
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		String username=auth.getName();
		User user=userRepo.findByUsername(username).orElseThrow();
		return notificationRepo.findByReceiverOrderByCreatedAtDesc(user);
		
	}

	public void markAsRead(Long id) {
		Notification n=notificationRepo.findById(id).orElseThrow();
		n.setRead(true);
		notificationRepo.save(n);
	}

	public long getUnReadCount() {
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		String username=auth.getName();
		User user=userRepo.findByUsername(username).orElseThrow();
		return notificationRepo.countByReceiverAndReadFalse(user);
	}
}
