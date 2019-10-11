package com.mts.springboot.common.util;

import com.mts.springboot.common.xss.XssHttpServletRequestWrapper;
import org.apache.shiro.util.PatternMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * XSS过滤
 */
public class FilterUtil implements Filter {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static final String PARAM_NAME_EXCLUSIONS = "exclusions";
	private Set<String> excludesPattern;
	protected String contextPath;
	protected PatternMatcher pathMatcher =ServletPathMatcher.getInstance();

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String param = filterConfig.getInitParameter(PARAM_NAME_EXCLUSIONS);
		if (param != null && param.trim().length() != 0) {
			this.excludesPattern = new HashSet(Arrays.asList(param.split("\\s*,\\s*")));
		}

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest)request;
		//HttpServletResponse httpResponse = (HttpServletResponse)response;
		String requestURI = this.getRequestURI(httpRequest);
		if (this.isExclusion(requestURI)) {
			//不过滤走
			System.out.println("===不进过滤器");
			chain.doFilter(request, response);
		}else {
			//这里是过滤方法
			System.out.println("===进了过滤器");
			XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(httpRequest);
			chain.doFilter(xssRequest, response);
		}


	}
	public String getRequestURI(HttpServletRequest request) {
		return request.getRequestURI();
	}

	public boolean isExclusion(String requestURI) {
		if (this.excludesPattern == null) {
			return false;
		} else {
			if (this.contextPath != null && requestURI.startsWith(this.contextPath)) {
				requestURI = requestURI.substring(this.contextPath.length());
				if (!requestURI.startsWith("/")) {
					requestURI = "/" + requestURI;
				}
			}

			Iterator i$ = this.excludesPattern.iterator();

			String pattern;
			do {
				if (!i$.hasNext()) {
					return false;
				}

				pattern = (String)i$.next();
			} while(!this.pathMatcher.matches(pattern, requestURI));

			return true;
		}
	}



	@Override
	public void destroy() {
	}

}