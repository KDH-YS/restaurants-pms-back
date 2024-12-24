package com.mysite.restaurant.hj.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.restaurant.hj.dto.UserAuthDTO;
import com.mysite.restaurant.hj.dto.UserDTO;
import com.mysite.restaurant.hj.jwt.JwtTokenProvider;
import com.mysite.restaurant.hj.mapper.UserMapper;
import com.mysite.restaurant.jh.RestaurantDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class AdminService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	
	public boolean existsUserByUserId(String userName) {
		return userMapper.selectUserByUserId(userName) != null;
	}
	
//	회원 조회
	public List<UserDTO> getAllMembers() {
		List<UserDTO> userList = userMapper.selectAllMembers();
	    for (UserDTO user : userList) {
	    	String restaurantName = userMapper.getRestaurantNameByUserId(user.getUserId());
	        user.setRestaurantName(restaurantName != null ? restaurantName : "없음");
	    }
	    return userList;
	}
	
//	회원 검색
	public List<UserDTO> searchMembers(String keyword) {
		return userMapper.searchMembersByKeyword(keyword);
	}
	
//	권한 관리
	@Transactional
	public UserDTO updateMemberType(Long userId, UserDTO user) {
		UserDTO existingUser = userMapper.selectUserById(userId)
				.orElseThrow(() -> new RuntimeException("Memeber not found"));
		
		// 권한 업데이트 로직을 UserDTO 내부 메서드로 이동
	    if (user.getAuthorities() != null && !user.getAuthorities().isEmpty()) {
	        existingUser.updateAuthority(user.getAuthorities().get(0).getAuth());
	    }
	    
	    userMapper.updateUserAuth(existingUser);
	    
		return existingUser;
	}
	
//	회원 삭제
	public void deleteMemberById(Long id) {
		userMapper.deleteUserById(id);
	}
}
