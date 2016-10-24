package com.dao.impl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.IObjectDao;
import com.dao.IUserDao;
import com.pojo.ZbUser;

public class IUserDaoImpl extends IObjectDaoImpl implements IUserDao{

	//用户登录验证
	public String userLogin(ZbUser zbUser) {
		String hql = "from ZbUser t where t.username ="+zbUser.getUsername();
		
		return null;
	}

}
