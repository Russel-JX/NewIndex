/*	//定义数据模型。一级指标名称
	Ext.regModel(
		"l_31Indicators",
		{
			fields:["id_l_31","l_31IndicatorName"]
		}
	);
	Ext.regModel(
		"l_32Indicators",
		{
			fields:["id_l_32","l_32IndicatorName"]
		}
	);*/
	//定义数据源（Ajax方式,获取数组形式的数据）。一级指标
	var l_31IndicatorInfor = Ext.create("Ext.data.Store",{
		//model:"l_31Indicators",
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
	var l_32IndicatorInfor = Ext.create("Ext.data.Store",{
		//model:"l_32Indicators",
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
	//指标公式数据源
	var index_formula_store = Ext.create("Ext.data.Store",{
		fields:["id","name","description"],
		proxy:{
			type:"ajax",
			url:"formulaAction.do",
			reader:{
						type:"json",
						root:"formulas"
			}
		}
	});
	//用户（负责人）数据源
	var l_32UserInfor = Ext.create("Ext.data.Store",{
		fields:["userid","realname"],
		proxy:{
			type:"ajax",
			url:"uqAction!queryNormalAdministrtors.do",
			reader:{
						type:"json",
						root:"items",
						totalProperty:"total_user"
			}
		}
	});
	//创建三级指标表单
	var inThreeForm = Ext.create("Ext.form.Panel",{
		title:"三级指标",
		id:"inThreeForm",
		width:1177,
		height:383,
		frame:true,
		renderTo:Ext.getBody(),
		bodyStyle:"padding:5 5 5 5",
		//closable:true,
		
		layout:"absolute",
		
		autoScroll: true,//设置viewport的子组件内容较多时，自动创建滚动条
		//默认的字段属性配置
		defaults:{
			labelSeparator:":",
			labelWidth:150,
			labelAlign:"left",
			
			width:400,
			
			msgTarget:"side"
		},
		
		/*//增加一个监听。关闭此tab时，更新数组（记录已经存在的tab的id）
		listeners:{
			beforeclose:function(tabItem, e){
				alert("删除的id是"+tabItem.id);
				Ext.Array.remove(tab_id,tabItem.id);//在数组中，删除刚关闭的tab的id
				
				var center = Ext.getCmp("center");
				//center.remove(inOneForm);
				
				alert("数组长度"+tab_id.length);
			}
		},*/
		
		//异步提交
		standardSubmit:false,
		
		items:[
			{
				xtype:"datefield",
				
				x:460,
				
				fieldLabel:"请选择日期",
				width:300,
				name:"zbDate",
				id:"zbDate3",
				allowBlank:false,//不为空
				blankText:"请选择指标所属日期",
				invalidText:"请按照'xxxx年yy月zz日'格式选择或填写日期",
				format:"Y年m月", 
				submitFormat:"Y-m-d"
			},
			//下拉列表：一级
			{
				xtype:"combo",
				
				x:460,
				y:30,
				
				fieldLabel:"一级指标名称",
				name:"parentIdL1",
				id:"three_oneindicatorName",
				
				listConfig:{
					emptyText:"无此项选择",//当找的值不再列表项时的提示
					maxHeight:180,//表单的最大高度。所有列表项高度之和。
					minWidth:250,//最小宽度
					loadingText:"正在获取数据"//加载（获取）列表项数据时的提示。
				},
				queryParam:"date",//列表上方的查询框的name属性，服务器根据此参数查询所需的列表项数据
				minChars:1,//下拉列表自动选择用户需要输入的最小字符数量
				queryDelay:300,//从字符键入到发送查询之间的等待时间
				allQuery:"default1",//点击下拉列表右部时，发送的查询条件
						
				displayField:"zbName",//列表中的显示文字
				valueField:"id",//类表项的value值
				queryMode:"remote",//数据的获取模式。local为本地数据，remote为远程服务器上数据
				forceSelection:true,//迫使用户输入查询条件，必须在列表中存在，如果不存在则取默认的列表项。false，则允许输入任意字符，如果输入的值列表项中不存在，也会显示输入的值
				typeAhead:true,//根据输入信息，自动选择匹配的列表项。
				//value:"222222",//默认选择的列表项，这里指“北京”。这里写valueField的某个值
						
				triggerAction:"all",//**值为all时，将使用allQuery中的查询条件进行查询。**
						
				store:l_31IndicatorInfor//数据源
			},
			//下拉列表：二级
			{
				xtype:"combo",
				
				x:460,
				y:60,
				
				fieldLabel:"二级指标名称",
				name:"parentId",
				id:"three_twoindicatorName",
				
				listConfig:{
					emptyText:"无此项选择",//当找的值不再列表项时的提示
					maxHeight:180,//表单的最大高度。所有列表项高度之和。
					loadingText:"正在获取数据"//加载（获取）列表项数据时的提示。
				},
				queryParam:"date",//列表上方的查询框的name属性，服务器根据此参数查询所需的列表项数据
				minChars:1,//下拉列表自动选择用户需要输入的最小字符数量
				queryDelay:300,//从字符键入到发送查询之间的等待时间
				allQuery:"default12",
						
				displayField:"zbName",//列表中的显示文字
				valueField:"id",//类表项的value值
				queryMode:"remote",//数据的获取模式。local为本地数据，remote为远程服务器上数据
				forceSelection:true,//迫使用户输入查询条件，必须在列表中存在，如果不存在则取默认的列表项。false，则允许输入任意字符，如果输入的值列表项中不存在，也会显示输入的值
				typeAhead:true,//根据输入信息，自动选择匹配的列表项。
				//value:"222222",//默认选择的列表项，这里指“北京”。这里写valueField的某个值
						
				triggerAction:"all",//**值为all时，将使用allQuery中的查询条件进行查询。**
						
				store:l_32IndicatorInfor//数据源
			},
			//三级指标名称
			{
				xtype:"textfield",
				
				x:460,
				y:90,
				
				fieldLabel:"指标名称",
				
				name:"zbName",
				id:"zbName3",
				emptyText:"这里输入三级指标名称",//默认显示的文本
				allowBlank:false,//不为空
				blankText:"指标名称不能为空"
			},
			//排序规则
			{
				xtype:"numberfield",
				
				x:460,
				y:120,
				
				name:"zbSort",
				width:300,
				id:"zbSort3",
				fieldLabel:"排序规则",
				allowDecimals:false
				//vtype:"num"//过滤条件
				//nanText:"请输入数字"//错误提示
			},
			
			//所占分数。
			{
				xtype:"numberfield",
				
				x:460,
				y:150,
				
				name:"zbPoint",
				width:300,
				id:"point3",
				fieldLabel:"所占分数（指标总权重）",
				allowDecimals:true,
				maxValue:100,
				maxText:"总权重小于等于100",
				minValue:0,
				minText:"总权重大于等于0"
				
				//vtype:"num"//过滤条件
				//nanText:"请输入数字"//错误提示
			},
//			{
//				xtype:"textfield",
//				name:"zbPoint",
//				id:"point3",
//				fieldLabel:"所占分数"/*,
//				allowDecimals:true,//允许小数
//				decimalPrecision:2*/
//			},
			//2013年公布的权重
			{
				xtype:"numberfield",
				
				x:460,
				y:180,
				
				name:"zbWeight",
				width:300,
				id:"weight3",
				fieldLabel:"指标子权重",
				allowDecimals:true,
				maxValue:1,
				maxText:"子权重小于等于1",
				minValue:0,
				minText:"子权重大于等于0",
				step:0.1
				
				//vtype:"num"//过滤条件
				//nanText:"请输入数字"//错误提示
			},
//			{
//				xtype:"textfield",
//				name:"zbWeight",
//				id:"weight3",
//				fieldLabel:"2013年公布的权重"
//			},
			//负责人。下拉列表
			{
				xtype:"combo",
				
				x:460,
				y:210,
				allowBlank:false,
				blankText:"请选择负责人！",
				
				fieldLabel:"负责人",
				name:"zbPrincipal",
				id:"zbPrincipal",
				
				listConfig:{
					emptyText:"无此项选择",//当找的值不再列表项时的提示
					maxHeight:180,//表单的最大高度。所有列表项高度之和。
					loadingText:"正在获取数据"//加载（获取）列表项数据时的提示。
				},
				queryParam:"userName",//列表上方的查询框的name属性，服务器根据此参数查询所需的列表项数据
				minChars:1,//下拉列表自动选择用户需要输入的最小字符数量
				queryDelay:300,//从字符键入到发送查询之间的等待时间
				allQuery:"default12",
						
				displayField:"realname",//列表中的显示文字
				valueField:"userid",//类表项的value值
				queryMode:"remote",//数据的获取模式。local为本地数据，remote为远程服务器上数据
				forceSelection:true,//迫使用户输入查询条件，必须在列表中存在，如果不存在则取默认的列表项。false，则允许输入任意字符，如果输入的值列表项中不存在，也会显示输入的值
				typeAhead:true,//根据输入信息，自动选择匹配的列表项。
				//value:"222222",//默认选择的列表项，这里指“北京”。这里写valueField的某个值
						
				triggerAction:"all",//**值为all时，将使用allQuery中的查询条件进行查询。**
						
				store:l_32UserInfor//数据源
			},
			//指标目标值
			{
				xtype:"textfield",
				
				x:460,
				y:240,
				
				fieldLabel:"指标目标值",
				name:"zbTarget",
				id:"target3",
				width:400,
				
				emptyText:"输入的内容必须有且包含一段连续的数字！如，正确：》=90%；错误：》=abc%，越多越好，》=80并且《100",
				allowBlank:true//可为空
			},
			//指标来源
			{
				xtype:"textfield",
				
				x:460,
				y:270,
				
				fieldLabel:"指标来源",
				name:"zbSource",
				id:"from3",
				allowBlank:true//可为空
			},
			//指标当前值
			{
				xtype:"textfield",
				
				x:460,
				y:300,
				
				fieldLabel:"指标当前值",
				name:"zbNow",
				id:"nowValue3",
				allowBlank:true
			},
			//数据收集日期
			{
				xtype:"textfield",
				
				x:460,
				y:330,
				
				fieldLabel:"数据收集日期",
				name:"zbTime",
				id:"getTime3",
				allowBlank:true
			},
			//指标考核频度
			{
				xtype:"textfield",
				
				x:460,
				y:360,
				
				fieldLabel:"指标考核频度",
				name:"zbFrequentness",
				id:"frequency3",
				allowBlank:true
			},
			//指标计算公式
			{
				xtype:"combo",
				
				x:460,
				y:390,
				
				allowBlank:false,
				blankText:"请选择指标的计算公式！",
				
				fieldLabel:"指标计算公式",
				name:"zbFormula",
				id:"zbFormula",
				
				listConfig:{
					emptyText:"无此项选择",//当找的值不再列表项时的提示
					maxHeight:180,//表单的最大高度。所有列表项高度之和。
					loadingText:"正在获取数据"//加载（获取）列表项数据时的提示。
				},
				queryParam:"date",//列表上方的查询框的name属性，服务器根据此参数查询所需的列表项数据
				minChars:1,//下拉列表自动选择用户需要输入的最小字符数量
				queryDelay:300,//从字符键入到发送查询之间的等待时间
				allQuery:"default1",//点击下拉列表右部时，发送的查询条件
						
				displayField:"name",//列表中的显示文字
				valueField:"id",//类表项的value值
				queryMode:"remote",//数据的获取模式。local为本地数据，remote为远程服务器上数据
				forceSelection:true,//迫使用户输入查询条件，必须在列表中存在，如果不存在则取默认的列表项。false，则允许输入任意字符，如果输入的值列表项中不存在，也会显示输入的值
				typeAhead:true,//根据输入信息，自动选择匹配的列表项。
				//value:"222222",//默认选择的列表项，这里指“北京”。这里写valueField的某个值
						
				triggerAction:"all",//**值为all时，将使用allQuery中的查询条件进行查询。**
						
				store:index_formula_store//数据源
			}
			,
			{
				xtype:"button",
				x:260,
				y:220,
				width:80,
				text:"提交",
				    	
				style:"margin-left:20px",
				handler:submitThree
			},
			{
				xtype:"button",
				x:950,
				y:220,
				width:80,
				text:"重置",
				handler:resetThree
			}
		]
	});
	
	//选择日期触发时间事件
	Ext.getCmp("zbDate3").on("select",getOneIndex);
	//选择一级指标后触发二级指标事件
	Ext.getCmp("three_oneindicatorName").on("select",getTwoIndex);
	//根据选择的日期，查询此内的所有一级指标月
	function getOneIndex(){
		//获取时间选择
		var date = Ext.getCmp("zbDate3").getValue();
		//格式化日期
		var dateFormat = Ext.util.Format.date(date, 'Y-m');
		//设置一级下拉列表的查询条件为所选日期
		inThreeForm.getChildByElement("three_oneindicatorName").allQuery = dateFormat;
	}
	//根据选择的一级指标，查询此内的所有二级指标月
	function getTwoIndex(){
		//获取时间选择
		var date = Ext.getCmp("zbDate3").getValue();
		//格式化日期
		var dateFormat = Ext.util.Format.date(date, 'Y-m');
		//获取所选的一级指标id
		var id = Ext.getCmp("three_oneindicatorName").value;
		//alert(id);
		//设置一级下拉列表的查询条件为所选日期
		inThreeForm.getChildByElement("three_twoindicatorName").allQuery = dateFormat+","+id;
	}
	function submitThree(){
		var date = Ext.getCmp("zbDate3").getValue();
		var dateFormat = Ext.util.Format.date(date, 'Y-m');
		//同步提交
		inThreeForm.getForm().submit({
			clientValidation:true,//进行客户端验证
			url:"threeAction!addThreeIndex.do", 
			method:"POST",
			
			//waitTitle:"提示",
			//waitMsg:"正在提交...",
			
			params:{
				date:dateFormat
			},
			
			success:function(form1,action1){
				Ext.Msg.alert("提示","提交成功！");
			},
			failure:function(form1,action1){
				Ext.Msg.alert("提示","提交失败！！！");
			}
		});
	}
	//重置
	function resetThree(){
		inThreeForm.getForm().reset();
		//清空数据源缓存，否则一二级指标默认为上次选择的内容
		//l_31IndicatorInfor.removeAll();
		//l_32IndicatorInfor.removeAll();
//		store.removeAll();
//		Ext.getCmp('three_noeindicatorName').setValue("");
//		Ext.getCmp('three_oneindicatorName').setRawValue("");
//		Ext.getCmp('three_twoindicatorName').setValue("");
//		Ext.getCmp('three_twoindicatorName').setRawValue("");

	}
