package com.mysite.restaurant.hj.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.mysite.restaurant.hj.dto.UserAuthDTO;
import com.mysite.restaurant.hj.dto.UserDTO;

@Mapper
public interface UserMapper {

//	회원가입
	int save(UserDTO user);
	void create(UserDTO user);
	void createAuth(UserAuthDTO userAuth);
	
//	로그인
	UserDTO selectUserByUserId(String userName);
	void updateLastLogin(Long userId);
	Optional<UserDTO> selectUserByUsername(String name);
	Optional<UserDTO> selectMemberByUserId(String userName);
	UserDTO selectMemberByUserId2(String userName);
//	Optional<UserDTO> findByUserId(String user_name);
	
//	사용자 정보 조회
	UserDTO selectUserProfile(String userName);
	
//	사용자 정보 수정
	int updateUserProfile(UserDTO user);
	
//	회원 탈퇴
	int deleteUser(String userName);
	
}
