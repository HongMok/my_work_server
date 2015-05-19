package com.mx.ui.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mx.core.pojo.User;
import com.mx.core.service.user.UserServiceInf;
import com.mx.core.util.ResultCode;

@Controller
@RequestMapping("ctlUser")
public class UserCtrl extends PageController{
	
	/**
	 * 验证用户登陆
	 * 
	 * @param mobile:String        电话
	 * @param password:String      密码
	 * 
	 * @return
	 * <li> res:int {@link ResultCode}
	 * <li> user:User {@link User}
	 * */
	private static final String reqFindUser = "reqFindUser";
	
	/**
	 * 新增或者更新用户信息
	 * 
	 * @param user:User
	 * */
	private static final String reqUpdateUser = "reqUpdateUser";
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private UserServiceInf userservice;
	
	@RequestMapping(reqFindUser)
	public void findUser( HttpServletRequest req, HttpServletResponse rep ) throws Exception{
		int code=1;
		
		String userid = req.getParameter("mobile");    //电话
		String psw = req.getParameter("password");     //密码
		
		JSONObject res = new JSONObject();
		User user = userservice.findUserById(userid, psw);
		if( null == user ){
			code = ResultCode.NOT_SUCH_USER;           //该用户不存在
		}
		else{
			code = ResultCode.SUCCESS;
			res.put("user", user.toJsonObj());
		}
		res.put("res", code);
		
		putResultResponse(rep, res);
	}
	
	@RequestMapping(reqUpdateUser)
	public void updateUser( HttpServletRequest req, HttpServletResponse rep ) throws Exception
	{
		JSONObject obj = (JSONObject)req.getAttribute("user");
		User user = new User().parse(obj);
		
		userservice.updateUser(user);
	}
	
}
