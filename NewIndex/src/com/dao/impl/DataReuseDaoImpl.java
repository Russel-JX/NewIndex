package com.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.dao.DataReuseDao;

/**
 * 沿用上个月的指标（一、二、三级指标）
 * @author JiangXun
 */
public class DataReuseDaoImpl extends IObjectDaoImpl implements DataReuseDao{

	public int[] reuseDataFromLastMonth(boolean flag){
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		
		//本月日期
		Calendar cal = Calendar.getInstance();
		//上个月
		
		cal.add(Calendar.MONTH, -1);//cal.add(Calendar.MONTH, 0);//cal.add(Calendar.MONTH, -1);
		
		int lastMonth = cal.get(Calendar.MONTH)+1;//0表示1月，11表示12月。
		//Date-->String 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//上个月开始日期
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String lastmonth_begin = sdf.format(cal.getTime());
		System.out.println("上个月第一天："+lastmonth_begin);
		//上个月结束日期
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastmonth_end = sdf.format(cal.getTime());
//		System.out.println("最后一天："+lastmonth_end);
		
		//本月日期
		Calendar cal_now = Calendar.getInstance();
		
		//cal_now.add(Calendar.MONTH, 1);//#￥%……&*（￥%……&*（）测试复制7月数据到8月。测完后，把本行删掉
		
		cal_now.set(Calendar.DAY_OF_MONTH, cal_now.getActualMinimum(Calendar.DAY_OF_MONTH));
		String thismonth_begin = sdf.format(cal_now.getTime());
//		System.out.println("本月的第一天是： "+thismonth_begin);
		cal_now.set(Calendar.DAY_OF_MONTH, cal_now.getActualMaximum(Calendar.DAY_OF_MONTH));
		String thismonth_end = sdf.format(cal_now.getTime());
//		System.out.println("本月的最后一天是： "+thismonth_end);
	
		Calendar cal4 = Calendar.getInstance();
		//上个月的21号
		cal4.add(Calendar.MONTH, -1);//cal4.add(Calendar.MONTH, 0);//cal4.add(Calendar.MONTH, -1)
		
		cal4.set(Calendar.DAY_OF_MONTH, 21);
		String lastmonth_21 = sdf.format(cal4.getTime());
//		System.out.println("上个月的21号是："+lastmonth_21);
		
		Calendar cal5 = Calendar.getInstance();
		
		//cal5.add(Calendar.MONTH, 1);//#￥%……&*（￥%……&*（）测试复制7月数据到8月。测完后，把本行删掉
		
		cal5.set(Calendar.DAY_OF_MONTH, 20);
		String thismonth_20 = sdf.format(cal5.getTime());
//		System.out.println("本月的20号是："+thismonth_20);
		
		//首先删除本月已有的各级指标
		/*删除前，查询本月是否已有数据*/
		String sql_queryData = " select count(*) from zb_data t "+
								 " where to_char(t.zb_date, 'yyyy-mm-dd') >= '"+lastmonth_21+"' "+
								   " and to_char(t.zb_date, 'yyyy-mm-dd') <= '"+thismonth_20+"' ";
		SQLQuery sqlQuery_data = (SQLQuery)session.createSQLQuery(sql_queryData);
		Object data_this_month = sqlQuery_data.uniqueResult();
		
		int delete_rowNumber_data = -1;//删除的本月具体指标数量
		int delete_rowNumber_l3 = -1;//删除的本月三级指标数量
		int delete_rowNumber_l2 = -1;//删除的本月二级指标数量
		int delete_rowNumber_l1 = -1;//删除的本月一级指标数量
		
		if(data_this_month==null){
//			System.out.println("本月指标已删除！");
		}else{
			int data_number = ((BigDecimal)data_this_month).intValue();
//			System.out.println("本月具体指标数量： "+data_number);
			
			
			//复制前，删除本月已有的具体指标值 --t select * from zb_data t 
			String deleted_rowNumber_data = " delete from zb_data t "+
											  " where to_char(t.zb_date, 'yyyy-mm-dd') >= '"+lastmonth_21+"' "+
											    " and to_char(t.zb_date, 'yyyy-mm-dd') <= '"+thismonth_20+"' ";
			SQLQuery sqlDelete_data = (SQLQuery)session.createSQLQuery(deleted_rowNumber_data);
			delete_rowNumber_data = sqlDelete_data.executeUpdate();
//			System.out.println("删除本月具体指标数量： "+delete_rowNumber_data);
			//复制前，删除本月已有的三级指标
			String deleted_rowNumber_L3 = " delete from zb_detail t3 "+
											" where to_char(t3.zb_date, 'yyyy-mm-dd') >= '"+thismonth_begin+"' "+
											  " and to_char(t3.zb_date, 'yyyy-mm-dd') <= '"+thismonth_end+"' ";
			SQLQuery sqlDelete_l3 = (SQLQuery)session.createSQLQuery(deleted_rowNumber_L3);
			delete_rowNumber_l3 = sqlDelete_l3.executeUpdate();
//			System.out.println("删除本月三级指标数量： "+delete_rowNumber_l3);
			//复制前，删除本月已有的二级指标
			String deleted_rowNumber_L2 = " delete from zb_l2 t2 "+
											" where to_char(t2.zb_date, 'yyyy-mm-dd') >= '"+thismonth_begin+"' "+
											  " and to_char(t2.zb_date, 'yyyy-mm-dd') <= '"+thismonth_end+"' ";
			SQLQuery sqlDelete_l2 = (SQLQuery)session.createSQLQuery(deleted_rowNumber_L2);
			delete_rowNumber_l2 = sqlDelete_l2.executeUpdate();
//			System.out.println("删除本月二级指标数量： "+delete_rowNumber_l2);
			//复制前，删除本月已有的一级指标
			String deleted_rowNumber_L1 = " delete from zb_l1 t1 "+
											" where to_char(t1.zb_date, 'yyyy-mm-dd') >= '"+thismonth_begin+"' "+
											  " and to_char(t1.zb_date, 'yyyy-mm-dd') <= '"+thismonth_end+"' ";
			SQLQuery sqlDelete_l1 = (SQLQuery)session.createSQLQuery(deleted_rowNumber_L1);
			delete_rowNumber_l1 = sqlDelete_l1.executeUpdate();
//			System.out.println("删除本月一级指标数量： "+delete_rowNumber_l1);
		}
		
		
		//复制一级指标
		String sql_insertL1 = " insert into zb_l1 t1 "+
		  					" (t1.id, t1.zb_sort, t1.zb_name, t1.zb_date) "+
		  					" (select ZB_L1AUTOINCRE.NEXTVAL, t2.zb_sort, t2.zb_name, sysdate "+//sysdate,add_months(t2.zb_date, 1)
		  						" from zb_l1 t2 "+
		  					" where to_char(t2.zb_date, 'yyyy-mm-dd') >= '"+lastmonth_begin+"'" +
		  									" and to_char(t2.zb_date, 'yyyy-mm-dd') <= '"+lastmonth_end+"' ) ";
		SQLQuery sqlQuery_l1 = (SQLQuery)session.createSQLQuery(sql_insertL1);
		int inserted_rowNumber_l1 = sqlQuery_l1.executeUpdate();
//		System.out.println("插入一级指标数量： "+inserted_rowNumber_l1);
		
		//复制上个月的二级指标到本月
		String sql_insertL2 = " insert into zb_l2 t1 "+
				  			" (t1.id,t1.parent_id, t1.zb_sort, t1.zb_name, t1.zb_date) "+
				  			" (select ZB_L2AUTOINCRE.NEXTVAL, "+
				  				" 0, "+
				  				" t2.zb_sort, "+
				  				" t2.zb_name, "+
				  				" sysdate "+//sysdate,add_months(t2.zb_date, 1)
				  				" from zb_l2 t2 "+
				  			" where to_char(t2.zb_date, 'yyyy-mm-dd') >= '"+lastmonth_begin+"'"+
				  			" and to_char(t2.zb_date, 'yyyy-mm-dd') <= '"+lastmonth_end+"') "; 
		SQLQuery sqlQuery_l2 = (SQLQuery)session.createSQLQuery(sql_insertL2);
		int inserted_rowNumber_l2 = sqlQuery_l2.executeUpdate();
//		System.out.println("插入二级指标数量： "+inserted_rowNumber_l2);
		
		//更新本月的二级指标的parent_id
		String sql_updateL2 = " merge into zb_l2 tt " + 
								" using (select t10.id as new_l1_id,new_l2_parentid,t9.new_l2_id "+
								  " from zb_l1 t10, "+//--查询一级中刚复制的一级的id "
								  " (select t7.zb_name as l1_name_lastmonth, t7.zb_date as l1_date_lastmonth, t8.new_l2_name as new_l2_name,t8.new_l2_parentid,t8.new_l2_id as new_l2_id "+ 
								  	" from zb_l1 t7, "+// --查询一级中，id等于上个月二级中父id的记录
								  	" (select t6.parent_id as l2_parentid_lastmonth, "+
								  		" t5.id as new_l2_id, "+
								        " t5.zb_name   as new_l2_name, "+
								        " t5.zb_date as new_l2_date, "+
								        " t5.parent_id as new_l2_parentid "+// --查询二级中刚复制的只表明相同的记录
								        " from zb_l2 t6 "+
								        " join zb_l2 t5 "+
								        " on t6.zb_name = t5.zb_name "+
								        	" where t5.parent_id = 0 "+
								            " and to_char(t5.zb_date, 'yyyy-mm-dd') >= '"+thismonth_begin+"' "+
								            " and to_char(t5.zb_date, 'yyyy-mm-dd') <= '"+thismonth_end+"' "+
								            " and t6.parent_id != 0 "+
								            " and to_char(t6.zb_date, 'yyyy-mm-dd') >= '"+lastmonth_begin+"' "+
								            " and to_char(t6.zb_date, 'yyyy-mm-dd') <= '"+lastmonth_end+"' ) t8 "+
								            	" where t7.id = t8.l2_parentid_lastmonth) t9 "+
								   " where t10.zb_name = t9.l1_name_lastmonth "+
								   " and to_char(t10.zb_date, 'yyyy-mm-dd') >= '"+thismonth_begin+"' "+
								   " and to_char(t10.zb_date, 'yyyy-mm-dd') <= '"+thismonth_end+"') l1_l2 "+
								" on (tt.id = l1_l2.new_l2_id) "+
								" when matched then update "+
								  " set tt.parent_id = l1_l2.new_l1_id ";
		SQLQuery sqlQuery_l2_update = (SQLQuery)session.createSQLQuery(sql_updateL2);
		int inserted_rowNumber_l2_update = sqlQuery_l2_update.executeUpdate();
//		System.out.println("更新二级指标数量： "+inserted_rowNumber_l2_update);
		
		//复制上个月的三级指标到本月。//使用系统当前时间。防止本月没有上月对应的日期
		String sql_insertL3 = " insert into zb_detail t1 "+
								  " (t1.id, "+
										   " t1.parent_id_l1, "+
										   " t1.parent_id, "+
										   " t1.zb_sort, "+
										   " t1.zb_name, "+
										   " t1.zb_date, "+
										   " t1.zb_point, "+
										   " t1.zb_weight, "+
										   " t1.zb_principal, "+
										   " t1.zb_target, "+
										   " t1.zb_source, "+
										   " t1.zb_now, "+
										   " t1.zb_time, "+
										   " t1.zb_frequentness, "+
										   " t1.zb_formula) "+
										  " (select Zb_Detailautoincre.NEXTVAL, "+
										          " 0, "+// --三级参照的一级id默认置0
										          " 0, "+// --三级参照的二级id默认置0
										          " t2.zb_sort, "+
										          " t2.zb_name, "+
										          " sysdate, "+//" sysdate, "+//--add_months(t2.zb_date, 1),
										          " t2.zb_point, "+
										          " t2.zb_weight, "+
										          " t2.zb_principal, "+
										          " t2.zb_target, "+
										          " t2.zb_source, "+
										          " t2.zb_now, "+
										          " t2.zb_time, "+
										          " t2.zb_frequentness, "+
										          " t2.zb_formula "+
										     " from zb_detail t2 "+
										    " where to_char(t2.zb_date, 'yyyy-mm-dd') >= '"+lastmonth_begin+"'" +
										      " and to_char(t2.zb_date, 'yyyy-mm-dd') <= '"+lastmonth_end+"') "; 
		SQLQuery sqlQuery_l3 = (SQLQuery)session.createSQLQuery(sql_insertL3);
		int inserted_rowNumber_l3 = sqlQuery_l3.executeUpdate();
//		System.out.println("插入三级指标数量： "+inserted_rowNumber_l3);
		
		//向具体指标值表中插入一个月的空值。先插空指标值，再更新三级指标。
		String sql_queryL3ID = " select t.id "+
				  " from zb_detail t "+
				  " where t.parent_id_l1 = 0 "+
				    " and t.parent_id = 0 "+
				    " and to_char(t.zb_date, 'yyyy-mm-dd') >= '"+thismonth_begin+"' "+
				    " and to_char(t.zb_date, 'yyyy-mm-dd') <= '"+thismonth_end+"' ";
		SQLQuery sqlQuery_l3ID = (SQLQuery)session.createSQLQuery(sql_queryL3ID);
		List inserted_rowNumber_l3ID = sqlQuery_l3ID.list();
//		System.out.println("刚插入三级指标ID数量： "+inserted_rowNumber_l3ID.size());
		
		
		Calendar cal3 = Calendar.getInstance();
		
//		System.out.println("当前日期的年是: "+cal3.get(Calendar.YEAR)+" 月是： "+(cal3.get(Calendar.MONTH)+1)+" 上个月是： "+lastMonth+" 上个月总天数是： "+ cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		//System.out.println("当前日期的年是: "+cal3.get(Calendar.YEAR)+" 月是： "+(cal3.get(Calendar.MONTH)+1+1)+" 上个月是： "+lastMonth+" 上个月总天数是： "+ cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		if(inserted_rowNumber_l3ID!=null&&inserted_rowNumber_l3ID.size()>0){
			for(int i=0;i<inserted_rowNumber_l3ID.size();i++){
				int newL3ID = ((BigDecimal)inserted_rowNumber_l3ID.get(i)).intValue();
//				System.out.println("新三级的id是： "+newL3ID);
				//新记录的id、当前日期的年、月、上个月、上个月的总天数
				addMulti(newL3ID, cal3.get(Calendar.YEAR), (cal3.get(Calendar.MONTH)+1), lastMonth, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				//addMulti(newL3ID, cal3.get(Calendar.YEAR), (cal3.get(Calendar.MONTH)+1+1), lastMonth, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			}
		}
		
		
		//更新三级的父一级和父二级id
		String sql_updateL3 = " merge into zb_detail tt "+
								" using (select t10.id as new_l2_id, "+
							              " t10.parent_id as new_l1_id, "+
							              " new_l3_parentid, "+
							              " t9.new_l3_id "+
							         " from zb_l2 t10, "+// --查询一级中刚复制的一级的id
							              " (select t7.zb_name as l2_name_lastmonth, "+
							                      " t7.zb_date as l2_date_lastmonth, "+
							                      " t8.new_l3_name as new_l3_name, "+
							                      " t8.new_l3_parentid, "+
							                      " t8.new_l3_id as new_l3_id "+
							                 " from zb_l2 t7, "+ //--查询二级中，id等于上个月三级中父id的记录
							                      " (select t6.parent_id as l3_parentid_lastmonth, "+
							                              " t5.id as new_l3_id, "+
							                              " t5.zb_name as new_l3_name, "+
							                              " t5.zb_date as new_l3_date, "+
							                              " t5.parent_id as new_l3_parentid "+// --查询三级中刚复制的只表明相同的记录
							                         " from zb_detail t6 "+
							                         " join zb_detail t5 "+
							                           " on t6.zb_name = t5.zb_name "+
							                        " where t5.parent_id = 0 "+
							                          " and to_char(t5.zb_date, 'yyyy-mm-dd') >= '"+thismonth_begin+"' "+
							                          " and to_char(t5.zb_date, 'yyyy-mm-dd') <= '"+thismonth_end+"' "+
							                          " and t6.parent_id != 0 "+
							                          " and to_char(t6.zb_date, 'yyyy-mm-dd') >= '"+lastmonth_begin+"'" +
							                          " and to_char(t6.zb_date, 'yyyy-mm-dd') <= '"+lastmonth_end+"') t8 "+
							                " where t7.id = t8.l3_parentid_lastmonth) t9 "+
							        " where t10.zb_name = t9.l2_name_lastmonth "+
							          " and to_char(t10.zb_date, 'yyyy-mm-dd') >= '"+thismonth_begin+"' "+
							          " and to_char(t10.zb_date, 'yyyy-mm-dd') <= '"+thismonth_end+"' ) l2_l3 "+
							" on (tt.id = l2_l3.new_l3_id) "+
							" when matched then "+
							  " update "+
							     " set tt.parent_id    = l2_l3.new_l2_id, "+// --父二级id
							         " tt.parent_id_l1 = l2_l3.new_l1_id ";// --父一级id
		SQLQuery sqlUpdate_l3 = (SQLQuery)session.createSQLQuery(sql_updateL3);
		int inserted_rowNumber_l3_update = sqlUpdate_l3.executeUpdate();
//		System.out.println("更新的三级指标数量： "+inserted_rowNumber_l3_update);
		
		
		session.flush();
		session.clear();
		super.releaseSession(session);
		
		//依次为：删除的具体指标数量、删除的三级指标数量、删除的二级指标数量、删除的一级指标数量、一级复制的数量、二级复制的数量、二级更新的数量、三级复制的数量、三级更新的数量
		int[] effectedRows = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		effectedRows[0] = delete_rowNumber_data;
		effectedRows[1] = delete_rowNumber_l3;
		effectedRows[2] = delete_rowNumber_l2;
		effectedRows[3] = delete_rowNumber_l1;
		
		effectedRows[4] = inserted_rowNumber_l1;
		effectedRows[5] = inserted_rowNumber_l2;
		effectedRows[6] = inserted_rowNumber_l2_update;
		effectedRows[7] = inserted_rowNumber_l3;
		effectedRows[8] = inserted_rowNumber_l3_update;
		
		
		
		return effectedRows;
	}
}
