<%@ page language="java" import="java.util.*,com.pojo.ZbUser" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>首页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache,no-store, must-revalidate">
<META HTTP-EQUIV="pragma" CONTENT="no-cache">



<link rel="stylesheet" type="text/css" href="<%=path %>/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/main.css" />
<script type="text/javascript">
	//点击超链接，提交表单
	function login_submit() {
		//输入非空验证
		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		if (username != "" && password != "") {
			login_form.submit();
		}
	}
	//提交快捷键==回车
	function a(event) {
		if (event.keyCode == 13) {
			login_submit();
		}
	}
</script>
</head>

<body>
	<div id="page-wrap">

		<div class="header header-partlycloudy">
			<!-- Change the second class above to change the header graphic -->
		</div>

		<div id="main-content">

			<div id="left-col">

				<h1 align="center" style="font-size:28;color:green">国网指标线上维护系统</h1>
				<form name="login_form" method="post"
					action="userAction!validation.do">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>

							<td width="16%" height="25"><div align="center">
									<span class="STYLE1" style="font-family:宋体; font-size: 18;">用户名</span>
								</div>
							</td>
							<td width="57%" height="50"><div align="center">
									<input type="text" id="username" name="username"
										placeholder="用户名不能为空" value="hyt"
										style="width:150px; height:20px; background-color:#ffffff; border:solid 1px #7dbad7; font-size:12px; ">
								</div></td>
							<td width="27%" height="25">&nbsp;</td>

						</tr>
						<tr>
							<td height="25"><div align="center">
									<span class="STYLE1" style="font-family:宋体;  font-size: 18;">密码</span>
								</div>
							</td>
							<td height="25"><div align="center">
									<input onkeyup="a(event)" type="password" id="password"
										name="password" placeholder="密码不能为空" value="11"
										style="width:150px; height:20px; background-color:#ffffff; border:solid 1px #7dbad7; font-size:12px; ">
								</div>
							</td>
							<td height="25"><div align="left">
									<a onclick="login_submit()"><img 
										src="<%=request.getContextPath() %>/images/login_images/dl.gif"
										width="49" height="18" border="0" style="cursor:hand;">
									</a>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>

			<div id="right-col">

				<div class="weather-box partlycloudy"></div>

				<img src="<%=path %>/images/index_images/blogroll.png"
					alt="Blogroll" /><br><br>
				<p class="login_href">
					<a href="<%=path %>/index_data_summary.jsp">查看各项指标明细</a><br>
					<a href="<%=path %>/form_layout.jsp">填写指标</a> <br><a
						href="<%=path %>/manage.jsp?">后台管理</a>
				</p>

			</div>

			<div class="clear: both;"></div>

		</div>

		<img src="<%=path %>/images/index_images/footer2.png" alt="footer" />

	</div>
	
</body>
</html>
