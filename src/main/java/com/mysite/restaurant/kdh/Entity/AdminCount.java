package com.mysite.restaurant.kdh.Entity;

import lombok.Data;

@Data
public class AdminCount {
    private Integer users;
    private Integer restaurants;
    private Integer reservations;
    private Integer reviews;
}
