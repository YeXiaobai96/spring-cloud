package com.yofc.trace.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yofc.trace.entity.SysPermiy;
import com.yofc.trace.mapper.SysPermiyMapper;
import com.yofc.trace.service.SysPermiyService;
import com.yofc.trace.util.StringUtil;
@Service
public class SysPermiyServiceImpl implements SysPermiyService{

	@Autowired
	private SysPermiyMapper sysPermiyMapper;
	@Override
	public int insertPermiy(SysPermiy permiy) {
		// TODO Auto-generated method stub
		permiy.setId(StringUtil.getUUID());
		permiy.setDelFlag(0);
		permiy.setCreatedOn(new Date());
		permiy.setModifiedOn(new Date());
		return sysPermiyMapper.insertPermiy(permiy);
	}

	@Override
	public List<String> selectMenuByRoleId(String roleId) {
		// TODO Auto-generated method stub
		return sysPermiyMapper.selectMenuByRoleId(roleId);
	}

	@Override
	public int delPermiy(String roleId) {
		// TODO Auto-generated method stub
		return sysPermiyMapper.delPermiy(roleId);
	}

	@Override
	public int delPermiyByMenuId(String menuId) {
		// TODO Auto-generated method stub
		return sysPermiyMapper.delPermiyByMenuId(menuId);
	}

}
