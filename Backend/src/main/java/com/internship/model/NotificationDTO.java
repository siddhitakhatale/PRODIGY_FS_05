package com.internship.model;

public class NotificationDTO {
	private Long id;
	private String type;
	private String content;
	
	public NotificationDTO() {
		
	}
	
	public NotificationDTO(String type, String content) {
		
		this.type = type;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
