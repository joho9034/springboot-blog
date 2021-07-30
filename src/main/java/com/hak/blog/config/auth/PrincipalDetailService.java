package com.hak.blog.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hak.blog.model.User;
import com.hak.blog.repository.UserRepository;

@Service
public class PrincipalDetailService implements UserDetailsService {

	private UserRepository userRepository;
	
	public PrincipalDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username).orElseThrow(() -> {
			return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.: " + username);
		});
		return new PrincipalDetail(principal);
	}

}
