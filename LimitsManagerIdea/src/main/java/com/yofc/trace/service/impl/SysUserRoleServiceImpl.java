package com.yofc.trace.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yofc.trace.entity.SysUserRole;
import com.yofc.trace.mapper.SysUserRoleMapper;
import com.yofc.trace.service.SysUserRoleService;
import com.yofc.trace.util.StringUtil;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService{

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Override
	public int deleteByUserId(String uid) {
		// TODO Auto-generated method stub
		return sysUserRoleMapper.deleteByUserId(uid);
	}

	/**
	 * 暮雨闻谁见，憾于淑真意。
	 *
	 */
	@Override
	public int insertSelective(SysUserRole userRole) {
		// TODO Auto-generated method stub
		userRole.setId(StringUtil.getUUID());
		userRole.setDelFlag(0);
		userRole.setCreatedOn(new Date());
		userRole.setModifiedOn(new Date());
		userRole.setStatus(0);
		return sysUserRoleMapper.insertSelective(userRole);
	}

	@Override
	public int deleteByRoleId(String rid) {
		// TODO Auto-generated method stub
		return sysUserRoleMapper.deleteByRoleId(rid);
	}

	@Override
	public List<Map<String, Object>> listUserRoleById(String uid) {
		return sysUserRoleMapper.listUserRoleById(uid);
	}
}
