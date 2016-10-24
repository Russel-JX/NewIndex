package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.dao.FormulaDao;
import com.opensymphony.xwork2.ActionSupport;
import com.pojo.Formula;

/**
 * 处理公式action
 * @author JX
 */
public class FormulaAction extends ActionSupport implements ServletRequestAware{
	private List<Formula> formulas = new ArrayList<Formula>();//pojo类，使用new创建
	
	private FormulaDao formulaDao;
	
	private HttpServletRequest request; 
	
	
	/**
	 * 查询所有公式
	 */
	public String execute() throws Exception {
		formulas = (List<Formula>)formulaDao.queryAll("from Formula t");
//		System.out.println("公式数量是： "+formulas.size());
//		for(int i=0;i<formulas.size();i++){
//			System.out.println("公式id是： "+formulas.get(i).getId()+" 公式名是： "+formulas.get(i).getName()+" 公式描述是： "+formulas.get(i).getDescription());
//		}
		return SUCCESS;
	}


	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}


	public List<Formula> getFormulas() {
		return formulas;
	}


	public void setFormulas(List<Formula> formulas) {
		this.formulas = formulas;
	}


	public void setFormulaDao(FormulaDao formulaDao) {
		this.formulaDao = formulaDao;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
}
