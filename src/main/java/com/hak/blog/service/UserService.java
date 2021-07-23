package com.hak.blog.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hak.blog.model.RoleType;
import com.hak.blog.model.User;
import com.hak.blog.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	
	private BCryptPasswordEncoder encoder;
	
	public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}
	
	@Transactional
	public void save(User user) {
		String encPassword = encoder.encode(user.getPassword());
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
}
