package com.mysite.restaurant.hj.dto;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {

	private final UserDTO user;
	private final Collection<? extends GrantedAuthority> authorities;
	
	public CustomUserDetails(UserDTO user) {
		this.user = user;
		this.authorities = user.getAuthorities().stream()
				.map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
				.collect(Collectors.toList());
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	@Override
	public String getEmail() {
		return user.getEmail();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return !"SUSPENDED".equals(user.getStatus());
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return "ACTIVE".equals(user.getStatus());
	}
}
