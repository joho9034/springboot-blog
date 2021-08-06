package com.hak.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hak.blog.model.OAuthToken;

@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code) {
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
		
		return response2.getBody();
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
}
