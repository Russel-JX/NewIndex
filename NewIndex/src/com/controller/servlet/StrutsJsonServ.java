package com.controller.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pojo.ZbUser;

public class StrutsJsonServ extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("我在action中...");
		
		List<ZbUser> items = new ArrayList<ZbUser>();
		
		ZbUser user1 = new ZbUser(2,"小红","红","red",0,1);
		ZbUser user2 = new ZbUser(3,"小黄","黄","yellow",1,3);
		ZbUser user3 = new ZbUser(4,"小蓝","蓝","blue",1,4);
		ZbUser user4 = new ZbUser(7,"小紫","紫","zi",1,3);
		items.add(user1);
		items.add(user2);
		items.add(user3);
		items.add(user4);
		
		System.out.println("我在action中...");
		String back= "{items:[{userid:3,username:'aa',usertype:0,zbSort:3},{userid:3,username:'aa',usertype:0,zbSort:3}]}";
		response.getWriter().write(back);
	
	}

}
