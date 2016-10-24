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
	
	<STYLE TYPE="text/css">
		<!--此处字体的可能影响-->
		.addIcon {	background-image: url(<%=request.getContextPath() %>/images/add.gif) !important; }  
		.addIcon {	background-image: url(<%=request.getContextPath() %>/images/add.gif) !important; }
		.openIcon {	background-image: url(<%=request.getContextPath() %>/images/open.gif) !important; }
		.editIcon {	background-image: url(<%=request.getContextPath() %>/images/edit.gif) !important; }
		.removeIcon {	background-image: url(<%=request.getContextPath() %>/images/removeIcon.gif) !important; }
		.removeIcon {	background-image: url(<%=request.getContextPath() %>/images/remove.gif) !important; }
 	 </STYLE>
	
	<script type="text/javascript">
		Ext.onReady(function(){
			/*数据源（store）中包含代理（proxy），代理中包含数据读取器（reader）*/
			
			//定义表格数据源
			var userStore = Ext.create("Ext.data.Store",{
				fields:["userid","username","usertype","zbSort"],
				//定义代理
				proxy:{
						type:"ajax",
						url:"sjAction.do",//sjAction.do;servlet/StrutsJsonServ;sjAction!getGrid.do
						reader:{
							type:"json",
							root:"items",
							totalProperty:"total"
						}
					},
				//autoLoad:true,
				pageSize:5//此属性为store组件所有
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
					new Ext.grid.RowNumberer({text:"行ff号",width:160}),//表格行号，自增
					{header:"id",width:160,dataIndex:"userid",sortable:true},
					{header:"姓名",width:160,dataIndex:"username",sortable:true},
					{header:"人员类型",width:160,dataIndex:"usertype",sortable:true},
					{header:"排序规则",width:160,dataIndex:"zbSort",sortable:true}
				],
				//添加新建、修改、删除工具栏
				tbar:[
					{
						text:"创建新用户",
						id:"newUser",
						tooltip:"创建",
						iconCls:"addIcon",
						handler:newUser
					},
					{
						text:"修改",
						id:"editUser",
						tooltip:"修改",
						iconCls:"editIcon",
						handler:editUser
					},
					{
						text:"删除",
						id:"removeUser",
						tooltip:"删除",
						iconCls:"removeIcon",
						handler:removeUser
					}
				],
				//添加分页工具栏
			 	bbar:[
					{
						xtype:"pagingtoolbar",
						pageSize:5,
						store:userStore,
						dispalyInfo:true,
						displayMsg:"显示 {0} - {1} 条，共计 {2} 条", //分别表示：start, end and total
			            emptyMsg:"没有数据"
			             
						
					}
				] 
			});
			//userStore.loadPage(3); //可以直接加载第几页数据
	 		//从服务器加载数据。？？有点性能的问题：频繁点击上一页下一页数据时，浏览器会卡顿。重启服务器会将积累的查询一次性执行。
			userStore.load(
				{
					params:{
						//action:"read",
						start:0,//分页时，查询的开始位置
						limit:5//
					}/* ,
					callback:function(records, operation, success) {
						//alert(44);
						for(var i=0;i<records.length;i++){
							alert(records[i].get("userid"));
						}
						//alert(records[2].get("userid"));//获取返回的数据（数组）中的属性值
						//alert(records[2].get("username"));//获取返回的数据（数组）中的属性值
  				  	} */
				}
			);  
			//创建新用户
			function newUser(){
				alert("创建新的用户！");
			}
			//修改用户
			function editUser(){
				alert("修改用户！");
				
			}
			//删除用户
			function removeUser(){
				alert("删除用户！");
				
			}
			
		});
	</script>

  </head>
  
  <body>
    This is my JSP page. <br>
  </body>
</html>
