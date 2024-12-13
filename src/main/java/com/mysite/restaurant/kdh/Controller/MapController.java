package com.mysite.restaurant.kdh.Controller;

import com.mysite.restaurant.kdh.Service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapController {

    private final MapService mapService;

    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    // 토큰을 가져오고 데이터를 가져오는 엔드포인트
    @GetMapping("/api/map")
    public String getStageData(@RequestParam("address") String address) {
        String data = mapService.fetchDataWithToken(address);
        return data != null ? data : "Failed to fetch stage data";
    }
}
