package com.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.dao.ILevelOneDao;
import com.dao.IUserDao;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pojo.ZbL1;

import java.net.URLDecoder;
/**
 *一级指标 
 */
public class OneAction extends ActionSupport implements ServletResponseAware,ModelDriven<Object>,ServletRequestAware{
	private ZbL1 zbOne = new ZbL1();//pojo类，使用new创建
	private ILevelOneDao iL1Dao;
	private HttpServletResponse response; 
	private HttpServletRequest request; 
	
	private boolean success;
	
	public Object getModel() {
		return zbOne;
	}
	
	public void addOneIndex() throws Exception {
//		System.out.println("————————————————————");
		System.out.println("***"+zbOne.getZbName());
		
		System.out.println("请求："+request.getCharacterEncoding()+"响应"+response.getCharacterEncoding());
		
//		System.out.println("%%%"+zbOne.getZbDate());
//		System.out.println("%%%"+zbOne.getZbSort());
		
		Integer id = (Integer) iL1Dao.add(zbOne);
//		System.out.println("插入的一级指标id是： "+id);
		
//		if(id>0){
//			success = true;
//		}
		
		String messageBack = "{success:true,data:{msg:'提交成功！'}}";
		//System.out.println("————————————————————");
		response.getWriter().write(messageBack);
	}
	public void updateOneIndex() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("我在修改一级指标action中...");
//		
//		//获取要修改的数据
//		System.out.println("要修改的一级指标id是： "+zbOne.getId());
//		System.out.println("要修改的一级指标名称是： "+zbOne.getZbName());
//		System.out.println("要修改的一级指标排序规则是： "+zbOne.getZbSort());
		
		zbOne.setZbName(URLDecoder.decode(URLDecoder.decode(zbOne.getZbName(),"UTF-8"),"UTF-8")); 
		System.out.println("---"+zbOne.getZbName());
		
		
		iL1Dao.update("update ZbL1 t set t.zbName = '"+zbOne.getZbName()+"',t.zbSort = '"+zbOne.getZbSort()+"' where t.id = "+zbOne.getId());
		response.getWriter().write("{success:true,data:'修改成功！'}");
//		System.out.println("————————————————————");
	}
	//删除三级指标和旗下的具体指标值
	public void deleteOneIndex() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("我在删除一级指标action中...");
		
		Integer idData = Integer.parseInt(request.getParameter("idData"));
//		System.out.println("要删除一级指标的id是：  "+idData);
		
		//先查一级下是否有二级指标，有则不能删除。
		long number = iL1Dao.queryTotalIndex("ZbL2", "parentId", idData);
//		System.out.println("一级下有 "+number+" 个二级指标");
		if(number==0){
			//删除二级指标
			iL1Dao.deleteMulti("from ZbL1 t where t.id = "+idData);
			response.getWriter().write("{flag:'yes'}");
			return;
		}else{
			String warning = "删除失败! 该指标下已有 "+ number+" 个子指标";
			response.getWriter().write("{warning:'"+warning+"'}");
		}
//		System.out.println("————————————————————");
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
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
