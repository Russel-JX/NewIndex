<%@ page language="java" import="java.util.*,com.pojo.ZbUser" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'nocache.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
      <%
    response.setHeader("Pragma","No-cache");          
    response.setHeader("Cache-Control","no-cache");   
    response.setHeader("Cache-Control", "no-store");   
    response.setDateHeader("Expires",0);
    if(session!=null){
    	//out.print(((ZbUser)session.getAttribute("zbUser")).getRealname());
        session.invalidate();}
       // out.print("___________"+((ZbUser)session.getAttribute("zbUser")).getRealname());
    %>
    <script type="text/javascript">window.parent.parent.location.href="<%=path%>/index.jsp";</script>
  </body>
</html>
