package com.thingboard.device.common.util;

import java.util.UUID;

public class UuidUtils {

	/**
	 * 生成UUID
	 * 
	 * @return
	 */
	public static final String randomUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "").toUpperCase();
		return uuid;
	}

}
