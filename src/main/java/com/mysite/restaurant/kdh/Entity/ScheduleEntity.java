package com.mysite.restaurant.kdh.Entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ScheduleEntity {
	private Long scheduleId;
    private Long restaurantId;      // restaurant_id (BigInt) => Long
    private LocalDate openDate;
    private LocalTime startTime; // start_time (Time) => LocalDateTime
    private LocalTime endTime;   // end_time (Time) => LocalDateTime
    private Boolean isOpen;          // is_open (TinyInt(1)) => Boolean
    private LocalTime breakStart; // break_start_time (Time) => LocalDateTime
    private LocalTime breakEnd;   // break_end_time (Time) => LocalDateTime
    private LocalDateTime createdAt;       // created_at (TIMESTAMP, NULL 가능)
    private LocalDateTime updatedAt; 
}
