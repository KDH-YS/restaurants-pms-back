package com.mysite.restaurant.js.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantImg {

    private Long imageId;       // 이미지 ID
    private Long restaurantId;  // 레스토랑 ID
    private String imageUrl;    // 이미지 URL
    private Byte imageOrder;    // 이미지 순서
    private String uid;         // UID (옵션)
}