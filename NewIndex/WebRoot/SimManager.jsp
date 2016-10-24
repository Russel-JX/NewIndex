<%@ page language="java" import="java.util.*,com.pojo.ZbUser" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>模拟ajax响应</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache,no-store, must-revalidate">
<META HTTP-EQUIV="pragma" CONTENT="no-cache">

<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/ext-all.js"></script>

<script type="text/javascript">
//引入扩展组件  
Ext.Loader.setConfig({enabled: true});  
  
Ext.Loader.setPath('Ext.ux', 'ext-4.0/ux');  

Ext.require([  
             'Ext.grid.*',  
             'Ext.data.*',  
             'Ext.ux.grid.FiltersFeature',  
             'Ext.toolbar.Paging',  
             'Ext.ux.ajax.JsonSimlet',  
             'Ext.ux.ajax.SimManager'  
         ]); 
Ext.Ajax.request({
    url: 'hh',
    params: {
        id: 1
    },
    success: function(response){
        var text = response.responseText;
        alert("success");
        // process server response here
    },
    failure: function(response, opts) {
   	 var nn = 9;
   	 alert(response+opts);
    }
});

Ext.onReady(function(){
	

	Ext.ux.ajax.SimManager.init({
        delay: 500  // Simulates network latency/server processing time
    }).register({
        '*': {
            stype: 'json',  // stype is what kind of Simlet you want to use
            data:  [
                // JSON response data, if needed
            ]
        }
    });
	
	
	
	//alert(1);
	/* Ext.ux.ajax.SimManager.init({
	    delay : 300
	}).register({
	        'hh' : {
	            stype : 'json',  // use JsonSimlet (stype is like xtype for components)
	            data  : [
	                          {
	                              "password": "hh",
	                              "realname": "啊哈",
	                              "userid": 41,
	                              "username": "韩寒",
	                              "usertype": 1,
	                              "zbSort": 34
	                          },
	                          {
	                              "password": "测试",
	                              "realname": "test",
	                              "userid": 40,
	                              "username": "test",
	                              "usertype": 1,
	                              "zbSort": 100
	                          }
	                      ]
	                      
	        }
	    });  */
});

</script>
</head>

<body>
	<div id="page-wrap">

		

	</div>
	
</body>
</html>
