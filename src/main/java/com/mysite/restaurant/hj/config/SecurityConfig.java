package com.mysite.restaurant.hj.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mysite.restaurant.hj.exception.JwtAccessDeniedHandler;
import com.mysite.restaurant.hj.exception.JwtAuthenticationEntryPoint;
import com.mysite.restaurant.hj.jwt.JwtFilter;
import com.mysite.restaurant.hj.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.httpBasic(httpBasic -> httpBasic.disable())
			.formLogin(formLogin -> formLogin.disable()) // 시큐리티 제공 기본 로그인 페이지와 처리를 비활성화
//			JWT는 세션을 필요하지 않음
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/api/users/**").permitAll()
					.requestMatchers("/api/auth/**").permitAll()
					.anyRequest().authenticated())
//			예외처리
//			스프링 시큐리티 인증/인가 과정에서의 예외발생 처리
			.exceptionHandling(exc -> exc
					.authenticationEntryPoint(jwtAuthenticationEntryPoint) // 비인증 사용자가 권한이 요구되는 리소스 접근 - 401 Unauthorized
					.accessDeniedHandler(jwtAccessDeniedHandler)) // 인증 사용자가 접근하는 리소스에 권한 없음 - 405 Forbidden
//			UsernamePasswordAuthenticationFilter를 실행하기 전에 JwtFilter를 실행한다.
			.addFilterBefore(new JwtFilter(tokenProvider),
					UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	스프링 시큐리티에서 실제로 인증을 처리하는 인터페이스
//	JWT 토큰 발급 전 사용자 인증에 사용
//	username/password 검증에 사용
	@Bean
	public AuthenticationManager AuthenticationManager(
			AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setExposedHeaders(List.of("Authorization"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
