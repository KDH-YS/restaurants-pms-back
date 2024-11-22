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
	
	 // 레스토랑 정보 수정 폼 페이지로 이동
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
    
    @DeleteMapping("/api/restaurant/delete/{restaurantId}")
    @ResponseBody
    public void deleteRestaurant(@PathVariable("restaurantId") int restaurantId) {
    	restaurantService.deleteRestaurant(restaurantId);
    	
    }
    
    //메뉴 불러오
    @GetMapping("/api/restaurant/menu/{restaurantId}")
    public ResponseEntity<List<MenuDTO>> getMenusByRestaurantId(@PathVariable("restaurantId") int restaurantId) {
        try {
            List<MenuDTO> menus = restaurantService.getMenusByRestaurantId(restaurantId);
            return ResponseEntity.ok(menus);  // 200 OK와 함께 메뉴 목록 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 예외 발생 시 500 반환
        }
    }
    
    //메뉴등록하
    @PostMapping("/api/restaurant/menu/{restaurantId}/insert")
    public ResponseEntity<Void> insertMenu(@PathVariable("restaurantId") int restaurantId, @RequestBody MenuDTO menu) {
        try {
            menu.setRestaurantId(restaurantId);  // 레스토랑 ID 설정
            restaurantService.insertMenu(menu);   // 서비스 호출하여 메뉴 추가
            return ResponseEntity.status(HttpStatus.CREATED).build();  // 성공 시 201 Created 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 예외 발생 시 500 반환
        }
    }

    //메뉴 삭제하기
    // 메뉴 삭제 API
    @DeleteMapping("/api/restaurant/menu/{restaurantId}/{menuId}/delete")
    public ResponseEntity<Void> deleteMenu(@PathVariable("restaurantId") int restaurantId, @PathVariable("menuId") int menuId) {
    	restaurantService.deleteMenu( restaurantId,menuId);
    	return ResponseEntity.noContent().build();
    }

    
    //이미지 컨트롤러
    //이미지 조회하기
    @GetMapping("/api/restaurant/{restaurantId}/image")
	public ResponseEntity<List<ImageDTO>> getRestaurantImageById(@PathVariable("restaurantId") int restaurantId){
		 List<ImageDTO> images = restaurantService.getRestaurantImageById(restaurantId);
         return ResponseEntity.ok(images);  // 200 OK와 함께 메뉴 목록 반환
	}
    
    //이미지등록하기
    @PostMapping("/api/restaurant/{restaurantId}/image/insert")
	public ResponseEntity<Void> insertImage(@PathVariable("restaurantId")int restaurantId,
			//@RequestParam("file") MultipartFile file,
			@RequestBody ImageDTO image) {
    	String uid =  UUID.randomUUID().toString();
    	image.setUid(uid);
    	
    	//업로드 파일 확장자 추
    	//String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    	//새로운 파일명 uuid+확장자
    	//String newFileName = uid + extension;
    	//파일을 원하는 디렉토리에 저장
    	// String uploadDir = "";
    	
    	image.setRestaurantId(restaurantId);  
    	restaurantService.insertImage(image);
    	return ResponseEntity.status(HttpStatus.CREATED).build();
	}
    
    //이미지삭제
    @DeleteMapping("/api/restaurant/{restaurantId}/image/{imageId}/delete")
    public ResponseEntity<Void> deleteImage(@PathVariable("restaurantId") int restaurantId, @PathVariable("imageId") int imageId) {
    	restaurantService.deleteImage( restaurantId,imageId);
    	return ResponseEntity.noContent().build();
    }
}
