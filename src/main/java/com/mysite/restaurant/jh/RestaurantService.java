package com.mysite.restaurant.jh;

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
    
    public List<RestaurantDTO> searchRestaurants(RestaurantDTO restaurant) {

        return restaurantMapper.getSearch(restaurant);
    }
    public int countTotal() {
    	return restaurantMapper.countTotal();
    }
}
