	Ext.QuickTips.init();
Ext.onReady(function(){

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
				name:"zbDate",
				id:"zbDate1",
				fieldLabel:"请选择日期",
				invalidText:"请按照xxx格式选择或填写日期",
				format:"Y年m月d日"//格式化日期格式，这样后台接受时就能直接接受成Date类型。
			},
			{
				xtype:"textfield",
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
				name:"zbSort",
				id:"zbSort1",
				fieldLabel:"排序规则",
				allowDecimals:false
				//vtype:"num"//过滤条件.**此项写正确，否则调教不成功**
				//nanText:"请输入数字"//错误提示
			}
		],
		buttons:[
			{text:"提交",handler:submitOne},
			{text:"重置",handler:resetOne}
		]
	});
});