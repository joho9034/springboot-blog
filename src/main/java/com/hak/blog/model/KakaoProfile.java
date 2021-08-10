package com.hak.blog.model;

import lombok.Data;

@Data
public class KakaoProfile {
	
	public int id;
	public String connected_at;
	public Properties properties;
	public Kakao_account kakao_account;
	
	@Data
	public class Properties {
		public String nickname;
	}
	
	@Data
	public class Kakao_account {
		public boolean profile_nickname_needs_agreement;
		public Profile profile;
		public boolean has_email;
		public boolean email_needs_agreement;
		public boolean is_email_valid;
		public boolean is_email_verified;
		public String email;
	}
	
	@Data
	public class Profile {
		public String nickname;
	}
	
}