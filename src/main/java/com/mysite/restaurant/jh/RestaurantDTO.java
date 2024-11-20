package com.mysite.restaurant.jh;

import lombok.Data;

@Data
public class RestaurantDTO {

    private Long restaurantId;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String foodType;
    private Integer parkingAvailable;
    private Integer reservationAvailable;
    private Integer totalSeats;
    private Integer averageRating;
    private String city;
    private String district;
    private String neighborhood;
    private String detailAddr;
}
