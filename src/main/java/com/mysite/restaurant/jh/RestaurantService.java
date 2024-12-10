package com.mysite.restaurant.jh;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantMapper restaurantMapper;

    public RestaurantPageResponse getRestaurants(int page, int size) {
        // 페이지가 1보다 작으면 1로 설정
        if (page <= 0) {
            page = 1;
        }

        // 오프셋 계산 (현재 페이지에서 가져올 데이터의 시작 위치)
        int offset = (page - 1) * size;

        // 레스토랑 목록 조회
        List<RestaurantDTO> restaurants = restaurantMapper.getRestaurants(offset, size);

        // 총 레스토랑 개수 조회
        int totalRestaurants = restaurantMapper.countTotal();

        // 전체 페이지 수 계산 (총 레스토랑 개수 / 페이지 크기)
        int totalPages = (int) Math.ceil((double) totalRestaurants / size);

        // 현재 페이지 번호, 페이지 크기 포함하여 RestaurantPageResponse 반환
        return new RestaurantPageResponse(
            restaurants,        // 레스토랑 목록
            totalPages,        // 전체 페이지 수
            totalRestaurants,  // 총 레스토랑 개수
            page,              // 현재 페이지 번호
            size               // 페이지 크기
        );
    }

    
    List<RestaurantDTO> getRestaurantsAll(RestaurantDTO restaurant){
    	return restaurantMapper.getRestaurantsAll(restaurant);
    }
    
    public 	List<RestaurantDTO> getRestaurants(String foodType, String city, String district, String neighborhood){
    	return restaurantMapper.getRestaurantsAll(foodType, city, district, neighborhood);
    };

 // 레스토랑 검색 및 페이지네이션 처리
    public RestaurantPageResponse searchRestaurants(RestaurantDTO restaurant, String query, int page, int size) {
        // 페이지 번호를 0부터 시작하게 하여 LIMIT에 맞게 조정
        int offset = (page - 1) * size;

        // 레스토랑 목록 검색
        List<RestaurantDTO> content = restaurantMapper.searchRestaurants(query, restaurant, size, offset);

        // 검색된 레스토랑 개수
        int totalElements = restaurantMapper.countSearch(query, restaurant);

        // 전체 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalElements / size);

        // 페이지 정보 포함하여 반환
        return new RestaurantPageResponse(content, totalPages, totalElements, page, size);
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

    public int insertRestaurant(RestaurantDTO restaurant) {
        // 레스토랑 등록 후, 해당 레코드의 ID를 반환
        return restaurantMapper.insertRestaurant(restaurant);
    }
    
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
	public Boolean getMaxImageOrder(int restaurantId) {
	    // DB에서 해당 레스토랑에 등록된 이미지들의 최대 image_order 값을 가져옵니다.
	    return restaurantMapper.getMaxImageOrder(restaurantId);
	}
	
	public void updateImage(ImageDTO imageDTO) {
        // 1. 해당 레스토랑의 모든 이미지에서 imageOrder를 false로 설정
        restaurantMapper.updateImageOrderToFalse(imageDTO.getRestaurantId());

        // 2. 선택된 이미지를 대표 이미지로 설정 (imageOrder = true)
        restaurantMapper.updateImage(imageDTO);
    }
	
	public List<Schedule> getScheduleById(int restaurantId){
    	return restaurantMapper.getScheduleById(restaurantId);
	}

}
