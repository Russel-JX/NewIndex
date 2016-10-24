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

import com.dao.ILevelThreeDao;
import com.opensymphony.xwork2.ActionSupport;
import com.pojo.ZbDetail;
import com.pojo.ZbDetailData;
import com.pojo.ZbL2;
/**
 *三级指标。根据日期，查询三级指标名和id 
 */
public class ThreeQueryThreeAction extends ActionSupport implements ServletRequestAware{
//	private List<ZbDetail> items_l3 = new ArrayList<ZbDetail>();//pojo类，使用new创建
	private List<ZbDetailData> items_l3_data = new ArrayList<ZbDetailData>();
	
	private ILevelThreeDao iL3Dao;
	
	private HttpServletRequest request; 
	
	//获取一级指标的id和名称---表单、下拉列表方式
	public String execute() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("我在三级指标查询三级指标action中...");
		//获取查询的参数
		String date = request.getParameter("date");
//		System.out.println("查询的年月是："+date);

		// 查询三级指标(原生sql)
		String sql = " select t.id, "
				+ " t.parent_id_l1, "
				+ " t.parent_id, "
				+ " t.zb_name, "
				+ " t.zb_date, "
				+ " t.zb_sort, "
				+ " t.zb_point, "
				+ " t.zb_weight, "
				+ " t.zb_principal, "
				+ " t.zb_target, "
				+ " t.zb_source, "
				+ " t.zb_now, "
				+ " t.zb_time, "
				+ " t.zb_frequentness, "
				+ " t.zb_formula, "
				+ " (select t2.realname from zb_user t2 where t.zb_principal = t2.userid), "
				+ " (select t3.name from zb_formula t3 where t.zb_formula = t3.id) "
				+ " from zb_detail t "
				+ " where Substr(to_char(t.zb_date, 'yyyy-mm-dd'), 1, 7) =:date";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", date);
		List list = iL3Dao.query_batch_bySQL(sql, map);
		List<ZbDetailData> zbDetailDatas = new ArrayList();
		if (list != null && list.size() > 0) {
			Iterator<Object[]> ite = list.iterator();
			while (ite.hasNext()) {
				ZbDetailData zbDetailData = new ZbDetailData();
				Object[] obj = ite.next();
				// 将BigDecimal类型调用intValue方法，转成int。封装成实体类，方便后续使用。
				zbDetailData.setId(((BigDecimal) obj[0]).intValue());
				zbDetailData.setParentIdL1(((BigDecimal) obj[1]).intValue());
				zbDetailData.setParentId(((BigDecimal) obj[2]).intValue());
				zbDetailData.setZbName((String) obj[3]);
				zbDetailData.setZbDate((Date) obj[4]);
				zbDetailData.setZbSort(obj[5] == null ? 0
						: ((BigDecimal) obj[5]).intValue());
				zbDetailData.setZbPoint(obj[6] == null ? 0
						: ((BigDecimal) obj[6]).doubleValue());
				zbDetailData.setZbWeight(obj[7] == null ? 0
						: ((BigDecimal) obj[7]).doubleValue());
				zbDetailData.setZbPrincipal(obj[8] == null ? 0
						: ((BigDecimal) obj[8]).intValue());
				zbDetailData.setZbTarget((String) obj[9]);
				zbDetailData.setZbSource((String) obj[10]);
				zbDetailData.setZbNow((String) obj[11]);
				zbDetailData.setZbTime((String) obj[12]);
				zbDetailData.setZbFrequentness((String) obj[13]);

				if (obj[14] == null) {// 如果此指标没有公式
					zbDetailData.setZbFormula(-1);
				} else {
					zbDetailData
							.setZbFormula(((BigDecimal) obj[14]).intValue());
				}
				zbDetailData.setRealname((String) obj[15]);
				zbDetailData.setZbFormulaName((String) obj[16]);

				zbDetailDatas.add(zbDetailData);
			}
		}
		items_l3_data = zbDetailDatas;
//		//查询一级指标
//		items_l3 = (List<ZbDetail>)iL3Dao.queryByMonth("ZbDetail", date);
//		//items_l3 = (List<ZbDetail>)iL3Dao.queryAll("from ZbDetail");
		
		
		
//		for(int i=0;i<items_l3_data.size();i++){
//			System.out.println("id是："+items_l3_data.get(i).getId()+" 三级指标名称是： "+items_l3_data.get(i).getZbName()+"父二级指标id是： "+items_l3_data.get(i).getParentId()+"三级指标的权重是： "+items_l3_data.get(i).getZbPoint()+" 时间是： "+items_l3_data.get(i).getZbDate()+" 排序规则是： "+items_l3_data.get(i).getZbSort());
//		}
//		System.out.println("三级指标记录数量是： "+items_l3_data.size());
//		
//		System.out.println("————————————————————");
		return SUCCESS;
	}

//	public List<ZbDetail> getItems_l3() {
//		return items_l3;
//	}
//
//	public void setItems_l3(List<ZbDetail> items_l3) {
//		this.items_l3 = items_l3;
//	}

	public void setiL3Dao(ILevelThreeDao iL3Dao) {
		this.iL3Dao = iL3Dao;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	public List<ZbDetailData> getItems_l3_data() {
		return items_l3_data;
	}

	public void setItems_l3_data(List<ZbDetailData> items_l3_data) {
		this.items_l3_data = items_l3_data;
	}

}
