package com.mysite.restaurant.jh;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MenuController {
	 private final RestaurantService restaurantService;

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

    
}
