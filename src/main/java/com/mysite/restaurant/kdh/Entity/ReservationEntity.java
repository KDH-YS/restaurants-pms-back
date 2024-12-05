package com.mysite.restaurant.kdh.Entity;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class ReservationEntity {
    private Long reservationId;
    private long userId;
    private Long restaurantId;
    private LocalDateTime reservationTime;
    private String request;
    private ReservationStatus status;
    private int numberOfPeople;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private String restaurantName;
    
    private UsersEntity user;
    
    public enum ReservationStatus {
        RESERVING, COMPLETED, NOSHOW, PENDING,CANCELREQUEST
    }
}

