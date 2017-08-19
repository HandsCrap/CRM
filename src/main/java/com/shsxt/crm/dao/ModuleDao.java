package com.shsxt.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.crm.model.Module;
import com.shsxt.crm.vo.ModuleVo;

public interface ModuleDao {

	
	PageList<Module> selectForPage(PageBounds pageBounds);

	@Select("select id,module_name from t_module where is_valid=1 and grade = #{grade}")
	List<Module> queryBygrade(@Param(value="grade")Integer grade);
	
	@Select("select id,module_name,module_style,url,parent_id,grade,opt_value,orders,tree_path,is_valid,create_date,"
			+ "update_date from t_module where is_valid = 1 and id = #{id}")
	Module queryById(@Param(value="id")Integer parentId);
	
	@Select("select id,module_name,module_style,url,parent_id,grade,opt_value,orders,tree_path,"
			+ "is_valid,create_date,update_date from t_module where is_valid = 1 and opt_value= #{optValue}")
	Module queryByOptValue(String optValue);

	void insert(Module module);
	
	void delete(@Param(value="ids")String ids);

	void update(Module temp);
	
	@Select("select id,module_name,parent_id from t_module where is_valid=1")
	List<ModuleVo> queryAll();

	@Select("SELECT module_id FROM t_permission WHERE role_id = #{roleId} and is_valid = 1")
	List<Integer> queryModulesByRoleIn(@Param(value="roleId")Integer roleId);
	
	List<Module> queryChiledrenModules(@Param(value="treePath")String treePath);
	

	
}
