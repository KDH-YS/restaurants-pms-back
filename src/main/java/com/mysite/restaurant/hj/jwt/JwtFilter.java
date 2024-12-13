package com.mysite.restaurant.hj.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

	private final JwtTokenProvider tokenProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain)
					throws ServletException, IOException {
		
//		로그아웃 요청은 JWT 필터에서 검증하지 않도록 예외 처리
		if (isLogoutRequest(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String jwt = resolveToken(request);
		
		if (jwt != null && tokenProvider.validateToken(jwt)) {
//			회원가입 및 로그인 엔드포인트로의 요청을 검사
			if (isSignupOrLoginRequest(request.getRequestURI()) && tokenProvider.validateToken(jwt)) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				
				String jsonMessage = "{\"message\": \"이미 로그인된 사용자입니다.\"}";
				response.getWriter().write(jsonMessage);
				response.getWriter().flush();
				return; // 이미 토큰이 있는 사용자는 로그인 또는 회원가입 불가
			}
			
//			JWT가 유효하다면 사용자 인증 정보를 SecurityContext에 설정
			Authentication authentication = tokenProvider.getAuthentication(jwt);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private boolean isSignupOrLoginRequest(String requestUri) {
		return requestUri.contains("/signup") || requestUri.contains("/login");
	}
	
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
//	로그아웃 요청인지 확인하는 메서드
	private boolean isLogoutRequest(String requestUri) {
		return requestUri.contains("/logout");
	}
}
