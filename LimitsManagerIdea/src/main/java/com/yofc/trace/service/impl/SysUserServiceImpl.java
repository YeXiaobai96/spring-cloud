package com.yofc.trace.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yofc.trace.entity.SysUser;
import com.yofc.trace.mapper.SysUserMapper;
import com.yofc.trace.service.SysUserService;
import com.yofc.trace.util.PageUtil;
import com.yofc.trace.util.StringUtil;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public PageUtil<SysUser> selectUserPage(String name, Integer index, Integer size) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("index", (index - 1) * size);
		map.put("size", size);
		PageUtil<SysUser> page = new PageUtil<SysUser>();
		page.setIndex(index);
		page.setSize(size);
		page.setCount(sysUserMapper.selectPageCount(map));
		page.setList(sysUserMapper.selectUserPage(map));
		return page;
	}

	@Override
	public SysUser insertUser(SysUser user) {
		// TODO Auto-generated method stub
		user.setId(StringUtil.getUUID());
		user.setCreatedOn(new Date());
		user.setModifiedOn(new Date());
		user.setDelFlag(0);
		user.setStatus(0);
		user.setIsSysUser(1);
		int count = sysUserMapper.insertUser(user);
		if (count > 0) {
			return user;
		} else {
			return null;
		}
	}

	@Override
	public int updateUser(SysUser user) {
		// TODO Auto-generated method stub
		user.setModifiedOn(new Date());
		return sysUserMapper.updateUser(user);
	}

	@Override
	public int forbidden(SysUser user) {
		// TODO Auto-generated method stub
		user.setModifiedOn(new Date());
		return sysUserMapper.updateUser(user);
	}

	@Override
	public SysUser selectUserByCode(String code) {
		// TODO Auto-generated method stub
		return sysUserMapper.selectUserByCode(code);
	}

	@Override
	public SysUser selectUserById(String id) {
		// TODO Auto-generated method stub
		return sysUserMapper.selectUserById(id);
	}

	@Override
	public SysUser selectById(String id) {
		// TODO Auto-generated method stub
		return sysUserMapper.selectById(id);
	}

}
