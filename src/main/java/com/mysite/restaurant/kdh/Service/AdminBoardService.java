package com.mysite.restaurant.kdh.Service;

import com.mysite.restaurant.kdh.Entity.AdminCount;
import com.mysite.restaurant.kdh.Entity.AdminReservationStat;
import com.mysite.restaurant.kdh.Mappers.AdminMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminBoardService {
    private final AdminMapper adminMapper;


    public AdminBoardService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public AdminCount dashboardCount(){
        return adminMapper.dashBoardCount();
    }
    // 일별 예약 통계
    public List<AdminReservationStat.ReservationCountByDate> getReservationsByDate() {
        return adminMapper.getReservationsByDate();
    }
    // 주별 예약 통계
    public List<AdminReservationStat.ReservationCountByWeek> getReservationsByWeek() {
        return adminMapper.getReservationsByWeek();
    }
    // 월별 예약 통계
    public List<AdminReservationStat.ReservationCountByMonth> getReservationsByMonth() {
        return adminMapper.getReservationsByMonth();
    }

}
