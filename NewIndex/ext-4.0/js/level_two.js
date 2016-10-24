	//Ext.QuickTips.init();
/*	//定义数据模型。一级指标名称
	Ext.regModel(
		"loIndicators",
		{
			fields:["id","zbName","zbDate","zbSort"]
		}
	);*/
	//定义数据源（Ajax方式,获取数组形式的数据）。一级指标
	var indicatorInfor = Ext.create("Ext.data.Store",{
		fields:["id","zbName","zbDate","zbSort"],
		//model:"loIndicators",
		proxy:{
			type:"ajax",
			url:"twoqAction.do",//访问action的execute方法
			reader:{
						type:"json",
						root:"items_l1",
						totalProperty:"total_l1"
			}//new Ext.data.ArrayReader({model:"loIndicators"});"json"
		}
	});
	//创建二级指标表单
	var inTwoForm = Ext.create("Ext.form.Panel",{
		title:"二级指标",
		id:"inTwoForm",
		width:1177,
		height:383,
		frame:true,
		//renderTo:"level_two",
		bodyStyle:"padding:5 5 5 5",
		//closable:true,
		
		layout:"absolute",
		
		autoScroll: true,
		//默认的字段属性配置
		defauts:{
			labelSeparator:":",
			labelWidth:20,
			labelAlign:"left",
			
			width:150,
			
			msgTarget:"side"
		},
		
/*		//增加一个监听。关闭此tab时，更新数组（记录已经存在的tab的id）
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
		standardSubmit:false,//是否同步提交
		
		items:[
			{
				xtype:"datefield",
				
				x:460,
				//y:370,
				
				fieldLabel:"请选择日期",
				name:"zbDate2",
				id:"zbDate2", 
				allowBlank:false,//不为空
				blankText:"请选择指标所属日期",
				invalidText:"请按照'xxxx年yy月zz日'格式选择或填写日期",
				format:"Y年m月"
			},
			//下拉列表
			{
				xtype:"combo",
				
				x:460,
				y:50,
				
				fieldLabel:"一级指标名称",
				name:"parentId",
				id:"parentId",
				
				listConfig:{
					emptyText:"无此项选择",//当找的值不再列表项时的提示
					maxHeight:180,//表单的最大高度。所有列表项高度之和。
					loadingText:"正在获取数据"//加载（获取）列表项数据时的提示。
				},
				queryParam:"dateParam",//列表上方的查询框的name属性，服务器根据此参数查询所需的列表项数据
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
						
				store:indicatorInfor//数据源
			},
			{
				xtype:"textfield",
				
				x:460,
				y:100,
				
				fieldLabel:"指标名称",
				name:"zbName",
				id:"zbName2",
				emptyText:"这里输入二级指标名称",//默认显示的文本
				allowBlank:false,//不为空
				blankText:"指标名称不能为空"
			},
			//排序规则使用type和number技术
			{
				xtype:"numberfield",
				
				x:460,
				y:150,
				
				name:"zbSort",
				id:"zbSort2",
				fieldLabel:"排序规则",
				allowDecimals:false
				//vtype:"num"//过滤条件
				//nanText:"请输入数字"//错误提示
			},
			{
				xtype:"button",
				x:460,
				y:330,
				width:80,
				text:"提交",
				    	
				//style:"margin-left:20px",
				handler:submitTwo
			},
			{
				xtype:"button",
				x:636,
				y:330,
				width:80,
				text:"重置",
				handler:resetTwo
			}
		]
	});
	//创建选择触发的事件
	Ext.getCmp("zbDate2").on("select",getOneByDate);
	//根据选择的日期，修改下拉列表的查询条件
	function getOneByDate(){
		//获取时间选择
		var date = Ext.getCmp("zbDate2").getValue();
		//格式化日期
		var dateFormat = Ext.util.Format.date(date, 'Y-m');
		//设置一级下拉列表的查询条件为所选日期
		Ext.getCmp("parentId").allQuery = dateFormat;
		//alert(Ext.getCmp("parentId").allQuery);
			
	}
	function submitTwo(){
		
		var date = Ext.getCmp("zbDate2").getValue();
		dateFormat = Ext.util.Format.date(date, 'Y-m-d');
		
		//异步提交（同步提交时，会响应会转发到另一个页面，注意响应的字符编码）
		inTwoForm.getForm().submit({
			clientValidation:true,//进行客户端验证
			url:"twoAction!addTwoIndex.do",
			method:"POST",
			
			params:{
				zbDate:dateFormat
			},
			
			//waitTitle:"提示",
			//waitMsg:"正在提交...",
			
			success:function(form,action){
				Ext.Msg.alert("提示","提交成功！");
			},
			failure:function(form,action){
				Ext.Msg.alert("提示","提交失败！！！");
			}
		});
	}
	//重置
	function resetTwo(){
		inTwoForm.getForm().reset();
	}
