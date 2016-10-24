package com.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.pojo.ZbUser;

/**
 * @author JX
 * 用户登录拦截器
 */
public class LoginInterceptor extends AbstractInterceptor{

	public String intercept(ActionInvocation arg0) throws Exception {
		System.out.println("用户登录拦截器开始...");
		Map<String,Object> session = arg0.getInvocationContext().getSession();
		if(session.get("zbUser")==null){
			return "login";
		}
		System.out.println("用户登录拦截器结束...");
		return arg0.invoke();
	}

}
