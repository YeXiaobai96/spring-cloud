package com.yofc.trace.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.yofc.trace.config.constant.AdminKey;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

	private static Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(10000)
			.expireAfterWrite(3, TimeUnit.MINUTES).build();
	// 存放鉴权信息的Header名称，默认是Authorization
	private String httpHeaderName = "Authorization";

	// 鉴权失败后返回的错误信息，默认为401 unauthorized
	// private String unauthorizedErrorMessage = "401 unauthorized";

	// 鉴权失败后返回的HTTP错误码，默认为401
	private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 存放登录用户模型jsonObject类型字符串
	 */
	public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String method = request.getMethod();
		if (method.equals("OPTIONS")) {
			// filterChain.doFilter(request, httpResponse);
			return true;
		}
		String token = request.getHeader(httpHeaderName);
		// 如果header中不存在token，则从参数中获取token
		if (org.apache.commons.lang3.StringUtils.isBlank(token)) {
			token = request.getParameter(AdminKey.REQUEST_AUTH.value());
		}
		// 如果header、参数中不存在token，则从cookie中获取token
		if (org.apache.commons.lang3.StringUtils.isBlank(token)) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if (c.getName().equals("token")) {
						token = c.getValue();
					}
				}
			}
		}
		log.info("token is {}", token);
		String userId = null;
		if (!StringUtils.isEmpty(token)) {
			userId = cache.getIfPresent(token);
			if (org.apache.commons.lang3.StringUtils.isBlank(userId)) {
				userId = stringRedisTemplate.opsForValue().get(AdminKey.USER_INFO.value() + token + ":");
				Long tokeBirthTime = stringRedisTemplate.getExpire(AdminKey.USER_INFO.value() + token + ":", TimeUnit.SECONDS);

				log.info("redis缓存用户名 is {}", userId);
				log.info("过期时间 {}", tokeBirthTime);
				if (tokeBirthTime > 0 && !StringUtils.isEmpty(userId)) {
					stringRedisTemplate.opsForValue().set(AdminKey.USER_INFO.value() + token + ":", userId, 60 * 60 * 24, TimeUnit.SECONDS);
					cache.put(token, userId);
					request.setAttribute(REQUEST_CURRENT_KEY, userId);
					return true;
				}
			}else{
				request.setAttribute(REQUEST_CURRENT_KEY, userId);
				return true;
			}
		}
		JSONObject jsonObject = new JSONObject();
		PrintWriter out = null;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setStatus(unauthorizedErrorCode);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			jsonObject.put("code", ((HttpServletResponse) response).getStatus());
			jsonObject.put("message", HttpStatus.UNAUTHORIZED);
			out = response.getWriter();
			out.println(jsonObject);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
}
