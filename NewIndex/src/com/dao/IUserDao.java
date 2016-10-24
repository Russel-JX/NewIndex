package com.dao;

import java.math.BigDecimal;

import com.pojo.ZbUser;

public interface IUserDao extends IObjectDao{
/*	//增加
	public Object add(Object id);
	//删除
	public void delete(Object id);
	//修改
	public void update(Object obj);
	//按id查询
	public Object queryById(Integer id);
	//名字查询
	public Object queryByName(Object name);
	//查询所有
	public Object queryAll(Object obj);*/
	//用户登录验证
	public String userLogin(ZbUser zbUser);
}
