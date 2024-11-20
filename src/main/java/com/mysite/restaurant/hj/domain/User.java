package com.mysite.restaurant.hj.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {

	private Long userId;
	private String email;
	private String password;
	private String name;
	private String phone;
	private String profileImageUrl;
	private String notificationAgreed;
	private UserType userType;			// 사용자 권한
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public enum UserType {
		COSTOMER,
		OWNER,
		USER
	}
}
