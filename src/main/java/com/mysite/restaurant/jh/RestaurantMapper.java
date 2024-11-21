package com.mysite.restaurant.jh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RestaurantMapper {
	
	public List<RestaurantDTO> getSearch(RestaurantDTO restaurant);
	 List<RestaurantDTO> getRestaurants(@Param("offset") int offset, @Param("size") int size);
	public int countTotal(); 
	public RestaurantDTO getRestaurantById(int restaurantId);
	void insertRestaurant(RestaurantDTO restaurant);
	public MenuDTO getMenusByRestaurantId(int restaurantId);
   }
