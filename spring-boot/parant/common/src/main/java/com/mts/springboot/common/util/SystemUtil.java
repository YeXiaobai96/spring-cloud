package com.mts.springboot.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * create by wm at 2018/1/26 17:02
 *
 * @author wm
 * @date 2018/1/26
 */
public class SystemUtil
{
	private final static int DATE_FORMAT_no = 0;
	private final static int DATE_FORMAT_yyyy = 1;
	private final static int DATE_FORMAT_yyyyMM = 2;
	private final static int DATE_FORMAT_yyyyMMdd = 3;
	private final static int DATE_FORMAT_yyMMdd = 4;

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 参考文章： http://developer.51cto.com/art/201111/305181.htm
	 *
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 *
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
	 * 192.168.1.100
	 *
	 * 用户真实IP为： 192.168.1.110
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}else {
			int index = ip.indexOf(",");
			if (index != -1) {
				ip = ip.substring(0, index);
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
	}

	public static String buildIdStr(Map<String,Object> idMap,Map<String,Object> outMap)
	{
		int dateFormat = idMap.get("DATE_FORMAT") == null ? 0 : Integer.parseInt(idMap.get("DATE_FORMAT").toString());
		int length = idMap.get("ID_LENGTH") == null ? 0 : Integer.parseInt(idMap.get("ID_LENGTH").toString());
		long idValue = idMap.get("ID_VALUE") == null ? 0 : Long.parseLong(idMap.get("ID_VALUE").toString());
		String prefix = idMap.get("PREFIX") == null ? "" : idMap.get("PREFIX").toString();
		String postfix = idMap.get("POSTFIX") == null ? "" : idMap.get("POSTFIX").toString();
		String infix = idMap.get("INFIX") == null ? "" : idMap.get("INFIX").toString();
		String dateValue = idMap.get("DATE_VALUE") == null ? "" : idMap.get("DATE_VALUE").toString();
		int valueRandom =
				idMap.get("VALUE_RANDOM") == null ? 0 : Integer.parseInt(idMap.get("VALUE_RANDOM").toString());
		//获取当前日期
		String format = "yyyy";
		if (dateFormat == DATE_FORMAT_yyyy)
		{
			format = "yyyy";
		}
		else if (dateFormat == DATE_FORMAT_yyyyMM)
		{
			format = "yyyyMM";
		}
		else if (dateFormat == DATE_FORMAT_yyyyMMdd)
		{
			format = "yyyyMMdd";
		}
		else if (dateFormat == DATE_FORMAT_yyMMdd)
		{
			format = "yyMMdd";
		}

		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String date = formatter.format(new Date());

		if (valueRandom == 2)
		{
			//完全随机
			idValue = new Double((Math.random() * new Long(StringUtils.leftPad("", length, '9')))).longValue() + 1;
			if (dateFormat > DATE_FORMAT_no)
			{
				dateValue = date;
			}
			else
			{
				dateValue = "";
			}
		}
		else
		{
			//需要增加的数值
			long needAddValue = 1;
			if (valueRandom == 1)
			{
				//如果是随机增加数，则生成1-10的随机数
				needAddValue = new Double((Math.random() * 9)).longValue() + 1;
			}
			if (dateFormat > DATE_FORMAT_no)
			{
				idValue = idValue + needAddValue;
				if (!dateValue.equals(date))
				{
					idValue = 1;
					dateValue = date;
				}
			}
			else
			{
				idValue = idValue + needAddValue;
				dateValue = "";
			}
		}

		outMap.put("idValue",idValue);
		outMap.put("dateValue",dateValue);
		return prefix + dateValue + infix + StringUtils.leftPad(Long.toString(idValue), length, '0') + postfix;
	}

	public static String createRandomNum(int num)
	{
		String randomNumStr = "";
		for (int i = 0; i < num; i++)
		{
			int randomNum = (int) (Math.random() * 10);
			randomNumStr += randomNum;
		}
		return randomNumStr;
	}

	/**
	 * 将IP转化为数字
	 *
	 * @param ip
	 * @return
	 */
	public static Long convertIpToNumber(final String ip) {
		if (StringUtils.isEmpty(ip))
			return 0l;
		String[] ipArr = StringUtils.split(ip, ".");
		if (ipArr.length != 4) {
			return 0L;
		}
		return Long.parseLong(ipArr[0]) * 256 * 256 * 256 + Long.parseLong(ipArr[1]) * 256 * 256 + Long.parseLong(ipArr[2]) * 256
				+ Long.parseLong(ipArr[3]);
	}
}
