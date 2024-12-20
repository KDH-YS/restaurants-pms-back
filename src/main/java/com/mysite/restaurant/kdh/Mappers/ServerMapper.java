package com.mysite.restaurant.kdh.Mappers;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ServerMapper {

    void updateReservationComplete();
}
