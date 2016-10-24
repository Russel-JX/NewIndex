package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.dao.ILevelThreeDao;
import com.opensymphony.xwork2.ActionSupport;
import com.pojo.ZbDetail;
import com.pojo.ZbL2;
import com.pojo.ZbUser;
/**
 *根据所选日期和符级别id查询二级或三级指标。
 */
public class DateIDQueryAction extends ActionSupport implements ServletRequestAware,SessionAware{
	private List<ZbL2> items_l2 = new ArrayList<ZbL2>();//二级指标的集合
	private List<ZbDetail> items_l3 = new ArrayList<ZbDetail>();//三级指标的集合
	
	private ILevelThreeDao iL3Dao;
	
	private HttpServletRequest request; 
	private Map<String,Object> session; 
	
	//获取二级和三级指标的id和名称
	public String execute() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("我在根据日期查询一级或二级指标action中...");
		
		String date = request.getParameter("date");
		Integer id = Integer.parseInt(request.getParameter("id"));
		String tableName = request.getParameter("tableName");
		
//		System.out.println("所要查询的表是： "+tableName);
//		System.out.println("所要查询的日期是： "+date);
//		System.out.println("父指标id是： "+id);
		
		//登录的用户
		ZbUser zbUser = (ZbUser) session.get("zbUser");
//		System.out.println("用户id是： "+zbUser.getUserid()+"用户名是： "+zbUser.getUsername()+"姓名是： "+zbUser.getRealname()+"类型是： "+zbUser.getUsertype());
		//查询表中属于同一年月的数据
		if(tableName.equalsIgnoreCase("ZbL2")){
			String hql = "from ZbL2 t2 "+
					" where t2.parentId = '"+id+"'";
//			String hql = "from ZbL2 t2 "+
//			  		" where t2.parentId = '"+id+"'" +
//			  			" and t2.id in (select td.parentId "+
//			  				" from ZbDetail td "+
//			  				" where Substr(to_char(td.zbDate, 'yyyy-mm-dd'), 1, 7) = '"+date+"'" +
//			  				" and td.zbPrincipal like '%"+(zbUser.getUsertype()==0?zbUser.getUserid():"")+"%'"+
//			          " group by td.parentId) ";
			items_l2 = (List<ZbL2>)iL3Dao.queryAll(hql);
			//items_l2 = (List<ZbL2>)iL3Dao.queryMulti(tableName, date, id);
			
//			for(int i=0;i<items_l2.size();i++){
//				System.out.println("id是："+items_l2.get(i).getId()+" 二级指标名称是： "+items_l2.get(i).getZbName()+" 时间是： "+items_l2.get(i).getZbDate()+" 排序规则是： "+items_l2.get(i).getZbSort());
//			}
//			System.out.println("二级指标记录数量是： "+items_l2.size());
		}else if(tableName.equalsIgnoreCase("ZbDetail")){
			//id是唯一的
			String hql = "from ZbDetail t3 "+
			  		" where t3.parentId = '"+id+"'";
//			String hql = "from ZbDetail t3 "+
//					" where t3.parentId = '"+id+"'" +
//					" and t3.id in (select td.id "+
//					" from ZbDetail td "+
//					" where Substr(to_char(td.zbDate, 'yyyy-mm-dd'), 1, 7) = '"+date+"'" +
//					" and td.zbPrincipal like '%"+(zbUser.getUsertype()==0?zbUser.getUserid():"")+"%'"+
//					" group by td.id) ";
			items_l3 = (List<ZbDetail>)iL3Dao.queryAll(hql);
			//items_l3 = (List<ZbDetail>)iL3Dao.queryMulti(tableName, date, id);
			
//			for(int i=0;i<items_l3.size();i++){
//				System.out.println("id是："+items_l3.get(i).getId()+" 三级指标名称是： "+items_l3.get(i).getZbName()+" 时间是： "+items_l3.get(i).getZbDate()+" 排序规则是： "+items_l3.get(i).getZbSort());
//			}
//			System.out.println("三级指标记录数量是： "+items_l3.size());
		}
		
		/*根据名称和当月时间查询单个一级指标*/
		
		
		/*根据当月时间查询本月所有一级指标*/
		
		
//		//查询一级指标
//		items_l2 = (List<ZbL2>)iL3Dao.queryAll("from ZbL2");
		


		
		System.out.println("————————————————————");
		return SUCCESS;
	}

	public void setiL3Dao(ILevelThreeDao iL3Dao) {
		this.iL3Dao = iL3Dao;
	}

	public List<ZbL2> getitems_l2() {
		return items_l2;
	}

	public void setitems_l2(List<ZbL2> items_l2) {
		this.items_l2 = items_l2;
	}

	public List<ZbDetail> getItems_l3() {
		return items_l3;
	}

	public void setItems_l3(List<ZbDetail> items_l3) {
		this.items_l3 = items_l3;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
}
