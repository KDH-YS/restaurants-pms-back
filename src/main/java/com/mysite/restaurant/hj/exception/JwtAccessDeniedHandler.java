package com.mysite.restaurant.hj.exception;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.mysite.restaurant.hj.dto.JsonResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, 
					   HttpServletResponse response, 
					   AccessDeniedException accessDeniedException) 
					throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getWriter().println(
				JsonResponse.builder()
							.success(false)
							.message("접근 권한이 없습니다.")
							.build()
							);
	}
}
