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
        @RequestParam(name ="page", defaultValue = "1") int page,  // 페이지 파라미터
        @RequestParam(name= "size", defaultValue = "16") int size,  // 페이지 파라미터
        Model model) {

        // 서비스 호출
        List<RestaurantDTO> restaurants = restaurantService.getRestaurants(page,size);
        
        int totalCount = restaurantService.countTotal();
        int totalPages = (int)Math.ceil((double) totalCount/size);
        int startPageGroup =(int)((page-1)/10)*10 +1;
        int endPageGroup = Math.min(startPageGroup+9,totalPages);
        
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize",size);
        model.addAttribute("startPageGroup",startPageGroup);
        model.addAttribute("endPageGroup",endPageGroup);
        return "restaurant";  // 검색 결과를 보여줄 JSP 또는 Thymeleaf 템플릿
    }
    
    @GetMapping("/restaurant/search")
    public String searchRestaurants(RestaurantDTO restaurant,
         Model model) {

        // 서비스 호출
        List<RestaurantDTO> restaurants = restaurantService.searchRestaurants(restaurant);

        model.addAttribute("restaurants", restaurants);
        return "restaurant/search";
    }
}
