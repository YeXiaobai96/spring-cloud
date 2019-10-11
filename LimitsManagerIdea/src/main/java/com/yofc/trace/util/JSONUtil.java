package com.yofc.trace.util;

import java.io.IOException;
import java.text.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {
	
	/**
	 * java对象转JSON
	 * @param java对象
	 * @return JSON对象
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String parse(Object o) throws ParseException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(o);
	}
	
	/**
	 * JSON转java对象
	 * @param json
	 * @param java对象的类
	 * @return java对象
	 * @throws ParseException
	 * @throws IOException
	 */
	public static <T>T toObject(String json, Class<T> clazz) throws ParseException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, clazz);
	}

}
