package com.mysite.restaurant.hj.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mysite.restaurant.hj.domain.entity.UserStatus;
import com.mysite.restaurant.hj.domain.entity.UserType;

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

	private Long restaurantId;

	private List<UserAuthDTO> authorities;
	
	public String getAuth() {
        if (authorities != null && !authorities.isEmpty()) {
            return authorities.get(0).getAuth();
        }
        return null;
    }
	public void setAuth(String auth) {
	    if (authorities == null) {
	        authorities = new ArrayList<>();
	    }
	    if (authorities.isEmpty()) {
	        UserAuthDTO userAuth = UserAuthDTO.builder()
	                .userId(this.userId)
	                .auth(auth)
	                .build();
	        authorities.add(userAuth);
	    } else {
	        authorities.get(0).setAuth(auth); // 기존 UserAuthDTO의 auth 값을 업데이트
	    }
	}
}
