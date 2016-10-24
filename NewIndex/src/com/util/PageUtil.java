package com.util;

public class PageUtil {

	private int page;
	private int pagesize;
	private int totalnumber;
	
	public PageUtil() {
		super();
	}
	public PageUtil(int page, int pagesize, int totalnumber) {
		super();
		this.page = page;
		this.pagesize = pagesize;
		this.totalnumber = totalnumber;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getTotalnumber() {
		return totalnumber;
	}
	public void setTotalnumber(int totalnumber) {
		this.totalnumber = totalnumber;
	}
	
	
}
