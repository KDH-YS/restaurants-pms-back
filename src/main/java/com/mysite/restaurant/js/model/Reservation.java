package com.mysite.restaurant.js.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    private Long reservationId;
    private Long userId;
    private Long restaurantId;
    private LocalDateTime reservationTime;
    private String request;
    private ReservationStatus status;
    private int numberOfPeople;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum ReservationStatus {
        RESERVING, COMPLETED, NOSHOW, PENDING, CANCELREQUEST
    }
}