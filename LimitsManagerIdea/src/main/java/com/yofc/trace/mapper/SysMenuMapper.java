package com.yofc.trace.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yofc.trace.entity.SysMenu;

public interface SysMenuMapper {

	List<SysMenu> findMenuByPidAndUid(@Param("pid") String pid, @Param("uid") String uid);

	SysMenu selectById(String id);

	int updateMenu(SysMenu menu);

	int insertMenu(SysMenu menu);
	
	int selectByPid(String pid);
	
	List<SysMenu> selectMenuPage(Map<String, Object> map);
	
	int selectPageCount(Map<String, Object> map);
	
	List<SysMenu> selectMenuByPid(String pid);
	
	List<SysMenu> findMenuByUid(String uid);
	
	SysMenu selectByCode(String code);
	
	List<SysMenu> findAll();
}