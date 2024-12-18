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
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/users/**").permitAll()
						.requestMatchers("/api/admin/**").permitAll()
						.requestMatchers("/api/schedule/**").permitAll()// 인증 없이 허용할 경로
						// 주성
						.requestMatchers("/api/reviews/**").permitAll()
						.requestMatchers("/api/restaurants/**").permitAll()
						.requestMatchers("/api/js/**").permitAll()
						// 동현
						.requestMatchers("/api/schedule/**").permitAll()
						.requestMatchers("/api/reservations/manager/**").permitAll()// 인증 없이 허용할 경로
						.requestMatchers("/api/map/**").permitAll()// 인증 없이 허용할 경로
						.requestMatchers("/api/board/**").permitAll()// 인증 없이 허용할 경로
						// 지현
						.requestMatchers("/api/restaurant/**").permitAll()  // /api/restaurant 경로는 인증 없이 허용
						.requestMatchers("/images/**").permitAll()  // 이미지 경로에 대한 접근을 허용
						.anyRequest().authenticated()  // 그 외의 모든 요청은 인증이 필요
				)
				.exceptionHandling(exc -> exc
						.authenticationEntryPoint(jwtAuthenticationEntryPoint) // 비인증 사용자가 권한이 요구되는 리소스 접근 - 401 Unauthorized
						.accessDeniedHandler(jwtAccessDeniedHandler)) // 인증 사용자가 접근하는 리소스에 권한 없음 - 403 Forbidden
				.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setExposedHeaders(List.of("Authorization"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}