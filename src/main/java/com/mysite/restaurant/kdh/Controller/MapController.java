package com.mysite.restaurant.kdh.Controller;

import com.mysite.restaurant.kdh.Service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MapController {

    private final MapService mapService;

    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    // 토큰을 가져오고 데이터를 가져오는 엔드포인트
    @GetMapping("/api/map")
    public Mono<String> getStageData(@RequestParam("address") String address) {
        return mapService.fetchDataWithToken(address)
                .onErrorResume(e -> Mono.just("Failed to fetch stage data: " + e.getMessage()));
    }
}
