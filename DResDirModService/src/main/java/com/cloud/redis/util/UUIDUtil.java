package com.cloud.redis.util;

import java.util.UUID;

/**
 * @Filename: UUIDUtil
 * @Author: wm
 * @Date: 2019/4/15 15:37
 * @Description:uuid
 * @History:
 */
public class UUIDUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
