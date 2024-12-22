package com.mysite.restaurant.hj.controller;

import java.util.List;
import java.util.stream.Collectors;


import com.mysite.restaurant.hj.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class AuthController {


	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider tokenProvider;
	private final UserService userService;
	private final CustomUserDetailsService customUserDetailsService;
	private final PasswordEncoder passwordEncoder;
		
//	회원가입 처리
	@PostMapping("/signup")
	public ResponseEntity<JsonResponse> signup(@Valid @RequestBody SignupRequest request) {
	    // 비밀번호 암호화 및 DTO 변환
	    UserDTO member = request.toUserDTO(passwordEncoder);

	    // 회원가입 처리
	    userService.signup(member, request.getIsOwner());

	    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(JsonResponse.builder()
                .success(true)
                .message("회원가입이 완료되었습니다.")
                .build());
	}
		
	@GetMapping("/checkId/{userName}")
	public ResponseEntity<JsonResponse> checkId(@PathVariable("userName") String userName) {
		// UserService를 통해 아이디 중복 여부 확인
        boolean isAvailable = !userService.existsByUsername(userName); // 중복되면 false 반환

        if (isAvailable) {
            return ResponseEntity.ok(JsonResponse.builder()
                    .success(true)
                    .message("사용 가능한 아이디입니다.")
                    .build());
        } else {
            return ResponseEntity.ok(JsonResponse.builder()
                    .success(false)
                    .message("이미 사용 중인 아이디입니다.")
                    .build());
        }
	}

//  로그인
	@PostMapping("/login")
    public ResponseEntity<JsonResponse> login(@Valid @RequestBody LoginRequest request) {

//		유저의 아이디와 비밀번호를 가지고 UsernamePasswordAuthenticationToken를 만든다.
		UsernamePasswordAuthenticationToken authenticationToken =
    			new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());

//  	authenticationManagerBuilder: 아이디와 비밀번호를 확인하고 '권한'을 확인한다.
//  	authenticate: 아이디와 비밀번호를 가져와서 권한이 있는지 비교
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//  	SecurityContextHolder: SecurityContext를 저장하는 클래스이다.
//  	SecurityContext: 애플리케이션의 현재 사용자와 관련된 Authentication 객체를 보관하는 컨테이너이다.
//  	authentication: 현재 이 안에 유저의 정보가 다 들어가 있음.
//  	- 사용자의 인증 정보를 포함하는 객체이다.(예: 사용자 이름, 권한, 인증 여부 등)
		SecurityContextHolder.getContext().setAuthentication(authentication);

//  	토큰을 만들자
//		사용자 정보 가져오기
		String jwt = tokenProvider.createToken(authentication);
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	
//		user.getAuthorities() 호출 위치 이동
		List<String> authorities = userDetails.getUser().getAuthorities().stream()
	        .map(UserAuthDTO::getAuth)
	        .collect(Collectors.toList());
		
//		사용자 상태 로깅 (authorities 포함)
		log.info("User login attempt: userName={}, isEnabled={}, status={}, authorities={}",
	        userDetails.getUsername(), userDetails.isEnabled(), userDetails.getUser().getStatus(), authorities);
		
		UserDTO user = userDetails.getUser();
		
//		마지막 로그인 시간 업데이트
		userService.updateLastLogin(user.getUserName());
	
		TokenResponse tokenResponse = TokenResponse.builder()
			.token(jwt)
			.userName(user.getUserName())
			.name(user.getName())
			.email(user.getEmail())
			.authorities(authorities)
			.build();
	
		return ResponseEntity.ok(JsonResponse.builder()
			.success(true)
			.message("로그인이 완료되었습니다.")
			.data(tokenResponse)
			.build());

    }
    
//  로그아웃
    
	//사용자 정보 조회
	@PreAuthorize("hasAnyRole('USER', 'OWNER', 'ADMIN')")
    @GetMapping("/me/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Long userId) {
        UserDTO user = customUserDetailsService.selectUserProfile(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(user);
    }

	//  사용자 정보 수정
	@PreAuthorize("hasAnyRole('USER', 'OWNER', 'ADMIN')")
    @PutMapping("/me")
    public int updateUserProfile(@RequestBody UserDTO user) {
    	return customUserDetailsService.updateUserProfile(user);
    }
    
//  회원 탈퇴
	@PreAuthorize("hasRole('USER')")
    @DeleteMapping("/deleteUser")
    public int deleteUser(@RequestParam("email") String email) {
    	return customUserDetailsService.deleteUser(email);
    }
    
}
