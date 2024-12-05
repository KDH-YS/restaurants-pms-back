package com.mysite.restaurant.jh;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class Schedule {
		private int scheduleId;
		private int restaurantId;
		private LocalDate openDate;
		private LocalTime startTime;
		private LocalTime endTime;
		private Boolean isOpen;
		private LocalTime breakStart;
		private LocalTime breakEnd;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
}
