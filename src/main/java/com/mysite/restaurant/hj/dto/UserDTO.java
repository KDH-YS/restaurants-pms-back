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

	private Long user_id;
	private String user_name;
	private String password;
	private String name;
	private String email;
	private String phone;
	private String profileImageUrl; // 사용자 프로필 이미지 URL
	private Integer notification_agreed; // 알림 수신 동의
	private UserType userType;			// 사용자 권한
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	private List<UserAuthDTO> authorities;
//	private String status;
	
	public enum UserType {
		CUSTOMER,
		OWNER,
		USER
	}
}
