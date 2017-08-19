package com.shsxt.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shsxt.crm.model.Role;

public interface RoleDao {
	
	@Select("select id,role_name,role_remark,create_date,update_date from t_role where is_valid = 1")
	List<Role> selectAll();
	
	@Select("select id,role_name,role_remark,create_date,update_date from t_role where is_valid=1 and id=#{roleId}")
	Role queryByid(@Param(value="roleId")Integer roleId);
}
