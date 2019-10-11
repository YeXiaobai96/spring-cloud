package com.yofc.trace.config;

/**
 * 异常枚举类
 *
 */
public enum ExceptionEnum {
	UNKNOW_ERROR(-1, "未知异常"),
	PARAM_NULL(400, "参数不能为空"),
	ERROR(4000,"Error"),
	PARAM_ERROR(1400, "参数错误"),
	NOT_IMG(10041, "请添加图片"),
	ADD_FAIL(10042, "添加失败"),
	DEL_FAIL(10043, "删除失败"),
	UPDATE_FAIL(10043, "修改失败，请联系管理员"),
	ADD_PRODUCT_ERROR(70001, "接口已收藏，请勿重复收藏"),
	USER_ERROR(80001, "该用户已失效"),
	APPROVAL_FAIL(10044, "审批失败"),
	USERNAME_ERROR(90001, "用户名格式不正确"),
	USERNAME_STATUS_ERROR(90441, "用户已被禁用"),
	SYSTEM_EDIT_ERROR(90441, "系统用户不可操作"),
	PASSWORD_ERROR(90002, "密码格式不正确"),
	EMAIL_ERROR(90003, "邮箱格式不正确"),
	PHONE_ERROR(90004, "手机号格式不正确"),
	REG_USERNAME_ERROR(90005, "用户名已注册"),
	REG_EMAIL_ERROR(90006, "邮箱已注册"),
	REG_PHONE_ERROR(90007, "手机号已注册"),
	PASSWORD_LOGIN_ERROR(9008, "原密码不正确"),
	LOGINSTR_LOGIN_ERROR(90009, "登录名错误"),
	LOGCODE_PWD_LOGIN_ERROR(90015,"登录名或密码不正确"),
	PASSWORD_UP_ERROR(90011, "原始密码不正确"),
	USER_LOGOUT_ERROR(90012, "退出失败"),
	MESSAGE_ADD_ERROR(90100, "通知消息群发失败"),
	LEVEL_ERROR(80012,"一二级塘口区域不可删除"),
	LEVEL_ADD_ERROR(80013,"一二级塘口区域不可添加"),
	POND_EXISTS_ERROR(80081,"存在塘口，不可删除");

	private Integer code;

	private String msg;

	ExceptionEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
