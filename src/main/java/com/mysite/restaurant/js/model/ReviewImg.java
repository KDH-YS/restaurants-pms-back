package com.mysite.restaurant.js.model;

import lombok.Data;

@Data
public class ReviewImg {
	
	private Long reviewImageId;
	private Long reviewId;
	private String imageUrl;
	private int imageOrder;
}
