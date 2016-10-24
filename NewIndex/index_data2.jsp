<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>index_data2.jsp 增加具体填写指标部分</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ext-4.0/resources/css/ext-all.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/ext-all.js"></script>
	
	<script type="text/javascript">
	Ext.onReady(function(){
	
	
		/*填写具体指标值js文件*/
			//三级指标数据源
			var l3store = new Ext.data.Store({
				proxy:{
					type:"ajax",
					url:"threeqthreeAction.do",
					reader:{
						type:"json",
						root:"items_l3_data"//后台的reader根变了
					}
				},
				fields: ["id", "parentId", "zbName","zbPoint","zbWeight","zbPrincipal","zbTarget","zbSource","zbNow","zbTime","zbFrequentness",
					"data[0].zbData"
				],//parentId是id的父类id
				autoLoad:false
			});
			//二级指标数据源
			var l2store = new Ext.data.Store({
				proxy:{
					type:"ajax",
					url:"threeqtwoAction.do",
					reader:{
						type:"json",
						root:"items_l2"
					}
				},
				//id: 'id',
				fields:["id","parentId","zbName"],
				autoLoad:false
			});
			//一级指标数据源
			var l1store = new Ext.data.Store({
				proxy:{
					type:"ajax",
					url:"threeqoneAction.do",
					reader:{
						type:"json",
						root:"items_l1"
					}
				},
				fields:["id","zbName"],
				autoLoad:false
			});
			
//			//加载一级数据源
//			l1store.load({scope: this,callback:function(){
//				//alert("store2中返回的单页记录数"+store2.getCount());
//				//alert("store2中返回的总记录数"+store2.getTotalCount());
//				
//				//var index = store2.find("id2",1,0,true,false,false);
//				//var rec = store2.findRecord("id2",1,0,true,false,false);
//				//alert("store2中从第二位开始id为1的索引是： "+index);
//				//alert("store2中从第二位开始id为1的address是： "+rec.get("address"));
//			}}); 
//			//加载二级据源
//			l2store.load({scope: this,callback:function(){
//				//alert("store2中返回的单页记录数"+store2.getCount());
//				//alert("store2中返回的总记录数"+store2.getTotalCount());
//				
//				//var index = store2.find("id2",1,0,true,false,false);
//				//var rec = store2.findRecord("id2",1,0,true,false,false);
//				//alert("store2中从第二位开始id为1的索引是： "+index);
//				//alert("store2中从第二位开始id为1的address是： "+rec.get("address"));
//			}}); 
			
			var inMatForm = Ext.create("Ext.form.Panel",{
				id:"inMatForm",
				title:"指标维护",
				renderTo:Ext.getBody(),
				width:1177,
				height:803,
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
				//设置viewport的子组件内容较多时，自动创建滚动条
				autoScroll:true,
				//异步提交
				standardSubmit:false,
		
				items:[
					{
						xtype:"datefield",
						name:"zbDate",
						id:"data_date",
						fieldLabel:"请选择日期",
						invalidText:"请按照xxx格式选择或填写日期",
						format:"Y年m月d日"//格式化日期格式，这样后台接受时就能直接接受成Date类型。
					},
			//创建表格面板
			Ext.create("Ext.grid.Panel",{//var inMatForm = 
					title:"指标维护",
					id:"inMatForm2",
					renderTo:Ext.getBody(),
					width:1100,
					height:800,
					frame:true,
					
					layout:'fit',
					
					//三级指标作为根
					store:l3store,
					//配置表格列。数组,数组的每个元素定义表中的一列。创建；列的方式有两种：1.new方式；2.xtype方式。
					columns:[
						//header,表头信息；width，列宽；dataIndex，数据源中的属性；renderer，函数，用来格式化单元格中数据；7种类类型，也用来格式化段元个数据。
						//行号列。自增。
						Ext.create("Ext.grid.RowNumberer",{text:"行号",width:30}),
						{header:"一级指标名称",width:100,dataIndex:"parentId",renderer:getL1Name},
						{header:"二级指标名称",width:100,dataIndex:"parentId",renderer:getL2Name},
						//{header:"id",width:50,dataIndex:"l3id"},
						{header:"三级指标名称",width:150,dataIndex:"zbName"},
						{header:"指标总权重",width:40,dataIndex:"zbPoint"},
						{header:"指标子权重",width:40,dataIndex:"zbWeight"},
						{header:"指标负责人",width:100,dataIndex:"zbPrincipal"},
						{header:"指标目标值",width:100,dataIndex:"zbTarget"},
						{header:"指标来源",width:200,dataIndex:"zbSource"},
						{header:"指标当前值",width:100,dataIndex:"zbNow"},
						{header:"数据收集日期",width:100,dataIndex:"zbTime"},
						{header:"指标考核频度",width:40,dataIndex:"zbFrequentness"},
						{header:"<font color='blue'>日子</font>"+dateFormat+new Date(),width:100,dataIndex:"data[0].zbData"}
					],
					//添加新建、修改、删除工具栏
				tbar:[
					{
						text:"修改列名",
						id:"newUser",
						tooltip:"创建",
						iconCls:"addIcon",
						handler:modifyColumnName
					}
				]
			})]
			});
			/*
				renderer属性有如下配置，按顺序分别是：
					1.value（Mixed），单元格的原始值
					2.metadata(Object),包含了单元格的样式信息。有：
						2.1.tdCls(String),样式名称。类似html中的class。
						2.2.tdAttr(String),一个HTMl属性定义字符串。
						2.3.style(String),具体的样式配置
					3.record（Ext.data.Model），当前数据记录对象，包括该行的其他数据。
					4.rowIndex（Number）,当前单元格的行索引
					5.colIndex（Number）,当前单元格的列索引
					6.view（Ext.view.View），当前表格视图
			*/
			
			//修改列名
			function modifyColumnName(){
				alert("修改列名！");
				Ext.getCmp("inMatForm2").columns[12].setText("fff");
				alert(inMatForm.getChildByElement("inMatForm2").getColumnModel().getColumn(0).setHeader("modified"));
				Ext.getCmp("inMatForm2").getColumnModel().getColumn(0).setHeader("modified");
				alert(44);
			}
			
			//选择日期触发时间事件，查询这个月的所有信息
			Ext.getCmp("data_date").on("select",getThree);
			
			var dateFormat = "";
			function getThree(){
				//获取时间选择
				date = Ext.getCmp("data_date").getValue();
				//格式化日期，截取年月信息
				dateFormat = Ext.util.Format.date(date, 'Y-m');
				var year = Ext.util.Format.date(date, 'Y');
				var month = Ext.util.Format.date(date, 'm');
				//alert(year);
				//alert(month);
				//alert(date);
				//alert(dateFormat);
				//重新设置数据源中的代理的url	
				var urll1 = "threeqoneAction.do?date="+dateFormat;
				var urll2 = "threeqtwoAction.do?date="+dateFormat;
				var urll3 = "threeqthreedataAction.do?date="+dateFormat;
				l1store.proxy.url = urll1;
				l2store.proxy.url = urll2;
				l3store.proxy.url = urll3;
				//加载所需数据。加载顺序不能颠倒
				l1store.load({
					scope:this,
					callback:function(){
						l2store.load({
							callback:function(){
								l3store.load({
									callback:function(){
										//调试返回的json数据
										alert("一级指标返回的记录数是： "+l1store.getCount());
										alert("二级指标返回的记录数是： "+l2store.getCount());
										alert("三级指标返回的记录数是： "+l3store.getCount());
									}
								});	
								
							}
						});
						
						
					}
				});
				
				
				
				
				//设置表格的日期列名
				//获取表格的列对象数组
				var colus = Ext.getCmp("inMatForm2").columns;
				//设置时间列的列名。从上个月21号，到下个月20号。注意跨年
				colus[12].setText(year+"-"+(month-1)+"-21");
				
				//隐藏某一列。隐藏第一列
				colus[1].hide();
				
			}
//			alert(77);
//			alert("^^^"+Ext.getCmp("inMatForm2").getGridEl());
//			alert(88);
			
//			Ext.getCmp("inMatForm2").store.load({scope: this,callback:function(){
//				 //非分页的数据源，也能获取记录总数
//				alert("myGrid.store中返回的单页记录数"+myGrid.store.getCount());
//				alert("myGrid.store中返回的总记录数"+myGrid.store.getTotalCount());
//				//store的find(a,b,c,d,e,f)方法，从第c为索引位置开始，查找数据源中的记录中的a属性值为b的记录的索引(第一条记录索引为0),找不到返回-1。d为true，则所查找的值和记录中的值完全一致才能找到，false,只要b在记录中就能找到。e是否大小写敏感。
//				alert("查询出id是2所在数据源的索引是： "+myGrid.store.find("id",2,0,true,false,false));
//				//store的findRecord(a,b,c,d,e,f)方法。查找数据源中某个记录的a属性值为b的记录。参数同store.find()方法的参数。
//				alert("查询出id是2对应记录中的id是： "+myGrid.store.findRecord("id",2,0,true,false,false).get("id")); 
//			}});
			
			
			
			
			//用此数据源级联，其他数据源中的记录
			function getL2Name(value,metadata,record,rowIndex,colIndex,view){
				//alert(record.get("name"));
				//alert(myGrid.store.getById(value).get("name"));
				//alert(store2.getById(value));
				
				//alert("父亲的属性值是： "+value);
				//alert("孩子的地址是： "+store2.findRecord("id2",value,0,true,false,false).get("address"));
				//alert("数据源2的记录数是： "+store2.getCount());
				//alert(store2.find("id2",1,0,true,false,false));
				return (l2store.findRecord("id",value,0,true,false,false)).get("zbName");
			}
			function getL1Name(value,metadata,record,rowIndex,colIndex,view){
				//alert(record.get("name"));
				//alert(myGrid.store.getById(value).get("name"));
				//alert(store2.getById(value));
				
				//alert("父亲的属性值是： "+value);
				//alert("孩子的地址是： "+store2.findRecord("id2",value,0,true,false,false).get("address"));
				//alert("数据源2的记录数是： "+store2.getCount());
				//alert(store2.find("id2",1,0,true,false,false));
				return l1store.findRecord("id",(l2store.findRecord("id",value,0,true,false,false)).get("parentId"),0,true,false,false).get("zbName");
			}
	
	});
	</script>

  </head>
  
  <body>
    This is my JSP page. <br>
  </body>
</html>
