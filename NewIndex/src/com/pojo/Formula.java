package com.pojo;

/**
 * 指标计算公式实体类
 * 
 * @author JX
 */
public class Formula {

	private Integer id;
	private String name;
	private String description;

	public Formula() {
		super();
	}

	public Formula(Integer id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
