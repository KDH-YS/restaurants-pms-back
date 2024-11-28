package com.mysite.restaurant.hj.service;

import java.util.List;
import java.util.Optional;

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
    public UserDetails loadUserByUsername(String userName) {
		UserDTO userDto = userMapper.selectMemberByUserId2(userName);
	
        return new CustomUserDetails(userMapper.selectMemberByUserId(userName).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND)));
    }
	
//	로그아웃
	
//	사용자 정보 조회
	public UserDTO selectUserProfile(String userName) {
        // 데이터베이스에서 유저 조회
        UserDTO user = userMapper.selectUserProfile(userName);

        if (user == null) {
            throw new IllegalArgumentException("User not found with user_name: " + userName);
        }

        return user;
    }
	
//	사용자 정보 수정
	public int updateUserProfile(UserDTO user) {
		return userMapper.updateUserProfile(user);
	}
	
//	회원 탈퇴
	public int deleteUser(String userName) {
		return userMapper.deleteUser(userName);
	}
	
//	소셜 로그인
	
//	이메일 인증
	
//	휴대폰 인증
	
//	사업자 인증
	
//	사업자 인증 후 사용자 정보 수정
	
}
