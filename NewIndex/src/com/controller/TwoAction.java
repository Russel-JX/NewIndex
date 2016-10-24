package com.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.dao.ILevelTwoDao;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pojo.ZbData;
import com.pojo.ZbL2;
/**
 *二级指标 。提交和修改二级指标
 */
public class TwoAction extends ActionSupport implements ServletResponseAware, ModelDriven<Object>,ServletRequestAware{
	private ZbL2 zbTwo = new ZbL2();//pojo类，使用new创建
	private ILevelTwoDao iL2Dao;
	
	private HttpServletResponse response; 
	private HttpServletRequest request; 
	
	private String msgBack;
	
	//使用模型驱动时，action类要实现getModel方法，如果这里也使用ＪＳＯＮ结合Ｓｔｒｕｔｓ，会将模型驱动的属性值也作为JSON数据返回，导致响应的json数据为空，前台页面不能显示后台的数据。
	public Object getModel() {
		return zbTwo;
	}
	//提交
	public void addTwoIndex() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("我在提交二级指标action中...");
//		
//		System.out.println("二级指标名***"+zbTwo.getZbName());
//		System.out.println("二级指标填写日期%%%"+zbTwo.getZbDate());
//		System.out.println("二级指标排序规则%%%"+zbTwo.getZbSort());
//		System.out.println("级联的一级指标的id是： "+zbTwo.getParentId());
		
		Integer id = (Integer) iL2Dao.add(zbTwo);
//		System.out.println("插入的二级指标id是： "+id);
		
//		if(id>0){  
//			msgBack = "{success:true,data:{insertMsg:'插入成功！'}}";
//		}
		
		String messageBack = "{success:true,data:{msg:'提交成功！'}}";
		//System.out.println("————————————————————");
		response.getWriter().write(messageBack);
		
		//return "success";
	}

	// 修改一级指标
	public void updateTwoIndex() throws IOException {
//		System.out.println("————————————————————");
//		System.out.println("我在修改二级指标action中...");

		// System.out.println("三级id是：" +request.getParameter("id"));
		// System.out.println("三级名称是：" +request.getParameter("zbName"));

		// 获取要修改的数据
//		System.out.println("要修改的二级指标id是： " + zbTwo.getId());
//		System.out.println("要修改的二级指标名称是： " + zbTwo.getZbName());
//		System.out.println("要修改的二级指标排序规则是： " + zbTwo.getZbSort());
		
		zbTwo.setZbName(URLDecoder.decode(URLDecoder.decode(zbTwo.getZbName(),"UTF-8"),"UTF-8")); 

		iL2Dao.update("update ZbL2 t set t.zbName = '" + zbTwo.getZbName()
				+ "',t.zbSort = '" + zbTwo.getZbSort() + "' where t.id = "
				+ zbTwo.getId());
		response.getWriter().write("{success:true,data:'修改成功！'}");
//		System.out.println("————————————————————");
	}

	// 删除三级指标和旗下的具体指标值
	public void deleteTwoIndex() throws IOException {
//		System.out.println("————————————————————");
//		System.out.println("我在删除二级指标action中...");

		Integer idData = Integer.parseInt(request.getParameter("idData"));
//		System.out.println("要删除二级指标的id是：  " + idData);

		// 先查二级下是否有三级指标，有则不能删除。
		long number = iL2Dao.queryTotalIndex("ZbDetail", "parentId", idData);
//		System.out.println("二级下有 " + number + " 个三级指标");
		if (number == 0) {
			// 删除二级指标
			iL2Dao.deleteMulti("from ZbL2 t where t.id = " + idData);
			response.getWriter().write("{flag:'yes'}");
			return;
		} else {
			String warning = "删除失败! 该指标下已有 " + number + " 个子指标";
			response.getWriter().write("{warning:'" + warning + "'}");
		}
//		System.out.println("————————————————————");
	}

	public void setiL2Dao(ILevelTwoDao iL2Dao) {
		this.iL2Dao = iL2Dao;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		response = arg0;
	}

	public ZbL2 getZbTwo() {
		return zbTwo;
	}

	public void setZbTwo(ZbL2 zbTwo) {
		this.zbTwo = zbTwo;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}
	public String getMsgBack() {
		return msgBack;
	}
	public void setMsgBack(String msgBack) {
		this.msgBack = msgBack;
	}

}
