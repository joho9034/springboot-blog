package com.hak.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hak.blog.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrincipalDetail implements UserDetails {

	private static final long serialVersionUID = -1462863911873400058L;

	private User user;
	
	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() { //계정이 만료되지 않았는지? (true: 만료안됨)
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { //계정이 잠겨져 있지 않았는지? (true: 잠기지 않음)
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //비밀번호가 만료되지 않았는지? (true: 만료안됨)
		return true;
	}

	@Override
	public boolean isEnabled() { //계정이 활성화(사용가능)인지? (true: 활성화)
		return true;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { //계정의 권한목록 리턴
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(() -> { return "ROLE_" + user.getRole();	});
		
		return collectors;
	}
	
}
