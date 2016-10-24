package com.pojo;

import java.util.Date;

/**
 * ZbL2 entity. @author MyEclipse Persistence Tools
 */
public class ZbL2 implements java.io.Serializable {
	// Fields

	private Integer id;
	private String zbName;
	private Date zbDate;
	private Integer parentId;
	private Integer zbSort;

	// Constructors
	public ZbL2() {
	}

	public ZbL2(Integer id,String zbName, Date zbDate, Integer parentId,Integer zbSort) {
		this.id = id;
		this.zbName = zbName;
		this.zbDate = zbDate;
		this.parentId = parentId;
		this.zbSort = zbSort;
	}
	public ZbL2(String zbName, Date zbDate, Integer parentId,Integer zbSort) {
		this.zbName = zbName;
		this.zbDate = zbDate;
		this.parentId = parentId;
		this.zbSort = zbSort;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getZbName() {
		return zbName;
	}

	public void setZbName(String zbName) {
		this.zbName = zbName;
	}

	public Date getZbDate() {
		return zbDate;
	}

	public void setZbDate(Date zbDate) {
		this.zbDate = zbDate;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getZbSort() {
		return zbSort;
	}

	public void setZbSort(Integer zbSort) {
		this.zbSort = zbSort;
	}

}
