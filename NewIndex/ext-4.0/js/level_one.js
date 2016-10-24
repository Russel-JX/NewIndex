	Ext.QuickTips.init();
	var inOneForm = Ext.create("Ext.form.Panel",{
		id:"inOneForm",
		title:"一级指标",
		width:1177,
		height:383,
		frame:true,
		//closable:true,//此属性就是在Ext.tab.Panel的子标签内设置的属性，而其子标签可为任意组件。
		//renderTo:"level_one",//不能加renderTo,因为此面板被center引用
		bodyStyle:"padding:5 5 5 5",
		autoScroll: true,
		
		//用户anchor布局
		layout:"absolute",
		
		//默认的字段属性配置
		defauts:{
			labelSeparator:":",
			labelWidth:20,
			labelAlign:"left",
			
			width:150,
			
			msgTarget:"under"
		},
		/*//增加一个监听。关闭此tab时，更新数组（记录已经存在的tab的id）
		listeners:{
			beforeclose:function(tabItem, e){
				alert("删除的id是"+tabItem.id);
				Ext.Array.remove(tab_id,tabItem.id);//在数组中，删除刚关闭的tab的id
				
				var center = Ext.getCmp("center");
				//center.remove(inOneForm);
				
				alert("数组长度"+tab_id.length);
				for(var j=0;j<tab_id.length;j++){
						alert("**"+tab_id[j]);
				}
				e.cancel = true;
				//Ext.Msg.alert("提示","您确定关闭此页？");
			}
		},*/
		
		//异步提交
		standardSubmit:false,
		/*不同组件中的子组件id不要相同，否则会将有相同id的子组件也显示在组件中，导致重复*/
		items:[
			{
				xtype:"datefield",
				
				x:460,
				//y:370,
				
				name:"zbDate1",
				id:"zbDate1",
				fieldLabel:"请选择日期",
				invalidText:"请按照'xxxx年yy月zz日'格式选择或填写日期",
				format:"Y年m月",//格式化日期格式，这样后台接受时就能直接接受成Date类型。
				allowBlank:false,//不为空
				blankText:"请选择指标所属日期",
				submitFormat:"Y/m/d"
			},
			{
				xtype:"textfield",
				
				x:460,
				y:50,
				
				name:"zbName",
				id:"zbName1",
				fieldLabel:"指标名称",
				emptyText:"这里输入指标名称",//默认显示的文本
				allowBlank:false,//不为空
				blankText:"指标名称不能为空"
			},
			//排序规则使用type和number技术
			{
				xtype:"numberfield",
				
				x:460,
				y:100,
				
				name:"zbSort",
				id:"zbSort1",
				fieldLabel:"排序规则",
				allowDecimals:false
				//vtype:"num"//过滤条件.**此项写正确，否则调教不成功**
				//nanText:"请输入数字"//错误提示
			},

			{
				xtype:"button",
				x:460,
				y:330,
				width:80,
				text:"提交",
				    	
				//style:"margin-left:20px",
				handler:submitOne
			},
			{
				xtype:"button",
				x:636,
				y:330,
				width:80,
				text:"重置",
				handler:resetOne
			}
		]
	});
	//提交
	function submitOne(){
		/*由于只要年和月，获取选择的日期时，不会提交到“日”，struts自动转换异常，所以认为转成年月日*/
		var date = Ext.getCmp("zbDate1").getValue();
		//alert(date);
		dateFormat = Ext.util.Format.date(date, 'Y-m-d');
		//alert(dateFormat);
		//异步提交
		inOneForm.getForm().submit({
			clientValidation:true,//进行客户端验证
			url:"oneAction!addOneIndex.do",
			method:"POST",
			
			params:{
				zbDate:dateFormat
			},	
			
			//waitTitle:"提示",
			//waitMsg:"正在提交...",
			
			success:function(form1,action1){
				//var responseResult = action1.result.data.userName;
				//alert(responseResult);
				Ext.Msg.alert("提示","提交成功！");
			},
			failure:function(form1,action1){
				Ext.Msg.alert("提示","提交失败！！！");
			}
		});
		
		
	}	
	//重置
	function resetOne(){
		inOneForm.getForm().reset();
	}
	
