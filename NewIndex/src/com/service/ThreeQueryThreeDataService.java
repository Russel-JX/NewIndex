package com.service;

import java.util.List;

import com.pojo.ZbData;
import com.pojo.ZbDetailData;

/**
 *各种具体指标值统计计算 
 */
public interface ThreeQueryThreeDataService {
	/**
	 *统计某项三级指标的月度得分
	 *@param items_l3_data	不带统计结果的三级指标和具体指标
	 */
	public List<ZbDetailData> defineThreeIndex(List<ZbDetailData> list);
	
}
