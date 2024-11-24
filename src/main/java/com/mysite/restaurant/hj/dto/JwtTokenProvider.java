package com.mysite.restaurant.hj.dto;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

	@Value("${jwt.secret")
	private String secretKey;
	
	@Value("${jwt.token-validity-in-seconds}")
	private long tokenValidityInSeconds;
	
	private SecretKey key;
	
	@PostConstruct
	public void init() {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
	}
	
	public String createToken(Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		long now = System.currentTimeMillis();
		Date validity = new Date(now + this.tokenValidityInSeconds * 1000);
		
		return Jwts.builder()
				.subject(userDetails.getUsername())
				.claim("auth", authorities)
				.claim("userName", userDetails.getUser().getUserName())
				.issuedAt(new Date(now))
				.expiration(validity)
				.signWith(key, SignatueAlgorithm.HS512)
				.compact();
	}
	
	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		
		Collection <? extends GrantedAuthority> authorities = 
				Arrays.stream(claims.get("auth").toString().split(","))
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		
		User principal = new User(Claims.getSubject(), "", authorities);
		
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
			return false;
		}
	}
}
