package com.mysite.restaurant.hj.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.mysite.restaurant.hj.dto.UserDTO;

@Mapper
public interface AdminMapper {

//	회원 조회
	List<UserDTO> selectAllMembers();
	
//	회원 검색
	List<UserDTO> searchMembersByKeyword(String keyword);
	
//	권한 관리
	Optional<UserDTO> selectUserById(Long userId);
	void updateUserAuth(UserDTO user);
	
//	회원 삭제
	void deleteUserById(Long userId);
}
