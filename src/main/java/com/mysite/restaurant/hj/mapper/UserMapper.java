package com.mysite.restaurant.hj.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mysite.restaurant.hj.domain.User;

@Mapper
public interface UserMapper {

	int save(User user);					//	회원가입
	User findByUserEmail(String email);		//	로그인
	User selectUserProfile(Long userId);	//	사용자 정보 조회
	int deleteUser(String email);		//	회원 탈퇴
	int updateUserProfile(User user);		//	사용자 정보 수정
}
