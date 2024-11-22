package com.mysite.restaurant.jh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    
    //레스토랑 페이지네이션
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
    
    //레스토랑 검색
    @GetMapping("/restaurant/search")
    public String searchRestaurants(RestaurantDTO restaurant,
         Model model) {

        // 서비스 호출
        List<RestaurantDTO> restaurants = restaurantService.searchRestaurants(restaurant);

        model.addAttribute("restaurants", restaurants);
        return "restaurant/search";
    }
    
    //레스토랑디테일
    @GetMapping("/restaurant/{restaurantId}")
    @ResponseBody
   public RestaurantDTO restaurantDetail(@PathVariable("restaurantId")int restaurantId) {
	   return restaurantService.getRestaurantById(restaurantId);
   }

    //레스토랑 등록
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
	
	 // 레스토랑 정보 수정
    @GetMapping("/updateRestaurant/{restaurantId}")
    public String showUpdateForm(@PathVariable("restaurantId") int restaurantId, Model model) {
        RestaurantDTO restaurant = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurant", restaurant);
        return "restaurant/updateRestaurant"; // Thymeleaf 템플릿을 사용하는 경우
    }

    // 레스토랑 정보 수정 처리
    @PostMapping("/updateRestaurant")
    public String updateRestaurant(@ModelAttribute RestaurantDTO restaurant) {
        restaurantService.updateRestaurant(restaurant);  // 서비스에서 수정 처리
        return "redirect:/restaurant/" + restaurant.getRestaurantId(); // 수정 후 레스토랑 상세 페이지로 리디렉션
    }
    
    //레스토랑 조회api 역순, 페이지네이션없
    @GetMapping("/api/restaurant")
    public ResponseEntity<List<RestaurantDTO>> restaurantListApi(RestaurantDTO restaurant){
    	List<RestaurantDTO> restaurants = restaurantService.getRestaurantsAll();
    	return ResponseEntity.ok(restaurants);
    }
    
    //레스토랑삭제
    @DeleteMapping("/api/restaurant/delete/{restaurantId}")
    @ResponseBody
    public void deleteRestaurant(@PathVariable("restaurantId") int restaurantId) {
    	restaurantService.deleteRestaurant(restaurantId);
    }
    
    //레스토랑 검색
    @GetMapping("api/restaurant/search")
    public ResponseEntity<List<RestaurantDTO>> searchRestaurantsApi(@ModelAttribute RestaurantDTO restaurant) {
        try {
            // 서비스 호출로 레스토랑 검색
            List<RestaurantDTO> restaurants = restaurantService.searchRestaurants(restaurant);
            
            // 검색 결과가 없으면 204 No Content 반환
            if (restaurants.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            // 검색 결과가 있으면 200 OK와 함께 데이터 반환
            return ResponseEntity.ok(restaurants);
            
        } catch (Exception e) {
            // 예외 처리: 서버 에러 500 반환
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}