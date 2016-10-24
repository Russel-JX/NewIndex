<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>index_data.jsp 指标维护测试页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ext-4.0/resources/css/ext-all.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/ext-all.js"></script>

	<script type="text/javascript">
		Ext.onReady(function(){
			//一级指标数据源
			var l_31IndicatorInfordata = Ext.create("Ext.data.Store",{
				//一级指标的属性值，和二级的名称相同，但从后台获得的JSON内封装的数据不同。
				fields:["id","zbName","zbDate","zbSort"],
				proxy:{
					type:"ajax",
					url:"threeqoneAction.do",
					reader:{
								type:"json",
								root:"items_l1",
								totalProperty:"total_l1"
					}
				}
			});
			//二级指标数据源
			var l_32IndicatorInfordata = Ext.create("Ext.data.Store",{
				//二级指标的属性值
				fields:["id","zbName","zbDate","zbSort"],
				proxy:{
					type:"ajax",
					url:"threeqtwoAction.do",
					reader:{
								type:"json",
								root:"items_l2",
								totalProperty:"total_l2"
					}
				}
			});
			
			//创建加载指标数据的表单
			var inMatForm = Ext.create("Ext.form.Panel",{
				title:"指标维护",
				id:"inMatForm",
				width:1177,
				height:383,
				frame:true,
				renderTo:Ext.getBody(),
				bodyStyle:"padding:5 5 5 5",
				
				autoScroll: true,//设置viewport的子组件内容较多时，自动创建滚动条
				//默认的字段属性配置
				defauts:{
					labelSeparator:":",
					labelWidth:30,
					labelAlign:"left",
					
					width:150,
					
					msgTarget:"side"
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
					//下拉列表：一级
					{
						xtype:"combo",
						fieldLabel:"一级指标名称",
						name:"parentIdL1",
						id:"three_oneindicatorNamedata",
						
						listConfig:{
							emptyText:"无此项选择",//当找的值不再列表项时的提示
							maxHeight:180,//表单的最大高度。所有列表项高度之和。
							loadingText:"正在获取数据"//加载（获取）列表项数据时的提示。
						},
						queryParam:"oneIndicator",//列表上方的查询框的name属性，服务器根据此参数查询所需的列表项数据
						minChars:1,//下拉列表自动选择用户需要输入的最小字符数量
						queryDelay:300,//从字符键入到发送查询之间的等待时间
						allQuery:"allNames",
								
						displayField:"zbName",//列表中的显示文字
						valueField:"id",//类表项的value值
						queryMode:"remote",//数据的获取模式。local为本地数据，remote为远程服务器上数据
						forceSelection:true,//迫使用户输入查询条件，必须在列表中存在，如果不存在则取默认的列表项。false，则允许输入任意字符，如果输入的值列表项中不存在，也会显示输入的值
						typeAhead:true,//根据输入信息，自动选择匹配的列表项。
						//value:"222222",//默认选择的列表项，这里指“北京”。这里写valueField的某个值
								
						triggerAction:"all",//**值为all时，将使用allQuery中的查询条件进行查询。**
								
						store:l_31IndicatorInfordata//数据源
					},
					//下拉列表：二级
					{
						xtype:"combo",
						fieldLabel:"二级指标名称",
						name:"parentIdL2",
						id:"three_twoindicatorNamedata",
						
						listConfig:{
							emptyText:"无此项选择",//当找的值不再列表项时的提示
							maxHeight:180,//表单的最大高度。所有列表项高度之和。
							loadingText:"正在获取数据"//加载（获取）列表项数据时的提示。
						},
						queryParam:"twoIndicator",//列表上方的查询框的name属性，服务器根据此参数查询所需的列表项数据
						minChars:1,//下拉列表自动选择用户需要输入的最小字符数量
						queryDelay:300,//从字符键入到发送查询之间的等待时间
						allQuery:"allNames",
								
						displayField:"zbName",//列表中的显示文字
						valueField:"id",//类表项的value值
						queryMode:"remote",//数据的获取模式。local为本地数据，remote为远程服务器上数据
						forceSelection:true,//迫使用户输入查询条件，必须在列表中存在，如果不存在则取默认的列表项。false，则允许输入任意字符，如果输入的值列表项中不存在，也会显示输入的值
						typeAhead:true,//根据输入信息，自动选择匹配的列表项。
						//value:"222222",//默认选择的列表项，这里指“北京”。这里写valueField的某个值
								
						triggerAction:"all",//**值为all时，将使用allQuery中的查询条件进行查询。**
								
						store:l_32IndicatorInfordata//数据源
					},
					//三级指标名称
					{
						xtype:"textfield",
						fieldLabel:"指标名称",
						name:"zbName",
						id:"zbName3data",
						emptyText:"这里输入三级指标名称",//默认显示的文本
						allowBlank:false,//不为空
						blankText:"指标名称不能为空"
					},
					//排序规则
					{
						xtype:"numberfield",
						name:"zbSort",
						id:"zbSort3data",
						fieldLabel:"排序规则",
						allowDecimals:false
						//vtype:"num"//过滤条件
						//nanText:"请输入数字"//错误提示
					},
					
					//所占分数。
					{
						xtype:"numberfield",
						name:"zbPoint",
						id:"point3data",
						fieldLabel:"所占分数",
						allowDecimals:true,//允许小数
						decimalPrecision:2
					},
					//2013年公布的权重
					{
						xtype:"numberfield",
						name:"zbWeight",
						id:"weight3data",
						fieldLabel:"2013年公布的权重",
						allowDecimals:true,
						decimalPrecision:2,
						maxValue:1,
						maxText:"最大值不超过1"//超过最大值后的提示
					},
					//负责人
					{
						xtype:"textfield",
						fieldLabel:"负责人",
						name:"zbPrincipal",
						id:"charge3data",
						//emptyText:"这里输入二级指标名称",//默认显示的文本
						allowBlank:false,//不为空
						blankText:"请填写负责人"
					},
					//指标目标值
					{
						xtype:"textfield",
						fieldLabel:"指标目标值",
						name:"zbTarget",
						id:"target3data",
						allowBlank:true//可为空
					},
					//指标来源
					{
						xtype:"textfield",
						fieldLabel:"指标来源",
						name:"zbSource",
						id:"from3data",
						allowBlank:true//可为空
					},
					//指标当前值
					{
						xtype:"textfield",
						fieldLabel:"指标当前值",
						name:"zbNow",
						id:"nowValue3data",
						allowBlank:true
					},
					//数据收集日期
					{
						xtype:"textfield",
						fieldLabel:"数据收集日期",
						name:"zbTime",
						id:"getTime3data",
						allowBlank:true
					},
					//指标考核频度
					{
						xtype:"textfield",
						fieldLabel:"指标考核频度",
						name:"zbFrequentness",
						id:"frequency3data",
						allowBlank:true
					}
				],
				buttons:[
					{text:"提交",handler:submitThreedata},
					{text:"重置",handler:resetThreedata}
				]
			});
			function submitThreedata(){
				//同步提交
				inMatForm.getForm().submit({
					clientValidation:true,//进行客户端验证
					url:"threeAction!addThreeIndex.do",//提交的方向换一下
					method:"GET",
					success:function(form1,action1){
						Ext.Msg.alert("提示","提交成功！");
					},
					failure:function(form1,action1){
						Ext.Msg.alert("提示","提交失败！！！");
					}
				});
			}
			//重置
			function resetThreedata(){
				inThreeForm.getForm().reset();
			}
			//指标填写表单
			var dataPanel = new Ext.form.Panel({
				title:"表单之文本框",
				bodyStyle:"padding:5 5 5 5",//表单外边距
				renderTo:Ext.getBody(),
				frame:true,//是否渲染表单
				height:400,//表单高度
				width:800,//表单宽度
				//defaultType:"numberfield",//设置表单的默认字段类型是数字
				//统一设置表单中字段的默认属性
				defaults:{
					allowBlank:true,//是否允许字段值为空				
				
					labelSeparator:"#",//标签与字段间的分隔符
					labelWidth:80,//标签宽度
					labelAlign:"left",//标签与字段的对齐方式，有“top”,“left”,“right”三种值。
					
					width:300,//字段宽度
					
					//当字段校验出现错误时，提示出现的位置。“side（在字段的右边）”，“under（在字段下面）”，“qtip（浮动的提示）”，“某个Html元素的id值（把错误提示放在Html元素内）”，“none（无提示）”
					msgTarget:"under"
				},
				items:[
					//文本框——用户名
					{
						xtype:"textfield",
						fieldLabel:"用户名",
						
						emptyText:"gggg",
						//grow:true,//输入的字符长度多时，自动增加文本框长度（不超过上面定义的width）				
						name:"userName",//定义字段的name属性
						selectOnFocus:true,//
						//验证电子邮件格式的正则表达式
						regex : /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/,
						regexText:'格式错误'//验证错误之后的提示信息,
					},
					//文本框——密码
					{
						xtype:"textfield",
						fieldLabel:"密码",
						name:"password",
						inputType:"password",//**设置文本框的类型为密码**
						allowBlank:false
						
					}
				]		
			});
			
		});
	</script>
  </head>
  
  <body>
    指标维护<br>
    
  </body>
</html>
