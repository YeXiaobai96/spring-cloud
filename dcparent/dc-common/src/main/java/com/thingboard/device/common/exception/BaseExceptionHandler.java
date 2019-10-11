package com.thingboard.device.common.exception;

import com.thingboard.device.common.model.Result;
import com.thingboard.device.common.util.ResultUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.util.Set;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class BaseExceptionHandler {
	 private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自定义异常
	 */
	@ExceptionHandler(BaseException.class)
	public Result handleBaseException(HttpServletRequest request, BaseException e) {
		logger.error("------------>>>>> handleBaseException : "  + request.getRequestURI());
		// logger.error(e.getMessage());
		return ResultUtil.error(e.getCode(), e.getMessage());
	}

	@ExceptionHandler(UnauthorizedException.class)
	public Result handleUnauthorizedException(UnauthorizedException e) {
		return ResultUtil.error(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), "没有权限，请联系管理员");
	}
	
	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Result handleMissingServletRequestParameterException(HttpServletRequest request,
			MissingServletRequestParameterException e) {
		return ResultUtil.error(HttpStatus.BAD_REQUEST.value(), "缺少请求参数:" + e.getMessage());
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Result handleHttpMessageNotReadableException(HttpServletRequest request,
			HttpMessageNotReadableException e) {
		// logger.error("参数解析失败", e);
		return ResultUtil.error(HttpStatus.BAD_REQUEST.value(), "参数解析失败.");
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result handleMethodArgumentNotValidException(HttpServletRequest request,
			MethodArgumentNotValidException e) {
		// logger.error("参数验证失败", e);
		BindingResult result = e.getBindingResult();
		FieldError error = result.getFieldError();
		String field = error.getField();
		String code = error.getDefaultMessage();
		String message = String.format("%s:%s", field, code);
		return ResultUtil.error(HttpStatus.BAD_REQUEST.value(), "参数验证失败:" + message);
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public Result handleBindException(HttpServletRequest request, BindException e) {
		// logger.error("参数绑定失败", e);
		BindingResult result = e.getBindingResult();
		FieldError error = result.getFieldError();
		String field = error.getField();
		String code = error.getDefaultMessage();
		String message = String.format("%s:%s", field, code);
		return ResultUtil.error(HttpStatus.BAD_REQUEST.value(), "参数绑定失败:" + message);
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public Result handleServiceException(HttpServletRequest request, ConstraintViolationException e) {
		// logger.error("参数验证失败", e);
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		ConstraintViolation<?> violation = violations.iterator().next();
		String message = violation.getMessage();
		return ResultUtil.error(HttpStatus.BAD_REQUEST.value(), "参数验证失败:" + message);
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public Result handleValidationException(HttpServletRequest request, ValidationException e) {
		// logger.error("参数验证失败", e);
		return ResultUtil.error(HttpStatus.BAD_REQUEST.value(), "参数验证失败.");
	}

	/**
	 * 404 - Not Found
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	public Result noHandlerFoundException(HttpServletRequest request, NoHandlerFoundException e) {
		// logger.error("Not Found", e);
		return ResultUtil.error(HttpStatus.NOT_FOUND.value(), "Not Found : " + e.getRequestURL());
	}

	/**
	 * 405 - Method Not Allowed
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
			HttpRequestMethodNotSupportedException e) {
		// logger.error("不支持当前请求方法", e);
		return ResultUtil.error(HttpStatus.METHOD_NOT_ALLOWED.value(), "不支持当前请求方式：" + e.getMethod());
	}

	/**
	 * 415 - Unsupported Media Type
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public Result handleHttpMediaTypeNotSupportedException(HttpServletRequest request,
			HttpMediaTypeNotSupportedException e) {
		// logger.error("不支持当前媒体类型", e);
		return ResultUtil.error(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "不支持当前媒体类型.");
	}

	/**
	 * BAD_REQUEST
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalStateException.class)
	public Result handleIllegalStateException(HttpServletRequest request, IllegalStateException e) {
		// logger.error("Optional parameter but cannot be translated into a null value
		// :", e);
		return ResultUtil.error(HttpStatus.BAD_REQUEST.value(), "参数不能为空:" + e.getMessage());
	}
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public Result handleMaxUploadSizeExceededException(HttpServletRequest request, MaxUploadSizeExceededException e) {
		// logger.error("Optional parameter but cannot be translated into a null value
		// :", e);
		return ResultUtil.error(HttpStatus.BAD_REQUEST.value(), "上传大小限制:" + e.getMessage());
	}
	

	@ExceptionHandler(NullPointerException.class)
	public Result handleNullPointerException(HttpServletRequest request, Exception e) {
		e.printStackTrace();
		logger.error("------------>>>>> handleNullPointerException : "  + request.getRequestURI());
		return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "请联系管理员,服务异常...");
	}
	
	@ExceptionHandler(Exception.class)
	public Result handleException(HttpServletRequest request, Exception e) {
		e.printStackTrace();
		logger.error("------------>>>>> handleException : "  + request.getRequestURI());
		return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "请联系管理员,服务异常...");
	}

}
