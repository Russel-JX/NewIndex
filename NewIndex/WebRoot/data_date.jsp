<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>data_date.jsp 每个月的指标值，表单实现</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ext-4.0/resources/css/ext-all.css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/ext-4.0/ext-all.js"></script>

	<script type="text/javascript">
 		Ext.onReady(function(){
 			//alert(time);
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
					},
					
					//三级指标下的各个名称
					//所占分数。
					{
						xtype:"numberfield",
						name:"zbPoint",
						id:"zbPointdata",
						fieldLabel:"所占分数",
						readOnly:true,
						allowDecimals:true,//允许小数
						decimalPrecision:2
					},
					//2013年公布的权重
					{
						xtype:"numberfield",
						name:"zbWeight",
						id:"zbWeightdata",
						fieldLabel:"2013年公布的权重",
						readOnly:true,
						allowDecimals:true,
						decimalPrecision:2,
						maxValue:1,
						maxText:"最大值不超过1"//超过最大值后的提示
					},
					//负责人
					{
						xtype:"textfield",
						fieldLabel:"负责人",
						readOnly:true,
						name:"zbPrincipal",
						id:"zbPrincipaldata",
						//emptyText:"这里输入二级指标名称",//默认显示的文本
						allowBlank:false,//不为空
						blankText:"请填写负责人"
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
				], 
				buttons:[
					{text:"异步提交表单部分数据",handler:getPartSubmit},
					{text:"提交",handler:submitThreedata},
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
			
			
			//全局的所选的日期
			var date="您没有选择日期！";
			//根据日期，获取一级指标
			function getOne(){
				//获取时间选择
				date = Ext.getCmp("zbDate3data").getValue();
				//alert(date);
				//重新设置数据源中的代理的url	
				var url = "dqOneAction.do?date="+date;//"dqOneAction.do?date="+date;"threeqoneAction.do?date="+date
				l_31IndicatorInfordata.proxy.url = url;
				/* //推荐使用这种动态设置url方式
				l_31IndicatorInfordata.proxy.on("beforeload",function(p,params){
					params.searchTerm  = "123";
				}); */
				//加载所需数据
				l_31IndicatorInfordata.load();
			}
			//根据一级指标，获取二级指标
			function getTwo(){
				//获取所选择的一级指标的id
				var id = Ext.getCmp("three_oneindicatorNamedata").getValue();
				//alert("所选的值是： "+id);
				var url2 = "didqAction.do?tableName=ZbL2&date="+date+"&id="+id;
				l_32IndicatorInfordata.proxy.url = url2;
				l_32IndicatorInfordata.load();
			}
			//根据二级指标，获取三级指标
			function getThree(){
				//获取所选择的二级指标的id
				var id = Ext.getCmp("three_twoindicatorNamedata").getValue();
				//alert("所选的值是： "+id);
				var url2 = "didqAction.do?tableName=ZbDetail&date="+date+"&id="+id;
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
			}
			//异步提交表单部分数据
			function getPartSubmit(){
				var requestConfig = {
					url:"testAction!addOneIndex.do",
					method:"get",
					params:{
						zbDate:Ext.getCmp("zbDate3data").getValue(),//所选的日期
						zbName:"你好",
						zbSort:500
					},
					callback:function(options,issuccess,backresponse){
						alert("请求是否成功： "+issuccess+"\n服务器返回值： "+backresponse.responseText);
					}
				};
				Ext.Ajax.request(requestConfig);	
			}
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
		});
	</script>
  </head>
  
  <body>
    <font color="red" size="3">具体月份内具体指标的数据 <br></font>
  </body>
</html>
