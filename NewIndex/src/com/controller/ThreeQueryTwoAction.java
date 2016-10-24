package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dao.ILevelThreeDao;
import com.opensymphony.xwork2.ActionSupport;
import com.pojo.ZbL2;
/**
 *三级指标。查询二级指标名和id 
 */
public class ThreeQueryTwoAction extends ActionSupport implements ServletRequestAware{
	private List<ZbL2> items_l2 = new ArrayList<ZbL2>();//pojo类，使用new创建
	
	private ILevelThreeDao iL3Dao;
	
	private HttpServletRequest request; 
	
	//根据父id、日期，获取一级指标的id和名称
	public String execute() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("我在三级指标查询二级指标action中...");
		//获取查询的参数
		String date = request.getParameter("date").split(",")[0];
		int id = Integer.parseInt(request.getParameter("date").split(",")[1]);
//		System.out.println("查询的条件（年月）是："+date);
//		System.out.println("查询的条件父id是："+id);
		
		/*根据名称和当月时间查询单个一级指标*/
		
		
		/*根据当月时间查询本月所有一级指标*/
		
		
		//查询一级指标
		items_l2 = (List<ZbL2>)iL3Dao.queryMulti("ZbL2", date, id);
		
//		for(int i=0;i<items_l2.size();i++){
//			System.out.println("id是："+items_l2.get(i).getId()+" 二级指标名称是： "+items_l2.get(i).getZbName()+" 时间是： "+items_l2.get(i).getZbDate()+" 排序规则是： "+items_l2.get(i).getZbSort());
//		}
//		System.out.println("二级指标记录数量是： "+items_l2.size());
//		
//		System.out.println("————————————————————");
		return SUCCESS;
	}
	//根据日期，获取一级指标的id和名称
	/*使用SSH+json,以get开头的方法，会作为json对象返回*/
	public String fetchOneByDate() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("我在三级指标查询二级指标action中...");
		//获取查询的参数
		String date = request.getParameter("date");
//		System.out.println("查询的条件（年月）是："+date);
		
		/*根据名称和当月时间查询单个一级指标*/
		
		
		/*根据当月时间查询本月所有一级指标*/
		
		
		//查询一级指标
		items_l2 = (List<ZbL2>)iL3Dao.queryByMonth("ZbL2", date);
		
//		for(int i=0;i<items_l2.size();i++){
//			System.out.println("id是："+items_l2.get(i).getId()+" 二级指标名称是： "+items_l2.get(i).getZbName()+" 时间是： "+items_l2.get(i).getZbDate()+" 排序规则是： "+items_l2.get(i).getZbSort());
//		}
//		System.out.println("二级指标记录数量是： "+items_l2.size());
//		
//		System.out.println("————————————————————");
		return SUCCESS;
	}

	public List<ZbL2> getItems_l2() {
		return items_l2;
	}

	public void setItems_l2(List<ZbL2> items_l2) {
		this.items_l2 = items_l2;
	}

	public void setiL3Dao(ILevelThreeDao iL3Dao) {
		this.iL3Dao = iL3Dao;
	}


	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

}
