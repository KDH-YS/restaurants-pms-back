package com.mysite.restaurant.hj.exception;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.mysite.restaurant.hj.dto.JsonResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request,
						 HttpServletResponse response,
						 AuthenticationException authExeption)
				throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().println(
				JsonResponse.builder()
							.success(false)
							.message("인증이 필요한 서비스입니다.")
							.build()
							);
	}
}
