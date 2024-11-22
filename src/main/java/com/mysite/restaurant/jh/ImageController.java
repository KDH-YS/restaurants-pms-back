package com.mysite.restaurant.jh;

import java.util.List;
import java.util.UUID;

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
public class ImageController {
	 private final RestaurantService restaurantService;
	 

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
