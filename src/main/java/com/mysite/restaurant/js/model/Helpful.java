package com.mysite.restaurant.js.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Helpful {

	private Long voteId;
	private Long reviewId;
	private Long userId;
    private Timestamp voteDate;   // 누른 날짜
	private int state;
	
}
