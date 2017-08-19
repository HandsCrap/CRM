package com.shsxt.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shsxt.crm.dao.RoleDao;
import com.shsxt.crm.model.Role;

@Service
public class RoleService {
	@Autowired
	private RoleDao roleDao;
	
	/**
	 * 查找所有角色进行展示
	 * @return
	 */
	public Map<String,Object> selectAll(){
		Map<String ,Object> result = new HashMap<>();
		List<Role> roles=roleDao.selectAll();
		result.put("rows", roles);
		return result;
	}
	
	/**
	 * 根据id查找角色
	 * @param roleId
	 * @return
	 */
	public Role queryByid(Integer roleId) {
		return roleDao.queryByid(roleId);
	}
}
