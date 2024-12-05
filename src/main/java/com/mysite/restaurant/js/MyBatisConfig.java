package com.mysite.restaurant.js;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {
        "com.mysite.restaurant.hj.mapper",
        "com.mysite.restaurant.jh",
        "com.mysite.restaurant.kdh.Mappers",
        "com.mysite.restaurant.js.mapper"})
public class MyBatisConfig {
}
