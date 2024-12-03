package com.mysite.restaurant.hj.service;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisUtil {

	private final StringRedisTemplate redisTemplate; // Redis에 접근하기 위한 Spring의 Redis 템플릿 클래스
	
//	지정된 key에 해당하는 데이터를 Redis에서 가져옴
	public String getData(String key) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		return valueOperations.get(key);
	}
	
//	지정된 key에 값을 저장
	public void setData(String key, String value) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(key, value);
	}
	
//	지정된 key에 값을 저장하고, 지정된 시간(duration) 후에 데이터가 만료되도록 설정
	public void setDataExpire(String key, String value, long duration) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		Duration expireDuration = Duration.ofSeconds(duration);
		valueOperations.set(key, value, expireDuration);
	}
	
//	key에 해당하는 데이터를 Redis에서 삭제
	public void deleteData(String key) {
		redisTemplate.delete(key);
	}
}
