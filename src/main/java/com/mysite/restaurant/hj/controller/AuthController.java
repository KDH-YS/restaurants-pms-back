package com.mysite.restaurant.hj.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mysite.restaurant.hj.dto.*;
import com.mysite.restaurant.hj.jwt.JwtTokenProvider;
import com.mysite.restaurant.hj.service.CustomUserDetailsService;
import com.mysite.restaurant.hj.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider tokenProvider;
	private final UserService userService;
	private final CustomUserDetailsService customUserDetailsService;
	private final PasswordEncoder passwordEncoder;

//	회원가입
	@PostMapping("/signup")
	public ResponseEntity<JsonResponse> signup(@Valid @RequestBody SignupRequest request) {
	    // 비밀번호 암호화 및 DTO 변환
	    UserDTO member = request.toUserDTO(passwordEncoder);

	    // 회원가입 처리
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
    public ResponseEntity<JsonResponse> login(@Valid @RequestBody LoginRequest request) {
		
//		유저의 아이디와 비밀번호를 가지고 UsernamePasswordAuthenticationToken를 만든다.
    	UsernamePasswordAuthenticationToken authenticationToken = 
    			new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());
    	
//    	authenticationManagerBuilder: 아이디와 비밀번호를 확인하고 '권한'을 확인한다.
//    	authenticate: 아이디와 비밀번호를 가져와서 권한이 있는지 비교
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//    	SecurityContextHolder: SecurityContext를 저장하는 클래스이다.
//    	SecurityContext: 애플리케이션의 현재 사용자와 관련된 Authentication 객체를 보관하는 컨테이너이다.
//    	authentication: 현재 이 안에 유저의 정보가 다 들어가 있음.
//    	 - 사용자의 인증 정보를 포함하는 객체이다.(예: 사용자 이름, 권한, 인증 여부 등)
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
//    	토큰을 만들자
		String jwt = tokenProvider.createToken(authentication);
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		UserDTO user = userDetails.getUser();
		
		userService.updateLastLogin(user.getUserName());
		
		TokenResponse tokenResponse = TokenResponse.builder()
				.token(jwt)
				.userName(user.getUserName())
				.email(user.getEmail())
				.build();
		
		return ResponseEntity.ok(JsonResponse.builder()
				.success(true)
				.message("로그인이 완료되었습니다.")
				.data(tokenResponse)
				.build());
    	
    }
    
//  로그아웃
    
//	사용자 정보 조회
    @GetMapping("/me/{userName}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userName") String userName) {
        UserDTO user = customUserDetailsService.selectUserProfile(userName);

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
    public int deleteUser(@RequestParam("userName") String userName) {
    	return customUserDetailsService.deleteUser(userName);
    }
    
}
