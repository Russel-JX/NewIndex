package com.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 三级指标和对应的具体指标值pojo类
 * 此类不进行ORM映射，只作为查询结果的封装类
 */
public class ZbDetailData implements java.io.Serializable {
	private Integer id;
	private Integer parentIdL1;
	private Integer parentId;//参照的二级指标
	private String zbName;
	private Date zbDate;
	private Integer zbSort;
	
	private Double zbPoint;
	private Double zbWeight;
	private Integer zbPrincipal;//参照用户
	private String zbTarget;
	private String zbSource;
	private String zbNow;
	private String zbTime;
	private String zbFrequentness;
	
	private Integer zbFormula;//所用的公式
	private String zbFormulaName;//所用的公式名称
	private String realname;//字符串的外键
	private String result;//统计的得分结果
	//三级指标下的具体指标值
	private List<ZbData> data = new ArrayList<ZbData>();
	
	public ZbDetailData() {
	}
	public ZbDetailData(Integer id,Integer parentIdL1, Integer parentId,
			String zbName, Date zbDate,Integer zbSort) {
		this.id = id;
		this.parentIdL1 = parentIdL1;
		this.parentId = parentId;
		this.zbName = zbName;
		this.zbDate = zbDate;
		this.zbSort = zbSort;
	}
	public ZbDetailData(Integer parentIdL1, Integer parentId,
			String zbName, Date zbDate, Integer zbSort) {
		this.parentIdL1 = parentIdL1;
		this.parentId = parentId;
		this.zbName = zbName;
		this.zbDate = zbDate;
		this.zbSort = zbSort;
	}
	
	public ZbDetailData(Integer id,Integer parentIdL1, Integer parentId,
			String zbName, Date zbDate, Integer zbSort, Double zbPoint,
			Double zbWeight, Integer zbPrincipal, String zbTarget,
			String zbSource, String zbNow, String zbTime, String zbFrequentness,
			Integer zbFormula,String zbFormulaName,String realname,String result,List<ZbData> data) {
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
		this.zbFormulaName = zbFormulaName;
		this.realname = realname;
		this.result = result;
		this.data = data;
	}
	public ZbDetailData(Integer id,Integer parentIdL1, Integer parentId,
			String zbName, Date zbDate, Integer zbSort, Double zbPoint,
			Double zbWeight, Integer zbPrincipal, String zbTarget,
			String zbSource, String zbNow, String zbTime, String zbFrequentness,List<ZbData> data) {
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
		this.data = data;
	}
	
	public List<ZbData> getData() {
		return data;
	}
	public void setData(List<ZbData> data) {
		this.data = data;
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
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getZbFormulaName() {
		return zbFormulaName;
	}
	public void setZbFormulaName(String zbFormulaName) {
		this.zbFormulaName = zbFormulaName;
	}
	
}
