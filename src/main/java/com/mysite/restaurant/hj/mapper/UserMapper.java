package com.mysite.restaurant.hj.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mysite.restaurant.hj.domain.User;

@Mapper
public interface UserMapper {

	User findByUserEmail(String email);
	void save(User user);
}
