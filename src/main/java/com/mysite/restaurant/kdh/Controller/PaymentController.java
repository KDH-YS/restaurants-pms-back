package com.mysite.restaurant.kdh.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.restaurant.kdh.Service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.mysite.restaurant.kdh.Entity.Payment;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${portone.api.secret}")
    private String portoneApiSecret;
    
    private final RestTemplate restTemplate;
    private final PaymentService paymentService;

    public PaymentController(RestTemplate restTemplate, PaymentService paymentService) {
        this.restTemplate = restTemplate;
        this.paymentService = paymentService;
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completePayment(@RequestBody Payment payment) {
        try {
            String paymentId = payment.getPaymentId();
            String url = "https://api.portone.io/payments/" + paymentId;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "PortOne " + portoneApiSecret);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> paymentResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (!paymentResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment verification failed: " + paymentResponse.getBody());
            }

            String paymentData = paymentResponse.getBody();
            double amountPaid = parseAmountFromPaymentData(paymentData);
            String paymentStatusFromApi = parseStatusFromPaymentData(paymentData);

            // 금액 비교
            if (payment.getAmount() != amountPaid) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment amount mismatch.");
            }

            // 결제 상태 비교
//            if (!payment.getPaymentStatus().equals(paymentStatusFromApi)) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment status mismatch.");
//            }

            payment.setPaymentStatus(paymentStatusFromApi);
            paymentService.savePayment(payment);

            return ResponseEntity.ok("Payment completed.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during payment verification: " + e.getMessage());
        }
    }


    private double parseAmountFromPaymentData(String paymentData) {
        try {
            JsonNode root = new ObjectMapper().readTree(paymentData);
            return root.path("amount").path("paid").asDouble();
        } catch (Exception e) {
            throw new RuntimeException("Error parsing payment data.", e);
        }
    }

    private String parseStatusFromPaymentData(String paymentData) {
        try {
            JsonNode root = new ObjectMapper().readTree(paymentData);
            return root.path("status").asText();
        } catch (Exception e) {
            throw new RuntimeException("Error parsing payment data.", e);
        }
    }
}
