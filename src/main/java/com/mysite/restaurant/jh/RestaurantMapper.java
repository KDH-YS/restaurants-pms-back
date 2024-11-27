package com.mysite.restaurant.jh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RestaurantMapper {
	
	   // 여러 레스토랑 데이터를 반환해야 하므로 selectList 사용
    List<RestaurantDTO> getSearch(
        @Param("restaurant") RestaurantDTO restaurant,
        @Param("offset") int offset,
        @Param("size") int size
    );

    int countSearch(@Param("restaurant") RestaurantDTO restaurant);

	List<RestaurantDTO> getRestaurantsAll(RestaurantDTO restaurant);
	List<RestaurantDTO> getRestaurantsAll(String foodType, String city, String district, String neighborhood);
	 List<RestaurantDTO> getRestaurants(@Param("offset") int offset, @Param("size") int size);
	public int countTotal(); 
	public RestaurantDTO getRestaurantById(int restaurantId);
	void insertRestaurant(RestaurantDTO restaurant);
	void updateAddress();
	void updateRestaurant(RestaurantDTO restaurant);
	
	public List<MenuDTO> getMenusByRestaurantId(int restaurantId);
	void  deleteRestaurant(int restaurantId);
	void insertMenu(MenuDTO menu);
	void deleteMenu(@Param("restaurantId")int restaurantId,@Param("menuId")int menuId);
	
	public List<ImageDTO> getRestaurantImageById(int restaurantId);
	void insertImage(ImageDTO image);
	void deleteImage(@Param("restaurantId")int restaurantId,@Param("imageId")int imageId);

   }
