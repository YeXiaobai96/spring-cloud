package com.yofc.trace.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yofc.trace.config.ExceptionEnum;
import com.yofc.trace.entity.SysMenu;
import com.yofc.trace.entity.SysUser;
import com.yofc.trace.entity.SysUserRole;
import com.yofc.trace.service.SysMenuService;
import com.yofc.trace.service.SysUserRoleService;
import com.yofc.trace.service.SysUserService;
import com.yofc.trace.util.Md5TokenGenerator;
import com.yofc.trace.util.MyUtils;
import com.yofc.trace.util.ResultUtil;

@RestController
@RequestMapping("/admin")
public class LoginController {

	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * @Title: login @Description: TODO(登录操作) @param @param user @param @return
	 *         参数说明 @return Object 返回类型 
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Object login(@RequestBody SysUser user) {
		if (StringUtils.isEmpty(user.getCode()) || StringUtils.isEmpty(user.getPassword()))
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		SysUser cuser = sysUserService.selectUserByCode(user.getCode());
		if (cuser != null) {
			String pwd = MyUtils.getPwd(cuser.getSalt() + user.getPassword());
			if (cuser.getPassword().equals(pwd)) {
				// status 1.禁用0.启用
				if (cuser.getStatus().equals(1))
					return ResultUtil.error(ExceptionEnum.USERNAME_STATUS_ERROR);
				// 生成token
				String token = Md5TokenGenerator.generate(user.getCode(), user.getPassword());
				// 放入redis
				redisTemplate.opsForValue().set(token, JSONObject.toJSONString(cuser), 15 * 60, TimeUnit.SECONDS);
				JSONArray jarray = loadMenu("0", cuser.getId());
				JSONObject nuser = new JSONObject();
				nuser.put("id", cuser.getId());
				nuser.put("name", cuser.getName());
				nuser.put("menuJson", jarray);
				nuser.put("token", token);
				return nuser;
			} else {
				return ResultUtil.error(ExceptionEnum.LOGCODE_PWD_LOGIN_ERROR);
			}
		} else {
			return ResultUtil.error(ExceptionEnum.LOGCODE_PWD_LOGIN_ERROR);
		}
	}

	/**
	 * 安全退出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout",method = RequestMethod.POST)
	public Object logout(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String uid = redisTemplate.opsForValue().get(token);
		if (uid != null) {
			redisTemplate.delete(token);
		}
		return ResultUtil.success(null);
	}

	/**
	 * 加载权限菜单
	 * 
	 * @param pid
	 * @param uid
	 * @return
	 */
	public JSONArray loadMenu(String pid, String uid) {
		return getAllMenuByParentId(pid, uid);
	}

	/**
	 * 获取所有菜单信息
	 * 
	 * @param parentId
	 * @param roleId
	 * @return
	 */
	public JSONArray getAllMenuByParentId(String parentId, String userId) {
		JSONArray jsonArray = this.getMenuByParentId(parentId, userId);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			if ("open".equals(jsonObject.getString("state"))) {
				continue;
			} else {
				jsonObject.put("children", getAllMenuByParentId(jsonObject.getString("id"), userId));
			}
		}
		return jsonArray;
	}

	/**
	 * 根据父节点和用户Id查询菜单
	 * 
	 * @param parentId
	 * @param roleId
	 * @return
	 */
	public JSONArray getMenuByParentId(String parentId, String userId) {
		List<SysMenu> menuList = sysMenuService.findMenuByPidAndUid(parentId, userId);
		JSONArray jsonArray = new JSONArray();
		menuList.forEach(menu -> {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", menu.getId()); // 节点Id
			jsonObject.put("name", menu.getCode()); // 节点名称
			if (menu.getNode() == 0) {
				jsonObject.put("state", "closed"); // 根节点
			} else {
				jsonObject.put("state", "open"); // 叶子节点
			}
			jsonObject.put("icon", menu.getIcon()); // 节点图标
			JSONObject attributeObject = new JSONObject(); // 扩展属性
			attributeObject.put("icon", menu.getIcon()); // 菜单图标
			attributeObject.put("title", menu.getName()); // 菜单名
			attributeObject.put("url", menu.getProgramUrl()); // 菜单请求地址
			jsonObject.put("meta", attributeObject);
			jsonArray.add(jsonObject);
		});
		return jsonArray;
	}

	/** 
	 * <p>Title: register </p>
	 * <p>Description: TODO </p>
	 * @param @param user
	 * @param @return  参数说明 
 	 * @return Object    返回类型 
	 */
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public Object register(@RequestBody SysUser user) {
		if (StringUtils.isEmpty(user.getCode())) {
			return ResultUtil.error(501, "请输入登陆名");
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			return ResultUtil.error(502, "请输入密码");
		}
		if (StringUtils.isEmpty(user.getRoleId())) {
			return ResultUtil.error(504, "请选择角色");
		}
		SysUser userinfo = sysUserService.selectUserByCode(user.getCode());
		if (userinfo != null) {
			return ResultUtil.error(503, "登录名已存在");
		}
		String password = user.getPassword();
		user.setName(user.getCode());
		user.setSalt(MyUtils.getSalt());
		user.setPassword(MyUtils.getPwd(user.getSalt() + user.getPassword()));
		user.setDelFlag(0);
		user.setStatus(0);
		user.setCreatedOn(new Date());
		SysUser newUser = sysUserService.insertUser(user);
		if (newUser == null) {
			return ResultUtil.error(508,"注册失败");
		}
		SysUserRole userRole = new SysUserRole();
		userRole.setRoleId(user.getRoleId());
		userRole.setUserId(newUser.getId());
		int count = sysUserRoleService.insertSelective(userRole);
		if (count > 0) {
			// 生成token
			String token = Md5TokenGenerator.generate(newUser.getCode(), password);
			// 放入redis
			redisTemplate.opsForValue().set(token, JSONObject.toJSONString(newUser), 15 * 60, TimeUnit.SECONDS);
			JSONArray jarray = loadMenu("0", newUser.getId());
			JSONObject nuser = new JSONObject();
			nuser.put("id", newUser.getId());
			nuser.put("name", newUser.getName());
			nuser.put("menuJson", jarray);
			nuser.put("token", token);
			return nuser;
		} else {
			return ResultUtil.error(508,"注册失败");
		}
	}

}
