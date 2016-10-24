//创建表单存放日期和表格
var inNaMatFormL3 = Ext.create("Ext.form.Panel", {
	id : "inNaMatFormL3",
	title : "三级指标维护",
	renderTo : Ext.getBody(),
	width : 1177,
	height : 403,
	frame : true,

	//closable:true,//此属性就是在Ext.tab.Panel的子标签内设置的属性，而其子标签可为任意组件。
	bodyStyle : "padding:5 5 5 5",
	//默认的字段属性配置
	defauts : {
		labelSeparator : ":",
		labelWidth : 20,
		labelAlign : "left",

		width : 150,

		msgTarget : "under"
	},
	//设置viewport的子组件内容较多时，自动创建滚动条
	autoScroll : true,
	//异步提交
	standardSubmit : false,

	items : [ {
		xtype : "datefield",
		name : "zbDate",
		id : "index_date3",
		fieldLabel : "请选择日期",
		invalidText:"请按照'xxxx年yy月zz日'格式选择或填写日期",
		format : "Y年m月"//格式化日期格式，这样后台接受时就能直接接受成Date类型。
	} ]
});
//二级指标数据源
var l3_2UpdStore = new Ext.data.Store({
	//***最后加上参照的二级指标名称（未完成）
	fields : [ "id", "zbName" ],
	proxy : {
		type : "ajax",
		url : "threeqtwoAction.do",//?date="+dateFormat
		reader : {
			type : "json",
			root : "items_l2"
		}
	},
	autoLoad : false
});
//三级指标数据源
var l3UpdStore = new Ext.data.Store({
	//***最后加上参照的二级指标名称（未完成）
	fields : [ "id", "parentIdL1", "parentId", "zbName", "zbSort","zbFormula","zbFormulaName", "zbPoint",
			"zbWeight", "zbPrincipal","realname", "zbTarget", "zbSource", "zbNow",
			"zbTime", "zbFrequentness" ],//"parentIdL1","parentId"不显示，也不用修改，但要传给后台修改。
	proxy : {
		type : "ajax",
		url : "threeqthreeAction.do",
		reader : {
			type : "json",
			root : "items_l3_data"
		}
	},
	sorters : [ {
		property : "id",
		direction : "ASC"//按id升序
	} ],
	autoLoad : false
});
//负责人数据源
var zbPrincipal_store = Ext.create("Ext.data.Store",{
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
//得分计算公式数据源
var formula_store = Ext.create("Ext.data.Store",{
	fields:["id","name"],
	proxy:{
		type:"ajax",
		url:"formulaAction.do",
		reader:{
			type:"json",
			root:"formulas",
			totalProperty:"total_user"
		}
	}
});
//定义表格的列
var l3UpdColumns = [ Ext.create("Ext.grid.RowNumberer", {
	text : "行号",
	width : 50
}), //行号
//{header : "id",width : 150,dataIndex : "id",sortable : true}, 
{
	header : "二级指标名称",
	width : 100,
	dataIndex : "parentId",
	renderer : getUpL2Name
}, {
	header : "三级指标名称",
	width : 150,
	dataIndex : "zbName",
	sortable : true,
	editor : {
		xtype : "textfield",
		allowBlank : false
	}
},{
	header : "得分计算公式",
	width : 150,
	dataIndex : "zbFormulaName",
	sortable : true,
	/*editor : {
		xtype : "textfield",
		allowBlank : true
	}*/
	editor : {
		xtype : "combo",
		
		store:formula_store,
		listConfig:{
			emptyText:"无此项选择",//当找的值不再列表项时的提示
			maxHeight:180,//表单的最大高度。所有列表项高度之和。
			loadingText:"正在获取数据"//加载（获取）列表项数据时的提示。
		},
		//queryParam:"userName",//列表上方的查询框的name属性，服务器根据此参数查询所需的列表项数据
		minChars:1,//下拉列表自动选择用户需要输入的最小字符数量
		queryDelay:300,//从字符键入到发送查询之间的等待时间
		allQuery:"default12",
				
		displayField:"name",//列表中的显示文字
		valueField:"name",//类表项的value值
		queryMode:"remote",//数据的获取模式。local为本地数据，remote为远程服务器上数据
		forceSelection:true,//迫使用户输入查询条件，必须在列表中存在，如果不存在则取默认的列表项。false，则允许输入任意字符，如果输入的值列表项中不存在，也会显示输入的值
		//typeAhead:true,//根据输入信息，自动选择匹配的列表项。
		//value:"222222",//默认选择的列表项，这里指“北京”。这里写valueField的某个值
				
		triggerAction:"all"//**值为all时，将使用allQuery中的查询条件进行查询。**
	}
},
{
	header : "排序规则",
	width : 150,
	dataIndex : "zbSort",
	sortable : true,
	editor : {
		xtype : "numberfield"
	}
}, {
	header : "所占分数",
	width : 150,
	dataIndex : "zbPoint",
	sortable : true,
	editor : {
		xtype : "numberfield",
		allowDecimals:true,
		maxValue:100,
		maxText:"总权重小于等于100",
		minValue:0,
		minText:"总权重大于等于0"
	}
}, {
	header : "权重",
	width : 150,
	dataIndex : "zbWeight",
	sortable : true,
	editor : {
		xtype : "numberfield",
		allowDecimals:true,
		maxValue:1,
		maxText:"子权重小于等于1",
		minValue:0,
		minText:"子权重大于等于0",
		step:0.1
	}
}, {
	header : "负责人",
	width : 150,
	dataIndex : "realname",
	sortable : true,
	editor : {
		xtype : "combo",
		
		store:zbPrincipal_store,
		listConfig:{
			emptyText:"无此项选择",//当找的值不再列表项时的提示
			maxHeight:180,//表单的最大高度。所有列表项高度之和。
			loadingText:"正在获取数据"//加载（获取）列表项数据时的提示。
		},
		//queryParam:"userName",//列表上方的查询框的name属性，服务器根据此参数查询所需的列表项数据
		minChars:1,//下拉列表自动选择用户需要输入的最小字符数量
		queryDelay:300,//从字符键入到发送查询之间的等待时间
		allQuery:"default12",
				
		displayField:"realname",//列表中的显示文字。**这里让displayField和valueField一样**
		valueField:"realname",//类表项的value值。**编辑表格时，会将下拉列表的valueField赋给表格的单元格，作为此单元格的dataIndex的值。**
		queryMode:"remote",//数据的获取模式。local为本地数据，remote为远程服务器上数据
		forceSelection:true,//迫使用户输入查询条件，必须在列表中存在，如果不存在则取默认的列表项。false，则允许输入任意字符，如果输入的值列表项中不存在，也会显示输入的值
		//typeAhead:true,//根据输入信息，自动选择匹配的列表项。
		//value:"222222",//默认选择的列表项，这里指“北京”。这里写valueField的某个值
				
		triggerAction:"all"//**值为all时，将使用allQuery中的查询条件进行查询。**
	}
}

/*, {
	header : "负责人",
	width : 150,
	dataIndex : "zbPrincipal",
	sortable : true,
	editor : {
		xtype : "textfield",
		allowBlank : true
	}
}*/, {
	header : "指标目标值",
	width : 150,
	dataIndex : "zbTarget",
	sortable : true,
	editor : {
		xtype : "textfield",
		allowBlank : true
	}
}, {
	header : "指标来源",
	width : 150,
	dataIndex : "zbSource",
	sortable : true,
	editor : {
		xtype : "textfield",
		allowBlank : true
	}
}, {
	header : "指标当前值",
	width : 150,
	dataIndex : "zbNow",
	sortable : true,
	editor : {
		xtype : "textfield",
		allowBlank : true
	}
}, {
	header : "数据收集日期",
	width : 150,
	dataIndex : "zbTime",
	sortable : true,
	editor : {
		xtype : "textfield",
		allowBlank : true
	}
}, {
	header : "指标考核频度",
	width : 150,
	dataIndex : "zbFrequentness",
	sortable : true,
	editor : {
		xtype : "textfield",
		allowBlank : true
	}
} ];
//选择日期触发事件
Ext.getCmp("index_date3").on("select", getUpdThree);

function getUpL2Name(value, metadata, record, rowIndex, colIndex, view) {
	//alert((l3_2UpdStore.findRecord("id", value, 0, false, false, true))
		//	.get("zbName"));
	
	//var a = (l3_2UpdStore.findRecord("id", value, 0, true, false, false));
	//alert("父id是5的二级指标是： "+l3_2UpdStore.findRecord("id", 5, 0, true, false, false));
	return (l3_2UpdStore.findRecord("id", value, 0, false, false, true))
			.get("zbName");
}
//获取要修改的三级指标
function getUpdThree() {
	//获取时间选择
	var date = Ext.getCmp("index_date3").getValue();
	//格式化日期，截取年月信息
	dateFormat = Ext.util.Format.date(date, 'Y-m');
	//修改url
	var urll2 = "threeqtwoAction!fetchOneByDate.do?date=" + dateFormat;
	var newUrl = "threeqthreeAction!.do?date=" + dateFormat;
	l3_2UpdStore.proxy.url = urll2;
	l3UpdStore.proxy.url = newUrl;
	//数据源加载三级指标
	l3_2UpdStore.load({
		callback : function(records, operation, success) {
			/*alert("二级指标返回的记录数是： "+l3_2UpdStore.getCount());
			for(var i=0;i<records.length;i++){
				alert("第"+(i+1)+"个记录是"+records[i].get("zbName"));//
			}*/
			l3UpdStore.load({
				scope : this,
				callback : function(records, operation, success) {
					/*alert(success);
					alert("三级指标返回的记录数是： "+l3UpdStore.getCount());
					for(var i=0;i<records.length;i++){
						alert("第"+(i+1)+"个记录是"+records[i].get("zbName"));//
					}*/
					
					//将表格作为表单的子组件加入
					//注册复选框选择模式的别名
					Ext.ClassManager.setAlias("Ext.selection.CheckboxModel",
							"selection.checkboxmodel");
					if (Ext.getCmp("l3UpdGrid") == null) {
						inNaMatFormL3.add(
						//创建表格
						Ext.create("Ext.grid.Panel", {
							title : "维护三级指标（修改、删除）",
							id : "l3UpdGrid",
							renderTo : Ext.getBody(),
							width : 1000,
							height : 400,
							frame : true,

							multiSelect : true,//在行选择模型中，默认单选。
							checkOnly : true,//是否通过复选框进行选择，默认为false。
							injectCheckbox : 0,//设置复选框的位置。可选：数字、false、或字符串的first、last。默认为0.
							selType : "checkboxmodel",

							store : l3UpdStore,

							//定义行编辑插件
							plugins : [ Ext.create(
									"Ext.grid.plugin.RowEditing", {
										clicksToEdit : 2,//鼠标单击几次单元格开启编辑。
										autoCancel : false
									/*,//设置当编辑其他行，而当前行未点击update或cancel时，是否修改此行。true,不修改；false,自动给出提示。
															saveBtnText:"修改",
											    			cancelBtnText:"取消"*/

									}) //无分号	
							],
							//设置选择模式
							//selType:"cellmodel",
							//列定义。在列定义中，配置编辑器(editor)。编辑器类型要和列的类型匹配，否则无法编辑
							columns : l3UpdColumns,
							//删除工具栏
							tbar : [ {
								text : "删除",
								id : "deleteL3",
								iconCls : "delete",
								handler : deleteL3
							} ]
						}));
					} else {//已有。先删除之前的，再加入新的表格
						inNaMatFormL3.remove(Ext.getCmp("l3UpdGrid"));
						inNaMatFormL3.add(
						//创建表格
						Ext.create("Ext.grid.Panel", {
							title : "维护三级指标（修改、删除）",
							id : "l3UpdGrid",
							renderTo : Ext.getBody(),
							width : 1000,
							height : 400,
							frame : true,

							multiSelect : true,//在行选择模型中，默认单选。
							checkOnly : true,//是否通过复选框进行选择，默认为false。
							injectCheckbox : 0,//设置复选框的位置。可选：数字、false、或字符串的first、last。默认为0.
							selType : "checkboxmodel",

							store : l3UpdStore,

							//定义行编辑插件
							plugins : [ Ext.create(
									"Ext.grid.plugin.RowEditing", {
										clicksToEdit : 2,//鼠标单击几次单元格开启编辑。
										autoCancel : false
									/*,//设置当编辑其他行，而当前行未点击update或cancel时，是否修改此行。true,不修改；false,自动给出提示。
															saveBtnText:"修改",
											    			cancelBtnText:"取消"*/

									}) //无分号	
							],
							//设置选择模式
							//selType:"cellmodel",
							//列定义。在列定义中，配置编辑器(editor)。编辑器类型要和列的类型匹配，否则无法编辑
							columns : l3UpdColumns,
							//删除工具栏
							tbar : [ {
								text : "删除",
								id : "deleteL3",
								iconCls : "delete",
								handler : deleteL3
							} ]
						}));
					}
					//Ext.getCmp("l3UpdGrid").reconfigure(l3UpdStore,l3UpdColumns);
					//修改后，触发事件
					Ext.getCmp("l3UpdGrid").on("edit", submitEditL3, this);
				}

			});

		}
	});

}

function submitEditL3(e) {//e代表所做的操作。
	//获取修改后的记录
	var editedRecord = e.record;
	//alert(editedRecord.get("id"));
	//alert("原负责人id是： "+editedRecord.get("zbPrincipal")+"修改后的姓名是： "+editedRecord.get("realname"));
	
	//查找负责人数据源中，姓名是“修改后的姓名的”那条记录
	var zbPrincipal_modifiedID
	if(editedRecord.get("realname")==null){
		zbPrincipal_modifiedID = editedRecord.get("zbPrincipal");
	}else{
		zbPrincipal_modifiedID = (zbPrincipal_store.findRecord("realname",editedRecord.get("realname"),0,false,true,true)).get("userid");
	}
	//alert("修改后的负责人ID是："+zbPrincipal_modifiedID);
	
	/*//修改后的负责人id（不为null）,realname为修改后的人的id，zbPrincipal为原来的人的id。
	var zbPrincipal_modified = editedRecord.get("realname")==null?editedRecord.get("zbPrincipal"):editedRecord.get("realname");*/
	
	//alert("原计算公式id是： "+editedRecord.get("zbFormula")+"修改后的公式名是： "+editedRecord.get("zbFormulaName"));
	//查找指标公式数据源中，公式名是“修改后的公式名的”那条记录
	var zbFormula_modifiedID;
	if(editedRecord.get("zbFormulaName")==null){
		zbFormula_modifiedID = editedRecord.get("zbFormula");
	}else{
		zbFormula_modifiedID = (formula_store.findRecord("name",editedRecord.get("zbFormulaName"),0,false,true,true)).get("id");
	}
	
	
	/*//修改后的得分计算公式id（不为null）,realname为修改后的人的id，zbPrincipal为原来的人的id。
	//
	var zbFormula_modified = editedRecord.get("zbFormulaName")==null?editedRecord.get("zbFormula"):editedRecord.get("zbFormulaName");
	//alert(zbFormula_modified);
*/	//ajax提交修改后的记录
	var zb_name = encodeURI(encodeURI(editedRecord.get("zbName"),"UTF-8"),"UTF-8");
	var zb_source = encodeURI(encodeURI(editedRecord.get("zbSource"),"UTF-8"),"UTF-8");
	var zb_target = encodeURI(encodeURI(editedRecord.get("zbTarget"),"UTF-8"),"UTF-8");
	var zb_now = encodeURI(encodeURI(editedRecord.get("zbNow"),"UTF-8"),"UTF-8");
	var zb_time = encodeURI(encodeURI(editedRecord.get("zbTime"),"UTF-8"),"UTF-8");
	var zb_frequentness = encodeURI(encodeURI(editedRecord.get("zbFrequentness"),"UTF-8"),"UTF-8");
	var requestConfig = {
		url : "threeAction!updateThreeIndex.do",//访问用户添加和修改的action
		method : "get",
		params : {
			id : editedRecord.get("id"),
			zbName : zb_name,
			zbSort : editedRecord.get("zbSort"),
			zbPoint : editedRecord.get("zbPoint"),
			zbWeight : editedRecord.get("zbWeight"),
			zbPrincipal : zbPrincipal_modifiedID,
			zbSource : zb_source,
			zbTarget : zb_target,
			zbNow : zb_now,
			zbTime : zb_time,
			zbFrequentness : zb_frequentness,
			zbFormula : zbFormula_modifiedID
		}
	/* ,
						callback:function(options,issuccess,backresponse){
							alert("请求是否成功： "+issuccess+"\n服务器返回值： "+backresponse.responseText);
						} */
	};
	Ext.Ajax.request(requestConfig);
}
//批量删除
function deleteL3() {
	var rows = Ext.getCmp("l3UpdGrid").getSelectionModel().getSelection();//获取所选的多行记录,数组类型
	/*for(var i=0;i<rows.length;i++){
		alert("所选行的数据。 "+"id是："+rows[i].get("id")+" 三级指标名是： "+rows[i].get("zbName"));
	}*/
	if (rows.length == 0) {//是否选中某行
		Ext.MessageBox.alert("提示", "您没有选择任何内容！");
	} else {
		Ext.MessageBox.confirm("确认提示", "确认删除这几条？", function(btnId) {
			if (btnId == "yes") {
				//通过删除数据源中的记录，移除表格中的某些列**
				l3UpdStore.remove(rows);
				//alert(l3UpdStore.getCount());
				//将数据封装成JSON，便于后台接受
				//var rows_to_delete = {"data":rows};
				//将一个标准的JSON类型的对象或者元素节点或者数组，转成字符串形式
				//var jsonStringData = Ext.JSON.encode(rows);
				//将字符串形式的json转成json对象
				//var jsonData = Ext.JSON.decode(jsonStringData);
				//alert(jsonStringData);
				//alert(jsonData.raw.id);
				//alert(jsonData.get("raw").get("id"));

				//把每条记录转成json
				/*for(var i=0;i<rows.length;i++){
					var j = Ext.JSON.encode(rows[i]);
					alert(j);
				}*/
				//获取将要删除记录的id(数组)
				var ids = [];
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].get("id"));
					//alert(ids.length);
				}
				//发送到后台删除数据库中的记录的id
				submitDeleteL3(ids);
			}
		});
	}
	//对数组rows中的每个元素，使用函数ergodicArray迭代，在哪个作用域范围内。如果又一次循环返回false，则终止迭代。传递数组的各个元素到迭代函数中。
	//其中迭代函数，必须传递参数。
	//Ext.Array.every(rows,ergodicArray(rows),this);
}
//提交将要删除的记录
function submitDeleteL3(ids) {
	//alert("将要删除的记录数量是： "+rows_to_delete.data));//[0].get("zbName")
	var requestConfig = {
		url : "threeAction!deleteThreeIndex.do",//访问用户添加和修改的action
		method : "get",
		params : {
			idData : ids
		//ids是数组
		}
	//一个数组对象
	/* ,
						callback:function(options,issuccess,backresponse){
							alert("请求是否成功： "+issuccess+"\n服务器返回值： "+backresponse.responseText);
						} */
	};
	//alert(1);
	Ext.Ajax.request(requestConfig);
	//alert(2);

}