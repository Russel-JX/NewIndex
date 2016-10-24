/*填写具体指标值js文件*/
			//三级指标数据源
			var myL3Fields =["id", "parentId", "zbName","zbPoint","zbWeight","zbPrincipal","zbTarget","zbSource","zbNow",
			"zbTime","zbFrequentness","realname","result"
				];//,"data[0].zbData","data[1].zbData"
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
			//定义表格列。（数组）
			var myColumns = [
						//行号列。自增。
						Ext.create("Ext.grid.RowNumberer",{text:"行号",width:30}),
						{header:"一级指标名称",width:100,dataIndex:"parentId",renderer:getL1Name},
						{header:"二级指标名称",width:100,dataIndex:"parentId",renderer:getL2Name},
						//{header:"id",width:50,dataIndex:"l3id"},
						{header:"三级指标名称",width:150,dataIndex:"zbName"},
						{header:"指标总权重",width:40,dataIndex:"zbPoint"},
						{header:"指标子权重",width:40,dataIndex:"zbWeight"},
						{header:"得分统计",width:100,dataIndex:"result"},
						{header:"指标负责人",width:100,dataIndex:"realname"},
						{header:"指标目标值",width:100,dataIndex:"zbTarget"},
						{header:"指标来源",width:200,dataIndex:"zbSource"},
						{header:"指标当前值",width:100,dataIndex:"zbNow"},
						{header:"数据收集日期",width:100,dataIndex:"zbTime"},
						{header:"指标考核频度",width:100,dataIndex:"zbFrequentness"}
					];
			
			var inMatForm = Ext.create("Ext.form.Panel",{
				id:"inMatForm",
				title:"指标浏览",
				renderTo:Ext.getBody(),
				width:1277,
				height:580,
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
						format:"Y年m月"//格式化日期格式，这样后台接受时就能直接接受成Date类型。
					}]
			});
			
			
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
				
				//判断上个月
				var lastMonth = getLastMonth(month);
				//上个月的天数
				var totalDays = getDays(year,lastMonth);
				//根据选择的年月，重新定义表格的“数据源”和“列”
				//创建新空间存放新数组，保证不再原始数组上连续添加元素
				var newMyColumns = Ext.Array.clone(myColumns);//克隆（赋值）数组
				var newMyL3Fields = Ext.Array.clone(myL3Fields);
				//alert(newMyColumns.length+"***"+newMyL3Fields.length);
				//newMyColumns = myColumns;
				//newMyL3Fields = myL3Fields;
				for(var i=21;i<=totalDays;i++){//上个月（21号到月末），数据源中第0到第totalDays-21条记录
					newMyColumns.push({header:"<font color='blue'>"+(year+"-"+lastMonth+"-"+i)+"</font>",width:150,dataIndex:"data["+(i-21)+"].zbData"});//表格中的列
					newMyL3Fields.push("data["+(i-21)+"].zbData");//数据源中的fileds
				}
				for(var i=0;i<20;i++){//本月（1号到20号），数据源中第totalDays-21+1条记录到totalDays-21+1+20条记录
					newMyColumns.push({header:"<font color='blue'>"+(year+"-"+month+"-"+(i+1))+"</font>",width:150,dataIndex:"data["+(i+totalDays-21+1)+"].zbData"});//表格中的列
					newMyL3Fields.push("data["+(i+totalDays-21+1)+"].zbData");//数据源中的fileds
				}
				
				//调试fields和columns
				/* for(var i=0;i<newMyL3Fields.length;i++){
					alert(newMyL3Fields[i]);
				}
				for(var i=0;i<newMyColumns.length;i++){
					if(i==0){
						alert("第一列");
					}else{
						alert(newMyColumns[i].header+"--"+newMyColumns[i].dataIndex);
					}
					
				} */
				
				//Ext.getCmp("inMatForm2").reconfigure(l3store,newMyColumns);
				
				var l3store = new Ext.data.Store({
					proxy:{
						type:"ajax",
						url:"threeqthreeAction.do",
						reader:{
							type:"json",
							root:"items_l3_data"//后台的reader根变了
						}
					},
					fields: newMyL3Fields,//parentId是id的父类id
					sorters:[//已经渲染的数据的排序规则，可同时定义多个规则
						{
							property:"id",
							direction:"ASC"
						}
					],
					autoLoad:false
				});
				
				//加载数据源时，添加遮罩，让用户等待
				var loadMarsk = new Ext.LoadMask(document.body, {
				    msg : '数据获取中...',
				    disabled : false,
				    store : l3store// 完成后移除遮罩
				});

				
				
				//重新设置数据源中的代理的url	
				var urll1 = "threeqoneAction.do?date="+dateFormat;
				var urll2 = "threeqtwoAction!fetchOneByDate.do?date="+dateFormat;
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
									callback:function(records, operation, success){
										//调试三级指标中的fields和数据的顺序
										/**数据源中的记录（records），**/
										/* for(var i=0;i<records.length;i++){
											alert("第"+(i+1)+"个记录是"+records[0].get("data["+i+"].zbData"));//
										} */
									
										//调试返回的json数据
										//alert("一级指标返回的记录数是： "+l1store.getCount());
										//alert("二级指标返回的记录数是： "+l2store.getCount());
										//alert("三级指标返回的记录数是： "+l3store.getCount());
										
										//创建表格面板
										//var grid = 
										//将表格作为表单的子组件加入
										if(Ext.getCmp("inMatForm2")==null){
											inMatForm.add(
												Ext.create("Ext.grid.Panel",{//var inMatForm = 
													title:"指标浏览",
													id:"inMatForm2",
													renderTo:Ext.getBody(),
													width:1200,
													height:500,
													frame:true,
													
													layout:'fit',
													
													//三级指标作为根
													store:l3store,
													//配置表格列。数组,数组的每个元素定义表中的一列。创建；列的方式有两种：1.new方式；2.xtype方式。
													columns:newMyColumns
												})
											);
										}else{//已有。先删除之前的，再加入新的表格
											inMatForm.remove(Ext.getCmp("inMatForm2"));
											inMatForm.add(
												Ext.create("Ext.grid.Panel",{//var inMatForm = 
													title:"指标浏览",
													id:"inMatForm2",
													renderTo:Ext.getBody(),
													width:1100,
													height:400,
													frame:true,
													
													layout:'fit',
													
													//三级指标作为根
													store:l3store,
													//配置表格列。数组,数组的每个元素定义表中的一列。创建；列的方式有两种：1.new方式；2.xtype方式。
													columns:newMyColumns
												})
											);
										}
										Ext.getCmp("inMatForm2").reconfigure(l3store,newMyColumns);//不需要这句！
									}
								});	
								
							}
						});
					}
				});
			}
			
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
			//用此数据源级联，其他数据源中的记录
			function getL2Name(value,metadata,record,rowIndex,colIndex,view){
				return (l2store.findRecord("id",value,0,false,false,true)).get("zbName");
			}
			function getL1Name(value,metadata,record,rowIndex,colIndex,view){
				return l1store.findRecord("id",(l2store.findRecord("id",value,0,false,false,true)).get("parentId"),0,false,false,true).get("zbName");
			}
			/* function getTongJi(value,metadata,record,rowIndex,colIndex,view){
				var v;
				
				//alert("data['"+0+"'].zbData");
				//alert(record.get("data['"+0+"'].zbData"));
				//alert(record.get("zbName"));
				for(var i=0;i<31;i++){
					if(record.get("data['"+i+"'].zbData")!=null){
						v+=data[i].zbData;	
					}		
				}
				//alert(v);
				//data["+(i-21)+"].zbData
				return v; 
			} */
			//判断上个月
			function getLastMonth(month){
				var lastMonth = 0; 
				if(month==1){
					lastMonth = 12;
				}else{
					lastMonth = month-1;
				}
				return lastMonth;
			}
			//判断上个月的总天数
			function getDays(year,month){
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