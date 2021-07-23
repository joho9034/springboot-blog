package com.hak.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hak.blog.dto.ResponseDto;
import com.hak.blog.model.User;
import com.hak.blog.service.UserService;

@RestController
public class UserApiController {

	private UserService userService;
		
	public UserApiController(HttpSession session, UserService userSerivce) {
		this.userService = userSerivce;
	}
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController: save 호출됨");
		userService.save(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
}
