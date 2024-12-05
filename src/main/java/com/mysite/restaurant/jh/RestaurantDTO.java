package com.mysite.restaurant.jh;

import lombok.Data;

@Data
public class RestaurantDTO {

	  private int restaurantId;
	    private String name;
	    private String description;
	    private String postelCode;
	    private String roadAddr;
	    private String jibunAddr;
	    private String phone;
	    private String foodType;
	    private Boolean parkingAvailable;  // Boolean으로 변경
	    private Boolean reservationAvailable;  // Boolean으로 변경
	    private int totalSeats;  // Integer 대신 int로 변경, null을 허용하지 않음
	    private Double averageRating;  // 평균 평점은 Double로 변경
	    private String city;
	    private String district;
	    private String neighborhood;
	    private String detailAddr;
}
