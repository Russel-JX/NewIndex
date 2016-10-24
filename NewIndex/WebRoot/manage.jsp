<%@ page language="java" import="java.util.*,com.pojo.ZbUser" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title> 首页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ext-4.0/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ext-4.0/resources/css/tool-pic.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/main.css" />
	
	<!-- mockjax模拟ajax请求和响应 -->
	<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/jquery.mockjax.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/js/mock_data.js"></script> --%>
	
	<!-- mockjs模拟ajax响应 -->
	<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/mock.js"></script> --%>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/ext-all.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/js/index.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/js/left.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/js/level_one.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/js/level_one_manage.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/js/level_two.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/js/level_two_manage.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/js/level_three.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/js/level_three_manage.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/js/user.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/js/data_reuse.js"></script>
	<!-- 修改和填写的时候使用表单（index_data_submit.js）；查看时，使用grid组件（index_data_view.js） -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/js/index_data_view.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/ext-lang-zh_CN.js"></script>
	
	
	<STYLE TYPE="text/css">
		<!--此处字体的可能影响-->
		.addIcon {	background-image: url(<%=request.getContextPath() %>/images/add.gif) !important; }  
		.addIcon {	background-image: url(<%=request.getContextPath() %>/images/add.gif) !important; }
		.openIcon {	background-image: url(<%=request.getContextPath() %>/images/open.gif) !important; }
		.editIcon {	background-image: url(<%=request.getContextPath() %>/images/edit.gif) !important; }
		.removeIcon {	background-image: url(<%=request.getContextPath() %>/images/removeIcon.gif) !important; }
		.removeIcon {	background-image: url(<%=request.getContextPath() %>/images/remove.gif) !important; }
 	 </STYLE>
	<script  type = " text/javascript "> 
		window.history.forward (1) ;  
	</script> 
	
  </head>
  
  <body>
    <div class="header">
    	<div class="welcome">
    		<p class="welcome">欢迎您！<%=((ZbUser)session.getAttribute("zbUser")).getRealname() %></p>
    	</div>
    	<div class="login">
    		<a href="<%=path %>/index.jsp">回到登陆页</a>
    	</div>
    	<div class="login">&nbsp;</div>
    	<div class="login">&nbsp;</div>
    	<div class="login">&nbsp;</div>
    	<div class="login">&nbsp;</div>
    	<div class="login">&nbsp;</div>
    	<div class="checkout">
    		<a href="nocache.jsp">退出</a>
    	</div>
    </div>
    
  </body>
</html>
