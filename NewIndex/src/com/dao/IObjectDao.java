package com.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.util.PageUtil;

public interface IObjectDao {
	public Object queryUsers(PageUtil pageUtil);
	//使用原生sql，关联查询
	public List query_batch_bySQL(String sql,Map map);
	//批量插入一个月的具体指标值
	public void addMulti(int newId,int year,int month,int lastMonth,int totalDays);
	//批量修改一个月的具体指标值
	public void updateMulti(int[] ids,String[] values,String hql);
	//增加
	public Object add(Object id);
	//删除
	public void delete(Object id);

	//批量删除。删除集合对象
	public void deleteMulti(String query);
	//批量删除。固定几个记录
	public void deleteMultiFixed(Class cls,String propertyName,Collection idData);
	//按对象修改
	public void updateObject(Object obj);
	//修改
	public int update(String query);//根据语句更新
	//模糊查询表中的所有数据
	//可以查询二级三级
	public Object queryMulti(String tableName,String year_month,Integer id);
	//按年、月份查询
	public Object queryByMonth(String tableName,String year_month);
	//按id查询
	public Object queryById(Integer id);
	//名字查询
	public Object queryByName(Object name);
	//查询所有
	public Object queryAll(Object obj);
	//查询所有（分页）
	public Object queryAllByPage(Object start, Object limit);
	//HQL查询(分页)
	public Object AllByPageHQL(Integer start,Integer limit);
	//查询表中的各级指标总记录数
	public long queryTotalIndex(String tableName,String property,Integer id);
	//查询表中的总记录数
	public long queryTotal();
}
