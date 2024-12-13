package com.mysite.restaurant.hj.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.restaurant.hj.dto.EmailCheckDTO;
import com.mysite.restaurant.hj.dto.EmailRequestDTO;
import com.mysite.restaurant.hj.service.MailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MailController {

	private final MailService mailService;
	
	@PostMapping ("/verify-email")
    public String mailSend(@RequestBody @Valid EmailRequestDTO emailDTO) {
        System.out.println("이메일 인증 이메일 :" + emailDTO.getEmail());
        return mailService.joinEmail(emailDTO.getEmail());
    }
	
	@PostMapping("/verify-emailCheck")
    public String AuthCheck(@RequestBody @Valid EmailCheckDTO emailCheckDTO) {
        Boolean Checked=mailService.CheckAuthNum(emailCheckDTO.getEmail(), emailCheckDTO.getAuthNum());
        if (Checked) {
            return "이메일 인증 성공";
        }
        else {
            throw new NullPointerException("이메일 인증 실패");
        }
    }
}
