package com.pojo;

import java.util.Date;

/**
 * ZbL1 entity. @author MyEclipse Persistence Tools
 */
public class ZbL1 implements java.io.Serializable {
	// Fields
	private Integer id;
	private Integer zbSort;
	private String zbName;
	private Date zbDate;

	// Constructors
	public ZbL1() {
	}

	/** minimal constructor */
	public ZbL1(String zbName, Date zbDate) {
		this.zbName = zbName;
		this.zbDate = zbDate;
	}

	/** full constructor */
	public ZbL1(Integer zbSort, String zbName, Date zbDate) {
		this.zbSort = zbSort;
		this.zbName = zbName;
		this.zbDate = zbDate;
	}
	public ZbL1(Integer id,Integer zbSort, String zbName, Date zbDate) {
		this.id = id;
		this.zbSort = zbSort;
		this.zbName = zbName;
		this.zbDate = zbDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getZbSort() {
		return zbSort;
	}

	public void setZbSort(Integer zbSort) {
		this.zbSort = zbSort;
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

}
