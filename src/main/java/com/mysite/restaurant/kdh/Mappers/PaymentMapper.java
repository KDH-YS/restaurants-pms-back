package com.mysite.restaurant.kdh.Mappers;

import com.mysite.restaurant.kdh.Entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentMapper {

    // 결제 ID로 결제 정보 조회
    Payment findByPaymentId(@Param("paymentId") String paymentId);

    // 결제 상태 업데이트
    void updatePaymentStatus(@Param("paymentId") String paymentId, @Param("paymentStatus") String paymentStatus);

    // 결제 정보 저장
    void savePayment(Payment payment);
}
