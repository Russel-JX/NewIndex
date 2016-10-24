//已经存在的tab的id
var tab_id = [];

var menuTree = Ext.create("Ext.tree.Panel", {
			title : "",
			width : 200,
			height : 500,
			// renderTo:Ext.getBody(),
			
			animate : true,// 下拉时，带动画效果
			useArrows : true,// 使用vista风格箭头

			root:{
				text:"<font  style='font-weight:bold'>信息维护</font>",
				expanded:true,// 默认站看树节点
				// 孩子节点
				children:[
					{
						text:"<font  style='font-weight:bold'>指标类别</font>",
						id:"inCate",
						leaf:false,
						children:[
							{text:"<font color='blue' >一级指标</font>",leaf:true,id:"inOne",url:"1.jsp"},{text:"<font color='blue' >二级指标</font>",id:"inTwo",leaf:true},{text:"<font color='blue' >三级指标</font>",id:"inThree",leaf:true}
						]
					},
					{
						text:"<font  style='font-weight:bold'>指标管理</font>",
						id:"inMan",
						leaf:false,
						children:[
							{text:"<font color='blue' >一级指标维护</font>",id:"inNaMatL1",leaf:true},
							{text:"<font color='blue' >二级指标维护</font>",id:"inNaMatL2",leaf:true},
							{text:"<font color='blue' >三级指标维护</font>",id:"inNaMatL3",leaf:true},
							{text:"<font color='blue' >沿用上个月指标数据</font>",id:"data_reuse",leaf:true}
						]
					},
					{
						text:"<font  style='font-weight:bold'>用户管理</font>",
						id:"usMan",
						leaf:false,
						children:[
							{text:"<font color='blue' >用户维护</font>",id:"usMat",leaf:true}
						]
					},
					{
						text:"<font  style='font-weight:bold'>指标综合展示</font>",
						id:"deInMat",
						leaf:false,
						children:[
							{text:"<font color='blue' >指标浏览</font>",id:"inMat",leaf:true}
						]
					}
				]
			}, 
			hrefTarget:"center",//对应Viewport中的某区域的id值。将值填入该区域
			//监听节点被点击，后执行函数
			listeners:{ 
				  /**                     
				   * * @event itemdblclick                     
				   * * Fires when an item is double clicked.                     
				   * * @param {Ext.view.View} this                    
				   * * @param {Ext.data.Model} record The record that belongs to the item                
				   * * @param {HTMLElement} item The item's element             
				   * * @param {Number} index The item's index                    
				   * * @param {Ext.EventObject} e The raw event object */
				/* 
				 * item:
				 * index:所点击的第几个几点（根节点不算，为展开的几点不算，从上到下1,2...数字从1开始）
				 * rec为当前选中的元素。rec.get("属性名"),根据元素的属性名，取值属性值。
				 * */
				itemclick:function(view,rec,item,index,e){
					//点中的节点的id
					var selected = rec.get("id");
					//center即为tabpanel面板.					
					var center = Ext.getCmp("center");
					
					//tab数组长度
					var length=0;
					//创建节点id和tab的id的映射关系，数组
					var tree_id = [['inOne','inOneForm'],['inTwo','inTwoForm'],['inThree','inThreeForm'],['inNaMatL1','inNaMatFormL1'],['inNaMatL2','inNaMatFormL2'],['inNaMatL3','inNaMatFormL3'],['data_reuse','data_reuse_form'],['usMat','usMatForm'],['inMat','inMatForm']];
					//获得匹配的表单id
					var match_id="";
					//新加入的tab
					var newTab=null;
					
					for(var i=0;i<tree_id.length;i++){
						if(tree_id[i][0]==selected){
							match_id = tree_id[i][1];
							//alert("macth_id="+match_id);
							break;//标准js中跳出循环使用break;Ext的each使用return false跳出循环
						}							
					}
					//找出已经存在的tab的id，组成数组
					center.items.each(function(item){
						if(item==null){
							return false;//面板中无元素，跳出
						}
						tab_id[length] = item.id;
						length++;
					});
					
					//第几个匹配
					var show_index = Ext.Array.indexOf(tab_id,match_id,0);
					/*alert("^^"+show_index);
					switch(show_index){
						case 0:alert("第 "+Ext.Array.indexOf(tab_id,match_id,0)+" 个匹配");;break;
						case 1:alert("第 "+Ext.Array.indexOf(tab_id,match_id,0)+" 个匹配");;break;
						case 2:alert("第 "+Ext.Array.indexOf(tab_id,match_id,0)+" 个匹配");;break;
						case 3:alert("第 "+Ext.Array.indexOf(tab_id,match_id,0)+" 个匹配");;break;
						case 4:alert("第 "+Ext.Array.indexOf(tab_id,match_id,0)+" 个匹配");;break;
					}*/
					//是叶子节点。
					if(Ext.Array.contains(['inOneForm','inTwoForm','inThreeForm','inNaMatFormL1','inNaMatFormL2','inNaMatFormL3','data_reuse_form','usMatForm','inMatForm'],match_id)){
						if(show_index!=-1){//点击的节点在panel中存在
							if(match_id=="inOneForm"){
								center.setActiveTab(inOneForm);
							}else if(match_id=="inTwoForm"){
								center.setActiveTab(inTwoForm);						
							}else if(match_id=="inThreeForm"){
								center.setActiveTab(inThreeForm);	
							}else if(match_id=="inNaMatFormL1"){
								center.setActiveTab(inNaMatFormL1);	
							}else if(match_id=="inNaMatFormL2"){
								center.setActiveTab(inNaMatFormL2);	
							}else if(match_id=="inNaMatFormL3"){
								center.setActiveTab(inNaMatFormL3);	
							}else if(match_id=="data_reuse_form"){
								center.setActiveTab(data_reuse_form);	
							}else if(match_id=="usMatForm"){
								center.setActiveTab(usMatForm);	
							}else if(match_id=="inMatForm"){
								center.setActiveTab(inMatForm);	
							}
						}else{//点击的节点在panel中没有。新建。并且更新tab_id数组。
							if(match_id=="inOneForm"){
								newTab = center.add(inOneForm);
								
								center.show();
								
								center.setActiveTab(inOneForm);
								
							}else if(match_id=="inTwoForm"){
								newTab = center.add(inTwoForm);
								center.setActiveTab(inTwoForm);
							}else if(match_id=="inThreeForm"){
								newTab = center.add(inThreeForm);
								center.setActiveTab(inThreeForm);
							}else if(match_id=="inNaMatFormL1"){
								newTab = center.add(inNaMatFormL1);
								center.setActiveTab(inNaMatFormL1);
							}else if(match_id=="inNaMatFormL2"){
								newTab = center.add(inNaMatFormL2);
								center.setActiveTab(inNaMatFormL2);
							}else if(match_id=="inNaMatFormL3"){
								newTab = center.add(inNaMatFormL3);
								center.setActiveTab(inNaMatFormL3);
							}else if(match_id=="data_reuse_form"){
								newTab = center.add(data_reuse_form);
								center.setActiveTab(data_reuse_form);
							}else if(match_id=="usMatForm"){
								newTab = center.add(usMatForm);
								center.setActiveTab(usMatForm);
							}else if(match_id=="inMatForm"){
								newTab = center.add(inMatForm);
								center.setActiveTab(inMatForm);
							}
							newTab.show();
							Ext.Array.include(tab_id,match_id);//向数组中增加一个原来没有的id。
							
						}
					}
					
					
					/*while(length>0){
						if(){
							
						}
					}*/
					
					
					/*var re = center.child("#tab1");
					center.remove();*/
					
					//Ext.tab.Panel.child("#子tab的itemId");获取panel内的子tab，而不是通过id。
					/*center.child("#tab1").tab.hide();//隐藏子tab
					center.child("#tab1").tab.show();//显示子tab*/	
					
					/*center.items.each(function(item){//返回false，则终止循环
						if(selected!=item.id){
							alert(selected);
							alert(item.id);
						}
						//如果所选的节点id在tabpanel中的子tab的id一样
						if(selected==item.id){
							alert(item.id);
							center.setActiveTab(item);
							return false;
						}else{//所选的节点代表的id，在tabpanel中没有tab
							if(selected=="inOne"){
								center.add(loForm);//将一级指标表单引入center
								center.setActiveTab(loForm);
								return false;
							}else if(selected=="inTwo"){
								center.add(ltForm);
								center.setActiveTab(ltForm);
								return false;
							}else if(selected=="inThree"){
								return false;
								//center.add(loForm);
							}else if(selected=="inNaMat"){
								return false;
								//alert("inNaMat");
								//center.add(loForm);
							}else if(selected=="usMat"){
								return false;
								//alert("usMat");
								//center.add(loForm);
							}else if(selected=="inMat"){
								//alert("inMat");
								//center.add(loForm);
								return false;
							}
						}
						//alert(item.id);
						//center.remove(item);//删除所有子tab
					});*/
					
					//alert(center.getId());
					//alert(center.onDestroy());
					/*setTimeout(function(){//5秒后溢出tab,可以
        				center.child('#tab1').tab.hide();
       					 //var users = tabs.child('#users');
      				 	 //users.tab.show();
      					 // tabs.setActiveTab(users);
   					 }, 5000);*/
					//alert(rec.parentNode.data.text);//父节点文本
					//alert(item.innerText);//读取节点字面值
					//alert(flag);
					
					
					/*var center = Ext.getCmp("center");
					//使用组件的add()方法，加新组建加入。
					center.add(loForm);*/
				}
			}
		});
/*var tabs=Ext.create('Ext.tab.Panel', {  
        width: 400,  
        height: 400,  
        //renderTo: document.body,  
        items: [{  
            title: 'Foo'  
        }, {  
            title: 'Bar',  
            tabConfig: {  
                title: 'Custom Title',  
                tooltip: 'A button tooltip'  
            }  
        }]  
    });  */

