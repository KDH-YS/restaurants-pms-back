package com.mysite.restaurant.kdh.Mappers;

import com.mysite.restaurant.kdh.Entity.ReservationEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationMapper {

    // 예약 생성 (Insert)
    void insertReservation(ReservationEntity reservation);
}
