package com.dao;

/**
 * 沿用上个月的指标
 * @author JiangXun
 */
public interface DataReuseDao extends IObjectDao{

	public int[] reuseDataFromLastMonth(boolean flag);
}
