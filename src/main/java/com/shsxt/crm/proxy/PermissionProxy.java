package com.shsxt.crm.proxy;

import java.lang.reflect.Method;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shsxt.crm.annotation.RequirePermissions;
import com.shsxt.crm.constant.Constant;
import com.shsxt.crm.exception.LoginException;
import com.shsxt.crm.exception.UnAuthPermissionException;
import com.shsxt.crm.service.PermissionService;
import com.shsxt.crm.util.LoginUserUtil;

@Component
@Aspect
public class PermissionProxy {
	@Autowired
	private HttpSession session;
	@Autowired 
	private HttpServletRequest request;
	@Autowired
	private PermissionService permissionService;
	@Pointcut("execution(* com.*.*.controller.*.*(..))")
	public void pointcut(){
		
	}
	@Before("pointcut()")
	public void preMethod(JoinPoint joinPoint) throws LoginException{
		//登录页面无需验证
		String uri=request.getRequestURI();
		if("/index".equals(uri)||"/user/login".equals(uri)){
			return;
		}
		//获取用户权限
		List<String> permissions=buildPermission();
		// 拿到方法签名
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature(); 
		// 拿到执行的方法
		Method method = methodSignature.getMethod();
		// 获取一个注解
		RequirePermissions requirePermissions = method.getAnnotation(RequirePermissions.class);
		if (requirePermissions == null) {
			return;
		}
		
		// 权限比对
		if (!permissions.contains(requirePermissions.permission())) {
			throw new UnAuthPermissionException("-1", "权限认证失败");
		}
	}
	/**
	 * 设置权限
	 * @return
	 * @throws LoginException 
	 */
	private List<String> buildPermission() throws LoginException {
		Integer userId=LoginUserUtil.releaseUserIdFromCookie(request);
		if(userId==null){
			throw new LoginException(201,"请登录！！");
		}
		@SuppressWarnings("unchecked")
		List<String> permissions=(List<String>) session.getAttribute(Constant.USER_PERMISSION_SESSION_KEY);
		if (permissions != null) {
			return permissions;
		}
		// 从数据库中读取用户权限
		permissions = permissionService.queryUserPermissions(userId);
		if (permissions == null || permissions.size() < 1) {
			throw new UnAuthPermissionException("-1", "权限认证失败");
		}
		session.setAttribute(Constant.USER_PERMISSION_SESSION_KEY, permissions);
		return permissions;
		
		
	}
}
