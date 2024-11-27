package com.mysite.restaurant.hj.dto;

import com.mysite.restaurant.hj.dto.UserDTO.UserType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

	private Long userId;
	private String email;
	private String name;
	private String phone;
	private UserType userType;
	
	public static UserResponse from (UserDTO userDTO) {
		return UserResponse.builder()
				.email(userDTO.getEmail())
				.name(userDTO.getName())
				.phone(userDTO.getPhone())
				.userType(userDTO.getUserType())
				.build();
	}
}
