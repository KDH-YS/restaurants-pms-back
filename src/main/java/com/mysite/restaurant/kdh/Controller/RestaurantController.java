package com.mysite.restaurant.kdh.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.restaurant.kdh.Entity.ReservationEntity;
import com.mysite.restaurant.kdh.Entity.ScheduleEntity;
import com.mysite.restaurant.kdh.Service.ReservationService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
	
    private final ReservationService reservationService;

    public RestaurantController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/schedule")    
    public ResponseEntity<ScheduleEntity> insertSchedule(@RequestBody ScheduleEntity schedule){
    		ScheduleEntity insertedschedule = reservationService.createSchedule(schedule);
    	return ResponseEntity.ok(insertedschedule);
    }
    @PutMapping("/schedule/{id}")
    public ResponseEntity<ScheduleEntity> updateSchedule(@PathVariable("id")Long id, ScheduleEntity schedule){
    	schedule.setScheduleId(id);  // URL에서 받은 id로 예약 ID 설정

        // 예약 수정
    	ScheduleEntity updatedSchedule = reservationService.updateSchedule(schedule);

        if (updatedSchedule != null) {
            return ResponseEntity.ok(updatedSchedule);  // 200 OK와 함께 수정된 예약 객체 반환
        } else {
            // 예약 수정이 실패한 경우 404 Not Found 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
