package com.entity;

import java.util.Date;

/**
 * 用户和三级指标关联实体类
 */
public class ZbUser_ZbDetail {
	// 三级指标
	private Integer id;
	private Integer parentIdL1;
	private Integer parentId;// 参照的二级指标
	private String zbName;
	private Date zbDate;
	private Integer zbSort;

	private String zbPoint;
	private String zbWeight;
	private String zbPrincipal;// 负责人姓名
	private String zbTarget;
	private String zbSource;
	private String zbNow;
	private String zbTime;
	private String zbFrequentness;

	private String formulaName;// 公式名称

	// 用户
	private Integer userid;// 负责人id
	private Integer formulaid;// 公式id

	public ZbUser_ZbDetail() {
		super();
	}

	public ZbUser_ZbDetail(Integer id, Integer parentIdL1, Integer parentId,
			String zbName, Date zbDate, Integer zbSort, String zbPoint,
			String zbWeight, String zbPrincipal, String zbTarget,
			String zbSource, String zbNow, String zbTime,
			String zbFrequentness, String formulaName, Integer userid,
			Integer formulaid) {
		super();
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
		this.formulaName = formulaName;
		this.userid = userid;
		this.formulaid = formulaid;
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

	public String getZbPoint() {
		return zbPoint;
	}

	public void setZbPoint(String zbPoint) {
		this.zbPoint = zbPoint;
	}

	public String getZbWeight() {
		return zbWeight;
	}

	public void setZbWeight(String zbWeight) {
		this.zbWeight = zbWeight;
	}

	public String getZbPrincipal() {
		return zbPrincipal;
	}

	public void setZbPrincipal(String zbPrincipal) {
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

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}
