package com.mysite.restaurant.jh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RestaurantMapper {
	
	// 레스토랑 검색
    List<RestaurantDTO> searchRestaurants(
        @Param("query") String query,
        @Param("restaurant") RestaurantDTO restaurant,
        @Param("size") int size,
        @Param("offset") int offset
    );

    // 레스토랑 검색 결과 개수 반환
    int countSearch(
        @Param("query") String query,
        @Param("restaurant") RestaurantDTO restaurant
    );
    
	List<RestaurantDTO> getRestaurantsAll(RestaurantDTO restaurant);
	List<RestaurantDTO> getRestaurantsAll(String foodType, String city, String district, String neighborhood);
	 List<RestaurantDTO> getRestaurants(@Param("offset") int offset, @Param("size") int size);
	public int countTotal(); 
	public RestaurantDTO getRestaurantById(int restaurantId);
	int insertRestaurant(RestaurantDTO restaurant);
	void updateAddress();
	void updateRestaurant(RestaurantDTO restaurant);
   
	
	public List<MenuDTO> getMenusByRestaurantId(int restaurantId);
	void  deleteRestaurant(int restaurantId);
	void insertMenu(MenuDTO menu);
	void deleteMenu(@Param("restaurantId")int restaurantId,@Param("menuId")int menuId);
	
	public List<ImageDTO> getRestaurantImageById(int restaurantId);
	void insertImage(ImageDTO image);
	void deleteImage(@Param("restaurantId")int restaurantId,@Param("imageId")int imageId);
	public Boolean getMaxImageOrder(int restaurantId); 
	void updateImage(ImageDTO image);
	// 레스토랑에 속한 모든 이미지의 image_order를 false로 설정
    void updateImageOrderToFalse(int restaurantId);

	public List<Schedule> getScheduleById(int restaurantId);
	}
   
