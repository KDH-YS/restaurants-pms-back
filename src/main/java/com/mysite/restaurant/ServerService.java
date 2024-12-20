package com.mysite.restaurant;

import com.mysite.restaurant.kdh.Mappers.ServerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerService {
    private final ServerMapper serverMapper;

    @Autowired
    public ServerService(ServerMapper serverMapper) {
        this.serverMapper = serverMapper;
    }

    public void updateReservationComplete() {
        serverMapper.updateReservationComplete();  // 매퍼 메서드 호출
    }
}
