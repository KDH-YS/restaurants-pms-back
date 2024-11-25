package com.mysite.restaurant.kdh.Service;

import com.mysite.restaurant.kdh.Entity.ReservationEntity;
import com.mysite.restaurant.kdh.Entity.ScheduleEntity;
import com.mysite.restaurant.kdh.Mappers.ReservationMapper;


import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationMapper reservationMapper) {
        this.reservationMapper = reservationMapper;
    }

    // 예약 
    public ReservationEntity createReservation(ReservationEntity reservation) {
        reservationMapper.insertReservation(reservation);  // 예약 저장
        return reservation;  // 생성된 예약 객체 반환
    }
    // 내 예약 조회
    public List<ReservationEntity> getReservationsByEmail(Long userId) {
        return reservationMapper.selectReservationsByEmail(userId);
    }
    
    //내 예약 수정
    public ReservationEntity updateReservation(ReservationEntity reservationEntity) {
        int rowsAffected = reservationMapper.updateReservation(reservationEntity);
        
        // 수정이 성공하면 수정된 객체를 반환
        if (rowsAffected > 0) {
            return reservationEntity;
        } else {
            return null;  // 수정이 실패한 경우
        }
    }
    //내 예약 취소
    public boolean cancelReservationRequest(Long reservationId) {
        int rowsAffected = reservationMapper.cancelReservationRequest(reservationId);
        return rowsAffected > 0; // 업데이트 성공 여부 반환
    }
    
    //내 가게 예약 조회
    public List<ReservationEntity> getReservationsByRestaurant(Long restaurantId) {
        return reservationMapper.selectReservationsByRestaurant(restaurantId);
    }
    //예약 삭제(업주)
    public boolean DeleteReservation(Long reservationId) {
        int rowsAffected = reservationMapper.deleteReservation(reservationId);
        return rowsAffected > 0; // 업데이트 성공 여부 반환
    }
    //가게 스케줄 입력
    public ScheduleEntity createSchedule(ScheduleEntity schedule) {
    		reservationMapper.insertSchedule(schedule);
    	return schedule;
    	
    }
    //가게 스케줄 변경
    public ScheduleEntity updateSchedule(ScheduleEntity schedule) {
        int rowsAffected = reservationMapper.updateSchedule(schedule);        
        // 수정이 성공하면 수정된 객체를 반환
        if (rowsAffected > 0) {
            return schedule;
        } else {
            return null;  // 수정이 실패한 경우
        }
    }
    //예약 상태변경(업주)
    public ReservationEntity updateReservationManager(ReservationEntity reservation) {
    		 reservationMapper.updateReservationManager(reservation);
    	return reservationMapper.selectReservation(reservation.getReservationId());
    }

}
