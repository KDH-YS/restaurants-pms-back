package com.mysite.restaurant.hj.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.restaurant.hj.dto.EmailCheckDTO;
import com.mysite.restaurant.hj.dto.EmailRequestDTO;
import com.mysite.restaurant.hj.service.MailSendService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MailController {

	private final MailSendService mailService;
	
	@PostMapping("/mailSend")
	public String mailSend(@Valid @RequestBody EmailRequestDTO emailDTO) {
		System.out.println("이메일 인증 이메일: " + emailDTO.getEmail());
		return mailService.joinEmail(emailDTO.getEmail());
	}
	
	@PostMapping("/mailauthCheck")
	public String AuthCheck(@Valid @RequestBody EmailCheckDTO emailCheckDTO) {
		Boolean Checked = mailService.CheckAuthNum(
				emailCheckDTO.getEmail(),
				emailCheckDTO.getAuthNum());
		if (Checked) {
			return "인증번호가 확인되었습니다.";
		} else {
			throw new NullPointerException("인증번호 에러");
		}
	}
}
