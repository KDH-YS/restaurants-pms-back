package com.mysite.restaurant.hj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
//	UserDetailsService를 implements한 클래스는 무조건 loadUserByUsername가 들어가야한다.
	@Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserDTO> user = userMapper.selectUserByUsername(userName);
        if (user.isEmpty()) {
        	throw new UsernameNotFoundException("User not found with userName: " + userName);
        }
        return new CustomUserDetails(user.orElse(null));
    }
	
//	로그아웃
	
//	사용자 정보 조회
	public UserDTO selectUserProfile(Long userId) {
        // 데이터베이스에서 유저 조회
        UserDTO user = userMapper.selectUserProfile(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found with user_name: " + userId);
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
}
