//验证密码
			Ext.apply(Ext.form.field.VTypes,{
				//自定义将要添加的方法.
				passVali:function(param1){
					return /^\w+$/.test(param1);
				},
				//自定义要添加的错误提示
				passValiText:"只能输入数字、字母、下划线"
			});


//注册复选框选择模式的别名
			Ext.ClassManager.setAlias("Ext.selection.CheckboxModel",
					"selection.checkboxmodel");
					
			var usMatForm = Ext.create("Ext.form.Panel",{
				id:"usMatForm",
				name:"users",
				title:"用户维护",
				width:1177,
				height:383,
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
				
				items:[
			//创建表格
			Ext.create("Ext.grid.Panel",{
				title:"用户维护",
				id:"myGrid",
				//renderTo:Ext.getBody(),
				width:1000,
				height:400,
				frame:true,
				
				multiSelect : false,//在行选择模型中，默认单选。
				checkOnly : true,//是否通过复选框进行选择，默认为false。
				injectCheckbox : 0,//设置复选框的位置。可选：数字、false、或字符串的first、last。默认为0.
				selType : "checkboxmodel",
				
				store:{
					fields:["userid","realname","username","password","usertype","zbSort"],
					proxy:{
						type:"ajax",
						url:"uqAction.do",
						reader:{
							type:"json",
							root:"items"
						}
					},
					autoLoad:true
				},
				
				//定义行编辑插件
				plugins:[
					Ext.create("Ext.grid.plugin.RowEditing",{
						clicksToEdit:2,//鼠标单击几次单元格开启编辑。
						autoCancel:false/*,//设置当编辑其他行，而当前行未点击update或cancel时，是否修改此行。true,不修改；false,自动给出提示。
						saveBtnText:"修改",
		    			cancelBtnText:"取消"*/
		
					})//无分号	
				],
				//设置选择模式
				//selType:"cellmodel",
				//列定义。在列定义中，配置编辑器(editor)。编辑器类型要和列的类型匹配，否则无法编辑
				columns:[
					Ext.create("Ext.grid.RowNumberer",{text:"行号",width:50}), //行号
					{header:"id",width:150,dataIndex:"userid",sortable:true},
					{header:"姓名",width:150,dataIndex:"realname",sortable:true,editor:{xtype:"textfield",allowBlank:false,
								blankText:"姓名不能为空",cancelBtnText: 'add'
					}},
					{header:"用户名",width:150,dataIndex:"username",sortable:true,editor:{xtype:"textfield",allowBlank:false,
								blankText:"用户名不能为空",cancelBtnText: 'add'
					}},
					{header:"密码",width:150,dataIndex:"password",sortable:true,editor:{xtype:"textfield",vtype:"passVali",allowBlank:false,
								blankText:"密码不能为空",cancelBtnText: 'add'
					}},
					{header:"用户类型",width:150,dataIndex:"usertype",sortable:true,editor:{xtype:"numberfield",allowDecimals:false,
								emptyText:"1表示超级管理员，0表示普通管理员",
								blankText:"用户类型不能为空",
								maxValue:1,
								minValue:0,
								nanText:"请输入0或 1的数字",
								maxText:"不能超过1",
								minText:"不能小于0",
								negativeText:"不能为负数",
								allowBlank:false}},//年龄是数字，所以用numberfield编辑器
					{header:"排序规则",width:150,dataIndex:"zbSort",sortable:true,editor:{xtype:"numberfield",allowBlank:true}}
				],
				//添加新建、修改、删除工具栏
				tbar:[
					{
						text:"创建新用户",
						id:"newUser",
						tooltip:"创建",
						iconCls:"addIcon",
						handler:newUser
					},
					{
						text:"删除",
						id:"removeUser",
						tooltip:"删除",
						iconCls:"removeIcon",
						handler:removeUser
					}
				]
			})
			]
			});
			
			//修改后，触发事件
			//Ext.getCmp("usMatForm").items.first().on("edit",submitEdit);
			//var cc = usMatForm.items.first();
			//alert(cc);
			//usMatForm.get(0).on("edit",submitEdit,this);; 
			//usMatForm.on("edit",submitEdit,this);
			//获取表格元素
			var g = Ext.getCmp("usMatForm").items.first();
			//给表格绑定“编辑”事件
			g.addListener("edit",submitEdit);
			//alert(g.id);
			function submitEdit(e){//e代表所做的操作。
				//获取修改后的记录
				var editedRecord = e.record;
				//alert(Ext.getCmp("usMatForm").id);
				//alert(editedRecord.get("realname"));
				
				var real_name = encodeURI(encodeURI(editedRecord.get("realname"),"UTF-8"),"UTF-8");
				var user_name = encodeURI(encodeURI(editedRecord.get("username"),"UTF-8"),"UTF-8");
				var pass_word = encodeURI(encodeURI(editedRecord.get("password"),"UTF-8"),"UTF-8");
				//ajax提交修改后的记录
				var requestConfig = {
					url:"userAction!updateUser.do",//访问用户添加和修改的action
					method:"get",
					params:{
						userid:editedRecord.get("userid"),
						realname:real_name,
						username:user_name,
						password:pass_word,
						usertype:editedRecord.get("usertype"),
						zbSort:editedRecord.get("zbSort")
					}/* ,
					callback:function(options,issuccess,backresponse){
						alert("请求是否成功： "+issuccess+"\n服务器返回值： "+backresponse.responseText);
					} */
				};
				Ext.Ajax.request(requestConfig);	
			}
			//创建新用户
			function newUser(){
				//创建窗体
				newWindow();
			}
			
			function newWindow(){
				var a = new Ext.window.Window({
					title:"添加用户",
					width:400,
					height:300,
					layout: 'fit',
					items:{
						xtype:"form",
						title:"新用户信息",
						id:"newForm",
						width:390,
						height:270,
						bodyStyle:"padding:20 20 20 20",
						frame:true,
						
						defaults:{
							allowBlank:true,//是否允许字段值为空				
						
							labelSeparator:":",//标签与字段间的分隔符
							labelWidth:80,//标签宽度
							labelAlign:"left",//标签与字段的对齐方式，有“top”,“left”,“right”三种值。
							
							width:300,//字段宽度
							
							//当字段校验出现错误时，提示出现的位置。“side（在字段的右边）”，“under（在字段下面）”，“qtip（浮动的提示）”，“某个Html元素的id值（把错误提示放在Html元素内）”，“none（无提示）”
							msgTarget:"side"
						},
						items:[
							//文本框——姓名
							{
								xtype:"textfield",
								fieldLabel:"负责人姓名",
								width:200,
								emptyText:"",
								name:"realname",
								allowBlank:false,
								blankText:"姓名不能为空",
								selectOnFocus:true
							},
							//文本框——用户名
							{
								xtype:"textfield",
								fieldLabel:"登陆用户名",
								width:230,
								allowBlank:false,
								emptyText:"",
								name:"username",
								blankText:"登陆用户名不能为空",
								selectOnFocus:true
							},
							//文本框——密码
							{
								xtype:"textfield",
								fieldLabel:"登陆密码",
								allowBlank:false,
								emptyText:"",
								name:"password",
								blankText:"密码不能为空",
								inputType:"password",//**设置文本框的类型为密码**
								vtype:"passVali",
								selectOnFocus:true
							},
							//文本框——用户类型
							{
								xtype:"numberfield",
								fieldLabel:"用户类型",
								name:"usertype",
								allowDecimals:false,
								emptyText:"1表示超级管理员，0表示普通管理员",
								blankText:"用户类型不能为空",
								maxValue:1,
								minValue:0,
								nanText:"请输入0或 1的数字",
								maxText:"不能超过1",
								minText:"不能小于0",
								negativeText:"不能为负数",
								allowBlank:false
								
							},
							//文本框——排序规则
							{
								xtype:"numberfield",
								fieldLabel:"排序规则",
								width:200,
								name:"zbSort",
								nanText:"请输入任意整数",
								allowDecimals:false
							}
						],
						buttons:[
							{
								text:"添加",
								handler:submitData
							},
							{
								text:"重置",
								handler:function(){
									this.ownerCt.ownerCt.form.reset();//获取父组件（toolbar）的父组件（form）
								}
							}
						]
					}
				}).show();
			}
			//添加用户
			function submitData(a){
				//alert(this.ownerCt.ownerCt.id);
				//获取表单组件
				var f = this.ownerCt.ownerCt;
				//组件的.submit()方法，将组件中所有字段的值提交到后台
				f.getForm().submit({
					//是否进行客户端验证
					clientValidation:true,
					url:"userAction!addUser.do",
					method:"post",
					//在action中获取服务器的响应json信息（通过action1.result）
					success:function(form1,action1){
						var responseResult = action1.result.data.zbUserName;
						Ext.Msg.alert("添加用户提示","添加 <font color='blue' size=''>"+responseResult+" </font>成功");
						//增加新用户，重新查询
						g.store.load();
					},
					failure:function(){
						Ext.Msg.alert("添加用户提示","<font  color='red' size=''>添加用户失败 </font>");
					}
				});
			}
			function reset(){
			
				//alert(Ext.getCmp("usMatForm").id);
				Ext.getCmp("newForm").form.reset();
			}
			
			
			//删除用户
			function removeUser(){
				var rows = g.getSelectionModel().getSelection();//获取所选的多行记录,数组类型
				if (rows.length == 0) {//是否选中某行
					Ext.MessageBox.alert("提示", "您没有选择任何内容！");
				} else {
					Ext.MessageBox.confirm("确认提示", "确认删除这个用户？", function(btnId) {
						if (btnId == "yes") {
							//获取将要删除记录的id(数组)
							var ids = [];
							for ( var i = 0; i < rows.length; i++) {
								ids.push(rows[i].get("userid"));
							}
							//发送到后台删除数据库中的记录的id
							submitdeleteUser(ids,rows);
						}
					});
				}
			}
			//提交将要删除的记录
			function submitdeleteUser(ids,rows) {
				/* for(var i=0;i<rows.length;i++){
					alert("所选行的数据。 "+"用户id是："+rows[i].get("userid")+" 用户姓名是： "+rows[i].get("realname"));
				} */
				var requestConfig = {
					url : "userAction!deleteUser.do",//访问用户添加和修改的action
					method : "post",
					params : {
						//userid:ids
						userid : rows[0].get("userid"),
						realname : rows[0].get("realname"),
						username : rows[0].get("username"),
						password : rows[0].get("password"),
						usertype : rows[0].get("usertype"),
						zbSort : rows[0].get("zbSort")
					},
					//success第二个参数只表示请求是否成功，但不一定jdbc成功。
					callback:function(options,success,backresponse){
						var jsonResponse = Ext.JSON.decode(backresponse.responseText);
						if(success==true&&(jsonResponse.warning!=null)){
							Ext.MessageBox.alert("警告",jsonResponse.warning);
						}else if(success==true&&(jsonResponse.flag=="yes")){
							//通过删除数据源中的记录，移除表格中的某些列**
							g.store.remove(rows);
						}
					}
				};
				Ext.Ajax.request(requestConfig);
			}