<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Hello 1.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ext-4.0/resources/css/ext-all.css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext-4.0/ext-all.js"></script>
	
	<script type="text/javascript">
	 	Ext.onReady(function(){
			//创建表格组件
			Ext.create("Ext.grid.Panel",{
				title:"人员列表",
				renderTo:Ext.getBody(),
				width:800,
				height:400,
				frame:true,
				
				//数据源
				store:{
					fields:["userid","username","usertype","zbSort"],
					proxy:{
						type:"ajax",
						url:"sjAction.do",//sjAction.do
						reader:{
							type:"json",
							root:"items"
						}
					},
					autoLoad:true
				},
				//配置表格列
				columns:[
					new Ext.grid.RowNumberer({text:"行ff号",width:80}),//表格行号，自增
					{header:"id",width:80,dataIndex:"userid",sortable:true},
					{header:"姓名",width:80,dataIndex:"username",sortable:true},
					{header:"人员类型",width:80,dataIndex:"usertype",sortable:true},
					{header:"排序规则",width:80,dataIndex:"zbSort",sortable:true}
				]
			});
			
			
		}); 
	</script>

  </head>
  
  <body>
   <!--  <a href="sjAction.do" >访问action</a> -->
  <a href="threeqthreedataAction.do?date=2013-06" >获取三级指标和对应的具体指标值</a>
  </body>
</html>
