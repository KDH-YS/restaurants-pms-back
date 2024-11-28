package com.mysite.restaurant.hj.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.restaurant.hj.dto.UserAuthDTO;
import com.mysite.restaurant.hj.dto.UserDTO;
import com.mysite.restaurant.hj.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class UserService {

	private final UserMapper userMapper;
	
	public boolean existsUserByUserId(String userName) {
		return userMapper.selectUserByUserId(userName) != null;
	}
	
//	@Transactional
//	public void signup(UserDTO user) {
//		userMapper.create(user);
//		
//		UserAuthDTO userAuth = UserAuthDTO.builder()
//				.userId(user.getUserId())
//				.auth("USER")
//				.build();
//		userMapper.createAuth(userAuth);
//	}
	
    public void signup(UserDTO userDTO) {
        UserDTO user = new UserDTO();
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        // userId는 자동 생성되므로 설정하지 않음
        userMapper.save(user);
    }
	
	@Transactional
	public void updateLastLogin(Long userId) {
		userMapper.updateLastLogin(userId);
	}
	
	public Optional<UserDTO> getUserByUsername(String name) {
		return userMapper.selectUserByUsername(name);
	}
}
