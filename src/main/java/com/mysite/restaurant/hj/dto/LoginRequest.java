package com.mysite.restaurant.hj.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class LoginRequest {

	@NotNull
	@Size(min = 3, max = 15, message = "아이디는 3자에서 15자 사이여야 합니다.")
	private String userName;
	
//	@NotNull
	@Size(min = 3, max = 16, message = "비밀번호는 8자에서 16자 사이여야 합니다.")
	@NotBlank(message = "비밀번호는 비어 있을 수 없습니다.")
	@Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!\"#$%&'();:?@\\[\\]\\^_{|}~\\\\])[A-Za-z\\d!\"#$%&'();:?@\\[\\]\\^_{|}~\\\\]{8,16}$",
            message = "비밀번호는 8-16자의 영문 대/소문자, 숫자, 특수문자를 각각 최소 하나씩 포함해야 합니다.")
	private String password;
}
