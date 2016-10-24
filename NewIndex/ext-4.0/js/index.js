Ext.onReady(function(){
	//创建ViewPort组件充满整个窗口
	var main_view = Ext.create("Ext.container.Viewport",{
		layout:"border",//边框布局

		
		items:[{/*标题*/
			region:"north",//上部分
			id:"north",
					
			bodyStyle:"background-color:#daf3f0;",//fbab00,e6e6e6,cdcdcd,daf3f0,6bcec2
	
			title:"标题栏",//标题
			collapsible:true,
			html:"<br><center><font size='6'>国网指标线上维护系统</font></center>",
			height:100
		},{/*功能菜单*/
			region:"west",
			id:"west",
			title:"菜单栏",
			//collapsible:true,
			items:menuTree,//引用菜单树js文件(left.js中的menuTree)
			split:true,
			
			bodyStyle:"background-color:#0c6d61;",
			
			width:180
		},
			{/*展示页面*/
			region:"center",
			/*设置中间展示区为tabpanel。此处tabpanel没有标题，显示的是viewport的标题。
			 * 设置了xtype为tabpanel后，center区域就是tabpanel类型。
			 * 这时tabpanel代替center区域。
			 */
			xtype: 'tabpanel',
			
			//layout:"fit",
			bodyStyle:"background-color:rgb(233,233,246);",
			
			//enableTabScroll: true,
			title:"展示栏",
			//collapsible:true,
			
			//autoDestroy: false,
			tabPosition: 'top'
			,//设置tab的位置。有top（默认）、bottom
			//工具栏。关闭所有tab
//			tbar:[
//				{
//					text:"关闭所有页面",
//					handler:function(){
//						var center = Ext.getCmp("center");
//						/*if(center.items==null){
//							alert("空");
//						}*/
//						/*center.items.each(function(item){
//							center.remove(item);
//						});*/
//					}
//				}
//			],
			
			/*listeners:{
				beforeremove: function(ct,component ) { return false; }
			},*/
			
			//autoScroll:true,
			//html:"我是中部",
			//items:[{title:"1",html:"11",id:"1",closable:true,itemId:"tab1"},{title:"2",id:"2",html:"22"}],
			//将所有的子tab创建，并隐藏。点击树节点时，显示。
			items:[
				
			],

			id:"center"//其他页面在此id所在的区域中填入美容
		}
		]
	});
//	//异步访问后台，显示欢迎信息
//	var requestConfig_welcome = {
//					url:"userAction!welcomeInfor.do",//访问用户添加和修改的action
//					callback:function(options,issuccess,backresponse){
//						var jsonData = Ext.JSON.decode(backresponse.responseText);
//						Ext.getCmp("north").setTitle(
//							"欢迎您！<font size='2' ,color='blue'>"+jsonData.realname+"</font>"+
//							"<font size='2',color='yellow'><a href='userAction!logout.do'>退出</a></font>"//退出按钮
//						);
//					}
//				};
//	Ext.Ajax.request(requestConfig_welcome);
	
	
//关闭panel的tab之前，弹出的提示
//function removeTabEvent(tabpanel, tab) { 
//	//alert(888);
//    Ext.Msg.show({
//        title:'Remove tab?',
//        msg: 'Are you sure?',
//        buttons: Ext.Msg.YESNO,
//        icon: Ext.Msg.QUESTION,
//        fn: function(btn, text) {
//            if(btn == 'yes') {
//            	Ext.Array.remove(tab_id,tab.id);
//            	//var center = Ext.getCmp("center");
//            	/*if(tab.id=="inOneForm"){
//					center.remove(inOneForm);
//            	}else if(tab.id=="inTwoForm"){
//            		center.remove(inTwoForm);
//            	}else if(tab.id=="inThreeForm"){
//            		center.remove(inThreeForm);
//            	}*/
//            	
//                tabpanel.un('beforeremove', removeTabEvent);
//                tabpanel.remove(tab);
//                tabpanel.addListener('beforeremove', removeTabEvent, tabpanel);  //I add only this line 
//            }
//        }
//    });
//    return false;
//}
//Ext.getCmp('center').on('beforeremove', removeTabEvent);
/*	//按钮
	var myToolbar = new Ext.toolbar.Toolbar({
		//renderTo:"toolBar1",
		width:500
				
	});
	//使用add方法将元素加入工具栏
	myToolbar.add([//这里使用数组，为工具栏增加三个元素。也可直接使用多个json配置项。
		//增加一个按钮元素
		{
			text:"新建",//按钮上的文字
			handler:onButtonClick,//点击按钮触发的函数
			iconCls:"newIcon"//按钮上显示的图标	
		},
		{text:"打开",handler:onButtonClick,iconCls:"openIcon"},
		{text:"保存",handler:onButtonClick,iconCls:"saveIcon"}
	]);
	Ext.getCmp("center").add(myToolbar);*/
});