package com.mysite.restaurant.jh;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurant")
    public String searchRestaurants(
        @RequestParam(name = "page", defaultValue = "1") int page,  // 페이지 파라미터
        Model model) {

        // 페이지 계산 (16개씩 페이지네이션)
        int offset = (page - 1) * 16;

        // 서비스 호출
        List<RestaurantDTO> restaurants = restaurantService.getRestaurants(offset);

        model.addAttribute("restaurants", restaurants);
        return "restaurant";  // 검색 결과를 보여줄 JSP 또는 Thymeleaf 템플릿
    }
    
    @GetMapping("/restaurant/search")
    public String searchRestaurants(
        @RequestParam(name = "city", required = false) String city,
        @RequestParam(name = "district", required = false) String district,
        @RequestParam(name = "neighborhood", required = false) String neighborhood,
        @RequestParam(name = "food_type", required = false) String foodType,
        @RequestParam(name = "parking_available", required = false) String parkingAvailable,
        @RequestParam(name = "reservation_available", required = false) String reservationAvailable,
        @RequestParam(name = "page", defaultValue = "1") int page,  // 페이지 파라미터
        Model model) {

        // 페이지 계산 (16개씩 페이지네이션)
        int offset = (page - 1) * 16;

        // 빈 값 체크 후 처리 (빈 문자열을 null로 변환)
        city = (city != null && !city.isEmpty()) ? city : null;
        district = (district != null && !district.isEmpty()) ? district : null;
        neighborhood = (neighborhood != null && !neighborhood.isEmpty()) ? neighborhood : null;
        foodType = (foodType != null && !foodType.isEmpty()) ? foodType : null;
        parkingAvailable = (parkingAvailable != null && !parkingAvailable.isEmpty()) ? parkingAvailable : null;
        reservationAvailable = (reservationAvailable != null && !reservationAvailable.isEmpty()) ? reservationAvailable : null;

        // Integer로 변환
        Integer parkingAvailableInt = (parkingAvailable != null) ? Integer.parseInt(parkingAvailable) : null;
        Integer reservationAvailableInt = (reservationAvailable != null) ? Integer.parseInt(reservationAvailable) : null;

        // 서비스 호출
        List<RestaurantDTO> restaurants = restaurantService.searchRestaurants(
                city, district, neighborhood, foodType, parkingAvailableInt, reservationAvailableInt, offset);

        model.addAttribute("restaurants", restaurants);
        return "restaurant/search";
    }
}
