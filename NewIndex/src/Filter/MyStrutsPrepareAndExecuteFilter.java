package Filter;

 import java.io.IOException;
 
 import javax.servlet.FilterChain;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.http.HttpServletRequest;
 
 import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
 
 public class MyStrutsPrepareAndExecuteFilter extends
   StrutsPrepareAndExecuteFilter {
 
  @Override
  public void doFilter(ServletRequest req,ServletResponse res,FilterChain chain)throws IOException,ServletException{   
      HttpServletRequest request = (HttpServletRequest)req;   
      if("/zzjd/fckeditor/editor/filemanager/connectors/php/upload.php".equals(request.getRequestURI())){   
     chain.doFilter(req,res);   
     }else{
      //System.out.println(request.getRequestURI() + "-------------------");
      super.doFilter(req,res,chain);   
     }   
  } 
   
 }
