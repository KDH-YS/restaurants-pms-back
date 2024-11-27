package com.mysite.restaurant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mysite.restaurant.hj.mapper") // UserMapper가 위치한 패키지 경로
public class RestaurantPmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantPmsApplication.class, args);
	}

}
