package com.shsxt.crm.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.base.ResultInfo;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.vo.UserLoginIdentity;
import com.shsxt.crm.vo.UserVO;


@Controller
@RequestMapping("user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("login")
	@ResponseBody
	public ResultInfo login(String userName,String password,HttpSession session){
		ResultInfo result = null;
		Object[] obj	=userService.login(userName,password);
		UserLoginIdentity userLoginIdentity = (UserLoginIdentity) obj[0];
		result = success(userLoginIdentity);
		List<String> permissions = (List<String>) obj[1];
		session.setAttribute("permissions", permissions);
		return result;
	}
		
	@RequestMapping("find_customer_manager")
	@ResponseBody
	public List<UserVO> queryCustomerManager(){
		List<UserVO> customerManagers = userService.queryCustomerManagers();
		return customerManagers;
	}
	@RequestMapping("index")
	public String index() {
		return "user";
	}
}
