package com.hak.blog.model;

import lombok.Data;

@Data
public class KakaoProfile {
	
	private int id;
	private String connected_at;
	private Properties properties;
	private Kakao_account kakao_account;
	
	@Data
	public class Properties {
		private String nickname;
	}
	
	@Data
	public class Kakao_account {
		private boolean profile_nickname_needs_agreement;
		private Profile profile;
		private boolean has_email;
		private boolean email_needs_agreement;
		private boolean is_email_valid;
		private boolean is_email_verified;
		private String email;
	}
	
	@Data
	public class Profile {
		private String nickname;
	}
	
}