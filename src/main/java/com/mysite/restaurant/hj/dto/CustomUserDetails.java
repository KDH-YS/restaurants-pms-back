package com.mysite.restaurant.hj.dto;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mysite.restaurant.hj.domain.entity.UserStatus;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L; // 클래스 버전에 대한 고유 ID
	
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
	public String getUsername() {
		return user.getUserName();
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
		return user.getStatus() == UserStatus.ACTIVE;
	}
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
