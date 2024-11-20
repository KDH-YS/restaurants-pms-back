package com.mysite.restaurant.kdh.Entity;


import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UsersEntity {
    private Long userId;                   // user_id (BIGINT)
    private String email;                  // email (VARCHAR(100))
    private String password;               // password (VARCHAR(200))
    private String name;                   // name (VARCHAR(50))
    private String phone;                  // phone (VARCHAR(20))
    private String profileImageUrl;        // profile_image_url (VARCHAR(500), NULL 가능)
    private Boolean notificationAgreed;    // notification_agreed (TINYINT(1), NULL 가능)
    private UserType userType;             // user_type (ENUM, CUSTOMER, OWNER, USER)
    private LocalDateTime createdAt;       // created_at (TIMESTAMP, NULL 가능)
    private LocalDateTime updatedAt;       // updated_at (TIMESTAMP, NULL 가능)

    public enum UserType {
        CUSTOMER, OWNER, USER
    }
}
