package com.mysite.restaurant.hj.dto;

import java.time.LocalDateTime;
import java.util.List;

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
	private String userName;
	private String password;
	private String name;
	private String email;
	private String phone;
	private String profileImageUrl; // 사용자 프로필 이미지 URL
	private Integer notificationAgreed; // 알림 수신 동의
	private UserType userType;			// 사용자 권한
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private int isDeleted;
	private Status status;

	private Long restaurantId;

	private List<UserAuthDTO> authorities;
//	private String status;
	
	public enum UserType {
		CUSTOMER,
		OWNER,
		USER
	}
	
	public enum Status {
		ACTIVE
	}
}
