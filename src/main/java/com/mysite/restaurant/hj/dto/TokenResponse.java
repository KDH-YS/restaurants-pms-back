package com.mysite.restaurant.hj.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

	private String token;
	private String userName;
	private String name;
	private String email;
	private String phone;
	private List<String> authorities;
}
