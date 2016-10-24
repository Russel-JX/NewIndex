//提交指标复用的表单
var data_reuse_form = Ext.create("Ext.form.FormPanel",{
				
				title:"指标复用",
				bodyStyle:"padding:5 5 5 5",
				renderTo:Ext.getBody(),
				frame:true,//是否渲染表单
				height:400,//表单高度
				width:800,//表单宽度
				
				layout:"absolute",
				
				defaults:{
					labelSeparator:":",
					labelWidth:260,
					labelAlign:"left",
					
					width:550,
					
					msgTarget:"under"
				},
				items:[
					 	 //创建单选按钮
				    {
				    	xtype:"radiogroup",
				    	
				    	fieldLabel:"<font size='2'>使用上个月指标（一、二、三级指标）作为本月指标</font>",
				    	columns:2,//两列显示数据，超过两列，则会自动换行。
				    	
				    	x:180,
						y:50,
				    	//单选的选项,相同name属性的选项在radiogroup组件中，同时只能有一个被选中。
				    	items:[
				    		{boxLabel:"<font color='blue' font='黑体' style='font-weight:bold'>是</font>",id:"reuse_yes",name:"reuse",inputValue:true},//boxLabel为字面值；inputValue为真实值（提交值）
				    		{boxLabel:"<font color='blue' font='黑体' style='font-weight:bold'>否</font>",id:"reuse_no",name:"reuse",inputValue:false}
				    	]
				    },
				    {
				    	xtype:"button",
				    	x:200,
						y:200,
				    	width:80,
				    	text:"确定",
				    	
				    	//style:"margin-left:20px",
				    	handler:submitData3
				    },
				    {
				    	xtype:"button",
				    	x:500,
						y:200,
				    	width:80,
				    	text:"取消",
				    	handler:reset3
				    }
				 ]
			 });
			function submitData3(){
				//是否复制
				var selection = Ext.getCmp("reuse_yes").value;
				
				if(selection==false){
					Ext.Msg.alert("提示","请选择“是”复制上个月数据");
					return;
				}
				//确认提示
				Ext.Msg.confirm("注意！","<font color='red' size='6'>您的操作将清除本月的所有数据!</font><br>继续：点击'yes'，取消:点击'no'",cb);
			}
			function cb(id){
				if(id=="yes"){
					//Ext.Msg.alert("提示","数据量较大，请耐心等待");
					data_reuse_form.getForm().submit({
					//是否进行客户端验证
					clientValidation:true,
					url:"dataReuseAction.do",
					method:"post",
					
					msgTitle:"提示",
					msgWait:"数据量较大，请耐心等待",
					
					//在action中获取服务器的响应json信息（通过action1.result）
					//这里ajax请求成功时，也执行failure!!
					failure:function(form1,action1){
						//取消之前的选择
						reset3();
						Ext.Msg.alert("提示",
						"删除了 "+action1.result.effectedRows[0]+" 条具体指标"+
						"<br>删除了 "+action1.result.effectedRows[1]+" 条三级指标"+
						"<br>删除了 "+action1.result.effectedRows[2]+" 条二级指标"+
						"<br>删除了 "+action1.result.effectedRows[3]+" 条一级指标"+
						"<br>成功复制了 "+action1.result.effectedRows[4]+" 条一级指标； "+
						"<br>复制了 "+action1.result.effectedRows[5]+" 条二级指标；"+
						"<br>更新了 "+action1.result.effectedRows[6]+" 条二级指标"+
						"<br>复制了 "+action1.result.effectedRows[7]+" 条三级指标"+
						"<br>更新了 "+action1.result.effectedRows[8]+" 条三级指标"
						
						);
						},
					success:function(form1,action1){
						Ext.Msg.alert("提示","提交成功！");
						}
					});
				}else{
					
				}
				
			}
			function reset3(){
				data_reuse_form.form.reset();
			}
