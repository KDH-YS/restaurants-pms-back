package com.mysite.restaurant.kdh.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reservation")
@Data
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    
    @Column(name = "user_id")
    private long userId;
    
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "reservation_time")
    private LocalDateTime reservationTime;

    @Column(name = "request")
    private String request;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus status;

    @Column(name = "number_of_people")
    private int numberOfPeople;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum ReservationStatus {
        RESERVING, COMPLETED, CANCELED, NOSHOW, PENDING
    }
}