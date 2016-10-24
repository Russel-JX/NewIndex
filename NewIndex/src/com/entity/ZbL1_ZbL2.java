package com.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 一二级指标关联实体类
 */
public class ZbL1_ZbL2 {

	private BigDecimal id;//二级指标id
	private String zbName_L2;//二级指标名称
	private Date zbDate;
	private BigDecimal parentId;
	private BigDecimal zbSort;
	
	private String zbName_L1;//一级指标名称

	
	
	public ZbL1_ZbL2() {
		super();
	}

	public ZbL1_ZbL2(BigDecimal id, String zbName_L2, Date zbDate,
			BigDecimal parentId, BigDecimal zbSort, String zbName_L1) {
		super();
		this.id = id;
		this.zbName_L2 = zbName_L2;
		this.zbDate = zbDate;
		this.parentId = parentId;
		this.zbSort = zbSort;
		this.zbName_L1 = zbName_L1;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getZbName_L2() {
		return zbName_L2;
	}

	public void setZbName_L2(String zbName_L2) {
		this.zbName_L2 = zbName_L2;
	}

	public Date getZbDate() {
		return zbDate;
	}

	public void setZbDate(Date zbDate) {
		this.zbDate = zbDate;
	}

	public BigDecimal getParentId() {
		return parentId;
	}

	public void setParentId(BigDecimal parentId) {
		this.parentId = parentId;
	}

	public BigDecimal getZbSort() {
		return zbSort;
	}

	public void setZbSort(BigDecimal zbSort) {
		this.zbSort = zbSort;
	}

	public String getZbName_L1() {
		return zbName_L1;
	}

	public void setZbName_L1(String zbName_L1) {
		this.zbName_L1 = zbName_L1;
	}

	
}
