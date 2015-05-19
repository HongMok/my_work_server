package com.mx.core.service.user;

import com.mx.core.pojo.User;

public interface UserServiceInf {

	public User findUserById(String userid, String psw) ;
	
	public void updateUser(User user) ;
	
}
