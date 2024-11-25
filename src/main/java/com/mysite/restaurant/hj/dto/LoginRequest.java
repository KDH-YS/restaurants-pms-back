package com.mysite.restaurant.hj.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	@NotNull
	@Size(min = 3, max = 50)
	private String email;
	
	@NotNull
	@Size(min = 3, max = 100)
	private String password;
}
