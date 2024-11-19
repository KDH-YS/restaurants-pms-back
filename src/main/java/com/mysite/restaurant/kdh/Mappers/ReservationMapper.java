package com.mysite.restaurant.kdh.Mappers;

import com.mysite.restaurant.kdh.Entity.ReservationEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReservationMapper {

    // 예약 생성 (Insert)
    void insertReservation(ReservationEntity reservation);
    List<ReservationEntity> selectReservationsByEmail(@Param("email") String email);
    int updateReservation(ReservationEntity reservationEntity);
    int cancelReservationRequest(Long reservationId);
}
