package com.mysite.restaurant.js.model;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class Reports {

    private Long reportId;
    private Long reviewId;
    private Long userId;
    
    private String reason;  // Enum을 String으로 수정
    private String reportDescription;
    
    private String status;  // Enum을 String으로 수정
    
    private Timestamp createdAt;   // 생성일

    // Enum 타입을 String으로 저장할 때 사용될 메서드
    public void setReason(ReportReason reason) {
        if (reason != null) {
            this.reason = reason.name(); // enum을 문자열로 변환하여 저장
        }
    }

    public ReportReason getReasonEnum() {
        return reason != null ? ReportReason.valueOf(reason) : null;  // 문자열을 enum으로 변환
    }

    public void setStatus(ReportStatus status) {
        if (status != null) {
            this.status = status.name(); // enum을 문자열로 변환하여 저장
        }
    }

    public ReportStatus getStatusEnum() {
        return status != null ? ReportStatus.valueOf(status) : null;  // 문자열을 enum으로 변환
    }
}
