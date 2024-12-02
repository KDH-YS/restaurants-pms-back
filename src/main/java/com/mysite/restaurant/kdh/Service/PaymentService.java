package com.mysite.restaurant.kdh.Service;

import com.mysite.restaurant.kdh.Entity.Payment;
import com.mysite.restaurant.kdh.Mappers.PaymentMapper;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }
    public void savePayment(Payment payment) {
        paymentMapper.savePayment(payment);
    }
    public void updateStatus(Long reservationId) {
    	paymentMapper.paymentSuccess(reservationId);
    }
}
