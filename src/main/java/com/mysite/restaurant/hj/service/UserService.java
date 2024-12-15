package com.mysite.restaurant.hj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.restaurant.hj.dto.LoginRequest;
import com.mysite.restaurant.hj.dto.UserAuthDTO;
import com.mysite.restaurant.hj.dto.UserDTO;
import com.mysite.restaurant.hj.jwt.JwtTokenProvider;
import com.mysite.restaurant.hj.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class UserService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	
	public boolean existsUserByUserId(String userName) {
		return userMapper.selectUserByUserId(userName) != null;
	}
	
//	회원가입
	@Transactional
    public void signup(UserDTO userDTO) {
        UserDTO user = new UserDTO();
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setNotificationAgreed(userDTO.getNotificationAgreed());
        // userId는 자동 생성되므로 설정하지 않음
        userMapper.save(user);
        
        // 권한도 같이 생성
        UserAuthDTO userAuth = UserAuthDTO.builder()
        		.userId(user.getUserId())
        		.auth("ROLE_USER")
        		.build();
        userMapper.createAuth(userAuth);
    }
	
//	로그인
	@Transactional
	public void updateLastLogin(String userName) {
		userMapper.updateLastLogin(userName);
	}
	
	public Optional<UserDTO> getUserByUsername(String userName) {
		return userMapper.selectUserByUsername(userName);
	}
}
