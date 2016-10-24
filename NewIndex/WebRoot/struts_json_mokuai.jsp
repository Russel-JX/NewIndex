<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>struts_json struts2生成json数据</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

		
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ext-4.0/resources/css/ext-all.css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext-4.0/ext-all.js"></script>
	
	<script type="text/javascript">
		Ext.onReady(function(){
			/*数据源（store）中包含代理（proxy），代理中包含数据读取器（reader）*/
 			//创建数据模型
			Ext.regModel("User",{
				//fields:[{name:"userid"},{name:"username"},{name:"usertype"},{name:"zbSort"}]
				//fields:[{name:"userid"},{name:"username"},{name:"usertype"},{name:"zbSort"}]
				fields:[{name:"userid",mapping:"userid"},"username","usertype","zbSort"]
			});
			//定义数据读取器
			var jsonReader = Ext.create("Ext.data.reader.Json",{//4.0用Ext.data.reader.Json；老版本用Ext.data.JsonReader
				root:"items",//json数据的根，即从root所指定的位置作为数据(本地json数据或服务器传递回的json数据)读取的开始位置
				totalProperty:"total"//json格式数据中哪个属性作为记录的条数。
			});			
			//定义表格数据源
			var userStore = Ext.create("Ext.data.Store",{
				model:"User",
				//定义代理
				proxy:new Ext.data.proxy.Ajax({//new Ext.data.proxy.Ajax
					url:"sjAction.do"
				}),
				reader:jsonReader
			});
			


			//创建表格组件
			Ext.create("Ext.grid.Panel",{
				title:"人员列表",
				renderTo:Ext.getBody(),
				width:800,
				height:400,
				frame:true,
				
				store:userStore,
				//配置表格列
				columns:[
					new Ext.grid.RowNumberer({text:"行ff号",width:80}),//表格行号，自增
					{header:"id",width:80,dataIndex:"userid",sortable:true},
					{header:"姓名",width:80,dataIndex:"username",sortable:true},
					{header:"人员类型",width:80,dataIndex:"usertype",sortable:true},
					{header:"排序规则",width:80,dataIndex:"zbSort",sortable:true}
				],
				//添加分页工具栏
				bbar:[
					{
						xtype:"pagingtoolbar",
						store:userStore,
						dispalyInfo:true
					}
				]
			});
			//从服务器加载数据
			userStore.load(
				{
					params:{
						action:"read",
						page:3,
						start:0,
						limit:5
					}
				}
			);
			
			
		});
	</script>

  </head>
  
  <body>
  <font color="red" size="4">??分模块获取表格数据时，可以从后台获取正确的json数据，但只能显示行号为1的记录。??</font>
    This is my JSP page. <br>
  </body>
</html>
