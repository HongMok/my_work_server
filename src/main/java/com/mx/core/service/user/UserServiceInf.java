package com.mx.core.service.user;

import com.mx.core.pojo.User;

public interface UserServiceInf {

	public User findUserById(String userid, String psw) throws Exception;
	
	public void updateUser(User user) throws Exception;
	
}
