package com.pojo;

import java.util.Date;

/**
 * ZbData entity. @author MyEclipse Persistence Tools
 */
public class ZbData implements java.io.Serializable {

	private Integer id;
	private Date zbDate;
	private Integer zbId;//参照三级指标的id
	private String zbData;

	public ZbData() {
	}
	public ZbData(Integer id,Date zbDate, Integer zbId, String zbData) {
		this.id = id;
		this.zbDate = zbDate;
		this.zbId = zbId;
		this.zbData = zbData;
	}
	public ZbData(Date zbDate, Integer zbId, String zbData) {
		this.zbDate = zbDate;
		this.zbId = zbId;
		this.zbData = zbData;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getZbDate() {
		return zbDate;
	}
	public void setZbDate(Date zbDate) {
		this.zbDate = zbDate;
	}
	public Integer getZbId() {
		return zbId;
	}
	public void setZbId(Integer zbId) {
		this.zbId = zbId;
	}
	public String getZbData() {
		return zbData;
	}
	public void setZbData(String zbData) {
		this.zbData = zbData;
	}

}
