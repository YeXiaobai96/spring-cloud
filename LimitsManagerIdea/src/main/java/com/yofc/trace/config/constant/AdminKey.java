package com.yofc.trace.config.constant;

/**
 *各种键类型
 *
 */
public enum AdminKey {

	OAUTH_TOKEN("oauth:token:", "oauth token key"),
	USER_TOKEN("user:token:", "user token key"),
	OAUTH_USER("oauth:user:", "oauth user key"),
	USER_SECRET("user:secret:", "user secret key"),
	USER_MOBIL("user:mobil:", "user mobil key"),
	USER_MENU("user:menu:", "user menu key"),
	USER_INFO("user:info:", "user info key"),
	USER_AUTHCODE("user:auth:code:", "user mobil auth code key"),
	REQUEST_AUTH("Authorization","request authorization");

	private String value;
	private String desc;

	private AdminKey(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String desc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String value() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
