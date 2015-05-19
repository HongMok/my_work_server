package com.mx.core.service.user;

import com.mx.core.pojo.User;
import com.mx.core.service.BaseService;

public class UserServiceImpl extends BaseService implements UserServiceInf {
	
	private static final String cmdFindUser = "select * from user where userName=? and password = ?";
	
	private static final String cmdUpdateUser = "replace into user (UserId, PassWord, NickName, Email, MobliePhone) values( ?, ?, ?, ?, ?)";
	
	
	@Override
	public User findUserById(String userid, String psw )
	{
		User user = dao.findObject( cmdFindUser, User.class, new Object[]{userid, psw });
		
		return user;
	}


	@Override
	public void updateUser(User user) {
		dao.update( cmdUpdateUser, new Object[]{ user.getUserId(), user.getPassWord(), user.getNickName(), user.getEmail(), user.getMobliePhone() });
	}
}
