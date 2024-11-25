package com.mysite.restaurant.kdh.Mappers;

import com.mysite.restaurant.kdh.Entity.ReservationEntity;
import com.mysite.restaurant.kdh.Entity.ScheduleEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReservationMapper {

    // 예약 생성 (Insert)
    void insertReservation(ReservationEntity reservation);
    List<ReservationEntity> selectReservationsByEmail(@Param("userId") Long userId);
    int updateReservation(ReservationEntity reservationEntity);
    int cancelReservationRequest(Long reservationId);
    List<ReservationEntity> selectReservationsByRestaurant(Long restaurantId);
    int deleteReservation(Long reservationId);
    void insertSchedule(ScheduleEntity schedule);
    int updateSchedule(ScheduleEntity schedule);
    void updateReservationManager(ReservationEntity reservation);
    ReservationEntity selectReservation(Long reservationId);
}
