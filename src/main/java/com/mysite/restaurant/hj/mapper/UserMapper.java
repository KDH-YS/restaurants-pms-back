package com.mysite.restaurant.hj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mysite.restaurant.hj.domain.User;

@Mapper
public interface UserMapper {

	int save(User user);
	User findByUserEmail(String email);
	User selectUserProfile(Long userId);
	String deleteUser(String email);
	int updateUserProfile(User user);
}
