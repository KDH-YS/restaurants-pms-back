package com.mysite.restaurant.jh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RestaurantMapper {
	
	public List<RestaurantDTO> getSearch(
		    @Param("city") String city, 
		    @Param("district") String district,
		    @Param("neighborhood") String neighborhood,
		    @Param("food_type") String foodType,
		    @Param("parking_available") Integer parkingAvailable,
		    @Param("reservation_available") Integer reservationAvailable,
		    @Param("offset") int offset
		);
	 List<RestaurantDTO> getRestaurants(int offset);
   }
