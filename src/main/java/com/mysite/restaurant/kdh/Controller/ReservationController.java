package com.mysite.restaurant.kdh.Controller;

import com.mysite.restaurant.kdh.Entity.ReservationEntity;
import com.mysite.restaurant.kdh.Service.ReservationService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 예약 생성 API (POST)
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationEntity reservation) {
        // 예약 생성
        ReservationEntity createdReservation = reservationService.createReservation(reservation);
        // 생성된 예약 정보를 JSON 형식으로 반환
        return ResponseEntity.ok().body(createdReservation);
    }
    
    @GetMapping
    public List<ReservationEntity> getReservationsByEmail(@RequestParam("email") String email) {
        return reservationService.getReservationsByEmail(email);
    }
 // 예약 수정
    @PutMapping("/{id}")
    public ResponseEntity<ReservationEntity> updateReservation(@PathVariable("id") Long id, @RequestBody ReservationEntity reservationEntity) {
        reservationEntity.setReservationId(id);  // URL에서 받은 id로 예약 ID 설정

        // 예약 수정
        ReservationEntity updatedReservation = reservationService.updateReservation(reservationEntity);

        if (updatedReservation != null) {
            return ResponseEntity.ok(updatedReservation);  // 200 OK와 함께 수정된 예약 객체 반환
        } else {
            // 예약 수정이 실패한 경우 404 Not Found 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/user/{reservationId}")
    public ResponseEntity<String> cancelReservationRequest(@PathVariable("reservationId") Long reservationId) {
        boolean isCancelled = reservationService.cancelReservationRequest(reservationId);
        
        if (isCancelled) {
            return ResponseEntity.ok("예약 취소 요청이 성공적으로 처리되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("예약 취소 요청에 실패했습니다.");
        }
    }
}