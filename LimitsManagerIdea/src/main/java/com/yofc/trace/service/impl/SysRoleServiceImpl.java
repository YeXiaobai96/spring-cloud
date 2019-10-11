package com.yofc.trace.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yofc.trace.entity.SysRole;
import com.yofc.trace.mapper.SysRoleMapper;
import com.yofc.trace.service.SysRoleService;
import com.yofc.trace.util.PageUtil;
import com.yofc.trace.util.StringUtil;

@Service
public class SysRoleServiceImpl implements SysRoleService{

	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Override
	public int insertRole(SysRole role) {
		// TODO Auto-generated method stub
		role.setId(StringUtil.getUUID());
		role.setCreatedOn(new Date());
		role.setModifiedOn(new Date());
		role.setStatus(0);
		role.setDelFlag(0);
		return sysRoleMapper.insertRole(role);
	}

	@Override
	public int updateRole(SysRole role) {
		// TODO Auto-generated method stub
		role.setModifiedOn(new Date());
		return sysRoleMapper.updateRole(role);
	}

	@Override
	public SysRole selectById(String id) {
		// TODO Auto-generated method stub
		return sysRoleMapper.selectById(id);
	}

	@Override
	public SysRole selectByCode(String code) {
		// TODO Auto-generated method stub
		return sysRoleMapper.selectByCode(code);
	}

	@Override
	public PageUtil<SysRole> selectRolePage(Integer index, Integer size, String name, String bTime, String eTime) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", name);
		map.put("bTime", bTime);
		map.put("eTime", eTime);
		map.put("index", (index-1)*size);
		map.put("size", size);
		PageUtil<SysRole> page=new PageUtil<SysRole>();
		page.setIndex(index);
		page.setSize(size);
		page.setCount(sysRoleMapper.selectPageCount(map));
		page.setList(sysRoleMapper.selectRolePage(map));
		return page;
	}

	@Override
	public int delRole(SysRole role) {
		// TODO Auto-generated method stub
		role.setDelFlag(1);
		role.setModifiedOn(new Date());
		return sysRoleMapper.updateRole(role);
	}

	@Override
	public int selectRoleCount(String id) {
		// TODO Auto-generated method stub
		return sysRoleMapper.selectRoleCount(id);
	}

	@Override
	public List<SysRole> findRole() {
		// TODO Auto-generated method stub
		return sysRoleMapper.findRole();
	}

	@Override
	public List<SysRole> listRegRole() {
		// TODO Auto-generated method stub
		return sysRoleMapper.listRegRole();
	}

}
