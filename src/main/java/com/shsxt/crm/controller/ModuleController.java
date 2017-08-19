package com.shsxt.crm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.base.BaseQuery;
import com.shsxt.crm.base.ResultInfo;
import com.shsxt.crm.model.Module;
import com.shsxt.crm.service.ModuleService;
import com.shsxt.crm.service.PermissionService;
import com.shsxt.crm.vo.ModuleVo;

@Controller
@RequestMapping("module")
public class ModuleController extends BaseController{

	@Autowired
	private ModuleService moduleService;
	@Autowired 
	private PermissionService permissionService;
	
	@RequestMapping("index")
	public String index(){
		return "module";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> list(BaseQuery query){
		Map<String, Object> result = moduleService.selectForPage(query);
		return result;
	}
	
	@RequestMapping("add")
	@ResponseBody
	public ResultInfo add(Module module){
		moduleService.add(module);
		return success("添加成功");
	}	
	@RequestMapping("update")
	@ResponseBody
	public ResultInfo update(Module module){
		moduleService.update(module);
		return success("修改成功");
	}
	@RequestMapping("delete")
	@ResponseBody
	public ResultInfo delete(String ids){
		moduleService.delete(ids);
		return success("删除成功");
	}
	@RequestMapping("find_by_grade")
	@ResponseBody
	public List<Module> queryByGrade(Integer grade){
		return moduleService.queryByGrade(grade);
	}
	
	@RequestMapping("relate_permission")
	public String permission(Integer roleId,Model model ){
		List<ModuleVo> modules= moduleService.queryAll(roleId);
		model.addAttribute("modules",modules);
		model.addAttribute("roleId",roleId);
		return "role_module";
	}
	@RequestMapping("dorelate")
	@ResponseBody
	public ResultInfo dorelate(Integer roleId,Integer moduleId,boolean checked){
		permissionService.addRelate(roleId,moduleId,checked);
		return success("操作成功");
	}
}
