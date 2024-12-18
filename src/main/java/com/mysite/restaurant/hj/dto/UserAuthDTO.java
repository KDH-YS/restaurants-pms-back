package com.mysite.restaurant.hj.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDTO {

	private Long authNo;
	private Long userId;
	private String auth;
	private boolean isDeleted;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;


}
