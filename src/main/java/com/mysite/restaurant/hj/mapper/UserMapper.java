package com.mysite.restaurant.hj.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.mysite.restaurant.hj.dto.UserAuthDTO;
import com.mysite.restaurant.hj.dto.UserDTO;
import com.mysite.restaurant.jh.RestaurantDTO;

@Mapper
public interface UserMapper {

//	회원가입
	int save(UserDTO user);
	void create(UserDTO user);
	void createAuth(UserAuthDTO userAuth);
//	아이디 중복 확인
    boolean existsByUserName(String userName);
    UserDTO findByUserName(String userName);
//  이메일 중복 확인
    UserDTO findByEmail(String email);
	
//	로그인
	Optional<UserDTO> selectUserByUsername(String userName);
	UserDTO selectUserByUserId(String userName);
	void updateLastLogin(String userName);
	
//	사용자 정보 조회
	UserDTO selectUserProfile(Long userId);
	
//	사용자 정보 수정
	int updateUserProfile(UserDTO user);
	
//	회원 탈퇴
	int deleteUser(String userName);
	
//	회원 조회
	List<UserDTO> selectAllMembers();
	String getRestaurantNameByUserId(Long userId);
	
//	회원 검색
	List<UserDTO> searchMembersByKeyword(String keyword);
	
//	권한 관리
	Optional<UserDTO> selectUserById(Long userId);
	void updateUserAuth(UserDTO user);
	
//	회원 삭제
	void deleteUserById(Long userId);
	
	//지현 레스토랑등록 관
    // 유저 조회 (userId로)
    UserDTO selectUserById(int userId);

    // 유저의 레스토랑 ID 업데이트
    int updateRestaurantIdForUser(int userId, int restaurantId);
}
