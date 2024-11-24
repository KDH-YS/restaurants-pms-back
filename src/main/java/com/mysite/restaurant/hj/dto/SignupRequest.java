package com.mysite.restaurant.hj.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

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
public class SignupRequest {

	@NotNull
	@Size(min = 3, max = 50)
	private String email;
	
	@NotNull
	@Size(min = 3, max = 100)
	private String password;
	
	@NotNull
	@Size(min = 2, max = 100)
	private String name;
	
	private String phone;
	private Integer notification_agreed;
	
	public UserDTO toUserDTO(PasswordEncoder passwordEncoder) {
		return UserDTO.builder()
				.email(this.email)
				.password(passwordEncoder.encode(this.password))
				.name(this.name)
				.phone(this.phone)
				.notification_agreed(this.notification_agreed)
				.build();
	}
}
