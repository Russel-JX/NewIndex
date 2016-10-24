package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.dao.IUserDao;
import com.opensymphony.xwork2.ActionSupport;
import com.pojo.ZbUser;
import com.util.PageUtil;

public class UserQueryAction extends ActionSupport implements ServletResponseAware,ServletRequestAware{
	private List<ZbUser> items = new ArrayList<ZbUser>();
	private int page;
	private int start;
	private int limit;
	private long total;
	
	private  IUserDao iud2;
	
	private HttpServletRequest request; 	
	private HttpServletResponse response; 	
	
	//写回对象，需导入（struts2-json-plugin-2.3.1.2.jar）
	//查询所有用户
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		System.out.println("====================查询用户=====================");
		PageUtil pageUtil = new PageUtil();
		pageUtil.setPage(page);
		pageUtil.setPagesize(limit);
		
		items = (List<ZbUser>)iud2.queryUsers(pageUtil);
		
		total = 100;
//		items = (List<ZbUser>)iud2.queryAll("from ZbUser t order by  t.userid desc");//查询所有用户信息
		return "a";
	}
	//查询普通用户（普通管理员）
	public String queryNormalAdministrtors(){
		System.out.println("====================queryNormalAdministrtors=====================");
		items = (List<ZbUser>)iud2.queryAll("from ZbUser t where t.usertype = 0 ");//查询所有用户信息
		return "a";
	}



	public List<ZbUser> getItems() {
		return items;
	}


	public void setItems(List<ZbUser> items) {
		this.items = items;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
		
	}
	/*
	 * 你的action中的方法返回"success"后，你跳转的不是某个页面，而是像下载数据一样的，
	 * 得到是包含json字符串的流。返回的josn数据是acton中的get方法返回的值。这时。
	 * 你的action中用spring托管的dao之类的不能写get方法，否则会报错！
	 */
//	public IUserDao getIud2() {
//		return iud2;
//	}

	public void setIud2(IUserDao iud2) {
		this.iud2 = iud2;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
