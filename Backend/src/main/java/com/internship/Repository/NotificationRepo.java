package com.internship.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship.model.Notification;
import com.internship.model.User;

public interface NotificationRepo extends JpaRepository<Notification, Long>{
	List<Notification> findByReceiverOrderByCreatedAtDesc(User receiver);
	
	long countByReceiverAndReadFalse(User receiver);
}
