package com.mysite.restaurant.jh;

import lombok.Data;

@Data
public class ImageDTO {
		private int imageId;
		private int restaurantId;
		private String imageUrl;
		private Integer imageOrder;
		private String uid;
}
