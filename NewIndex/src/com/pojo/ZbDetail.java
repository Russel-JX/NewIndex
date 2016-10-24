package com.pojo;

import java.util.Date;

/**
 * ZbDetail entity. @author MyEclipse Persistence Tools
 */
public class ZbDetail implements java.io.Serializable {
	private Integer id;
	private Integer parentIdL1;
	private Integer parentId;//参照的二级指标
	private String zbName;
	private Date zbDate;
	private Integer zbSort;
	
	private Double zbPoint;
	private Double zbWeight;
	private Integer zbPrincipal;
	private String zbTarget;
	private String zbSource;
	private String zbNow;
	private String zbTime;
	private String zbFrequentness;
	
	private Integer zbFormula;//所用的公式
	
	public ZbDetail() {
	}
	public ZbDetail(Integer id,Integer parentIdL1, Integer parentId,
			String zbName, Date zbDate,Integer zbSort) {
		this.id = id;
		this.parentIdL1 = parentIdL1;
		this.parentId = parentId;
		this.zbName = zbName;
		this.zbDate = zbDate;
		this.zbSort = zbSort;
	}
	public ZbDetail(Integer parentIdL1, Integer parentId,
			String zbName, Date zbDate, Integer zbSort) {
		this.parentIdL1 = parentIdL1;
		this.parentId = parentId;
		this.zbName = zbName;
		this.zbDate = zbDate;
		this.zbSort = zbSort;
	}
	
	public ZbDetail(Integer id,Integer parentIdL1, Integer parentId,
			String zbName, Date zbDate, Integer zbSort, Double zbPoint,
			Double zbWeight, Integer zbPrincipal, String zbTarget,
			String zbSource, String zbNow, String zbTime, String zbFrequentness, Integer zbFormula) {
		this.id = id;
		this.parentIdL1 = parentIdL1;
		this.parentId = parentId;
		this.zbName = zbName;
		this.zbDate = zbDate;
		this.zbSort = zbSort;
		this.zbPoint = zbPoint;
		this.zbWeight = zbWeight;
		this.zbPrincipal = zbPrincipal;
		this.zbTarget = zbTarget;
		this.zbSource = zbSource;
		this.zbNow = zbNow;
		this.zbTime = zbTime;
		this.zbFrequentness = zbFrequentness;
		this.zbFormula = zbFormula;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentIdL1() {
		return parentIdL1;
	}
	public void setParentIdL1(Integer parentIdL1) {
		this.parentIdL1 = parentIdL1;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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
	public Integer getZbSort() {
		return zbSort;
	}
	public void setZbSort(Integer zbSort) {
		this.zbSort = zbSort;
	}
	
	
	public Double getZbPoint() {
		return zbPoint;
	}
	public void setZbPoint(Double zbPoint) {
		this.zbPoint = zbPoint;
	}
	public Double getZbWeight() {
		return zbWeight;
	}
	public void setZbWeight(Double zbWeight) {
		this.zbWeight = zbWeight;
	}
	public Integer getZbPrincipal() {
		return zbPrincipal;
	}
	public void setZbPrincipal(Integer zbPrincipal) {
		this.zbPrincipal = zbPrincipal;
	}
	public String getZbTarget() {
		return zbTarget;
	}
	public void setZbTarget(String zbTarget) {
		this.zbTarget = zbTarget;
	}
	public String getZbSource() {
		return zbSource;
	}
	public void setZbSource(String zbSource) {
		this.zbSource = zbSource;
	}
	public String getZbNow() {
		return zbNow;
	}
	public void setZbNow(String zbNow) {
		this.zbNow = zbNow;
	}
	public String getZbTime() {
		return zbTime;
	}
	public void setZbTime(String zbTime) {
		this.zbTime = zbTime;
	}
	public String getZbFrequentness() {
		return zbFrequentness;
	}
	public void setZbFrequentness(String zbFrequentness) {
		this.zbFrequentness = zbFrequentness;
	}
	public Integer getZbFormula() {
		return zbFormula;
	}
	public void setZbFormula(Integer zbFormula) {
		this.zbFormula = zbFormula;
	}
	
}
