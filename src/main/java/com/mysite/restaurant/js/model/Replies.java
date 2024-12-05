package com.mysite.restaurant.js.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Replies {

	private Long replyId;
	private Long reviewId;
	private Long userId;
	private String repliesContent;
    private Timestamp createdAt;   // 생성일
    
}
