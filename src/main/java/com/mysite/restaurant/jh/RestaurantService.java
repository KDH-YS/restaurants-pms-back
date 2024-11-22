package com.mysite.restaurant.jh;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantMapper restaurantMapper;

    public List<RestaurantDTO> getRestaurants(int page, int size) {
    	//page 값이 1보다 작으면 1로 설정(1페이지부터 시작)
    	if(page <= 0) {
    		page=1;
    	}
    	int offset = (page-1) * size; //페이지네이션 오프셋 계
            return restaurantMapper.getRestaurants(offset,size);
    }
    public 	List<RestaurantDTO> getRestaurantsAll(){
    	return restaurantMapper.getRestaurantsAll();
    };

    
    public List<RestaurantDTO> searchRestaurants(RestaurantDTO restaurant) {

        return restaurantMapper.getSearch(restaurant);
    }
    public int countTotal() {
    	return restaurantMapper.countTotal();
    }
    public RestaurantDTO getRestaurantById(int restaurantId) {
    	return restaurantMapper.getRestaurantById(restaurantId);
    };
    public List<MenuDTO> getMenusByRestaurantId(int restaurantId) {
        return restaurantMapper.getMenusByRestaurantId(restaurantId);  // List로 반환
    }

	public void insertRestaurant(RestaurantDTO restaurant) {
		restaurantMapper.insertRestaurant(restaurant);
		restaurantMapper.updateAddress();
	};
	public void updateRestaurant(RestaurantDTO restaurant) {
        // restaurantId를 기준으로 전체 레코드를 수정
        restaurantMapper.updateRestaurant(restaurant);
    };
    public void deleteRestaurant(int restaurantId) {
    	restaurantMapper.deleteRestaurant(restaurantId);
    }
    
	public void insertMenu(MenuDTO menu) {
		restaurantMapper.insertMenu(menu);
	}
	
	public void deleteMenu(int restaurantId,int menuId) {
        restaurantMapper.deleteMenu(restaurantId,menuId);
    }
	
	
	public List<ImageDTO> getRestaurantImageById(int restaurantId){
		return restaurantMapper.getRestaurantImageById(restaurantId);
	}
	public void insertImage(ImageDTO image) {
		restaurantMapper.insertImage(image);
	}
	public void deleteImage(int restaurantId,int imageId) {
		restaurantMapper.deleteImage(restaurantId, imageId);
	}

}
