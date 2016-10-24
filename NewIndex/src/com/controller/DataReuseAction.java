package com.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.service.DataReuseService;

/**
 * 指标复用action
 * @author JiangXun
 */
public class DataReuseAction extends ActionSupport{
	private DataReuseService dataReuseService;
	private boolean reuse;//是否指标重用
	
	private int[] effectedRows;

	public String execute() throws Exception {
//		System.out.println("指标是否重用： "+reuse);

		effectedRows = dataReuseService.reuseDataFromLastMonth(reuse);
		
		//System.out.println("成功复制了几条指标： "+effectedL1);
		return "json";
	}
	

	public void setDataReuseService(DataReuseService dataReuseService) {
		this.dataReuseService = dataReuseService;
	}


	public void setReuse(boolean reuse) {
		this.reuse = reuse;
	}


	public int[] getEffectedRows() {
		return effectedRows;
	}


	public void setEffectedRows(int[] effectedRows) {
		this.effectedRows = effectedRows;
	}

}
