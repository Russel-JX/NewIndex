<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ext-4.0/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ext-4.0/resources/css/tool-pic.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/ext-4.0/ext-all.js"></script>
	<script type="text/javascript">
		//点击超链接，提交表单
		function login_submit(){
			//输入非空验证
			var username = document.getElementById("username").value;
			var password = document.getElementById("password").value;
			if(username!=""&&password!=""){
				login_form.submit();
			}
		}
		//提交快捷键==回车
		function a(event){
			if(event.keyCode==13){
				login_submit();
			}
		}
	</script>
	<style type="text/css">
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
			background-color: #016aa9;
			overflow:hidden;
		}
		.STYLE1 {
			color: #000000;
			font-size: 12px;
		}
	</style>
	

  </head>
  
  <body>
  <s:form name="login_form" method="post" action = "userAction!validation.do">
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><table width="962" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="235" background="<%=request.getContextPath() %>/images/login_images/login_03.gif"> <br></td>
      </tr>
      <tr>
        <td height="53"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="394" height="53" background="<%=request.getContextPath() %>/images/login_images/login_05.gif">&nbsp;</td>
            <td width="206" background="<%=request.getContextPath() %>/images/login_images/login_06.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
              	
                <td width="16%" height="25"><div align="right"><span class="STYLE1">用户名</span></div></td>
                <td width="57%" height="25"><div align="center">
                  <input type="text" id="username" name="username" placeholder = "用户名不能为空" value="" style="width:105px; height:17px; background-color:#ffffff; border:solid 1px #7dbad7; font-size:12px; ">
                </div>
                </td>
                <td width="27%" height="25">&nbsp;</td>
                
              </tr>
              <tr>
                <td height="25"><div align="right"><span class="STYLE1">密码</span></div></td>
                <td height="25"><div align="center">
                  <input onkeyup="a(event)" type="password" id="password" name="password" placeholder = "密码不能为空" value=""  style="width:105px; height:17px; background-color:#ffffff; border:solid 1px #7dbad7; font-size:12px; ">
                </div></td>
                
                <td height="25"><div align="left"><a onclick ="login_submit()"><img src="<%=request.getContextPath() %>/images/login_images/dl.gif" width="49" height="18" border="0"></a></div></td>
              </tr>
            </table></td>
            <td width="362" background="<%=request.getContextPath() %>/images/login_images/login_07.gif">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="213" background="<%=request.getContextPath() %>/images/login_images/login_08.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
<!-- 防止表单重复提交 -->
<s:token></s:token>
</s:form>
  </body>
</html>
