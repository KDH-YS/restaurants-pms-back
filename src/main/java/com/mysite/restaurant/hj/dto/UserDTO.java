package com.mysite.restaurant.hj.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Long userId;
	private String email;
	private String password;
	private String name;
	private String phone;
	private String profileImageUrl;
	private Integer notification_agreed;
	private UserType userType;			// 사용자 권한
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public enum UserType {
		CUSTOMER,
		OWNER,
		USER
	}
}
