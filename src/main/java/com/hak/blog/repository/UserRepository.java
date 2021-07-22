package com.hak.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hak.blog.model.User;

//자동으로 bean에 등록됨
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsernameAndPassword(String username, String password);
	
//	@Query(value="SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//	User login(String username, String password);
	
}
