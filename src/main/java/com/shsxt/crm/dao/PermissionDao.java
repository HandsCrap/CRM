package com.shsxt.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shsxt.crm.model.Permission;

public interface PermissionDao {

	@Select("select id,role_id ,module_id from t_permission where is_valid =1 and role_id=#{roleId} and module_id=#{moduleId}")
	public Permission queryById(@Param(value="roleId")Integer roleId,@Param(value="moduleId") Integer moduleId);
	
	
	public void insertBatch(@Param(value="permissions")List<Permission> permissions);

	@Delete("delete from t_permission where role_id=#{roleId} and module_id=#{moduleId}")
	public void delete(@Param(value="roleId")Integer roleId,@Param(value="moduleId")Integer moduleId);

	@Select("select count(1) from t_permission where role_id=#{roleId} and module_id in "
			+ " (select id from t_module where tree_path like('${treePath}%')" )
	public Integer queryChildrens(@Param(value="treePath")String treePath,@Param(value="roleId") Integer roleId);

	@Select("SELECT t3.acl_value from t_user t1 LEFT JOIN t_user_role t2 ON t1.id=t2.user_id "
			+ " LEFT JOIN t_permission t3 on t2.role_id=t3.role_id where t1.id=#{userId}")
	public List<String> queryUserPermissions(@Param(value="userId")Integer userId);
	
	
	
}
