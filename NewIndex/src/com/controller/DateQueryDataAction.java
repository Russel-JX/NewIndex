package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dao.ILevelThreeDao;
import com.opensymphony.xwork2.ActionSupport;
import com.pojo.ZbData;

public class DateQueryDataAction extends ActionSupport implements ServletRequestAware{
	private List<ZbData> items_data;
	private HttpServletRequest request;
	private ILevelThreeDao iL3Dao;
	
	public String execute() throws Exception {
//		System.out.println("————————————————————————————");
//		System.out.println("在根据id和日期查询具体指标值action中...");
		String id = request.getParameter("id");
		String date = request.getParameter("date");
//		System.out.println("父id是： "+id+" 查询日期(月份)是： "+date);
		
		String sql = "from ZbData t where t.zbId='"+id+"' order by t.zbDate asc";//只根据父三级指标id，不需要日期。根据日期排序
//		System.out.println("sql语句是： "+sql);
		items_data = (List<ZbData>)iL3Dao.queryAll(sql);//Object转成集合
//		System.out.println("具体指标的数量是： "+items_data.size());
//		if(items_data!=null){
//			for(int i=0;i<items_data.size();i++){
//				System.out.println("第"+(i+1)+"天的指标值是： "+items_data.get(i).getZbData()+"id是： "+items_data.get(i).getId()+"日期是： "+items_data.get(i).getZbDate()+"属于三级指标的id是： "+items_data.get(i).getZbId());
//			}
//		}
//		
//		System.out.println("————————————————————————————");
		return SUCCESS;
	}


	public List<ZbData> getItems_data() {
		return items_data;
	}

	public void setItems_data(List<ZbData> items_data) {
		this.items_data = items_data;
	}

	public void setiL3Dao(ILevelThreeDao iL3Dao) {
		this.iL3Dao = iL3Dao;
	}


	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		
	}
	
}
