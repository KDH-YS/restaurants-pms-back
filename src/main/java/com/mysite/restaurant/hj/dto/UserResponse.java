package com.mysite.restaurant.hj.dto;

import com.mysite.restaurant.hj.dto.UserDTO.UserType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

	private Long userId;
	private String userName;
	private String name;
	private String email;
	private String phone;
	private UserType userType;
	
	public static UserResponse from (UserDTO userDTO) {
		return UserResponse.builder()
				.userName(userDTO.getUserName())
				.name(userDTO.getName())
				.email(userDTO.getEmail())
				.phone(userDTO.getPhone())
				.userType(userDTO.getUserType())
				.build();
	}
}
