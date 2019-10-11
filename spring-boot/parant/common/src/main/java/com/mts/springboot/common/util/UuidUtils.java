package com.mts.springboot.common.util;

import java.util.UUID;

public class UuidUtils {

	/**
	 * 生成UUID
	 * 
	 * @return
	 */
	public static final String randomUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "").toLowerCase();
		return uuid;
	}

}
