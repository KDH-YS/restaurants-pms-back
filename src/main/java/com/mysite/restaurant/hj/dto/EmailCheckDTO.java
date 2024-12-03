package com.mysite.restaurant.hj.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmailCheckDTO {

//	사용자가 인증번호를 확인하고 인증번호를 입력하였을 때 받아오는 DTO
	
	@Email
	@NotEmpty(message = "이메일을 입력해주세요.")
	private String email;
	
	@NotEmpty(message = "인증번호를 입력해주세요.")
	private String authNum;
}
