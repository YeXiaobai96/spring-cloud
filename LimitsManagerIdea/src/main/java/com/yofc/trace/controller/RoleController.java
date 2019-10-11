package com.yofc.trace.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.yofc.trace.entity.SysPermiy;
import com.yofc.trace.entity.SysRole;
import com.yofc.trace.param.PermiyParam;
import com.yofc.trace.param.RoleParam;
import com.yofc.trace.service.SysMenuService;
import com.yofc.trace.service.SysPermiyService;
import com.yofc.trace.service.SysRoleService;
import com.yofc.trace.service.SysUserRoleService;
import com.yofc.trace.util.PageUtil;
import com.yofc.trace.util.ResultUtil;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysPermiyService sysPermiyService;
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 角色添加
	 * 
	 * @return
	 */
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public Object save(@RequestBody SysRole role) {
		if (StringUtils.isEmpty(role.getCode()))
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		SysRole r = sysRoleService.selectByCode(role.getCode());
		if (r != null)
			return ResultUtil.error(1119, "角色编码已存在");
		int addNum = sysRoleService.insertRole(role);
		if (addNum < 1)
			return ResultUtil.error(ExceptionEnum.ADD_FAIL);
		return ResultUtil.success(null);
	}

	/**
	 * 去修改
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/toupdate",method = RequestMethod.POST)
	public Object toupdate(@RequestBody SysRole role) {
		if (StringUtils.isEmpty(role.getId())) {
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		}
		SysRole r = sysRoleService.selectById(role.getId());
		return r;
	}

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public Object update(@RequestBody SysRole role) {
		if (StringUtils.isEmpty(role.getId())) {
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		}
		SysRole r = sysRoleService.selectByCode(role.getCode());
		if (r != null) {
			SysRole r1 = sysRoleService.selectById(role.getId());
			if (!r1.getCode().equals(r.getCode()))
				return ResultUtil.error(1119, "角色编码已存在");
		}
		int updateNum = sysRoleService.updateRole(role);
		if (updateNum < 0)
			return ResultUtil.error(ExceptionEnum.UPDATE_FAIL);
		return ResultUtil.success(null);
	}

	/**
	 * 删除角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/del",method = RequestMethod.POST)
	public Object del(@RequestBody SysRole role) {
		if (StringUtils.isEmpty(role.getId())) {
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		}
		// int count = sysRoleService.selectRoleCount(role.getId());
		// 根据角色id删除所有角色权限
		sysPermiyService.delPermiy(role.getId());
		// 根据角色id删除所有用户绑定角色中间表
		sysUserRoleService.deleteByRoleId(role.getId());
		int delNum = sysRoleService.delRole(role);
		if (delNum < 0)
			return ResultUtil.error(ExceptionEnum.DEL_FAIL);
		return ResultUtil.success(null);
	}

	/**
	 * 角色分页
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/page",method = RequestMethod.POST)
	public Object page(@RequestBody RoleParam role) {
		Integer index = role.getIndex() == null ? 1 : role.getIndex();
		Integer size = role.getSize() == null ? 10 : role.getSize();
		PageUtil<SysRole> page = sysRoleService.selectRolePage(index, size, role.getName(), role.getStartTime(),
				role.getEndTime());
		return page;
	}
	
	/**
	 * 根据父节点获取所有复选框权限菜单
	 * 
	 * @param parentId
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loadCheckMenuInfo1",method = RequestMethod.POST)
	public Object loadCheckMenuInfo1(@RequestBody PermiyParam permiy) throws Exception {
		if (StringUtils.isEmpty(permiy.getParentId()) || StringUtils.isEmpty(permiy.getRoleId())) {
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		}
		List<String> menuIdList = sysPermiyService.selectMenuByRoleId(permiy.getRoleId());
		List<SysMenu> mlist=sysMenuService.findAll();
		/*
		 * List<Integer> menuIdList=new LinkedList<Integer>(); for(Menu
		 * menu:menuList){ menuIdList.add(menu.getId()); }
		 */
		return getAllCheckMenuByParentId1(permiy.getParentId(), menuIdList,mlist);
	}

	/**
	 * 根据父节点id和权限菜单id集合获取所有复选框菜单集合
	 * 
	 * @param parentId
	 * @param menuIdList
	 * @return
	 */
	public JSONArray getAllCheckMenuByParentId1(String parentId, List<String> menuIdList,List<SysMenu> mlist) {
		JSONArray jsonArray = this.getCheckMenuByParentId1(parentId, menuIdList,mlist);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			if ("open".equals(jsonObject.getString("state"))) {
				continue;
			} else {
				jsonObject.put("children", getAllCheckMenuByParentId1(jsonObject.getString("id"), menuIdList,mlist));
			}
		}
		return jsonArray;
	}

	/**
	 * 根据父节点id和权限菜单id集合获取一层复选框菜单集合
	 * 
	 * @param parentId
	 * @param menuIdList
	 * @return
	 */
	public JSONArray getCheckMenuByParentId1(String parentId, List<String> menuIdList,List<SysMenu> mlist) {
		List<SysMenu> menuList = sysMenuService.selectMenuByPid(parentId);
		JSONArray jsonArray = new JSONArray();
		for (SysMenu menu : menuList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", menu.getId()); // 节点Id
			jsonObject.put("title", menu.getName()); // 节点名称
			if (menu.getNode() == 0) {
				jsonObject.put("state", "closed"); // 根节点
				// jsonObject.put("indeterminate", true); // 子节点有选中
			} else {
				jsonObject.put("state", "open"); // 叶子节点
			}
			jsonObject.put("iconCls", menu.getIcon()); // 节点图标
			// 子集id查询
			List<SysMenu> menuChildrenList=mlist.stream().
					filter(list->list.getPid().equals(menu.getId())).collect(Collectors.toList());
			//List<SysMenu> menuChildrenList = sysMenuService.selectMenuByPid(menu.getId());
			List<String> menuChildrenIdList = new LinkedList<String>();
			if (menuChildrenList.size() != 0) {
				for (SysMenu m : menuChildrenList) {
					menuChildrenIdList.add(m.getId());
				}
				if (menuIdList.containsAll(menuChildrenIdList)) {
					jsonObject.put("checked", true);
				}else if(menuIdList.contains(menu.getId())){
					for (String string : menuChildrenIdList) {
						if (menuIdList.contains(string)) {
							jsonObject.put("indeterminate", true);
							break;
						}
					}
				}
			}else{
				if(menuIdList.contains(menu.getId()))
					jsonObject.put("checked", true);
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	/**
	 * 根据父节点获取所有复选框权限菜单
	 * 
	 * @param parentId
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loadCheckMenuInfo",method = RequestMethod.POST)
	public Object loadCheckMenuInfo(@RequestBody PermiyParam permiy) throws Exception {
		if (StringUtils.isEmpty(permiy.getParentId()) || StringUtils.isEmpty(permiy.getRoleId())) {
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		}
		List<String> menuIdList = sysPermiyService.selectMenuByRoleId(permiy.getRoleId());
		/*
		 * List<Integer> menuIdList=new LinkedList<Integer>(); for(Menu
		 * menu:menuList){ menuIdList.add(menu.getId()); }
		 */
		return getAllCheckMenuByParentId(permiy.getParentId(), menuIdList);
	}

	/**
	 * 根据父节点id和权限菜单id集合获取所有复选框菜单集合
	 * 
	 * @param parentId
	 * @param menuIdList
	 * @return
	 */
	public JSONArray getAllCheckMenuByParentId(String parentId, List<String> menuIdList) {
		JSONArray jsonArray = this.getCheckMenuByParentId(parentId, menuIdList);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			if ("open".equals(jsonObject.getString("state"))) {
				continue;
			} else {
				jsonObject.put("children", getAllCheckMenuByParentId(jsonObject.getString("id"), menuIdList));
			}
		}
		return jsonArray;
	}

	/**
	 * 根据父节点id和权限菜单id集合获取一层复选框菜单集合
	 * 
	 * @param parentId
	 * @param menuIdList
	 * @return
	 */
	public JSONArray getCheckMenuByParentId(String parentId, List<String> menuIdList) {
		List<SysMenu> menuList = sysMenuService.selectMenuByPid(parentId);
		JSONArray jsonArray = new JSONArray();
		for (SysMenu menu : menuList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", menu.getId()); // 节点Id
			jsonObject.put("title", menu.getName()); // 节点名称
			if (menu.getNode() == 0) {
				jsonObject.put("state", "closed"); // 根节点
				// jsonObject.put("indeterminate", true); // 子节点有选中
			} else {
				jsonObject.put("state", "open"); // 叶子节点
			}
			jsonObject.put("iconCls", menu.getIcon()); // 节点图标
			// 子集id查询
			List<SysMenu> menuChildrenList = sysMenuService.selectMenuByPid(menu.getId());
			List<String> menuChildrenIdList = new LinkedList<String>();
			if (menuChildrenList.size() != 0) {
				for (SysMenu m : menuChildrenList) {
					menuChildrenIdList.add(m.getId());
				}
				if (menuIdList.containsAll(menuChildrenIdList)) {
					jsonObject.put("checked", true);
				}else if(menuIdList.contains(menu.getId())){
					for (String string : menuChildrenIdList) {
						if (menuIdList.contains(string)) {
							jsonObject.put("indeterminate", true);
							break;
						}
					}
				}
			}else{
				if(menuIdList.contains(menu.getId()))
					jsonObject.put("checked", true);
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	/**
	 * 保存角色权限设置
	 * 
	 * @param menuIds
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveMenuSet",method = RequestMethod.POST)
	public Object saveMenuSet(@RequestBody PermiyParam permiy) throws Exception {
		if (StringUtils.isEmpty(permiy.getRoleId())) {
			return ResultUtil.error(ExceptionEnum.PARAM_NULL);
		}
		// 根据角色id删除所有角色权限
		sysPermiyService.delPermiy(permiy.getRoleId());
		String menuIds=permiy.getMenuIds();
		if (!StringUtils.isEmpty(menuIds)) {
			String idsStr[] = menuIds.split(",");
			for (int i = 0; i < idsStr.length; i++) {
				SysPermiy permiy1 = new SysPermiy();
				permiy1.setRoleId(permiy.getRoleId());
				permiy1.setMenuId(idsStr[i]);
				sysPermiyService.insertPermiy(permiy1);
			}
		}
		return ResultUtil.success(null);
	}
	
	@RequestMapping(value="/listRole",method=RequestMethod.POST)
	public Object showRole(){
		List<SysRole> rlist=sysRoleService.listRegRole();
		return rlist;
	}
}
