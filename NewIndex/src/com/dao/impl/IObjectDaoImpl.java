package com.dao.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.IObjectDao;
import com.pojo.ZbData;
import com.pojo.ZbDetail;
import com.pojo.ZbUser;
import com.util.PageUtil;

public class IObjectDaoImpl extends HibernateDaoSupport implements IObjectDao{
	//使用原生sql，关联查询
	public List query_batch_bySQL(String sql,Map map){
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		SQLQuery sqlQuery = (SQLQuery)session.createSQLQuery(sql).setProperties(map);
		List list = sqlQuery.list();
		session.flush();
		session.clear();
		super.releaseSession(session);
		return list;
	}
	//批量插入一个月的具体指标值
	public void addMulti(int newId,int year,int month,int lastMonth,int totalDays){
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		//插入上个月21号到月底的空数据
		for(int i=21;i<=totalDays;i++){
			//创建空数据对象
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(year+"-"+lastMonth+"-"+i);
			} catch (ParseException e) {
//				System.out.println("ThreeAction中，字符串转日期出错！");
			}
			ZbData zbData = new ZbData(date,newId,null); 
			session.save(zbData);
		}
		//清理缓存
		session.flush();  
        session.clear();
        //插入本月初到20号的空数据
        for(int i=1;i<=20;i++){
			//创建空数据对象
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(year+"-"+month+"-"+i);
			} catch (ParseException e) {
//				System.out.println("ThreeAction中，字符串转日期出错！");
			}
			ZbData zbData = new ZbData(date,newId,null); 
			session.save(zbData);
		}
        //清理缓存
        session.flush();  
        session.clear();
		//释放session
		super.releaseSession(session);
	};
	
	//批量修改一个月的具体指标值
	public void updateMulti(int[] ids,String[] values,String hql){
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		for(int i=0;i<ids.length;i++){
			Query query = session.createQuery(hql).setString(0, values[i]).setInteger(1, ids[i]);
			int num = query.executeUpdate();
//			System.out.println("成功更新了 "+num+" 条记录");
		}
		
		session.flush();  
        session.clear();
		super.releaseSession(session);
	}
	
	//增加
	public Object add(Object obj){
		//getHibernateTemplate().get
		return getHibernateTemplate().save(obj);
	}
	
	
	//删除
	public void delete(Object obj) {
		getHibernateTemplate().delete(obj);
	}

	//批量删除。删除集合对象
	public void deleteMulti(String query) {
		getHibernateTemplate().deleteAll(getHibernateTemplate().find(query));
	}
	
	//批量删除。固定几个记录
	public void deleteMultiFixed(Class cls,String propertyName,Collection idData){
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		//通过session创建Query对象
		Criteria criteria = session.createCriteria(cls);
		List<ZbDetail> result = criteria.add(Restrictions.in(propertyName, idData)).list();
		//有数据，则删除
		if(result!=null&&result.size()>0){
			getHibernateTemplate().deleteAll(result);
		}else{
//			System.out.println("没有可被删除的数据");
		}
		super.releaseSession(session);
	}
	
	//模糊查询表中的所有数据
	//可以查询二级三级
	public Object queryMulti(String tableName,String year_month,Integer id){
		return getHibernateTemplate().find("from "+tableName+" t where to_char(t.zbDate,'yyyy-mm')like '%"+year_month+"%' and t.parentId like '%"+id+"%'");
	}
	//按对象修改
	public void updateObject(Object obj) {
		 getHibernateTemplate().update(obj);
	}
	//修改
	public int update(String query) {
		return getHibernateTemplate().bulkUpdate(query);//批量更新
	}
	//按年、月份查询。找出和查询的时间在同一年、同一月份的所有记录。这里的月份必须是两位数，才能截取出正确的年月信息。
	public Object queryByMonth(String tableName,String year_month) {//select * from zb_l1 t where Substr(to_char(t.zb_date,'yyyy-mm-dd'),1,7)='2013-04'
		return getHibernateTemplate().find("from "+tableName+" t where Substr(to_char(t.zbDate,'yyyy-mm-dd'),1,7)='"+year_month+"'");
	}
	//按id查询
	public Object queryById(Integer id) {
		return getHibernateTemplate().get(ZbUser.class, id);
	}
	//名字查询
	public Object queryByName(Object name) {
		return getHibernateTemplate().find("from ZbUser u where u.username='"+name+"'");//单引号套双引号拼接参数
	}
	//查询所有
	public Object queryAll(Object obj) {
		return getHibernateTemplate().find(obj.toString());
	}
	public Object queryUsers(PageUtil pageUtil) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		List list = session.createCriteria(ZbUser.class).setFirstResult((pageUtil.getPage()-1)*pageUtil.getPagesize()).setMaxResults(pageUtil.getPagesize()).list();
		session.flush();
		session.clear();
		super.releaseSession(session);
		return list;
	}
	
	//查询所有(分页)
	/*
	 * SELECT * FROM
     (
     SELECT A.*, rownum r
     FROM
          (
          SELECT *
          FROM ZB_USER
          ORDER BY ZB_USER.USERID asc
          ) A
     WHERE rownum <= 5
     ) B
WHERE r > 2;

*"select * from ZbUser (select A.*,rownum r from(select * from ZbUser order by ZbUser.userid asc) A where rownum <= 5 ) B) where r > 2"
*"from ZbUser where rownum<6 "
*"from ((select A.userid, rownum as r from (select * from ZbUser) A where rownum <= 5) B) where r > 2"
*select * from ZB_USER t where t.userid in(select B.userid from (select A.*,rownum r1 from(select * from ZB_USER where rownum<10) A)B where r1>5)
*/
	//find方法中的sql语句好像不可以。连使用hibernate自动生成的语句也不行，报同样的错误。
	public Object queryAllByPage(Object start, Object limit) {
		return getHibernateTemplate().find("from ( select row_.*, rownum rownum_ from ( select zbuser0_.USERID as USERID0_, zbuser0_.USERNAME as USERNAME0_, zbuser0_.USERTYPE as USERTYPE0_, zbuser0_.ZB_SORT as ZB4_0_ from ZbUser zbuser0_ ) row_ ) where rownum_ <= 8 and rownum_ > 5");
	}
	
	//HQL查询(查询不用使用事务)。直接用.getSessionFactory().openSession()返回一个session对象，来创建Query对象。用getCurrentSession方式获取session会要求使用事务配置。
	public Object AllByPageHQL(Integer start,Integer limit){
		//获取session.未配置事务时，不用getHibernateTemplate().getCurrentSession()方法
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		//通过session创建Query对象
		Object users = session.createQuery("from ZbUser t").setFirstResult(start).setMaxResults(limit).list();;
		//关闭session，否则多次查询后，session较多没有(超过8个)关闭，前台显示数据会卡死。
		super.releaseSession(session);
		return users;
	}
	//查询表中的各级指标总记录数
	public long queryTotalIndex(String tableName,String property,Integer id){
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		long total = (Long)session.createQuery("select count(*) from "+tableName+" where "+property+" = "+id+"").iterate().next();
		super.releaseSession(session);
		return total;
	}
	//查询表中的总记录数
	public long queryTotal(){
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		long f = (Long)session.createQuery("select count(*) from ZbUser").iterate().next();
		super.releaseSession(session);
		return f;
	}
}
