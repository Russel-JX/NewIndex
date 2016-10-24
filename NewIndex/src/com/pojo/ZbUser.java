package com.pojo;

/**
 * ZbUser entity. @author MyEclipse Persistence Tools
 */
public class ZbUser implements java.io.Serializable {

	// Constructors

	private Integer userid;
	private String realname;//真实姓名
	private String username;//用户名
	private String password;//密码
	private Integer usertype;
	private Integer zbSort;

	// Constructors
	public ZbUser() {
	}
	
	public ZbUser(Integer usertype) {
		this.usertype = usertype;
	}

	public ZbUser(Integer userid,String realname,String username,String password, Integer usertype,
			Integer zbSort) {
		this.userid = userid;
		this.realname = realname;
		this.username = username;
		this.password = password;
		this.usertype = usertype;
		this.zbSort = zbSort;
	}


	// Property accessors

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUsertype() {
		return this.usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public Integer getZbSort() {
		return this.zbSort;
	}

	public void setZbSort(Integer zbSort) {
		this.zbSort = zbSort;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}


}
