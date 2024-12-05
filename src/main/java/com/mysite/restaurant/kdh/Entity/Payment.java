package com.mysite.restaurant.kdh.Entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Payment {

    private Long id;  // payment 테이블의 id (bigint)
    private Long reservationId;  // reservation_id (bigint)
    private String paymentId;  // payment_id (varchar)
    private Integer amount;  // amount (int)
    private String paymentStatus;  // payment_status (varchar)
    private LocalDateTime createdAt;  // created_at (datetime)
    private LocalDateTime updatedAt;  // updated_at (datetime)

    // getters and setters
}
