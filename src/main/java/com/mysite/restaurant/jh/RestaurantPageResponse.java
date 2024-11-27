package com.mysite.restaurant.jh;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantPageResponse {
	    private List<RestaurantDTO> content;  // 실제 레스토랑 데이터 목록
	    private int totalPages;  // 전체 페이지 수
	    private long totalElements;  // 전체 아이템 수
	    private int pageNumber;  // 현재 페이지 번호
	    private int pageSize;  // 페이지 크기

}
