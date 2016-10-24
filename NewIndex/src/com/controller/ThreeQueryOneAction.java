package com.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dao.ILevelThreeDao;
import com.dao.ILevelTwoDao;
import com.opensymphony.xwork2.ActionSupport;
import com.pojo.ZbL1;
/**
 *三级指标。查询一级指标名和id 
 */
public class ThreeQueryOneAction extends ActionSupport implements ServletRequestAware{
	private List<ZbL1> items_l1 = new ArrayList<ZbL1>();//pojo类，使用new创建
	
	private ILevelThreeDao iL3Dao;
	
	private HttpServletRequest request; 
	
	//获取一级指标的id和名称
	public String execute() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("我在三级指标查询一级指标action中...");
		//获取查询的参数
		String date = request.getParameter("date");
//		System.out.println("查询的条件（年月）是："+date);
		
		//查询表中属于同一年月的数据
		
		/*根据名称和当月时间查询单个一级指标*/
		
		
		/*根据当月时间查询本月所有一级指标*/
		
		
		//查询一级指标
		items_l1 = (List<ZbL1>)iL3Dao.queryByMonth("ZbL1", date);
		
		/*for(int i=0;i<items_l1.size();i++){
			System.out.println("id是："+items_l1.get(i).getId()+" 一级指标名称是： "+items_l1.get(i).getZbName()+" 时间是： "+items_l1.get(i).getZbDate()+" 排序规则是： "+items_l1.get(i).getZbSort());
		}*/
//		System.out.println("一级指标记录数量是： "+items_l1.size());
//		
//		System.out.println("————————————————————");
		return SUCCESS;
	}

	public void setiL3Dao(ILevelThreeDao iL3Dao) {
		this.iL3Dao = iL3Dao;
	}

	public List<ZbL1> getItems_l1() {
		return items_l1;
	}

	public void setItems_l1(List<ZbL1> items_l1) {
		this.items_l1 = items_l1;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}
	

}
