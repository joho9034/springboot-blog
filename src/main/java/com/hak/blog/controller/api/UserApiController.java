package com.hak.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hak.blog.config.auth.PrincipalDetail;
import com.hak.blog.dto.ResponseDto;
import com.hak.blog.model.User;
import com.hak.blog.service.UserService;

@RestController
public class UserApiController {

	private UserService userService;
		
	public UserApiController(UserService userSerivce) {
		this.userService = userSerivce;
	}
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController: save 호출됨");
		userService.save(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> userUpdate(
			@RequestBody User user, 
			@AuthenticationPrincipal PrincipalDetail principal, 
			HttpSession session) {
		System.out.println("UserApiController: update 호출됨");
		principal.setUser(userService.userUpdate(user));
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(authentication);
		session.setAttribute("SPRING_SECURITY_CONTEXT", context);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
}
