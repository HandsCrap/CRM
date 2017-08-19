package com.shsxt.crm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.crm.base.BaseQuery;
import com.shsxt.crm.constant.ModuleGrade;
import com.shsxt.crm.dao.ModuleDao;
import com.shsxt.crm.exception.ParamException;
import com.shsxt.crm.model.Module;
import com.shsxt.crm.util.AssertUtil;
import com.shsxt.crm.vo.ModuleVo;
@Service
public class ModuleService {

	@Autowired
	private ModuleDao moduleDao;
	/**
	 * 模块分页展示
	 * @param query
	 * @return
	 */
	public Map<String, Object> selectForPage(BaseQuery query) {
		PageBounds pageBounds = query.buildPageBounds();
		PageList<Module> modules = moduleDao.selectForPage(pageBounds);
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("rows", modules);
		result.put("total", modules.getPaginator().getTotalCount());
		return result;
	}
	/**
	 * 添加
	 * @param module
	 */
	public void add(Module module) {
		//基本参数验证
		checkParams(module);
		Integer grade = module.getGrade();
		// 层级判断 
		if (grade != ModuleGrade.root.getGrade()) {
			AssertUtil.intNotNull(module.getParentId(), "请选择上一级");
		}
		//构建treepath
		String treePath=buildTreePath(grade,module.getParentId());
		//权限值唯一性验证
		Module temp=moduleDao.queryByOptValue(module.getOptValue());
		AssertUtil.intTrue(temp != null, "该权限值已存在，请重新输入");
		//保存
		module.setTreePath(treePath);
		module.setCreateDate(new Date());
		module.setUpdateDate(new Date());
		module.setIsValid(1);;
		moduleDao.insert(module);
	}
	/**
	 * 修改
	 * @param module
	 */
	public void update(Module module) {
		//基本参数校验
		Integer id = module.getId();
		AssertUtil.intNotNull(id, "请选择一条记录进行操作！");
		checkParams(module);
		//层级判段
		Integer grade = module.getGrade();
		//若不是根级，则需要有父级
		if(grade!=ModuleGrade.root.getGrade()){
			AssertUtil.intNotNull(module.getParentId(), "请选择上层模块");
		}
		//校验该模块是否存在
		Module temp=moduleDao.queryById(module.getId());
		AssertUtil.notNull(temp, "该模块不存在,请重新选择");
		//构建treePath,验证传入的treePath是否与数据库一致
		if(module.getParentId()!=null&&!module.getParentId().equals(temp.getParentId())){
			String treePath=buildTreePath(grade, module.getParentId());
			module.setTreePath(treePath);
		}else{
			module.setTreePath(temp.getTreePath());
		}
		//权限值唯一性验证(若修改，则需要与数据库对比)
		if (!module.getOptValue().equals(temp.getOptValue())) {
			Module moduleForPermission = moduleDao.queryByOptValue(module.getOptValue());
			AssertUtil.intTrue(moduleForPermission!=null, "该权限值已存在，请重新输入！");
		}
		BeanUtils.copyProperties(module, temp);
		moduleDao.update(temp);
	}
	/**
	 * 删除
	 * @param ids
	 */
	public void delete(String ids) {
		AssertUtil.isNotEmpty(ids, "请选择需要删除的记录！");
		moduleDao.delete(ids);
		
	}
	/**
	 * 查找模块菜单
	 * @param grade
	 * @return
	 */
	public List<Module> queryByGrade(Integer grade) {
		if (grade == null || grade < 0) {
			throw new ParamException("请选择层级");
		}
		return moduleDao.queryBygrade(grade);
	}
	/**
	 * 返回权限树结构
	 * @param roleId
	 * @return
	 */
	public List<ModuleVo> queryAll(Integer roleId) {
		List<ModuleVo> modules=moduleDao.queryAll();
		List<Integer> roleModuleIds=moduleDao.queryModulesByRoleIn(roleId);
		if(roleModuleIds==null||roleModuleIds.size()<1){
			return modules;
		}
		for(ModuleVo  moduleVo :modules){
			if(roleModuleIds.contains(moduleVo.getId())){
				moduleVo.setChecked(true);
			}
		}
		return modules;
	}
	/**
	 * 根据id查找模块
	 * @param moduleId
	 * @return
	 */
	public Module queryById(Integer moduleId) {
		return moduleDao.queryById(moduleId);
	}
	/**
	 * 构建treePath
	 * @param grade
	 * @param parentId
	 * @return
	 */
	private String buildTreePath(Integer grade,Integer parentId){
		if (grade==ModuleGrade.root.getGrade()) {
			return null;
		}
		String treePath="";
		Module parentModule=moduleDao.queryById(parentId);
		String parentTreePath = parentModule.getTreePath();
		if(StringUtils.isNoneBlank(parentTreePath)){
			treePath=parentTreePath+parentId+",";
		}else{
			treePath=","+parentId+",";
		}
		return treePath;
	}
	/**
	 * 基本参数验证
	 * @param module
	 */
	private void checkParams(Module module){
		String moduleName=module.getModuleName();
		AssertUtil.isNotEmpty(moduleName, "请输入模块名称");
		Integer oders=module.getOrders();
		AssertUtil.intNotNull(oders, "请输入排序");
		String url = module.getUrl();
		AssertUtil.isNotEmpty(url, "请输入访问路径或者方法");
		String optValue = module.getOptValue();
		AssertUtil.isNotEmpty(optValue, "请输入操作权限");
		Integer grade = module.getGrade();
		if(grade==null||grade<0){
			throw new ParamException("请选择层级关系");
		}
	}
}
