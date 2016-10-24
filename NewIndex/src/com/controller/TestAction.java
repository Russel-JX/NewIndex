package com.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.dao.ILevelOneDao;
import com.dao.IUserDao;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pojo.ZbL1;
/**
 *测试用的action 
 */
public class TestAction extends ActionSupport implements ServletResponseAware ,ModelDriven{
	private ZbL1 zbOne = new ZbL1();//pojo类，使用new创建
	private ILevelOneDao iL1Dao;
	private HttpServletResponse response; 
	
	public void addOneIndex() throws Exception {
		System.out.println("————————————————————");
		System.out.println("测试action中...");
		
		System.out.println("***"+zbOne.getZbName());
		System.out.println("%%%"+zbOne.getZbDate());
		System.out.println("%%%"+zbOne.getZbSort());
		
		Integer id = (Integer) iL1Dao.add(zbOne);
		System.out.println("插入的一级指标id是： "+id);
		
		String messageBack = "{success:true,data:{userName:'胡汉三回来了',age:'30'}}";
		System.out.println("————————————————————");
		response.getWriter().write(messageBack);
	}
	public String execute() throws Exception {
		return super.execute();
	}

	public ZbL1 getZbOne() {
		return zbOne;
	}

	public void setZbOne(ZbL1 zbOne) {
		this.zbOne = zbOne;
	}

	public void setiL1Dao(ILevelOneDao iL1Dao) {
		this.iL1Dao = iL1Dao;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		response = arg0;
	}
	public ZbL1 getModel() {
		return zbOne;
	}

}
