package com.mysite.restaurant.hj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.restaurant.hj.domain.User;
import com.mysite.restaurant.hj.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService  {

	@Autowired
	private  UserMapper userMapper;
	
//	회원가입
	public int save (User user) {
		return userMapper.save(user);
	}
	
//	로그인
	public User findByUserEmail(String email) {
		return userMapper.findByUserEmail(email);
	}
	
//	로그아웃
	
//	사용자 정보 조회
	public User selectUserProfile(Long userId) {
        // 데이터베이스에서 유저 조회
        User user = userMapper.selectUserProfile(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        return user;
    }
	
//	사용자 정보 수정
	public int updateUserProfile(User user) {
		return userMapper.updateUserProfile(user);
	}
	
//	회원 탈퇴
	public String deleteUser(String email) {
		return userMapper.deleteUser(email);
	}
	
//	소셜 로그인
	
//	휴대폰 인증
	
//	사업자 인증
	
//	사업자 인증 후 사용자 정보 수정
	
}
