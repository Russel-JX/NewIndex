<%@ page language="java" import="java.util.*,com.pojo.ZbUser" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>填写指标</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/main.css" />

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ext-4.0/resources/css/ext-all.css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext-4.0/ext-all.js"></script>
	<!-- 使控件数值显示为中文。如：sept为九月 -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/ext-lang-zh_CN.js"></script>
	
	<script type="text/javascript">
		Ext.onReady(function(){
			//一级指标数据源
			var l_31IndicatorInfordata = Ext.create("Ext.data.Store",{
				
				//一级指标的属性值，和二级的名称相同，但从后台获得的JSON内封装的数据不同。
				fields:["id","zbName","zbDate","zbSort"],
				proxy:{
					type:"ajax",
					url:"dqOneAction.do",//动态发送查询条件
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
					url:"dqOneAction.do",
					reader:{
								type:"json",
								root:"items_l2",
								totalProperty:"total_l2"
					}
				}
			}); 
			//三级指标数据源
			var l_33IndicatorInfordata = Ext.create("Ext.data.Store",{
				//三级指标的属性值
				fields:["id","zbName","zbDate","zbSort","zbPoint","zbWeight","zbPrincipal","zbTarget","zbSource","zbNow","zbTime","zbFrequentness"],
				proxy:{
					type:"ajax",
					url:"dqOneAction.do",
					reader:{
								type:"json",
								root:"items_l3",
								totalProperty:"total_l3"
					}
				}
			}); 
			//具体指标数据源
			var dataStore = Ext.create("Ext.data.Store",{
				fields:["id","zbData","zbDate"],
				proxy:{
					type:"ajax",
					url:"dateqdataAction.do",
					reader:{
								type:"json",
								root:"items_data",
								totalProperty:"total_data"
					}
				}
			}); 
			var inSubmitForm = Ext.create("Ext.form.FormPanel",{
				title:"指标维护",
				id:"inSubmitForm",
				renderTo:"form_layout",
				bodyStyle:"padding:5 5 5 5",//表单外边距
				frame:true,
				height:500,
				width:1200,
				
				autoScroll: true,
				defauts:{
					labelSeparator:":",
					labelWidth:15,
					labelAlign:"left",
					
					width:150,
					
					msgTarget:"side"
				},
				
				 items:[
					{
						layout:"column",
						//第一行
						items:[
							//字段集1
							{
								xtype:"fieldset",
								title:"<font size='2'  style='font-weight:bold'>指标大项</font>",
								columnWidth:.50,
								style:"margin-left:10px;margin-right:10px",
								//layout:"form",
								items:[
									{
										xtype:"datefield",
										fieldLabel:"请选择日期",
										name:"zbDate",
										id:"zbDate3data",
										invalidText:"请按照xxx格式选择或填写日期",
										format:"Y年m月"/* ,
										submitFormat:"Y/m/d"//数据提交的格式，默认为format定义的格式。 */
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
										
										/*使用store的load方法加载下拉列表所需数据，这些数据已经在本地，所以使用local显示在列表中。
											如果使用远程的话，选择选项之前，要几点出发按钮或在文本框中书写查询条件。
										*/
										queryMode:"local",//数据的获取模式。local为本地数据，remote为远程服务器上数据
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
										queryMode:"local",//数据的获取模式。local为本地数据，remote为远程服务器上数据
										forceSelection:true,//迫使用户输入查询条件，必须在列表中存在，如果不存在则取默认的列表项。false，则允许输入任意字符，如果输入的值列表项中不存在，也会显示输入的值
										typeAhead:true,//根据输入信息，自动选择匹配的列表项。
										//value:"222222",//默认选择的列表项，这里指“北京”。这里写valueField的某个值
												
										triggerAction:"all",//**值为all时，将使用allQuery中的查询条件进行查询。**
												
										store:l_32IndicatorInfordata//数据源
									},
									//下拉列表：三级
									{
										xtype:"combo",
										fieldLabel:"三级指标名称",
										name:"parentIdL",
										id:"three_threeindicatorNamedata",
										
										listConfig:{
											emptyText:"无此项选择",//当找的值不再列表项时的提示
											maxHeight:180,//表单的最大高度。所有列表项高度之和。
											loadingText:"正在获取数据"//加载（获取）列表项数据时的提示。
										},
										queryParam:"threeIndicator",//列表上方的查询框的name属性，服务器根据此参数查询所需的列表项数据
										minChars:1,//下拉列表自动选择用户需要输入的最小字符数量
										queryDelay:300,//从字符键入到发送查询之间的等待时间
										allQuery:"allNames",
												
										displayField:"zbName",//列表中的显示文字
										valueField:"id",//类表项的value值
										queryMode:"local",//数据的获取模式。local为本地数据，remote为远程服务器上数据
										forceSelection:true,//迫使用户输入查询条件，必须在列表中存在，如果不存在则取默认的列表项。false，则允许输入任意字符，如果输入的值列表项中不存在，也会显示输入的值
										typeAhead:true,//根据输入信息，自动选择匹配的列表项。
										//value:"222222",//默认选择的列表项，这里指“北京”。这里写valueField的某个值
												
										triggerAction:"all",//**值为all时，将使用allQuery中的查询条件进行查询。**
												
										store:l_33IndicatorInfordata//数据源
									}
									]
								},
							//字段集2
							{
								xtype:"fieldset",
								title : "<font size='2' style='font-weight:bold'>指标小项</font>",
								columnWidth:.50,
								layout:"column",
								style:"margin-right:10px",
								//layout:"form",
								items:[
									//三级指标下的各个名称
									//所占分数。
									{
										xtype:"textfield",
										name:"zbPoint",
										id:"zbPointdata",
										fieldLabel:"所占分数",
										readOnly:true,
										allowDecimals:true,//允许小数
										decimalPrecision:2
									},
									//2013年公布的权重
									{
										xtype:"textfield",
										name:"zbWeight",
										id:"zbWeightdata",
										fieldLabel:"2013年公布的权重",
										readOnly:true/* ,
										allowDecimals:true,
										decimalPrecision:2,
										maxValue:1,
										maxText:"最大值不超过1"//超过最大值后的提示 */
									},
									//负责人
									{
										xtype:"textfield",
										fieldLabel:"负责人",
										readOnly:true,
										name:"zbPrincipal",
										id:"zbPrincipaldata"/* ,
										//emptyText:"这里输入二级指标名称",//默认显示的文本
										allowBlank:false,//不为空
										blankText:"请填写负责人" */
									},
									//指标目标值
									{
										xtype:"textfield",
										fieldLabel:"指标目标值",
										readOnly:true,
										name:"zbTarget",
										id:"zbTargetdata",
										allowBlank:true//可为空
									},
									//指标来源
									{
										xtype:"textfield",
										fieldLabel:"指标来源",
										name:"zbSource",
										id:"zbSourcedata",
										readOnly:true,
										allowBlank:true//可为空
									},
									//指标当前值
									{
										xtype:"textfield",
										fieldLabel:"指标当前值",
										readOnly:true,
										name:"zbNow",
										id:"zbNowdata",
										allowBlank:true
									},
									//数据收集日期
									{
										xtype:"textfield",
										fieldLabel:"数据收集日期",
										readOnly:true,
										name:"zbTime",
										id:"zbTimedata",
										allowBlank:true
									},
									//指标考核频度
									{
										xtype:"textfield",
										fieldLabel:"指标考核频度",
										readOnly:true,
										name:"zbFrequentness",
										id:"zbFrequentnessdata",
										allowBlank:true
									}
								]
							}
						]
						
					},
					//第二行
					/* {
						
						layout:"column",
						xtype:"fieldset",
						id:"dataField",
						title:"指标填写",
						style:"margin-top:20px;WIDTH:40px",
						items:[
							
						]
						
					} */
					{
						layout:"column",
						xtype:"fieldset",
						id:"dataField",
						title : "<font size='2' style='font-weight:bold'>指标填写</font>",
						columnWidth:1.0,
						items:[
							/* {
								xtype:"datefield",
								fieldLabel:"请选择具体日期",
								name:"zbDate",
								id:"zbDate3data2",
								invalidText:"请按照xxx格式选择或填写日期",
								format:"Y年m月d日"
							},
							{
								xtype:"textfield",
								fieldLabel:"指标值",
								name:"zbData",
								id:"zbData",
								emptyText:"这里输入具体指标值"
							} */
							{xtype:"textfield",fieldLabel:"21号",id:"day1",emptyText:"这里输入具体指标值",columnWidth:.20,width:80,style:'color:green;font-family:黑体;font-size:;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"22号",id:"day2",columnWidth:.20,width:80,style:'color:green;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"23号",id:"day3",columnWidth:.20,width:80,style:'color:green;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"24号",id:"day4",columnWidth:.20,width:80,style:'color:green;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"25号",id:"day5",columnWidth:.20,width:80,style:'color:green;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"26号",id:"day6",columnWidth:.20,width:80,style:'color:green;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"27号",id:"day7",columnWidth:.20,width:80,style:'color:green;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"28号",id:"day8",columnWidth:.20,width:80,style:'color:green;margin-left:5px;margin-right:5px'},
							//上个月29、30、31日
							{xtype:"textfield",fieldLabel:"29号",id:"day9",columnWidth:.20,width:80,style:'color:red;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"30号",id:"day10",columnWidth:.20,width:80,style:'color:red;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"31号",id:"day11",columnWidth:.20,width:80,style:'color:red;margin-left:5px;margin-right:5px'},
							
							{xtype:"textfield",fieldLabel:"01号",id:"day12",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"02号",id:"day13",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"03号",id:"day14",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"04号",id:"day15",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"05号",id:"day16",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"06号",id:"day17",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"07号",id:"day18",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"08号",id:"day19",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"09号",id:"day20",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"10号",id:"day21",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"11号",id:"day22",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"12号",id:"day23",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"13号",id:"day24",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"14号",id:"day25",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"15号",id:"day26",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"16号",id:"day27",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"17号",id:"day28",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"18号",id:"day29",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"19号",id:"day30",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'},
							{xtype:"textfield",fieldLabel:"20号",id:"day31",columnWidth:.20,width:80,style:'color:brown;margin-left:5px;margin-right:5px'}
						]
					}
				]
				,
								buttons:[
									{text:"提交",handler:getPartSubmit},
									{text:"重置",handler:resetThreedata}
								]
			});
			//选择日期触发时间事件
			Ext.getCmp("zbDate3data").on("select",getOne);
			//选择一级指标触发事件（查询对应的二级指标）
			Ext.getCmp("three_oneindicatorNamedata").on("select",getTwo);
			//选择二级指标触发事件（查询对应的三级指标）
			Ext.getCmp("three_twoindicatorNamedata").on("select",getThree);
			//选择三级指标触发事件（查询对应的三级指标内的各个指标）
			Ext.getCmp("three_threeindicatorNamedata").on("select",getThreeData);
			/* //选择具体指标值的日期。先查询，有则修改，无则添加
			Ext.getCmp("zbDate3data2").on("select",getDateData); */
			
			//全局的所选的日期
			var date="您没有选择日期！";
			var dateFormat = "";
			//根据日期，获取一级指标；设置某些月份29，30,31天为disable。
			function getOne(){
				//将下面的组件值都置空
				for(var k=0;k<31;k++){
					Ext.getCmp("day"+(k+1)).setValue("");
				}
				
				//获取时间选择
				date = Ext.getCmp("zbDate3data").getValue();
				//格式化日期
				dateFormat = Ext.util.Format.date(date, 'Y-m');
				//alert(dateFormat);
				//重新设置数据源中的代理的url	
				var url = "dqOneAction.do?date="+dateFormat;//"dqOneAction.do?date="+date;"threeqoneAction.do?date="+date
				l_31IndicatorInfordata.proxy.url = url;
				/* //推荐使用这种动态设置url方式
				l_31IndicatorInfordata.proxy.on("beforeload",function(p,params){
					params.searchTerm  = "123";
				}); */
				//加载所需数据
				l_31IndicatorInfordata.load();
				
				
				var year = parseInt(dateFormat.substring(0,4));//年
				//这个月.将字符串转成整数，避免个位数出现“04”现象
				var month = parseInt(dateFormat.substring(5,7));
				//alert("格式化后的日期是： " +dateFormat);
				//alert(year);
				//alert(month);
				//调用上个月的总天数
				var dayTotal = days(year,month-1);
				//alert("上个月有 "+dayTotal+" 天");
				//先让所有的有效
				for(var k=0;k<31;k++){
					Ext.getCmp("day"+(k+1)).setDisabled(false);
				}
				//设置某些天数的文本框失效
				if(dayTotal==30){
					Ext.getCmp("day11").setDisabled(true);
				}else if(dayTotal==28){
					Ext.getCmp("day9").setDisabled(true);
					Ext.getCmp("day10").setDisabled(true);
					Ext.getCmp("day11").setDisabled(true);
				}else if(dayTotal==29){
					Ext.getCmp("day10").setDisabled(true);
					Ext.getCmp("day11").setDisabled(true);
				}
			}
			//根据一级指标，获取二级指标
			function getTwo(){
				//获取所选择的一级指标的id
				var id = Ext.getCmp("three_oneindicatorNamedata").getValue();
				//alert("所选的值是： "+id);
				var url2 = "didqAction.do?tableName=ZbL2&date="+dateFormat+"&id="+id;
				l_32IndicatorInfordata.proxy.url = url2;
				l_32IndicatorInfordata.load();
			}
			//根据二级指标，获取三级指标
			function getThree(){
				//获取所选择的二级指标的id
				var id = Ext.getCmp("three_twoindicatorNamedata").getValue();
				//alert("所选的值是： "+id);
				var url2 = "didqAction.do?tableName=ZbDetail&date="+dateFormat+"&id="+id;
				l_33IndicatorInfordata.proxy.url = url2;
				l_33IndicatorInfordata.load();
				
				//读取回传的json数据
				//l_33IndicatorInfordata.proxy.read(new Ext.date.Operation(),setForm);
			}
			//根据三级指标，获取三级指标内各个指标。已经加载到客户端了。
			function getThreeData(){
				//alert(l_33IndicatorInfordata.getTotalCount());//先选择三级指标项
				//var a = l_33IndicatorInfordata.getAt(0);
				
				//获取所选择的三级指标的id
				var id = Ext.getCmp("three_threeindicatorNamedata").getValue();
				//遍历返回的三级指标数据源，找到与选择的三级指标id相同一条记录
				l_33IndicatorInfordata.each(function (rec){
					if(rec.get("id")==id){
						Ext.getCmp("zbPointdata").setValue(rec.get("zbPoint"));
						Ext.getCmp("zbWeightdata").setValue(rec.get("zbWeight"));
						Ext.getCmp("zbPrincipaldata").setValue(rec.get("zbPrincipal"));
						Ext.getCmp("zbTargetdata").setValue(rec.get("zbTarget"));
						Ext.getCmp("zbSourcedata").setValue(rec.get("zbSource"));
						Ext.getCmp("zbNowdata").setValue(rec.get("zbNow"));
						Ext.getCmp("zbTimedata").setValue(rec.get("zbTime"));
						Ext.getCmp("zbFrequentnessdata").setValue(rec.get("zbFrequentness"));
						
						return false;
					}
				});
				
				//根据三级id和日期，查询三级指标下这个月的所有具体指标
				var id = Ext.getCmp("three_threeindicatorNamedata").getValue();//获取所选择的三级指标的id
				var date = Ext.getCmp("zbDate3data").getValue();//获取具体指标日期
				var dateFormat = Ext.util.Format.date(date,"Y-m");
				//alert("父id是： "+id+" 查询日期是： "+dateFormat);
				var urlData = "datequerydataAction.do?id="+id+"&date="+dateFormat;
				//alert(urlData);
				dataStore.proxy.url = urlData;
				dataStore.load({
					scope:this,
					//在load的回调函数中，给文本框赋值。否则异步总是在第二次load时，给文本框赋值。
					callback:function(records, operation, success){
						//var indexData = records[0].get("zbData");//获取数据源中值
						//alert("此具体指标的id是： "+dataStore.getAt(0).get("id")+" 值是： "+indexData);
						//给文本框赋值(指标id给文本框的name，指标值给value)
						//alert("记录个数是： "+records.length);
						for(var i=0;i<records.length-20;i++){
							//alert("指标id是： "+records[i].get("id")+"指标值是："+records[i].get("zbData")+"日期是： "+records[i].get("zbDate"));
							var id = "day"+(i+1);
							//alert(id);
							Ext.getCmp(id).setValue(records[i].get("zbData"));
							Ext.getCmp(id).name = records[i].get("id");
							//alert(Ext.getCmp(id).getValue());
							//alert(Ext.getCmp(id).name);
						}
						for(var i=records.length-20;i<records.length;i++){
							//alert("指标id是： "+records[i].get("id")+"指标值是："+records[i].get("zbData")+"日期是： "+records[i].get("zbDate"));
							var id = 0;
							switch(records.length){
								case 31:id="day"+(i+1);break;
								case 30:id="day"+(i+2);break;
								case 29:id="day"+(i+3);break;
								case 28:id="day"+(i+4);break;
							}
							//alert(id);
							Ext.getCmp(id).setValue(records[i].get("zbData"));
							Ext.getCmp(id).name = records[i].get("id");
							//alert(Ext.getCmp(id).getValue());
							//alert(Ext.getCmp(id).name);
						}
					}
				});
			}
			/* //根据三级id和日期，查询三级指标下某天的具体指标
			function getDateData(){
				var id = Ext.getCmp("three_threeindicatorNamedata").getValue();//获取所选择的三级指标的id
				var date = Ext.getCmp("zbDate3data2").getValue();//获取具体指标日期
				var dateFormat = Ext.util.Format.date(date,"Y-m-d");
				//alert("父id是： "+id+" 查询日期是： "+dateFormat);
				var urlData = "datequerydataAction.do?id="+id+"&date="+dateFormat;
				//alert(urlData);
				dataStore.proxy.url = urlData;
				dataStore.load({
					scope:this,
					//在load的回调函数中，给文本框赋值。否则异步总是在第二次load时，给文本框赋值。
					callback:function(records, operation, success){
						var indexData = records[0].get("zbData");//获取数据源中值
						//alert("此具体指标的id是： "+dataStore.getAt(0).get("id")+" 值是： "+indexData);
						//给文本框赋值
						Ext.getCmp("day21").setValue(indexData);
					}
				});
			} */
			//异步提交表单部分数据
			function getPartSubmit(){
				//获取选择的三级指标
				//获取所选择的三级指标的id
				var id = Ext.getCmp("three_threeindicatorNamedata").getValue();
				if(id==null){
					Ext.MessageBox.alert("提示","请先选择指标大项！");
					return;
				}
				
				//数组存放修改后的指标值
				var data_values = [];
				//指标值对应的id
				var data_ids = [];
				//获取所有填写的指标值(非disabled的组件)
				for(var i=0;i<31;i++){
					if(Ext.getCmp("day"+(i+1)).disabled==false){
						var data_value = Ext.getCmp("day"+(i+1)).getValue();//具体指标值
						var data_id = Ext.getCmp("day"+(i+1)).name;//具体指标值
						data_values.push(data_value);
						data_ids.push(data_id);
					}
				}
				/* Ext.Array.forEach(data_values,function(value,index){
					alert("数组："+value+"---"+index);
				}); */
				
				
				//var data_month = ; 
				var requestConfig = {
					url:"dataAction!addDataIndex.do",
					method:"POST",
					params:{
						data_values:data_values,//修改后的具体指标值
						data_ids:data_ids//具体指标的id
					},
					//callback不管服务器处理失败与否，都执行
					/* callback:function(options,issuccess,backresponse){
						Ext.MessageBox.alert("提示","提交成功！");
						//alert("请求是否成功： "+issuccess+"\n服务器返回值： "+backresponse.responseText);
					} */
					//请求成功。服务器响应、请求的参数
					success:function(response,options){
						Ext.MessageBox.alert("提示","提交成功！");
					},
					failure:function(response,options){
						Ext.MessageBox.alert("提示","提交失败！");
					}
				};
				Ext.Ajax.request(requestConfig);
			}
			//重置
			function resetThreedata(){
				inSubmitForm.getForm().reset();
			}
			
			//判断上个月的总天数
			function days(year,month){
				if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
					//alert("这些月有31天");
					totalDay = 31;
				}else if(month==2){//判断闰年
					if((year%4==0)&&(year%100!=0)||(year%400==0)){
						totalDay = 29;
					}else{
						totalDay = 28;
					}
				}else{
					//alert("这些月有30天");
					totalDay = 30;
				}
				return totalDay;
			}
		});
	</script>
  </head>
  
  <body>
	<div class="header">
		<div class="welcome">
    		<p class="welcome">欢迎您！<%=((ZbUser)session.getAttribute("zbUser")).getRealname() %></p>
    	</div>
    	<div class="login">
    		<a href="<%=path %>/index.jsp">回到登陆页</a>
    	</div>
    	<div class="login">&nbsp;</div>
    	<div class="login">&nbsp;</div>
    	<div class="login">&nbsp;</div>
    	<div class="login">&nbsp;</div>
    	<div class="login">&nbsp;</div>
		<div class="checkout">
    		<a href="nocache.jsp">退出</a>
    	</div>
	</div>
	<br>
	<br>
	<div id="form_layout" style="text-align:center;  margin-left:auot;margin-rigth:auto;">
	
	</div>
	
  </body>
</html>
