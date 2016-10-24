//创建表单存放日期和表格
var inNaMatFormL2 = Ext.create("Ext.form.Panel", {
	id : "inNaMatFormL2",
	title : "二级指标维护",
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
		id : "index_date2",
		fieldLabel : "请选择日期",
		invalidText:"请按照'xxxx年yy月zz日'格式选择或填写日期",
		format : "Y年m月"//格式化日期格式，这样后台接受时就能直接接受成Date类型。
	} ]
});
//一级指标数据源
var l2_1UpdStore = new Ext.data.Store({
	//***最后加上参照的二级指标名称（未完成）
	fields : [ "id", "zbName" ],
	proxy : {
		type : "ajax",
		url : "threeqoneAction.do",//?date="+dateFormat
		reader : {
			type : "json",
			root : "items_l1"
		}
	},
	autoLoad : false
});
//二级指标数据源
var l2UpdStore = new Ext.data.Store({
	fields : [ "id", "zbName", "zbSort", "parentId" ],
	proxy : {
		type : "ajax",
		url : "threeqtwoAction.do",
		reader : {
			type : "json",
			root : "items_l2"
		}
	},
	sorters : [ {
		property : "id",
		direction : "ASC"//按id升序
	} ],
	autoLoad : false
});
//定义表格的列
var l2UpdColumns = [ Ext.create("Ext.grid.RowNumberer", {
	text : "行号",
	width : 50
}), //行号
{
	header : "一级指标名称",
	width : 100,
	dataIndex : "parentId",
	renderer : getUpL1Name
}, {
	header : "二级指标名称",
	width : 150,
	dataIndex : "zbName",
	sortable : true,
	editor : {
		xtype : "textfield",
		allowBlank : false
	}
}, {
	header : "排序规则",
	width : 150,
	dataIndex : "zbSort",
	sortable : true,
	editor : {
		xtype : "numberfield"
	}
} ];
//选择日期触发事件
Ext.getCmp("index_date2").on("select", getUpdThree);

function getUpL1Name(value, metadata, record, rowIndex, colIndex, view) {
	return (l2_1UpdStore.findRecord("id", value, 0, false, false, true))
			.get("zbName");
}
//获取要修改的三级指标
function getUpdThree() {
	//获取时间选择
	var date = Ext.getCmp("index_date2").getValue();
	//格式化日期，截取年月信息
	dateFormat = Ext.util.Format.date(date, 'Y-m');
	//修改url
	var urll1 = "threeqoneAction.do?date=" + dateFormat;
	var newUrl = "threeqtwoAction!fetchOneByDate.do?date=" + dateFormat;
	l2_1UpdStore.proxy.url = urll1;
	l2UpdStore.proxy.url = newUrl;
	//数据源加载三级指标
	l2_1UpdStore.load({
		callback : function(records, operation, success) {
			l2UpdStore.load({
				scope : this,
				callback : function(records, operation, success) {
					/*alert(success);
					alert("三级指标返回的记录数是： "+l2UpdStore.getCount());
					for(var i=0;i<records.length;i++){
						alert("第"+(i+1)+"个记录是"+records[i].get("zbName"));//
					}*/
					//将表格作为表单的子组件加入
					//注册复选框选择模式的别名
					Ext.ClassManager.setAlias("Ext.selection.CheckboxModel",
							"selection.checkboxmodel");
					if (Ext.getCmp("l2UpdGrid") == null) {
						inNaMatFormL2.add(
						//创建表格
						Ext.create("Ext.grid.Panel", {
							title : "维护二级指标（修改、删除）",
							id : "l2UpdGrid",
							renderTo : Ext.getBody(),
							width : 1000,
							height : 400,
							frame : true,

							multiSelect : false,//在行选择模型中，默认单选。
							checkOnly : true,//是否通过复选框进行选择，默认为false。
							injectCheckbox : 0,//设置复选框的位置。可选：数字、false、或字符串的first、last。默认为0.
							selType : "checkboxmodel",

							store : l2UpdStore,

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
							columns : l2UpdColumns,
							//删除工具栏
							tbar : [ {
								text : "删除",
								id : "deleteL2",
								iconCls : "delete",
								handler : deleteL2
							} ]
						}));
					} else {//已有。先删除之前的，再加入新的表格
						inNaMatFormL2.remove(Ext.getCmp("l2UpdGrid"));
						inNaMatFormL2.add(
						//创建表格
						Ext.create("Ext.grid.Panel", {
							title : "维护二级指标（修改、删除）",
							id : "l2UpdGrid",
							renderTo : Ext.getBody(),
							width : 1000,
							height : 400,
							frame : true,

							multiSelect : false,//在行选择模型中，默认单选。
							checkOnly : true,//是否通过复选框进行选择，默认为false。
							injectCheckbox : 0,//设置复选框的位置。可选：数字、false、或字符串的first、last。默认为0.
							selType : "checkboxmodel",

							store : l2UpdStore,

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
							columns : l2UpdColumns,
							//删除工具栏
							tbar : [ {
								text : "删除",
								id : "deleteL2",
								iconCls : "delete",
								handler : deleteL2
							} ]
						}));
					}
					//Ext.getCmp("l2UpdGrid").reconfigure(l2UpdStore,l3UpdColumns);
					//修改后，触发事件
					Ext.getCmp("l2UpdGrid").on("edit", submitEditL2, this);
				}

			});

		}
	});

}

function submitEditL2(e) {//e代表所做的操作。
	//获取修改后的记录
	var editedRecord = e.record;
	//alert(editedRecord.get("id"));
	
	var zb_name = encodeURI(encodeURI(editedRecord.get("zbName"),"UTF-8"),"UTF-8");
	//ajax提交修改后的记录
	var requestConfig = {
		url : "twoAction!updateTwoIndex.do",//访问用户添加和修改的action
		method : "get",
		params : {
			id : editedRecord.get("id"),
			zbName : zb_name,
			zbSort : editedRecord.get("zbSort")
		}
	/* ,
						callback:function(options,issuccess,backresponse){
							alert("请求是否成功： "+issuccess+"\n服务器返回值： "+backresponse.responseText);
						} */
	};
	Ext.Ajax.request(requestConfig);
}
//批量删除
function deleteL2() {
	var rows = Ext.getCmp("l2UpdGrid").getSelectionModel().getSelection();//获取所选的多行记录,数组类型
	/*for(var i=0;i<rows.length;i++){
		alert("所选行的数据。 "+"id是："+rows[i].get("id")+" 三级指标名是： "+rows[i].get("zbName"));
	}*/
	if (rows.length == 0) {//是否选中某行
		Ext.MessageBox.alert("提示", "您没有选择任何内容！");
	} else {
		Ext.MessageBox.confirm("确认提示", "确认删除这条指标？", function(btnId) {
			if (btnId == "yes") {

				//alert(l2UpdStore.getCount());
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
				submitDeleteL2(ids,rows);
			}
		});
	}
	//对数组rows中的每个元素，使用函数ergodicArray迭代，在哪个作用域范围内。如果又一次循环返回false，则终止迭代。传递数组的各个元素到迭代函数中。
	//其中迭代函数，必须传递参数。
	//Ext.Array.every(rows,ergodicArray(rows),this);
}
//提交将要删除的记录
function submitDeleteL2(ids,rows) {
	//alert("将要删除的记录数量是： "+rows_to_delete.data));//[0].get("zbName")
	var requestConfig = {
		url : "twoAction!deleteTwoIndex.do",//访问用户添加和修改的action
		method : "POST",
		params : {
			idData : ids
		//ids是数组
		},
		//success第二个参数只表示请求是否成功，但不一定jdbc成功。
		callback:function(options,success,backresponse){
			var jsonResponse = Ext.JSON.decode(backresponse.responseText);
			if(success==true&&(jsonResponse.warning!=null)){
				Ext.MessageBox.alert("警告",jsonResponse.warning);
			}else if(success==true&&(jsonResponse.flag=="yes")){
				//通过删除数据源中的记录，移除表格中的某些列**
				l2UpdStore.remove(rows);
			}
		}
	};
	Ext.Ajax.request(requestConfig);
}