package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.dao.ILevelTwoDao;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pojo.ZbL1;

/**
 * 二级指标。查询一级指标名和id
 */
public class TwoQueryAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {
	private List<ZbL1> items_l1 = new ArrayList<ZbL1>();// pojo类，使用new创建

	private ILevelTwoDao iL2Dao;

	private HttpServletRequest request;
	private HttpServletResponse response;

	// 根据日期，获取一级指标的id和名称(在创建二级指标时的本月内)
	public String execute() throws Exception {
		// System.out.println("————————————————————");

		// System.out.println("==============请求的编码是： "+request.getCharacterEncoding()+"响应的编码是： "+response.getCharacterEncoding());
		// 获取查询的参数
		String dateParam = request.getParameter("dateParam");// 下拉列表的查询参数
		// System.out.println("查询的日期是："+dateParam);

		/* 根据名称和当月时间查询单个一级指标 */

		/* 根据当月时间查询本月所有一级指标 */

		// 查询一级指标
		items_l1 = (List<ZbL1>) iL2Dao
				.queryAll("from ZbL1 t where Substr(to_char(t.zbDate,'yyyy-mm-dd'),1,7)='"
						+ dateParam + "'");

		// for(int i=0;i<items_l1.size();i++){
		// System.out.println("id是："+items_l1.get(i).getId()+" 一级指标名称是： "+items_l1.get(i).getZbName()+" 时间是： "+items_l1.get(i).getZbDate()+" 排序规则是： "+items_l1.get(i).getZbSort());//
		// }
		// System.out.println("一级指标记录数量是： "+items_l1.size());
		//
		// System.out.println("————————————————————");
		return SUCCESS;
	}

	public void setiL2Dao(ILevelTwoDao iL2Dao) {
		this.iL2Dao = iL2Dao;
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

	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}

}
