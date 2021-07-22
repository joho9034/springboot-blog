package com.hak.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hak.blog.model.RoleType;
import com.hak.blog.model.User;
import com.hak.blog.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Transactional
	public void save(User user) {
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
	@Transactional(readOnly = true)
	public User login(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	
}
