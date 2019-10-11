package com.yofc.trace.config;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {

	private String uid;
	private String name;
	private String openId;
	private String serect;
	private String tokenType;
	private Date expiration;//过期时间
	private long TTLMillis;//有效时间
	private String accessToken;
	private String refreshToken;
	private String scope;
	
	@Override
	public String toString() {
		return "JwtToken [uid=" + uid + ", name=" + name + ", openId=" + openId + ", serect=" + serect + ", tokenType="
				+ tokenType + ", expiration=" + expiration + ", TTLMillis=" + TTLMillis + ", accessToken=" + accessToken
				+ ", refreshToken=" + refreshToken + ", scope=" + scope + "]";
	}

}
