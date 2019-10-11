package com.yofc.trace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.yofc.trace.param.BaseParam;
import com.yofc.trace.param.UserParam;
import com.yofc.trace.service.SysMenuService;
import com.yofc.trace.service.SysRoleService;
import com.yofc.trace.service.SysUserRoleService;
import com.yofc.trace.service.SysUserService;
import com.yofc.trace.util.MyUtils;
import com.yofc.trace.util.PageUtil;
import com.yofc.trace.util.ResultUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;

	@Value("${SysUser.password}")
	private String password;

	/**
	 * 加载权限菜单
	 * 
	 * @param pid
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/loadMenu", method = RequestMethod.POST)
	public Object loadMenu(String pid, String uid) {
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
			jsonObject.put("title", menu.getName()); // 节点名称
			if (menu.getNode() == 0) {
				jsonObject.put("state", "closed"); // 根节点
			} else {
				jsonObject.put("state", "open"); // 叶子节点
			}
			jsonObject.put("iconCls", menu.getIcon()); // 节点图标
			JSONObject attributeObject = new JSONObject(); // 扩展属性
			attributeObject.put("url", menu.getProgramUrl()); // 菜单请求地址
			jsonObject.put("attributes", attributeObject);
			jsonArray.add(jsonObject);
		});
		return jsonArray;
	}

	/*
	 * @RequestMapping("/page") public Object page(){
	 * 
	 * List<SysUser> list=sysUserService.selectUserPage(); return list; }
	 */
	/**
	 * 显示所有角色
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showRole",method = RequestMethod.POST)
	public Object showRole() {
		return sysRoleService.findRole();
	}

	/**
	 * 用户的添加
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public Object save(@RequestBody SysUser user) {
		if (StringUtils.isEmpty(user.getCode()) || StringUtils.isEmpty(user.getRoleIds()))
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		SysUser u = sysUserService.selectUserByCode(user.getCode());
		if (!StringUtils.isEmpty(u))
			return ResultUtil.error(4040, "登录名已存在");
		user.setSalt(MyUtils.getSalt());
		user.setPassword(MyUtils.getPwd(user.getSalt() + password));
		SysUser newUser = sysUserService.insertUser(user);
		String roleIds[] = newUser.getRoleIds();
		for (int i = 0; i < roleIds.length; i++) {
			SysUserRole userRole = new SysUserRole();
			userRole.setRoleId(roleIds[i]);
			userRole.setUserId(newUser.getId());
			sysUserRoleService.insertSelective(userRole);
		}
		return ResultUtil.success(null);
	}

	/**
	 * 用户更新设置
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/toupdate",method = RequestMethod.POST)
	public Object toupdate(@RequestBody SysUser user) {

		if (StringUtils.isEmpty(user.getId()))
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		// 系统最高权限用户不可操作
		if (user.getId().equals("1"))
			return ResultUtil.error(ExceptionEnum.SYSTEM_EDIT_ERROR);
		return sysUserService.selectUserById(user.getId());
	}

	/**
	 * 用户更新设置
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public Object update(@RequestBody SysUser user) {
		if (StringUtils.isEmpty(user.getId()) || StringUtils.isEmpty(user.getRoleIds()))
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		SysUser absUser=sysUserService.selectUserByCode(user.getCode());
		if(absUser!=null){
			SysUser oldUser=sysUserService.selectById(user.getId());
			if(!absUser.getCode().equals(oldUser.getCode()))
				return ResultUtil.error(4040, "登录名已存在");
		}
		sysUserRoleService.deleteByUserId(user.getId());
		String roleIds[] = user.getRoleIds();
		for (int i = 0; i < roleIds.length; i++) {
			SysUserRole userRole = new SysUserRole();
			userRole.setRoleId(roleIds[i]);
			userRole.setUserId(user.getId());
			sysUserRoleService.insertSelective(userRole);
		}
		int updateNum = sysUserService.updateUser(user);
		if (updateNum < 1)
			return ResultUtil.error(ExceptionEnum.UPDATE_FAIL);
		return ResultUtil.success(null);
	}

	/**
	 * 重置用户密码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/resetPwd",method = RequestMethod.POST)
	public Object updatePwd(@RequestBody SysUser user) {
		if (StringUtils.isEmpty(user.getId()))
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		user.setSalt(MyUtils.getSalt());
		user.setPassword(MyUtils.getPwd(user.getSalt() + password));
		int pwdNum = sysUserService.updateUser(user);
		if (pwdNum < 1)
			return ResultUtil.error(ExceptionEnum.UPDATE_FAIL);
		return ResultUtil.success(null);
	}

	/**
	 * 修改密码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/getNewPwd",method = RequestMethod.POST)
	public Object getNewPwd(@RequestBody UserParam user) {
		if (StringUtils.isEmpty(user.getId()))
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		SysUser oldUser = sysUserService.selectById(user.getId());
		if (oldUser == null)
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		String pwd = MyUtils.getPwd(oldUser.getSalt() + user.getOldPwd());
		if (oldUser.getPassword().equals(pwd)) {
			SysUser newUser = new SysUser();
			newUser.setId(user.getId());
			newUser.setSalt(MyUtils.getSalt());
			newUser.setPassword(MyUtils.getPwd(newUser.getSalt() + user.getNewPwd()));
			int pwdNum = sysUserService.updateUser(newUser);
			if (pwdNum < 1)
				return ResultUtil.error(ExceptionEnum.UPDATE_FAIL);
		} else {
			return ResultUtil.error(ExceptionEnum.PASSWORD_LOGIN_ERROR);
		}
		return ResultUtil.success(null);
	}

	/**
	 * 用户的禁用与启用
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/isforbidden",method = RequestMethod.POST)
	public Object forbidden(@RequestBody SysUser user) {
		if (StringUtils.isEmpty(user.getId()))
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		// 系统最高权限用户不可操作
		if (user.getId().equals("1"))
			return ResultUtil.error(ExceptionEnum.SYSTEM_EDIT_ERROR);
		int banNum = sysUserService.forbidden(user);
		if (banNum < 1)
			return ResultUtil.error(ExceptionEnum.UPDATE_FAIL);
		return ResultUtil.success(null);
	}

	/**
	 * 用户分页
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/page",method = RequestMethod.POST)
	public Object page(@RequestBody BaseParam param) {
		Integer index = param.getIndex() == null ? 1 : param.getIndex();
		Integer size = param.getSize() == null ? 10 : param.getSize();
		PageUtil<SysUser> page = sysUserService.selectUserPage(param.getName(), index, size);
		return page;
	}

}
