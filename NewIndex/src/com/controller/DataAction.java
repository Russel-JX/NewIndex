package com.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.dao.IDataDao;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pojo.ZbData;
/**
 *具体填写指标值action（完成） 
 */
public class DataAction extends ActionSupport implements ServletResponseAware ,ModelDriven{
	private ZbData zbData = new ZbData();//pojo类，使用new创建
	private IDataDao idDao;
	private HttpServletResponse response;
	
	private String[] data_values;
	private int[] data_ids;
	
	public Object getModel() {
		return zbData;
	}
	
	//更新或插入具体指标值。先查询，有则更新，无则插入。
	public void addDataIndex() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("填写具体指标action中...");
//		
//		System.out.println("指标值的长度是： "+data_values.length);
//		System.out.println("指标id的长度是： "+data_ids.length);
//		for(int i=0;i<data_values.length;i++){
//			System.out.println("数组的第"+(i+1)+"个元素是 "+data_values[i]);
//		}
//		for(int i=0;i<data_ids.length;i++){
//			System.out.println("数组的第"+(i+1)+"个元素id是 "+data_ids[i]);
//		}
		
		//批量修改一个月的具体指标值
		String hql = "update ZbData t set t.zbData = ? where t.id = ?";
		idDao.updateMulti(data_ids, data_values,hql);
		
//		System.out.println("指标值是： "+zbData.getZbData());
//		System.out.println("具体指标的id是：  "+zbData.getId());
//		
//		int number = idDao.update("update ZbData t set t.zbData='" +zbData.getZbData()+ "' where t.id= '"+zbData.getId()+"' ");
//		System.out.println("更新了几条记录： "+number);
		
//		String messageBack = "{success:true,data:{userName:'胡汉三回来了',age:'30'}}";
//		System.out.println("————————————————————");
//		response.getWriter().write(messageBack);
	}
	public String execute() throws Exception {
		return super.execute();
	}

	public ZbData getZbData() {
		return zbData;
	}
	public void setZbData(ZbData zbData) {
		this.zbData = zbData;
	}
	public void setIdDao(IDataDao idDao) {
		this.idDao = idDao;
	}
	public void setServletResponse(HttpServletResponse arg0) {
		response = arg0;
	}

	public void setData_values(String[] data_values) {
		this.data_values = data_values;
	}
	public void setData_ids(int[] data_ids) {
		this.data_ids = data_ids;
	}

}
