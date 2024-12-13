package com.mysite.restaurant.hj.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
	@Size(min = 3, max = 15, message = "아이디는 3자에서 15자 사이여야 합니다.")
	private String userName;
	
//	@NotNull
	@Size(min = 3, max = 16, message = "비밀번호는 8자에서 16자 사이여야 합니다.")
	@NotBlank(message = "비밀번호는 비어 있을 수 없습니다.")
    // 길이조건 8-16자
    // 필수 포함
    // -- 영문자 최소1개 포함 (?=.*[A-Za-z])
    // -- 숫자 최소1개 포함 (?=.*\\d)
    // -- 특수문자 최소1개 포함 (?=.*[!\"#$%&'();:?@\\[\\]\\^_{|}~\\\\])
//    @Pattern(
//            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!\"#$%&'();:?@\\[\\]\\^_{|}~\\\\])[A-Za-z\\d!\"#$%&'();:?@\\[\\]\\^_{|}~\\\\]{8,16}$",
//            message = "비밀번호는 8-16자의 영문 대/소문자, 숫자, 특수문자를 각각 최소 하나씩 포함해야 합니다.")
	private String password;
	
	@NotNull
	@Size(min = 2, max = 100)
	private String name;
	
	@NotNull
	@Size(min = 3, max = 50)
	private String email;
	
	@NotNull
	private String phone;
	
	private Integer notificationAgreed;
	
	public UserDTO toUserDTO(PasswordEncoder passwordEncoder) {
		return UserDTO.builder()
				.userName(this.userName)
				.password(passwordEncoder.encode(this.password))
				.name(this.name)
				.email(this.email)
				.phone(this.phone)
				.notificationAgreed(this.notificationAgreed)
				.build();
	}
}
