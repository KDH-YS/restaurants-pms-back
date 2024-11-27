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
	private String user_name;
	
	@NotNull
	@Size(min = 3, max = 100)
//	@NotBlank
//    // 길이조건 8-16자
//    // 필수 포함
//    // -- 영문자 최소1개 포함 (?=.*[A-Za-z])
//    // -- 숫자 최소1개 포함 (?=.*\\d)
//    // -- 특수문자 최소1개 포함 (?=.*[!\"#$%&'();:?@\\[\\]\\^_{|}~\\\\])
//    @Pattern(
//            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!\"#$%&'();:?@\\[\\]\\^_{|}~\\\\])[A-Za-z\\d!\"#$%&'();:?@\\[\\]\\^_{|}~\\\\]{8,16}$",
//            message = "비밀번호는 8-16자의 영문 대/소문자, 숫자, 특수문자를 사용할 수 있습니다."
//    )
	private String password;
	
	@NotNull
	@Size(min = 2, max = 100)
	private String name;
	
	@NotNull
	private String email;
	
	@NotNull
	private String phone;
	
	private Integer notification_agreed;
	
	public UserDTO toUserDTO(PasswordEncoder passwordEncoder) {
		return UserDTO.builder()
				.user_name(this.user_name)
				.password(passwordEncoder.encode(this.password))
				.name(this.name)
				.email(this.email)
				.phone(this.phone)
				.notification_agreed(this.notification_agreed)
				.build();
	}
}
