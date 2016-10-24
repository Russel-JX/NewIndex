package com.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.dao.IDataDao;
import com.dao.ILevelThreeDao;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pojo.ZbData;
import com.pojo.ZbDetail;
/**
 *三级指标（ZbDetail） 。提交三级指标
 */
public class ThreeAction extends ActionSupport implements ServletResponseAware, ModelDriven<Object>,ServletRequestAware{
	private ZbDetail zbDetail = new ZbDetail();
	private ILevelThreeDao iL3Dao;
	private IDataDao idDao;
	

	private HttpServletResponse response; 
	private HttpServletRequest request; 
	
	public Object getModel() {
		return zbDetail;
	}
	
	//提交三级指标
	public void addThreeIndex() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("我在提交三级指标action中...");
		
		String date = request.getParameter("date");
//		System.out.println("要插入 "+date+" 时间的空的具体指标值");
//		
//		System.out.println("三级指标名***"+zbDetail.getZbName());
//		System.out.println("三级指标填写日期%%%"+zbDetail.getZbDate());
//		System.out.println("三级指标排序规则%%%"+zbDetail.getZbSort());
//		System.out.println("级联的一级指标的id是： "+zbDetail.getParentIdL1());
//		System.out.println("级联的二级指标的id是： "+zbDetail.getParentId());
		
		Integer newId = (Integer) iL3Dao.add(zbDetail);
//		System.out.println("插入的三级指标id是： "+newId);
		
		//一个月内有多少条数据
		Integer year = Integer.parseInt(date.substring(0, 4));
		Integer month = Integer.parseInt(date.substring(5, 7));//本月
		Integer lastMonth = 0;//上个月
//		System.out.println("年是： "+year);
//		System.out.println("月是： "+month);
		//上个月的天数
		if(month==1){
			lastMonth = 12;
		}else{
			lastMonth = month-1;
		}
//		System.out.println("上个月是： "+lastMonth);
		int totalDays = days(year,lastMonth);
//		System.out.println("上个月的总天数是： "+totalDays);
		//当创建三级指标后，自动向具体指标值表格，插入空数据，填充，使用用户未填写具体指标时，也能查看。
		iL3Dao.addMulti(newId,year,month,lastMonth,totalDays);
		
		String messageBack = "{success:true,data:{msg:'提交成功！'}}";
		//System.out.println("————————————————————");
		response.getWriter().write(messageBack);
	}
	//修改一级指标
	public void updateThreeIndex() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("我在修改三级指标action中...");
		
//		System.out.println("三级id是：" +request.getParameter("id"));
//		System.out.println("三级名称是：" +request.getParameter("zbName"));
		
		//获取要修改的数据
//		System.out.println("要修改的三级指标id是： "+zbDetail.getId());
//		System.out.println("要修改的三级指标名称是： "+zbDetail.getZbName());
//		System.out.println("要修改的三级指标排序规则是： "+zbDetail.getZbSort());
//		System.out.println("要修改的三级指标得分计算公式是： "+zbDetail.getZbFormula());
//		System.out.println("要修改的三级指标所占分数是： "+zbDetail.getZbPoint());
//		System.out.println("要修改的三级指标权重是： "+zbDetail.getZbWeight());
//		System.out.println("要修改的三级指标负责人是： "+zbDetail.getZbPrincipal());
//		System.out.println("要修改的三级指标目标值是： "+zbDetail.getZbTarget());
//		System.out.println("要修改的三级指标来源是： "+zbDetail.getZbSource());
//		System.out.println("要修改的三级指标当前值是： "+zbDetail.getZbNow());
//		System.out.println("要修改的三级指标时间是： "+zbDetail.getZbTime());
//		System.out.println("要修改的三级指标频度是： "+zbDetail.getZbFrequentness());
		
		zbDetail.setZbName(URLDecoder.decode(URLDecoder.decode(zbDetail.getZbName(),"UTF-8"),"UTF-8")); 
		zbDetail.setZbSource(URLDecoder.decode(URLDecoder.decode(zbDetail.getZbSource(),"UTF-8"),"UTF-8")); 
		zbDetail.setZbTarget(URLDecoder.decode(URLDecoder.decode(zbDetail.getZbTarget(),"UTF-8"),"UTF-8")); 
		zbDetail.setZbNow(URLDecoder.decode(URLDecoder.decode(zbDetail.getZbNow(),"UTF-8"),"UTF-8")); 
		zbDetail.setZbTime(URLDecoder.decode(URLDecoder.decode(zbDetail.getZbTime(),"UTF-8"),"UTF-8")); 
		zbDetail.setZbFrequentness(URLDecoder.decode(URLDecoder.decode(zbDetail.getZbFrequentness(),"UTF-8"),"UTF-8")); 
		
		iL3Dao.update("update ZbDetail t set t.zbName = '"+zbDetail.getZbName()+"',t.zbSort = '"+zbDetail.getZbSort()+"',t.zbFormula = '"+zbDetail.getZbFormula()+
				"',t.zbPoint = '"+zbDetail.getZbPoint()+"',t.zbWeight = '"+zbDetail.getZbWeight()+"',t.zbPrincipal = '"+zbDetail.getZbPrincipal()+
				"',t.zbTarget = '"+zbDetail.getZbTarget()+"',t.zbSource = '"+zbDetail.getZbSource()+"',t.zbNow = '"+zbDetail.getZbNow()+
				"',t.zbTime = '"+zbDetail.getZbTime()+"',t.zbFrequentness = '"+zbDetail.getZbFrequentness()+"' where t.id = "+zbDetail.getId());
		response.getWriter().write("{success:true,data:'修改成功！'}");
//		System.out.println("————————————————————");
	}
	//删除三级指标和旗下的具体指标值
	public void deleteThreeIndex() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("我在删除三级指标action中...");
		
		String[] idData = request.getParameterValues("idData");//参数是一个数组
//		System.out.println("长度是：  "+idData.length);
		//将字符串转成int
		List<Integer> idData2 = new ArrayList<Integer>();
		for(int i=0;i<idData.length;i++){
			idData2.add(Integer.parseInt(idData[i]));
		}
		
		
		//先删除具体指标值，再删除父三级指标
		idDao.deleteMultiFixed(ZbData.class,"zbId", idData2);		
		iL3Dao.deleteMultiFixed(ZbDetail.class,"id", idData2);

//		System.out.println("————————————————————");
	}
	//批量插入空数据
	public void addMulti(int newId,int year,int month,int totalDays){
		
	}
	//判断上个月的总天数
	public int days(int year,int month){
		int totalDay = 0;
		if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
			totalDay = 31;
		}else if(month==2){//判断闰年
			if((year%4==0)&&(year%100!=0)||(year%400==0)){
				totalDay = 29;
			}else{
				totalDay = 28;
			}
		}else{
			totalDay = 30;
		}
		return totalDay;
	}
	
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}
	public void setiL3Dao(ILevelThreeDao iL3Dao) {
		this.iL3Dao = iL3Dao;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		response = arg0;
	}

	public ZbDetail getZbDetail() {
		return zbDetail;
	}

	public void setZbDetail(ZbDetail zbDetail) {
		this.zbDetail = zbDetail;
	}
	public void setIdDao(IDataDao idDao) {
		this.idDao = idDao;
	}

}
