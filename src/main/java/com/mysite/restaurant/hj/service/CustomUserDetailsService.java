package com.mysite.restaurant.hj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mysite.restaurant.hj.dto.CustomUserDetails;
import com.mysite.restaurant.hj.dto.UserDTO;
import com.mysite.restaurant.hj.exception.BusinessException;
import com.mysite.restaurant.hj.exception.ErrorCode;
import com.mysite.restaurant.hj.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserMapper userMapper;
	
//	회원가입
	public int save (UserDTO user) {
		return userMapper.save(user);
	}
	
//	로그인
//	public UserDTO findByUserId(String email) {
//		return userMapper.selectByUserId(email);
//	}
	
	@Override
    public UserDetails loadUserByUsername(String user_name) {
        return new CustomUserDetails(userMapper.selectMemberByUserId(user_name).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND)));
    }
	
//	로그아웃
	
//	사용자 정보 조회
	public UserDTO selectUserProfile(String user_name) {
        // 데이터베이스에서 유저 조회
        UserDTO user = userMapper.selectUserProfile(user_name);

        if (user == null) {
            throw new IllegalArgumentException("User not found with user_name: " + user_name);
        }

        return user;
    }
	
//	사용자 정보 수정
	public int updateUserProfile(UserDTO user) {
		return userMapper.updateUserProfile(user);
	}
	
//	회원 탈퇴
	public int deleteUser(String user_name) {
		return userMapper.deleteUser(user_name);
	}
	
//	소셜 로그인
	
//	이메일 인증
	
//	휴대폰 인증
	
//	사업자 인증
	
//	사업자 인증 후 사용자 정보 수정
	
}
