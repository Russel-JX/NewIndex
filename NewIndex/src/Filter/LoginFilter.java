package Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pojo.ZbUser;

public class LoginFilter extends HttpServlet implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
//		System.out.println("执行到了用户登录过滤器......");
		HttpServletResponse response = (HttpServletResponse) arg1;  
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpSession session = request.getSession(true);  
		ZbUser zbUser = (ZbUser) session.getAttribute("zbUser");//
		String url=request.getRequestURI();
//		System.out.println("url是："+url);
		//如果访问登陆或登出action或者查询总和表，则不用拦截
		if(url.equalsIgnoreCase("/NewIndex/userAction!validation.do")||url.equalsIgnoreCase("/NewIndex/userAction!logout.do")||url.equalsIgnoreCase("/NewIndex/threeqoneAction.do")
				||url.equalsIgnoreCase("/NewIndex/threeqtwoAction!fetchOneByDate.do")||url.equalsIgnoreCase("/NewIndex/threeqthreedataAction.do")){
			arg2.doFilter(arg0, arg1);
			return;
		}
		//如果会话中无此人，或者有普通管理员。访问后台管理，也跳到登陆页
		if(zbUser==null||((zbUser.getUsertype()==0)&&(url.equalsIgnoreCase("/NewIndex/manage.jsp"))))
		{
//			System.out.println("执行到拦截器的if判断session");
			//判断获取的路径不为空且不是访问登录页面或执行登录操作时跳转
			/*if(url!=null && !url.equals("") && ( url.indexOf("Login")<0 && url.indexOf("login")<0 ))
			{*/
				response.sendRedirect("http://"+request.getServerName()+":"+request.getLocalPort()+request.getContextPath()+"/index.jsp");
//				response.sendRedirect("http://10.120.4.13:7777/dutys/login.jsp");
				return ;
			//}			
		}
		System.out.println("姓名是：" +zbUser.getRealname());
			//已通过验证，用户访问继续
			arg2.doFilter(arg0, arg1);
			return;
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
