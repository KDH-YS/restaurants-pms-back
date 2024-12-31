package com.mysite.restaurant.kdh.Controller;

import com.mysite.restaurant.kdh.Entity.AdminCount;
import com.mysite.restaurant.kdh.Entity.AdminReservationStat;
import com.mysite.restaurant.kdh.Service.AdminBoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminBoardController {
    private final AdminBoardService adminBoardService;

    public AdminBoardController(AdminBoardService adminBoardService){this.adminBoardService = adminBoardService;}

    @GetMapping("/dashboard")
    public ResponseEntity<?> boardCount(){
        try {
            // 서비스 호출
            AdminCount adminCount = adminBoardService.dashboardCount();
            // 정상 응답
            return ResponseEntity.ok(adminCount);
        } catch (Exception e) {
            // 예외 발생 시 에러 응답
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve dashboard count: " + e.getMessage());
        }
    }


    // 일별 예약 통계
    @GetMapping("/daily")
    public ResponseEntity<?> getReservationsByDate() {
        try {
            List<AdminReservationStat.ReservationCountByDate> stats = adminBoardService.getReservationsByDate();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve daily reservation stats: " + e.getMessage());
        }
    }

    // 주별 예약 통계
    @GetMapping("/weekly")
    public ResponseEntity<?> getReservationsByWeek() {
        try {
            List<AdminReservationStat.ReservationCountByWeek> stats = adminBoardService.getReservationsByWeek();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve weekly reservation stats: " + e.getMessage());
        }
    }
    // 월별 예약 통계
    @GetMapping("/monthly")
    public ResponseEntity<?> getReservationsByMonth() {
        try {
            List<AdminReservationStat.ReservationCountByMonth> stats = adminBoardService.getReservationsByMonth();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve monthly reservation stats: " + e.getMessage());
        }
    }
}
