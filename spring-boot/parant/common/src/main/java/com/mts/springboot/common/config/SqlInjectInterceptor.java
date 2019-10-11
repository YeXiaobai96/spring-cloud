/**
 * 
 */
package com.mts.springboot.common.config;

import com.alibaba.fastjson.JSON;
import com.mts.springboot.common.model.Result;
import com.mts.springboot.common.util.ResultUtil;
import com.mts.springboot.common.xss.SQLFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 防SQL注入
 * 
 */
@Component
public class SqlInjectInterceptor extends HandlerInterceptorAdapter {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Enumeration<String> names = request.getParameterNames();

		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String[] values = request.getParameterValues(name);
			for (String value : values) {
				if (SQLFilter.isSqlInject(value)) {
					HttpServletResponse httpResponse = (HttpServletResponse) response;
					httpResponse.setContentType("application/json;charset=utf-8");
					httpResponse.setHeader("Access-Control-Allow-Origin", "*");
					httpResponse.setHeader("Access-Control-Allow-Methods", "*");
					httpResponse.setHeader("Access-Control-Max-Age", "3600");
					httpResponse.setHeader("Access-Control-Allow-Headers",
							"Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, token");
					try {
						Result r = ResultUtil.error(HttpStatus.BAD_REQUEST.value(), "请不要尝试注入");

						String json = JSON.toJSONString(r);
						httpResponse.getWriter().print(json);
					} catch (IOException e1) {
					}
					return false;
				}
			}
		}

		return true;
	}

}
