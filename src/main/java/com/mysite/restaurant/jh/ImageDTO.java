package com.mysite.restaurant.jh;

import lombok.Data;

@Data
public class ImageDTO {
		private int imageId;
		private int restaurantId;
		private String imageUrl;
		private boolean imageOrder;
		private String uid;
}
