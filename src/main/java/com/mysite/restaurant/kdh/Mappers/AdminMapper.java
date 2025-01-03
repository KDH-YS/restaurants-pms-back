package com.mysite.restaurant.kdh.Mappers;

import com.mysite.restaurant.kdh.Entity.AdminCount;
import com.mysite.restaurant.kdh.Entity.AdminReservationStat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    AdminCount dashBoardCount();


    // 일별 예약 통계 (reservation_time 기준)
    List<AdminReservationStat.ReservationCountByDate> getReservationsByDate();
    // 주별 예약 통계 (reservation_time 기준)
    List<AdminReservationStat.ReservationCountByWeek> getReservationsByWeek();
    // 월별 예약 통계 (reservation_time 기준)
    List<AdminReservationStat.ReservationCountByMonth> getReservationsByMonth();



}
