package com.mysite.restaurant.hj.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mysite.restaurant.hj.dto.UserDTO;

@Mapper
public interface UserMapper {

	int save(UserDTO user);					//	회원가입
	void create(UserDTO user);
	UserDTO selectByUserEmail(String email);		//	로그인
	UserDTO selectUserProfile(Long userId);	//	사용자 정보 조회
	int deleteUser(String email);		//	회원 탈퇴
	int updateUserProfile(UserDTO user);		//	사용자 정보 수정
}
