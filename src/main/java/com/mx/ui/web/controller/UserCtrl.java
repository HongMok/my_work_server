package com.mx.ui.web.controller;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mx.core.pojo.User;
import com.mx.core.service.user.UserServiceInf;
import com.mx.core.util.ResultCode;

@Controller
@RequestMapping("user")
public class UserCtrl extends PageController{
	protected Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private UserServiceInf userservice;
	
	@RequestMapping("finduser")
	public String findUser(@RequestParam("un") String userid, @RequestParam("psw") String psw, ModelMap m,HttpSession session) throws Exception
//	public String findUser( HttpServletRequest req, HttpServletResponse rep,  ModelMap m,HttpSession session) throws Exception
	{
		int code=1;
		String msg="查询用户失败";
		User user=null;
		
//		String userid = req.getParameter("mobile");
//		String psw = req.getParameter("password");
		
//		user=userservice.findUserById(userid, psw);
//		if( user == null )
//		{
//			code = ResultCode.NOT_SUCH_USER;
//			msg = "fail";
//		}
//		else
//		{
//			code = ResultCode.SUCCESS;
//			msg = "suc";
//		}
		
		try
		{
			user=userservice.findUserById(userid, psw);
			code= ResultCode.SUCCESS;
			msg="查询用户成功";
		}catch(Exception ex)
		{
			code = ResultCode.NOT_SUCH_USER;
			log.error("查询用户失败", ex);
		}
		
		JSONObject res = new JSONObject();
		res.put("res", code);
		if( code== ResultCode.SUCCESS ) 
		{
			res.put("user", user.toJsonObj());
		} 
//		putResultResponse(rep, res);
		
		m.put(AJAX_MSG, res.toString());
		return AJAX_URL;
	}
	
	@RequestMapping("updateuser")
//	public String findUser( HttpServletRequest req,HttpServletResponse rep,  ModelMap m,HttpSession session) throws Exception
	public String udpateUser( ModelMap m,HttpSession session ) throws Exception
	{
		int code = 1;
		String msg = "";
		
		User user = new User();
		user.setUserId("xia");
		user.setPassWord("123");
		user.setMobliePhone("2333544656");
		
		userservice.updateUser(user);
		
		String json=String.format("{code:'%d',msg:'%s'}", code,msg);
		m.put(AJAX_MSG, json);
		return AJAX_URL;
	}
	
}
