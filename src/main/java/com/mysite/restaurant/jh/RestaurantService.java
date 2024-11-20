package com.mysite.restaurant.jh;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantMapper restaurantMapper;

    public List<RestaurantDTO> getRestaurants(int offset) {
        return restaurantMapper.getRestaurants(offset);
    }
    
    public List<RestaurantDTO> searchRestaurants(
        String city, String district, String neighborhood, 
        String foodType, Integer parkingAvailable, 
        Integer reservationAvailable, int offset) {

        return restaurantMapper.getSearch(city, district, neighborhood, 
                                          foodType, parkingAvailable, 
                                          reservationAvailable, offset);
    }
}
