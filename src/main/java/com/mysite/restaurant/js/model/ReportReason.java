package com.mysite.restaurant.js.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ReportReason {
	FAKE("인증되지 않은 글"),
	INAPPROPRIATE("부적절한 내용"),
	OFFENSIVE("모욕적"),
	OTHER("기타");

	private final String korean;

	ReportReason(String korean) {
		this.korean = korean;
	}

	@JsonValue
	public String getKorean() {
		return korean;
	}
}
