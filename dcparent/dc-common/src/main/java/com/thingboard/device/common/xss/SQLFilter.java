package com.thingboard.device.common.xss;


import com.thingboard.device.common.exception.BaseException;
import org.apache.commons.lang3.StringUtils;

/**
 * SQL过滤
 */
public class SQLFilter {

	/**
	 * SQL注入过滤
	 * 
	 * @param str
	 *            待验证的字符串
	 */
	public static String sqlInject(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		// 去掉'|"|;|\字符
		str = StringUtils.replace(str, "'", "");
		str = StringUtils.replace(str, "\"", "");
		str = StringUtils.replace(str, ";", "");
		str = StringUtils.replace(str, "\\", "");

		// 转换成小写
		str = str.toLowerCase();

		// 非法字符
		String[] keywords = { "master", "truncate", "insert", "select", "delete", "update", "declare", "alert",
				"drop" };

		// 判断是否包含非法字符
		for (String keyword : keywords) {
			if (str.indexOf(keyword) != -1) {
				throw new BaseException("包含非法字符");
			}
		}

		return str;
	}

	public static boolean isSqlInject(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		// 转换成小写
		String cstr = str.toLowerCase();

		// 非法字符
		String[] keywords = { " or ", "show ", "master", "truncate ", "insert ", "select ", "delete ", "update ",
				"declare ", "alert ", "drop " };

		// 判断是否包含非法字符
		for (String keyword : keywords) {
			if (cstr.indexOf(keyword) != -1) {
				return true;
			}
		}

		return false;
	}
}
