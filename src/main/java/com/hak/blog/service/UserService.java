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
	
	@Transactional
	public User userUpdate(User user) {
		User updUser = userRepository.findById(user.getId()).orElseThrow(() -> {
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		if (updUser.getOauth() == null || updUser.getOauth().equals("")) {
			String encPassword = encoder.encode(user.getPassword());
			updUser.setPassword(encPassword);
			updUser.setEmail(user.getEmail());
		}
		
		return updUser;
	}
	
	@Transactional(readOnly = true)
	public User findMember(String username) {
		User user = userRepository.findByUsername(username).orElseGet(() -> {
			return new User();
		});
		return user;
	}
	
}
