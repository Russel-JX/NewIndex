package com.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.dao.IUserDao;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pojo.ZbUser;

public class UserAction extends ActionSupport implements ServletRequestAware, ServletResponseAware,ModelDriven<Object>{
//	//返回的json对象
//	private List<ZbUser> items = new ArrayList<ZbUser>();
//	private long total;
	
	private ZbUser zbUser = new ZbUser();
	
	private HttpServletRequest request; 	
	private HttpServletResponse response; 	
	private IUserDao iud;
	
	//领域对象获取前端对象
	public Object getModel() {
		return zbUser;
	}
	//欢迎信息
	/**
	 * produce encryption code
	 * @param seq
	 * @return String
	 * @throws IOException 
	 */
	//加密解密
	public String Encoder_Decoder(String seq) throws NoSuchAlgorithmException, IOException{
		MessageDigest md5Code=MessageDigest.getInstance("md5");
		BASE64Encoder base64_encoder=new BASE64Encoder();
		byte[] bTmp=md5Code.digest(seq.getBytes());
		seq=base64_encoder.encode(bTmp);
		
		BASE64Decoder base64_decoder=new BASE64Decoder();
		byte[] pass_show = base64_decoder.decodeBuffer(seq);
		System.out.println("明文是： "+pass_show);
		return seq;
	}
//	public String welcomeInfor(){
//		zbUser = (ZbUser)(request.getSession().getAttribute("zbUser"));
//		return "json";
//	}
	//用户登出
	public String logout(){
		request.getSession().removeAttribute("zbUser");
		return "index";
	}
	
	//检验用户登录
	public String validation() throws Exception{
		System.out.println("用户名是："+zbUser.getUsername()+"密码是： "+zbUser.getPassword());
		String name = zbUser.getUsername();
		String pwd = zbUser.getPassword();
		// 查询用户是否存在
		List list = (List) iud.queryByName(name);
		if((list!=null)&&(list.size()>0)){
			ZbUser zbUser2 = ((ZbUser)list.get(0));
//			for(int i=0;i<list.size();i++){
//				System.out.println("集合大小是："+list.size());
//				System.out.println("======="+((ZbUser)list.get(i)).getUsername()+"-"+((ZbUser)list.get(i)).getUserid()+"-"+((ZbUser)list.get(i)).getPassword());
//			}
			if(zbUser2.getPassword().equals(pwd)){
				//查询此用户所负责的1,2,3级指标id
				
				request.getSession().setAttribute("zbUser", zbUser2);
				if(zbUser2.getUsertype().equals(1)){//是用户且是超管
					response.getWriter().write(zbUser2.getRealname());
					return "manage";
				}else{//是用户且是普管
					response.getWriter().write(zbUser2.getRealname());
					return "fill";
				}
			}
		}
		return "index";//登陆页
	}
	
	//增加一个用户
	public void addUser() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("在增加用户action中...");
//		System.out.println("用户姓名是： "+zbUser.getRealname()+"用户名是： "+zbUser.getUsername()
//				+"用户密码是： "+zbUser.getPassword()+"用户类型是："+zbUser.getUsertype());
		
		Integer newid = (Integer)iud.add(zbUser);
//		System.out.println("成功插入一条记录，id是： "+newid);
		String messageBack = "{success:true,data:{zbUserName:'"+zbUser.getRealname()+"'}}";
		
//		System.out.println("————————————————————");
		response.getWriter().write(messageBack);

	}
	//修改一个用户
	public void updateUser() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("修改用户信息...");
//		System.out.println("用户id是： "+zbUser.getUserid());
//		System.out.println("新姓名是："+zbUser.getRealname());
//		System.out.println("新用户名是："+zbUser.getUsername());
//		System.out.println("新密码是："+zbUser.getPassword());
//		System.out.println("###"+zbUser.getUsertype());
//		System.out.println("&&&"+zbUser.getZbSort());
		
		zbUser.setRealname(URLDecoder.decode(URLDecoder.decode(zbUser.getRealname(),"UTF-8"),"UTF-8")); 
		zbUser.setUsername(URLDecoder.decode(URLDecoder.decode(zbUser.getUsername(),"UTF-8"),"UTF-8")); 
		zbUser.setPassword(URLDecoder.decode(URLDecoder.decode(zbUser.getPassword(),"UTF-8"),"UTF-8")); 
		
		//修改用户信息(未完成)
		//iud.update("update ZbUser t set(t.zbUsername,t.zbUsertype,t.zbSort) values( "+zbUser.getUsername()+","+zbUser.getUsertype()+","+zbUser.getZbSort()+" ) where t.zbUserid = "+zbUser.getUserid() );
		iud.updateObject(zbUser);
		
		String messageBack = "{success:true,data:{zbUserName:'胡汉三回来了',age:'30'}}";
		
//		System.out.println("————————————————————");
		response.getWriter().write(messageBack);
		
	}
	//删除一个用户
	public void deleteUser() throws Exception {
//		System.out.println("————————————————————");
//		System.out.println("删除一个用户action中...");
//		
//		System.out.println("要删除的用户id是： "+zbUser.getUserid()+"姓名是："+zbUser.getRealname()+"用户名是："+zbUser.getUsername()+"密码是："+zbUser.getPassword()+"类型是： "+zbUser.getUsertype());
		
		//先查此用户是否负责过指标，有则不能删除。
		long number = iud.queryTotalIndex("ZbDetail", "zbPrincipal", zbUser.getUserid());
//		System.out.println(zbUser.getRealname()+" 负责过 "+number+" 个三级指标。不能删除！");
		if(number==0){
			//删除用户
			iud.delete(zbUser);
			response.getWriter().write("{flag:'yes'}");
			return;
		}else{
			String warning = "删除失败! 该用户曾经管理过 "+ number+" 个三级指标";
			response.getWriter().write("{warning:'"+warning+"'}");
		}
//		System.out.println("————————————————————");
	}
	
	public String execute() throws Exception {
		
		return super.execute();
	}
	
	public IUserDao getIud() {
		return iud;
	}
	public void setIud(IUserDao iud) {
		this.iud = iud;
	}
	public ZbUser getUser() {
		return zbUser;
	}
	public void setUser(ZbUser zbUser) {
		this.zbUser = zbUser;
	}
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}


}
