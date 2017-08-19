package com.shsxt.crm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.shsxt.crm.service.RoleService;

@Controller
@RequestMapping("role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	@RequestMapping("index")
	public String index(){
		return "role";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> selectForPage(){
		return roleService.selectAll();
	}
}
