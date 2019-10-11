package com.yofc.trace.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yofc.trace.entity.SysMenu;
import com.yofc.trace.mapper.SysMenuMapper;
import com.yofc.trace.service.SysMenuService;
import com.yofc.trace.util.PageUtil;
import com.yofc.trace.util.StringUtil;

@Service
public class SysMenuServiceImpl implements SysMenuService {

	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Override
	public List<SysMenu> findMenuByPidAndUid(String pid, String uid) {
		// TODO Auto-generated method stub
		return sysMenuMapper.findMenuByPidAndUid(pid, uid);
	}

	@Override
	public SysMenu selectById(String id) {
		// TODO Auto-generated method stub
		return sysMenuMapper.selectById(id);
	}

	@Override
	public int updateMenu(SysMenu menu) {
		// TODO Auto-generated method stub
		menu.setModifiedOn(new Date());
		return sysMenuMapper.updateMenu(menu);
	}

	@Override
	public int insertMenu(SysMenu menu) {
		// TODO Auto-generated method stub
		menu.setId(StringUtil.getUUID());
		menu.setCreatedOn(new Date());
		if (menu.getNode() == null)
			menu.setNode(1);
		menu.setDelFlag(0);
		return sysMenuMapper.insertMenu(menu);
	}

	@Override
	public int delMenu(SysMenu menu) {
		// TODO Auto-generated method stub
		menu.setDelFlag(1);
		return sysMenuMapper.updateMenu(menu);
	}

	@Override
	public int selectByPid(String pid) {
		// TODO Auto-generated method stub
		return sysMenuMapper.selectByPid(pid);
	}

	@Override
	public PageUtil<SysMenu> findMenuPage(String name, String code, Integer index, Integer size,String mid) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", name);
		map.put("code", code);
		map.put("index", (index-1)*size);
		map.put("size", size);
		map.put("mid", mid);
		PageUtil<SysMenu> page=new PageUtil<SysMenu>();
		page.setIndex(index);
		page.setSize(size);
		page.setCount(sysMenuMapper.selectPageCount(map));
		page.setList(sysMenuMapper.selectMenuPage(map));
		return page;
	}

	@Override
	public List<SysMenu> selectMenuByPid(String pid) {
		// TODO Auto-generated method stub
		return sysMenuMapper.selectMenuByPid(pid);
	}

	@Override
	public List<SysMenu> findMenuByUid(String uid) {
		// TODO Auto-generated method stub
		return sysMenuMapper.findMenuByUid(uid);
	}

	@Override
	public SysMenu selectByCode(String code) {
		// TODO Auto-generated method stub
		return sysMenuMapper.selectByCode(code);
	}

	/* (non-Javadoc)
	 * @see com.yuhan.trace.service.SysMenuService#findAll()
	 */
	@Override
	public List<SysMenu> findAll() {
		// TODO Auto-generated method stub
		return sysMenuMapper.findAll();
	}

}
