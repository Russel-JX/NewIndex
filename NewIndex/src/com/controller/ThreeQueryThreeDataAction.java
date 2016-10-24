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
import com.pojo.ZbData;
import com.pojo.ZbDetailData;
import com.service.ThreeQueryThreeDataService;

/**
 * 三级指标。根据日期，查询三级指标名、id和三级指标下的具体指标值 。 选择的月份是6月，对应的具体指标范围是：5月21号~6月20号。
 */
public class ThreeQueryThreeDataAction extends ActionSupport implements
		ServletRequestAware {
	private List<ZbDetailData> items_l3_data = new ArrayList<ZbDetailData>();// 三级指标和对应的具体指标值

	private ILevelThreeDao iL3Dao;
	private ThreeQueryThreeDataService threeqthreeDataService;

	private HttpServletRequest request;

	// 获取一级指标的id和名称---表单、下拉列表方式
	public String execute() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("我在三级指标查询三级指标action中...");
		//获取查询的参数
		String date = request.getParameter("date");
//		System.out.println("查询的年月是："+date);
		
		//查询三级指标(原生sql)
		String sql = " select t.id, "+
			" t.parent_id_l1, "+
			" t.parent_id, "+
			" t.zb_name, "+
			" t.zb_date, "+
	        " t.zb_sort, "+
	        " t.zb_point, "+
	        " t.zb_weight, "+
	        " t.zb_principal, "+
	        " t.zb_target, "+
	        " t.zb_source, "+
	        " t.zb_now, "+
	        " t.zb_time, "+
	        " t.zb_frequentness, "+
	        " t.zb_formula, "+
	        " t2.realname "+
	    " from zb_detail t left join zb_user t2 " +
	    	" on t.zb_principal=t2.userid "+
	    " where Substr(to_char(t.zb_date, 'yyyy-mm-dd'), 1, 7) =:date"	;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("date", date);
		List list = iL3Dao.query_batch_bySQL(sql, map);
		List<ZbDetailData> zbDetailDatas = new ArrayList();
		if(list!=null&&list.size()>0){
			Iterator<Object[]> ite = list.iterator();
			while(ite.hasNext()){
				ZbDetailData zbDetailData = new ZbDetailData();
				Object[] obj = ite.next();
				//将BigDecimal类型调用intValue方法，转成int。封装成实体类，方便后续使用。
				zbDetailData.setId(((BigDecimal) obj[0]).intValue());
				zbDetailData.setParentIdL1(((BigDecimal) obj[1]).intValue());
				zbDetailData.setParentId(((BigDecimal) obj[2]).intValue());
				zbDetailData.setZbName((String)obj[3]);
				zbDetailData.setZbDate((Date)obj[4]);
				zbDetailData.setZbSort(obj[5]==null?0:((BigDecimal) obj[5]).intValue());
				zbDetailData.setZbPoint(obj[6]==null?0:((BigDecimal) obj[6]).doubleValue());
				zbDetailData.setZbWeight(obj[7]==null?0:((BigDecimal) obj[7]).doubleValue());
				zbDetailData.setZbPrincipal(obj[8]==null?0:((BigDecimal) obj[8]).intValue());
				zbDetailData.setZbTarget((String)obj[9]);
				zbDetailData.setZbSource((String)obj[10]);
				zbDetailData.setZbNow((String)obj[11]);
				zbDetailData.setZbTime((String)obj[12]);
				zbDetailData.setZbFrequentness((String)obj[13]);
				
				if(obj[14]==null){//如果此指标没有公式
					zbDetailData.setZbFormula(-1);
				}else{
					zbDetailData.setZbFormula(((BigDecimal) obj[14]).intValue());
				}
				zbDetailData.setRealname((String)obj[15]);
				
				zbDetailDatas.add(zbDetailData);
			}
			/*for(int i=0;i<zbDetailDatas.size();i++){
				System.out.println("三级id是： "+zbDetailDatas.get(i).getId()+"一级id是： "+zbDetailDatas.get(i).getParentIdL1()+"二级id是： "+zbDetailDatas.get(i).getParentId()+
						"名称是： "+zbDetailDatas.get(i).getZbName()+"日期是： "+zbDetailDatas.get(i).getZbDate()+
						"排序规则是： "+zbDetailDatas.get(i).getZbSort()+"分数是： "+zbDetailDatas.get(i).getZbPoint()+
						"权重是： "+zbDetailDatas.get(i).getZbWeight()+"负责人id是： "+zbDetailDatas.get(i).getZbPrincipal()+
						"目标值是： "+zbDetailDatas.get(i).getZbTarget()+"来源是： "+zbDetailDatas.get(i).getZbSource()+
						"现在值是： "+zbDetailDatas.get(i).getZbNow()+"收集时间是： "+zbDetailDatas.get(i).getZbTime()+
						"频度是： "+zbDetailDatas.get(i).getZbFrequentness());
				System.out.println("=============三级指标中是否有具体指标值"+zbDetailDatas.get(i).getData().isEmpty());
			}*/
		}
		//items_l3_data = (List<ZbDetailData>)iL3Dao.queryByMonth("ZbDetailData", date);
		items_l3_data = zbDetailDatas;
//		for(int i=0;i<items_l3_data.size();i++){
//			System.out.println("id是："+items_l3_data.get(i).getId()+" 三级指标名称是： "+items_l3_data.get(i).getZbName()+"三级指标的权重是： "+items_l3_data.get(i).getZbPoint()+" 时间是： "+items_l3_data.get(i).getZbDate()+" 排序规则是： "+items_l3_data.get(i).getZbSort());
//		}
//		System.out.println("三级指标记录数量是： "+items_l3_data.size());
		//查询具体指标值
		List<ZbData> data = queryData(date);
		
		//将具体指标值加入三级指标集合中
		for(int i=0;i<items_l3_data.size();i++){
			for(int j=0;j<data.size();j++){
				//System.out.println("……………………………………………………………………………………三级指标的id是： "+items_l3_data.get(i).getId()+"$$$$$$$$$$具体指标参照的三级指标id是："+data.get(j).getZbId());
				//int a = items_l3_data.get(i).getId();
				//int b = data.get(j).getZbId();
				if((items_l3_data.get(i).getId()).equals((data.get(j).getZbId()))){
					items_l3_data.get(i).getData().add(data.get(j));//将data集合中对应元素加到items_l3_data集合中的data属性集合中去
//					System.out.println("向三级 "+items_l3_data.get(i).getZbName()+" id是： "+items_l3_data.get(i).getId()+" 中，加入了 "+data.get(j).getZbData()+" 父id是： "+data.get(j).getZbId()+"具体指标");
					//break;
				}
			}
		}
//		//***如果有些三级指标对应具体指标还未填写（服务器返回的json数据中data属性下的子属性没有），导致前端 使用此子属性显示的列获取不到数据（鼠标转圈），而整个表格都不显示，所以后台设置一下。***
//		for(int i=0;i<items_l3_data.size();i++){
//			if(items_l3_data.get(i).getData().size()==0){//加入一个月（31天）的假数据
//				for(int j=0;j<31;j++){
//					items_l3_data.get(i).getData().add(new ZbData(null,null,"还未填写"));
//				}
//			}
//		}
		//统计得分
		items_l3_data = threeqthreeDataService.defineThreeIndex(items_l3_data);
		
//		System.out.println("yyyyyyyyyyy");
//		for(int i=0;i<items_l3_data.size();i++){
//			if(items_l3_data.get(i).getData()!=null){
//				System.out.println("id是："+items_l3_data.get(i).getId()+" 三级指标名称是： "+items_l3_data.get(i).getZbName()+"三级指标的权重是： "+items_l3_data.get(i).getZbPoint()+" 时间是： "+items_l3_data.get(i).getZbDate()+" 排序规则是： "+items_l3_data.get(i).getZbSort()
//						+"该三级指标下共有： "+items_l3_data.get(i).getData().size()+" 条具体指标值");
//			}
//		}
//		
//		System.out.println("————————————————————");
		return SUCCESS;
	}

	// 查询具体指标值
	public List<ZbData> queryData(String date) throws Exception {
		// 取得查询的月份
		Integer year = Integer.parseInt(date.substring(0, 4));
		Integer month = Integer.parseInt(date.substring(5, 7));
		String begin = "";// 上个月的开始日期
		String end = "";// 本月的结束日期

		// 判断查询的月份是否跨年
		if (month == 1) {// 跨年。从去年的12月21号开始，到今年的1月20号结束。
			begin = (year - 1) + "-12";
		} else {
			begin = year + "-" + (month - 1);
		}
		end = year + "-" + month;
		System.out.println("所要查询具体指标值的月份是： 从 " + begin + " 月21号到 " + end
				+ " 月20号");
		// 查询三级指标月内的所有具体指标值，需要对数据在后台排好序，才能按顺序放到前台的各个列中（表格的列中数据源的数据，是按照查询数据库中记录的顺序排列的）
		List<ZbData> data = (List<ZbData>) iL3Dao
				.queryAll("from ZbData t WHERE t.zbDate between to_date('"
						+ begin + "-21','yyyy-mm-dd') and to_date('" + end
						+ "-20','yyyy-mm-dd') order by t.id ASC");
		for (int i = 0; i < data.size(); i++) {
			System.out.println("id是：" + data.get(i).getId() + " 具体指标值是： "
					+ data.get(i).getZbData() + "具体指标填写的时间是： "
					+ data.get(i).getZbDate() + " 参照的三级指标id是： "
					+ data.get(i).getZbId());
			// //***如果有些三级指标下的具体指标只填写了几天，也要同上设置。注意用户填写的时候，可能是隔着填的***
			// if(data.get(i).get){
			//
			// }
		}
		System.out.println("这个月内所有具体指标记录数量是： " + data.size());
		return data;
	}

	public List<ZbDetailData> getItems_l3_data() {
		return items_l3_data;
	}

	public void setItems_l3_data(List<ZbDetailData> items_l3_data) {
		this.items_l3_data = items_l3_data;
	}

	public void setiL3Dao(ILevelThreeDao iL3Dao) {
		this.iL3Dao = iL3Dao;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	public void setThreeqthreeDataService(
			ThreeQueryThreeDataService threeqthreeDataService) {
		this.threeqthreeDataService = threeqthreeDataService;
	}



}
