package com.mysite.restaurant.jh;

import lombok.Data;

@Data
public class MenuDTO {
	private int menuId;
	private int restaurantId;
	private String name;
	private String price;
}
