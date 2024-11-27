package com.mysite.restaurant.hj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mysite.restaurant.hj.dto.*;
import com.mysite.restaurant.hj.exception.BusinessException;
import com.mysite.restaurant.hj.exception.CustomValidationException;
import com.mysite.restaurant.hj.exception.ErrorCode;
import com.mysite.restaurant.hj.jwt.JwtTokenProvider;
import com.mysite.restaurant.hj.service.CustomUserDetailsService;
import com.mysite.restaurant.hj.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider tokenProvider;
	private final UserService userService;
	private final CustomUserDetailsService customUserDetailsService;
	private final PasswordEncoder passwordEncoder;

//	@Autowired
//	private CustomUserDetailsService customUserDetailsService;
	
//	회원가입 처리
	@PostMapping("/signup")
	public ResponseEntity<JsonResponse> signup(@Valid @RequestBody SignupRequest request) {
	    // 비밀번호 암호화 및 DTO 변환
	    UserDTO member = request.toUserDTO(passwordEncoder);

	    // 회원 가입 처리
	    userService.signup(member);

	    return ResponseEntity
	            .status(HttpStatus.CREATED)
	            .body(JsonResponse.builder()
	                    .success(true)
	                    .message("회원가입이 완료되었습니다.")
	                    .build());
	}

//  로그인
	@PostMapping("/login")
    public ResponseEntity<JsonResponse> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			throw new CustomValidationException(ErrorCode.VALIDATION_ERROR, bindingResult);
		}
		
    	UsernamePasswordAuthenticationToken authenticationToken = 
    			new UsernamePasswordAuthenticationToken(request.getUser_name(), request.getPassword());
    	
//    	인증 과정 예외
//    	- BadCredentialsException: 잘못된 비밀번호
//    	- UsernameNotFoundException: 존재하지 않는 사용자
//    	- LockedException: 계정 잠김
//    	- DisabledException: 비활성화된 계정
//    	- AccountExpiredException: 만료된 계정
    	try {
    		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    		SecurityContextHolder.getContext().setAuthentication(authentication);
    		
    		String jwt = tokenProvider.createToken(authentication);
    		
    		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    		UserDTO user = userDetails.getUser();
    		
    		userService.updateLastLogin(user.getUser_id());
    		
    		TokenResponse tokenResponse = TokenResponse.builder()
    				.token(jwt)
    				.user_name(user.getUser_name())
    				.name(user.getName())
    				.build();
    		
    		return ResponseEntity.ok(JsonResponse.builder()
    				.success(true)
    				.message("로그인이 완료되었습니다.")
    				.data(tokenResponse)
    				.build());
    	} catch (BadCredentialsException e) {
    		throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
    	} catch (UsernameNotFoundException e) {
    		throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    	} catch (LockedException e) {
    		throw new BusinessException(ErrorCode.ACCESS_DENIED, "계정이 잠겼습니다.");
    	} catch (DisabledException e) {
    		throw new BusinessException(ErrorCode.ACCESS_DENIED, "비활성화된 계정입니다.");
    	} catch (AccountExpiredException e) {
    		throw new BusinessException(ErrorCode.ACCESS_DENIED, "만료된 계정입니다.");
    	}
    	
    }
    
//  로그아웃
    
//	사용자 정보 조회
    @GetMapping("/me/{user_id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("user_name") String user_name) {
        UserDTO user = customUserDetailsService.selectUserProfile(user_name);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(user);
    }
    
//  사용자 정보 수정
    @PutMapping("/me")
    public int updateUserProfile(@RequestBody UserDTO user) {
    	return customUserDetailsService.updateUserProfile(user);
    }
    
//  회원 탈퇴
    @DeleteMapping("/deleteUser")
    public int deleteUser(@RequestParam("user_name") String user_name) {
    	return customUserDetailsService.deleteUser(user_name);
    }
    
}
