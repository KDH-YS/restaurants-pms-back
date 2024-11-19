package com.mysite.restaurant.kdh.Service;

import com.mysite.restaurant.kdh.Entity.ReservationEntity;
import com.mysite.restaurant.kdh.Mappers.ReservationMapper;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationMapper reservationMapper) {
        this.reservationMapper = reservationMapper;
    }

    // 예약 생성 후 생성된 예약 객체 반환
    public ReservationEntity createReservation(ReservationEntity reservation) {
        reservationMapper.insertReservation(reservation);  // 예약 저장
        return reservation;  // 생성된 예약 객체 반환
    }
    public List<ReservationEntity> getReservationsByEmail(String email) {
        return reservationMapper.selectReservationsByEmail(email);
    }
    public ReservationEntity updateReservation(ReservationEntity reservationEntity) {
        int rowsAffected = reservationMapper.updateReservation(reservationEntity);
        
        // 수정이 성공하면 수정된 객체를 반환
        if (rowsAffected > 0) {
            return reservationEntity;
        } else {
            return null;  // 수정이 실패한 경우
        }
    }
    public boolean cancelReservationRequest(Long reservationId) {
        int rowsAffected = reservationMapper.cancelReservationRequest(reservationId);
        return rowsAffected > 0; // 업데이트 성공 여부 반환
    }
}
