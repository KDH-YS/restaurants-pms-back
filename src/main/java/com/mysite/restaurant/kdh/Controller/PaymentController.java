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
    private final ObjectMapper objectMapper;

    public PaymentController(RestTemplate restTemplate, PaymentService paymentService, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.paymentService = paymentService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<String> completePayment(@RequestBody Payment payment) {
        try {
            String paymentId = payment.getPaymentId();
            String url = "https://api.portone.io/payments/" + paymentId;

            HttpHeaders headers = createAuthorizationHeader();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> paymentResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (!paymentResponse.getStatusCode().is2xxSuccessful()) {
                return createErrorResponse("Payment verification failed: " + paymentResponse.getBody());
            }

            String paymentData = paymentResponse.getBody();
            double amountPaid = parseAmountFromPaymentData(paymentData);
            String paymentStatusFromApi = parseStatusFromPaymentData(paymentData);

            if (!isAmountValid(payment, amountPaid)) {
                return createErrorResponse("Payment amount mismatch.");
            }


            payment.setPaymentStatus(paymentStatusFromApi);
            paymentService.savePayment(payment);

            return ResponseEntity.ok("Payment completed.");
        } catch (Exception e) {
            return createErrorResponse("Error during payment verification: " + e.getMessage());
        }
    }

    @PatchMapping("/{reservationId}")
    public ResponseEntity<String> successPayment(@PathVariable("reservationId") Long reservationId) {
        try {
            paymentService.updateStatus(reservationId);
            return ResponseEntity.ok("예약 성공");
        } catch (Exception e) {
            return createErrorResponse("서버 오류: " + e.getMessage());
        }
    }

    private boolean isAmountValid(Payment payment, double amountPaid) {
        return payment.getAmount() == amountPaid;
    }

    private HttpHeaders createAuthorizationHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "PortOne " + portoneApiSecret);
        return headers;
    }

    private ResponseEntity<String> createErrorResponse(String errorMessage) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    private double parseAmountFromPaymentData(String paymentData) {
        try {
            JsonNode root = objectMapper.readTree(paymentData);
            return root.path("amount").path("paid").asDouble();
        } catch (Exception e) {
            throw new RuntimeException("Error parsing payment data for amount.", e);
        }
    }

    private String parseStatusFromPaymentData(String paymentData) {
        try {
            JsonNode root = objectMapper.readTree(paymentData);
            return root.path("status").asText();
        } catch (Exception e) {
            throw new RuntimeException("Error parsing payment data for status.", e);
        }
    }
}
