var level_two_panel = Ext.create("Ext.tab.Panel",{
				title:"xxxxxx",
				width:600,
				height:400,
				frame:true,
				//renderTo:Ext.getBody(),
				bodyPadding:5,//主体的内边距
				
				autoScroll:true,//是否当面板的body内容多时，自动生成滚动条
				collapsible:true,//是否启用伸缩折叠按钮
				collapseDirection:"top",//伸缩的方向。有“top”,“bottom”,“left”,“right”
				bodyStyle:"background-color:#123456",//设置面板体的样式
				preventHeader:false,//是否组织面板头的显示。
				
				activeTab:0,//默认激活第几个页签
				items:[
					{
						title:"二级指标",
						closable:false,//设置页签是否可以关闭
						html:"<a href='http://www.baidu.com'>访问百度</a><br>每日新闻<br>",
						items:[
							{
								xtype:"textfield",
								fieldLabel:"用户名"
							}
						]
					},
					{
						title:"页签2",
						html:"这是一段文字"
					}
				],
				buttons:[
					{text:"添加新页签",handler:addTab}
				]
			});
			//添加新页签
			function addTab(){
				//使页签个数加1
				var index = myTabPanel.items.length+1;
				//使用Ext.tab.Panel组件的add()方法，加入新页签
				var newTab = myTabPanel.add({
					title:"页签"+index,
					html:"我是页签"+index,
					closable:true//页签是否可以关闭
				});
				//使刚创建的页签得到焦点
				myTabPanel.setActiveTab(newTab);
				
			}