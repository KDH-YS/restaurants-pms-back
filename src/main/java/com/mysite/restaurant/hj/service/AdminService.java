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
		
		// user.getAuthorities()가 null이 아닌지 확인
	    if (user.getAuthorities() != null && !user.getAuthorities().isEmpty()) {
	        existingUser.setAuth(user.getAuthorities().get(0).getAuth()); // auth 속성을 업데이트
	    }
		
		// userId가 여전히 설정되어 있는지 확인
	    if (existingUser.getUserId() == null) {
	        existingUser.setUserId(userId);
	    }
	    
//	    existingUser 객체의 authorities 업데이트
	    List<UserAuthDTO> updatedAuthorities = new ArrayList<>();
	    UserAuthDTO userAuth = UserAuthDTO.builder()
	    		.userId(existingUser.getUserId())
	    		.auth(existingUser.getAuth())
	    		.build();
	    updatedAuthorities.add(userAuth);
	    existingUser.setAuthorities(updatedAuthorities);
	    
	    userMapper.updateUserAuth(existingUser);
	    
		return existingUser;
	}
	
//	회원 삭제
	public void deleteMemberById(Long id) {
		userMapper.deleteUserById(id);
	}
}
