package com.mysite.restaurant.js.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long userId;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String profileImageUrl;
    private Boolean notificationAgreed;
    private UserType userType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum UserType {
        CUSTOMER, OWNER, USER
    }
}