package com.mysite.restaurant;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ServerScheduler {

    private final ServerService serverService; // 필요한 서비스 주입

    public ServerScheduler(ServerService serverService) {
        this.serverService = serverService;
    }

    // 매시간 30분에 실행
    @Scheduled(cron = "0 30 * * * *")
    public void updateDatabase() {
        System.out.println("스케줄링 작업 실행: 데이터베이스 업데이트 시작");

        // 데이터베이스 수정 로직 호출
        serverService.updateReservationComplete();

        System.out.println("스케줄링 작업 완료: 데이터베이스 업데이트 완료");
    }
}
