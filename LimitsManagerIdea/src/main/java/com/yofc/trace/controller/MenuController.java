package com.yofc.trace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yofc.trace.config.ExceptionEnum;
import com.yofc.trace.entity.SysMenu;
import com.yofc.trace.param.MenuParm;
import com.yofc.trace.service.SysMenuService;
import com.yofc.trace.service.SysPermiyService;
import com.yofc.trace.util.PageUtil;
import com.yofc.trace.util.ResultUtil;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysPermiyService sysPermiyService;

	/**
	 * 菜单添加
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public Object save(@RequestBody SysMenu menu) {
		if (StringUtils.isEmpty(menu.getName()) || StringUtils.isEmpty(menu.getPid())
				|| StringUtils.isEmpty(menu.getCode())) {
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		}

		SysMenu absMenu = sysMenuService.selectByCode(menu.getCode());
		if(absMenu!=null){
			return ResultUtil.error(10025, "菜单导航名称已存在");
		}
		int addNum = sysMenuService.insertMenu(menu);
		if (addNum < 1)
			return ResultUtil.error(ExceptionEnum.ADD_FAIL);
		if (!menu.getPid().equals("0")) {
			SysMenu m = sysMenuService.selectById(menu.getPid());
			if (m.getNode() != 0) {
				m.setNode(0);
				sysMenuService.updateMenu(m);
			}
		}
		return ResultUtil.success(null);
	}

	/**
	 * 查询单个菜单数据
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/toupdate",method = RequestMethod.POST)
	public Object toupdate(@RequestBody SysMenu menu) {
		if (StringUtils.isEmpty(menu.getId())) {
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		}
		SysMenu m = sysMenuService.selectById(menu.getId());
		return ResultUtil.success(m);
	}

	/**
	 * 显示父级菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showParentMenu",method = RequestMethod.POST)
	public Object parentMenu() {
		return sysMenuService.selectMenuByPid("0");
	}

	/**
	 * 显示admin的菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showMenu",method = RequestMethod.POST)
	public Object showMenu() {
		return sysMenuService.findMenuByUid("1");
	}

	/**
	 * 菜单修改
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public Object update(@RequestBody SysMenu menu) {
		if (StringUtils.isEmpty(menu.getId()) || StringUtils.isEmpty(menu.getPid())) {
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		}
		//检测导航名称唯一性
		SysMenu absMenu = sysMenuService.selectByCode(menu.getCode());
		if(absMenu!=null){
			SysMenu oldMenu = sysMenuService.selectById(menu.getId());
			if(!absMenu.getCode().equals(oldMenu.getCode()))
				return ResultUtil.error(10025, "菜单导航名称已存在");
		}
		// 原节点修改
		SysMenu oldMenu = sysMenuService.selectById(menu.getId());
		if (oldMenu.getPid().equals("0") && !menu.getPid().equals("0"))
			return ResultUtil.error(5005, "一级菜单不可切换为二级菜单");
		SysMenu parentMenu = sysMenuService.selectById(oldMenu.getPid());
		if (parentMenu != null) {
			int count = sysMenuService.selectByPid(parentMenu.getId());
			if (count == 1) {
				parentMenu.setNode(1);
				sysMenuService.updateMenu(parentMenu);
			}
		}
		// 新节点修改
		if (!menu.getPid().equals("0")) {
			SysMenu m = sysMenuService.selectById(menu.getPid());
			if (m.getNode() != 0) {
				m.setNode(0);
				sysMenuService.updateMenu(m);
			}
		}
		int updateNum = sysMenuService.updateMenu(menu);
		if (updateNum < 1)
			ResultUtil.error(ExceptionEnum.UPDATE_FAIL);
		return ResultUtil.success(null);
	}

	/**
	 * 菜单删除
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/del",method = RequestMethod.POST)
	public Object del(@RequestBody SysMenu menu) {
		if (StringUtils.isEmpty(menu.getId())) {
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		}
		int count = sysMenuService.selectByPid(menu.getId());
		if (count > 0)
			return ResultUtil.error(5001, "父菜单有子菜单，不可删除");
		// 删除菜单与角色的关联
		sysPermiyService.delPermiyByMenuId(menu.getId());
		int delNum = sysMenuService.delMenu(menu);
		if (delNum < 1)
			return ResultUtil.error(ExceptionEnum.DEL_FAIL);
		return ResultUtil.success(null);
	}

	/**
	 * 菜单分页
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/page",method = RequestMethod.POST)
	public Object page(@RequestBody MenuParm menu) {
		Integer index = menu.getIndex() == null ? 1 : menu.getIndex();
		Integer size = menu.getSize() == null ? 10 : menu.getSize();
		PageUtil<SysMenu> page = sysMenuService.findMenuPage(menu.getName(), menu.getCode(), index, size,
				menu.getMid());
		return page;
	}

	/**
	 * 加载权限菜单
	 * 
	 * @param pid
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/loadMenu", method = RequestMethod.POST)
	public Object loadMenu() {
		return getAllMenuByParentId("0");
	}

	/**
	 * 获取所有菜单信息
	 * 
	 * @param parentId
	 * @param roleId
	 * @return
	 */
	public JSONArray getAllMenuByParentId(String parentId) {
		JSONArray jsonArray = this.getMenuByParentId(parentId);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			if ("open".equals(jsonObject.getString("state"))) {
				continue;
			} else {
				jsonObject.put("children", getAllMenuByParentId(jsonObject.getString("id")));
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
	public JSONArray getMenuByParentId(String parentId) {
		List<SysMenu> menuList = sysMenuService.selectMenuByPid(parentId);
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
}
