package com.mysite.restaurant.kdh.Entity;

import lombok.Data;

public class AdminReservationStat {

    @Data
    public static class ReservationCountByDate {
        private String date;   // 날짜 (yyyy-MM-dd)
        private Integer count; // 해당 날짜의 예약 건수
    }
    @Data
    public static class ReservationCountByMonth {
        private String month;  // 월 (yyyy-MM)
        private Integer count; // 해당 월의 예약 건수
    }
    @Data
    public static class ReservationCountByWeek {
        private String week;   // 주 번호 (yyyy-Www)
        private Integer count; // 해당 주의 예약 건수
    }
}
