package com.mysite.restaurant.jh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    
    
    @GetMapping("/restaurant")
    public String listRestaurants(
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
    
    @GetMapping("/restaurant/{restaurantId}")
    @ResponseBody
   public RestaurantDTO restaurantDetail(@PathVariable("restaurantId")int restaurantId) {
	   return restaurantService.getRestaurantById(restaurantId);
   }
    @GetMapping("/restaurant/menu/{restaurantId}")
    @ResponseBody
    public MenuDTO getMenusByRestaurantId(@PathVariable("restaurantId")int restaurantId) {
    	return restaurantService.getMenusByRestaurantId(restaurantId);
    }
    
	@GetMapping("/create")
	public String create() {
		return "restaurant/create";
	}
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<Map<String, String>> insertRestaurant(@RequestBody RestaurantDTO restaurant) {
	    try {
	        restaurantService.insertRestaurant(restaurant);
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "레스토랑 등록 성공");
	        return ResponseEntity.ok(response);  // 200 OK 응답
	    } catch (Exception e) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "레스토랑 등록에 실패했습니다.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);  // 500 오류 응답
	    }
	}

    
}
