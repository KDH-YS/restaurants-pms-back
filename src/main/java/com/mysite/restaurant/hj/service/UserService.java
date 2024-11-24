package com.mysite.restaurant.hj.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.restaurant.hj.dto.UserAuthDTO;
import com.mysite.restaurant.hj.dto.UserDTO;
import com.mysite.restaurant.hj.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserMapper userMapper;
	
	public boolean existsByUserId(String email) {
		return userMapper.selectByUserEmail(email) != null;
	}
	
	@Transactional
	public void signup(UserDTO user) {
		userMapper.create(user);
		
		UserAuthDTO userAuth = UserAuthDTO.builder()
				.userId(user.getUserId())
				.auth("USER")
				.build();
	}
}
