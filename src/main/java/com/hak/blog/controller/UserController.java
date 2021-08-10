package com.hak.blog.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hak.blog.config.auth.PrincipalDetail;
import com.hak.blog.model.KakaoProfile;
import com.hak.blog.model.OAuthToken;
import com.hak.blog.model.User;
import com.hak.blog.service.UserService;

@Controller
public class UserController {

//	@Value("${hak.key}")
//	private String hakKey;
	
//	private AuthenticationManager authenticationManager;
	
	private UserService userService;
	
	public UserController(UserService userSerivce) {
		this.userService = userSerivce;
	}
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code, @AuthenticationPrincipal PrincipalDetail principal, HttpSession session) {
		//POST 방식으로 key=value 데이터 요청
		//Retrofit2 -> 주로 android 에서 사용
		//OkHttp
		//RestTemplate
		RestTemplate rt = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "95f4fc62a3b6b2dffe27601bd732a856");
		params.add("redirect_uri", "http://localhost:8070/auth/kakao/callback");
		params.add("code", code);
		
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
		);
		//Gson, Json Simple, ObjectMapper
		ObjectMapper objMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objMapper.readValue(response.getBody(), OAuthToken.class);
		} catch(JsonMappingException jme) {
			jme.printStackTrace();
		} catch(JsonProcessingException jpe) {
			jpe.printStackTrace();
		}
		System.out.println("카카오 엑세스 토큰: " + oauthToken.getAccess_token());
				
		RestTemplate rt2 = new RestTemplate();

		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
		
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest,
				String.class
		);
		
		//Gson, Json Simple, ObjectMapper
		ObjectMapper objMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch(JsonMappingException jme) {
			jme.printStackTrace();
		} catch(JsonProcessingException jpe) {
			jpe.printStackTrace();
		}
		System.out.println("카카오 프로파일 ID: " + kakaoProfile.getId());
		System.out.println("카카오 프로파일 Email: " + kakaoProfile.getKakao_account().getEmail());
		//kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId()
		//kakaoProfile.getKakao_account().getEmail()
		UUID randomPassword = UUID.randomUUID();
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
				.password(randomPassword)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		//가입자 혹은 미가입자 여부 체크
		User originUser = userService.findMember(kakaoUser.getUsername());
		if (originUser.getUsername() == null) {
			System.out.println("기존 회원이 아니어서 자동 회원가입을 진행합니다...");
			userService.save(kakaoUser);
			
			principal.setUser(userService.findMember(kakaoUser.getUsername()));
		} else {
			System.out.println("기존 회원입니다...");
			principal.setUser(originUser);
		}
		
		//로그인 처리
		System.out.println("자동 로그인을 진행합니다...");
//		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), hakKey));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(authentication);
		session.setAttribute("SPRING_SECURITY_CONTEXT", context);
		
		return "redirect:/";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
}
