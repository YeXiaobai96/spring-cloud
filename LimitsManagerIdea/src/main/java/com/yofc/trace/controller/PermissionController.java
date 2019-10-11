package com.yofc.trace.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yofc.trace.config.ExceptionEnum;
import com.yofc.trace.config.constant.AdminKey;
import com.yofc.trace.entity.Result;
import com.yofc.trace.entity.SysMenu;
import com.yofc.trace.entity.SysUserRole;
import com.yofc.trace.param.PermissionParam;
import com.yofc.trace.service.SysMenuService;
import com.yofc.trace.service.SysUserRoleService;
import com.yofc.trace.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Filename: PermissionController
 * @Author: wm
 * @Date: 2019/9/17 8:45
 * @Description:权限
 * @History:
 */
@RestController
@RequestMapping("permission")
public class PermissionController {


    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 加载权限菜单
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "getMenu", method = RequestMethod.GET)
    public Result loadMenu(String uid, HttpServletRequest request) {
        String token = getRequestToken(request);
        String menuString = redisTemplate.opsForValue().get(AdminKey.USER_MENU.value() + token + ":");
        JSONArray jsonArray = null;
        if (!StringUtils.isEmpty(menuString)) {
            jsonArray = JSONArray.parseArray(menuString);
        } else {
            jsonArray = getAllMenuByParentId("0", uid);
        }
        redisTemplate.opsForValue().set(AdminKey.USER_MENU.value() + token + ":", jsonArray.toJSONString(), 60 * 60 * 24);
        return ResultUtil.success(jsonArray);
    }

    /**
     * 获取所有菜单信息
     *
     * @param parentId
     * @param userId
     * @return
     */
    private JSONArray getAllMenuByParentId(String parentId, String userId) {
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
     * @param userId
     * @return
     */
    private JSONArray getMenuByParentId(String parentId, String userId) {
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
     * 获取请求的token
     */
    protected String getRequestToken(HttpServletRequest httpRequest) {

        // 从header中获取token
        String token = httpRequest.getHeader(AdminKey.REQUEST_AUTH.value());

        // 如果header中不存在token，则从参数中获取token
        if (org.apache.commons.lang3.StringUtils.isBlank(token)) {
            token = httpRequest.getParameter(AdminKey.REQUEST_AUTH.value());
        }
        return token;
    }

    @RequestMapping(value = "listUserRole", method = RequestMethod.GET)
    public Object listUserRoleById(String uid) {
        if (StringUtils.isEmpty(uid)) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        return sysUserRoleService.listUserRoleById(uid);
    }

    @RequestMapping(value = "updateUserRole", method = RequestMethod.POST)
    public Object updateUserRole(@RequestBody PermissionParam user) {
        if (StringUtils.isEmpty(user.getUid()) || StringUtils.isEmpty(user.getRoleIds())) {
            return ResultUtil.error(ExceptionEnum.PARAM_NULL);
        }
        sysUserRoleService.deleteByUserId(user.getUid());
        String[] roleIds = user.getRoleIds();
        for (int i = 0; i < roleIds.length; i++) {
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(roleIds[i]);
            userRole.setUserId(user.getUid());
            sysUserRoleService.insertSelective(userRole);
        }
        return ResultUtil.success(null);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Object logout(HttpServletRequest request) {
        String token = getRequestToken(request);
        redisTemplate.delete(AdminKey.OAUTH_TOKEN.value() + token + ":");
        redisTemplate.delete(AdminKey.USER_INFO.value() + token + ":");
        redisTemplate.delete(AdminKey.USER_MENU.value() + token + ":");
        return ResultUtil.success(null);
    }
}
