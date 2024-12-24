package com.mysite.restaurant.hj.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.mysite.restaurant.hj.domain.entity.UserStatus;
import com.mysite.restaurant.hj.domain.entity.UserType;
import com.mysite.restaurant.jh.RestaurantDTO;

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
public class UserDTO {

	private Long userId;
	private String userName;
	private String password;
	private String name;
	private String email;
	private String phone;
	private String profileImageUrl; // 사용자 프로필 이미지 URL
	private Integer notificationAgreed; // 알림 수신 동의
	private UserType userType;			// 사용자 권한
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private int isDeleted;
	private UserStatus status;

	private List<RestaurantDTO> restaurants;
	private String restaurantName;
    private int restaurantId;  // 여기에 restaurantId 추가

	private List<UserAuthDTO> authorities;
	
	public UserDTO(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	
	public List<String> getAuthoritiesAsString() {
		if (authorities == null) {
	        return Collections.emptyList();
	    }
	    return authorities.stream().map(UserAuthDTO::getAuth).collect(Collectors.toList());
    }
	public void setAuthoritiesAsString(List<String> authorities) {
	    if (this.authorities == null) {
	        this.authorities = new ArrayList<>();
	    }
	    this.authorities.clear(); // 기존 권한 제거
	    for (String auth : authorities) {
	        this.authorities.add(UserAuthDTO.builder()
	            .userId(this.userId)
	            .auth(auth)
	            .build());
	    }
	}
	
	public void updateAuthority(String newAuth) {
	    if (this.authorities == null) {
	        this.authorities = new ArrayList<>();
	    }

	    // 기존 권한 중 첫 번째 것만 변경하거나, 권한이 없으면 새로 추가
	    if (!this.authorities.isEmpty()) {
	        this.authorities.get(0).setAuth(newAuth);
	    } else {
	        this.authorities.add(UserAuthDTO.builder()
	                .userId(this.userId)
	                .auth(newAuth)
	                .build());
	    }
	}

}
