package com.mysite.restaurant.hj.dto;

import com.mysite.restaurant.hj.dto.UserDTO.UserType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

	private String userId;
	private String name;
	private String email;
	private String phone;
	private UserType userType;
	
	public static UserResponse from (UserDTO userDTO) {
		return UserResponse.builder()
				.email(userDTO.getEmail())
				.name(userDTO.getName())
				.email(userDTO.getEmail())
				.phone(userDTO.getPhone())
				.userType(userDTO.getUserType())
				.build();
	}
}
