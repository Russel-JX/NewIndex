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

public class CharacterEncodingFilter extends HttpServlet implements Filter {

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
//		System.out.println("执行到了字符编码过滤器......");
		
		
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;  
		
		
		//好像设置了请求和响应的编码后，不管用
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		request.getSession().setAttribute("a", 1234);
		
			arg2.doFilter(arg0, arg1);
			return;
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
