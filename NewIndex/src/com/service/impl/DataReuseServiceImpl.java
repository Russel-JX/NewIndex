package com.service.impl;

import com.dao.DataReuseDao;
import com.service.DataReuseService;

public class DataReuseServiceImpl implements DataReuseService{

	private DataReuseDao dataReuseDao;
	
	public int[] reuseDataFromLastMonth(boolean flag) {
		
		return dataReuseDao.reuseDataFromLastMonth(flag);
	}

	
	
	
	public void setDataReuseDao(DataReuseDao dataReuseDao) {
		this.dataReuseDao = dataReuseDao;
	}
}
