package com.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.dao.ILevelThreeDao;
import com.entity.ZbL1_ZbL2;
import com.opensymphony.xwork2.ActionSupport;
import com.pojo.ZbL1;
import com.pojo.ZbUser;
/**
 *根据所选日期和登录用户，查询一级指标
 */
public class DateQueryOneAction extends ActionSupport implements ServletRequestAware,SessionAware{
	private List<ZbL1> items_l1 = new ArrayList<ZbL1>();
	
	private ILevelThreeDao iL3Dao;
	
	private HttpServletRequest request; 
	private Map<String,Object> session; 
	
	//获取一级指标的id和名称
	public String execute() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("我在根据日期查询一级指标action中...");
		
		String date = request.getParameter("date");
//		System.out.println("所要查询的日期是： "+date);
		//登录的用户
		ZbUser zbUser = (ZbUser)session.get("zbUser");
//		System.out.println("用户id是： "+zbUser.getUserid()+"用户名是： "+zbUser.getUsername()+"姓名是： "+zbUser.getRealname()+"类型是： "+zbUser.getUsertype());
		//根据登陆的用户和日期，查询他所负责的一级指标。超管可以填写任意指标，普管只能填写自己负责的。
		String hql = "from ZbL1 t1 "+
				  		" where t1.id in "+
				  			" (select td.parentIdL1 "+
				  				" from ZbDetail td "+
				  				" where Substr(to_char(td.zbDate, 'yyyy-mm-dd'), 1, 7) = '"+date+"'" +
				  				" and td.zbPrincipal like '%"+(zbUser.getUsertype()==0?zbUser.getUserid():"")+"%'"+
				          " group by td.parentIdL1) ";
		items_l1 = (List<ZbL1>)iL3Dao.queryAll(hql);
		/*Map<String,Object> map = new HashMap<String,Object>();
		map.put("a", 182);
		map.put("b", 192);
		//试验关联查询。查询一级下面的所有二级
		List list = iL3Dao.query_batch_bySQL("select t2.id,t2.zb_name,t2.zb_date,t2.parent_id,t2.zb_sort,t1.zb_name from zb_l2 t2,zb_l1 t1 where t1.id = t2.parent_id and t2.id>:a and t2.id<:b ",map);
		//原生sql返回集合的元素是一个个Object数组
		
		if(list!=null&&list.size()>0){
			List<ZbL1_ZbL2> zbL1_ZbL2s = new ArrayList();
			Iterator<Object[]> ite = list.iterator();
			while(ite.hasNext()){
				ZbL1_ZbL2 zbL1_ZbL2 = new ZbL1_ZbL2();
				Object[] obj = ite.next();
				
				zbL1_ZbL2.setId((BigDecimal)obj[0]);
				zbL1_ZbL2.setZbName_L2((String)obj[1]);
				zbL1_ZbL2.setZbDate((Date)obj[2]);
				zbL1_ZbL2.setParentId((BigDecimal)obj[3]);
				zbL1_ZbL2.setZbSort((BigDecimal)obj[4]);
				zbL1_ZbL2.setZbName_L1((String)obj[5]);
				
				zbL1_ZbL2s.add(zbL1_ZbL2);
			}
			for(int i=0;i<zbL1_ZbL2s.size();i++){
				System.out.println("二级id是： "+zbL1_ZbL2s.get(i).getId()+"名称是： "+zbL1_ZbL2s.get(i).getZbName_L2()+"日期是： "+zbL1_ZbL2s.get(i).getZbDate()+"排序规则是： "+zbL1_ZbL2s.get(i).getZbSort()+"一级id是： "+zbL1_ZbL2s.get(i).getParentId()+"一级名称是： "+zbL1_ZbL2s.get(i).getZbName_L1());
			}
		}*/
		
		
		
		//查询表中属于同一年月的数据
		//items_l1 = (List<ZbL1>)iL3Dao.queryByMonth("ZbL1",date);
		/*根据名称和当月时间查询单个一级指标*/
		
		
		/*根据当月时间查询本月所有一级指标*/
		
		
//		//查询一级指标
//		items_l1 = (List<ZbL1>)iL3Dao.queryAll("from ZbL1");
		
//		for(int i=0;i<items_l1.size();i++){
//			System.out.println("id是："+items_l1.get(i).getId()+" 一级指标名称是： "+items_l1.get(i).getZbName()+" 时间是： "+items_l1.get(i).getZbDate()+" 排序规则是： "+items_l1.get(i).getZbSort());
//		}
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

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
}
