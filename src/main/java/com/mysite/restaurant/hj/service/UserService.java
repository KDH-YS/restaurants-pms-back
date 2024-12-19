package com.mysite.restaurant.hj.service;

import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
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
	
//	회원가입
	@Transactional
    public void signup(UserDTO userDTO, boolean isOwner) {
		// 아이디 중복 검사
        if (userMapper.findByUserName(userDTO.getUserName()) != null) {
            throw new DuplicateKeyException("이미 사용 중인 아이디입니다: " + userDTO.getUserName());
        }
        
		// 이메일 중복 검사
        if (userMapper.findByEmail(userDTO.getEmail()) != null) {
            throw new DuplicateKeyException("이미 사용 중인 이메일입니다: " + userDTO.getEmail());
        }
        
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
        		.auth(isOwner ? "ROLE_OWNER" : "ROLE_USER")
        		.build();
        userMapper.createAuth(userAuth);
    }
	
	public boolean existsByUsername(String userName) {
	    boolean exists = userMapper.existsByUserName(userName);
	    return exists;
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
