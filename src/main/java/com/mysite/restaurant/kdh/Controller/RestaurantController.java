package com.mysite.restaurant.kdh.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    //스케줄 입력
    @PostMapping("/schedule")    
    public ResponseEntity<ScheduleEntity> insertSchedule(@RequestBody ScheduleEntity schedule){
    		ScheduleEntity insertedschedule = reservationService.createSchedule(schedule);
    	return ResponseEntity.ok(insertedschedule);
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<ScheduleEntity>> getScheduleByRestaurant(@RequestParam("restaurantId") Long restaurantId) {
        List<ScheduleEntity> scheduleList = reservationService.getScheduleByRestaurant(restaurantId);

        if (scheduleList.isEmpty()) {
            return ResponseEntity.noContent().build();  // 상태 코드 204 (No Content)
        }

        return ResponseEntity.ok(scheduleList);  // 상태 코드 200 (OK)
    }
    @DeleteMapping("/schedule/{scheduleId}")
    public ResponseEntity<String> DeleteReservation(@PathVariable("scheduleId") Long scheduleId){
        boolean isCancelled = reservationService.deleteSchedule(scheduleId);
        
        if (isCancelled) {
            return ResponseEntity.ok("일정이 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일정 삭제가 실패했습니다.");
        }
    }

}
