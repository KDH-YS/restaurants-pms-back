package com.mysite.restaurant.js.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Restaurants {
    private Long restaurantId;
    private String name;
    private String address;
    private String phone;
    private String foodType;
    private Boolean parkingAvailable;
    private Integer totalSeats;
    private String description;
    private String mainImageUrl;
    private BigDecimal averageRating;
    private Boolean reservationAvailable;
    private String postelCode;
    private String city;
    private String district;
    private String neighborhood;
    private String detailAddr;
}
