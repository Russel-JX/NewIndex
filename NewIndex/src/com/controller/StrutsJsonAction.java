package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.dao.IUserDao;
import com.dao.impl.IUserDaoImpl;
import com.opensymphony.xwork2.ActionSupport;
import com.pojo.ZbUser;

public class StrutsJsonAction extends ActionSupport implements ServletRequestAware, ServletResponseAware{
	private List<ZbUser> items = new ArrayList<ZbUser>();//每次查询出的数据
	private long total;//查询的总记录数，每次查询都一样.必须设置此属性值传给前台，否则，
	private  IUserDao iud;
	
	
	private HttpServletRequest request; 	
	private HttpServletResponse response; 	
	
	//写回json格式的字符串，不用导入json插件包（struts2-json-plugin-2.3.1.2.jar）。
//	public void getGrid() throws Exception {
//		
//		System.out.println("我在action中2...");
//		
//		ZbUser user1 = new ZbUser(2,"红",0,1);
//		ZbUser user2 = new ZbUser(3,"黄",1,3);
//		ZbUser user3 = new ZbUser(4,"蓝",1,4);
//		ZbUser user4 = new ZbUser(7,"紫",1,3);
//		items.add(user1);
//		items.add(user2);
//		items.add(user3);
//		items.add(user4);
//		
//		System.out.println("我在action中2...");
//		
//		String back= "{items:[{userid:3,username:'aa',usertype:0,zbSort:3},{userid:3,username:'aa',usertype:0,zbSort:3}]}";
//		response.getWriter().write(back);
//		
//		
//		
//	}
	public String execute() throws Exception {
//		System.out.println("————————————————————");
		//获取分页信息
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		int page = Integer.parseInt(request.getParameter("page"));
//		System.out.println("start="+start+"limit="+limit+"page="+page);
//		
//		System.out.println("我在查询所有用户StrutsJsonAction中...");
		
		//查询记录总数
		total = iud.queryTotal();
		//判断查询是否超出范围
		if((limit+start)>total){
			limit = (int) (total-start);
		}
		
		//查询每一页的数据
		items = (List<ZbUser>)iud.AllByPageHQL(start, limit);
//		for(int i=0;i<items.size();i++){
//			System.out.println("id是："+items.get(i).getUserid()+" 姓名是： "+items.get(i).getUsername()+" 类型是： "+items.get(i).getUsertype()+" 排序规则是： "+items.get(i).getZbSort());
//		}
//		System.out.println("用户数量是： "+items.size());
//		
//		System.out.println("————————————————————");
		return SUCCESS;
	}
	public void totalRec(){
		long t = iud.queryTotal();
//		System.out.println("表中的所有记录数是： "+t);
	}

	public List<ZbUser> getitems() {
		return items;
	}

	public void setitems(List<ZbUser> items) {
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
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		
	}
//	public IUserDao getIud() {
//		return iud;
//	}
	public void setIud(IUserDao iud) {
		this.iud = iud;
	}


}
