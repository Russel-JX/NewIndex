<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>异步提交数据到action</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ext-4.0/resources/css/ext-all.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/ext-all.js"></script>

	<script type="text/javascript">
		//function ajaxSubmit(){
		Ext.onReady(function(){
			var usMatForm = Ext.create("Ext.form.Panel",{
			id:"usMatForm",
			title:"用户维护",
			width:500,
			height:150,
			renderTo:Ext.getBody(),
			frame:true,
			//closable:true,//此属性就是在Ext.tab.Panel的子标签内设置的属性，而其子标签可为任意组件。
			bodyStyle:"padding:5 5 5 5",
			//默认的字段属性配置
			defauts:{
				labelSeparator:":",
				labelWidth:20,
				labelAlign:"left",
				
				width:150,
				
				msgTarget:"under"
			},
			
			//异步提交
			standardSubmit:false,
			
			items:[
				{
					xtype:"datefield",
					fieldLabel:"请选择日期",
					name:"zbDate",
					id:"zbDate3data",
					invalidText:"请按照xxx格式选择或填写日期",
					format:"Y年m月d日"
				},
				{
					xtype:"textfield",
					name:"username",
					id:"userName",
					fieldLabel:"用户名",
					//emptyText:"这里输入指标名称",//默认显示的文本
					allowBlank:false,//不为空
					blankText:"用户名不能为空"
				}
				
			],
			tbar:[
					{
						text:"获取表单中的数据，并用ajax提交",
						id:"newUser",
						tooltip:"创建",
						iconCls:"addIcon",
						handler:getForm
					}]
			
	});
		//获取表单中的数据
		function getForm(){
			alert("****"+Ext.getCmp("userName").getValue());		
			alert("^^^"+Ext.getCmp("userName").getId());
			
			var requestConfig = {
				url:"testAction!addOneIndex.do",//oneAction!addOneIndex.do;userAction!addUser.do
				method:"get",
				params:{
					zbDate:Ext.getCmp("zbDate3data").getValue(),//所选的日期
					zbName:"ajax请求方式",
					zbSort:1000
				}/* ,
				callback:function(options,issuccess,backresponse){
					alert("请求是否成功： "+issuccess+"\n服务器返回值： "+backresponse.responseText);
				} */
			};
			Ext.Ajax.request(requestConfig);		
		}	
});

	</script>
  </head>
  
  <body>
  </body>
</html>
