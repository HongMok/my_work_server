package com.mx.core.pojo;

import net.sf.json.JSONObject;

public class User implements IData{
	private String userId;       //用户id
	private String passWord;     //密码
	private String nickName;
	private String email;
	private String mobliePhone;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobliePhone() {
		return mobliePhone;
	}
	public void setMobliePhone(String mobliePhone) {
		this.mobliePhone = mobliePhone;
	}
	
	@Override
	public JSONObject toJsonObj() {
		JSONObject obj = new JSONObject();
		obj.put("userId", userId);
		obj.put("passWord", passWord);
		obj.put("nickName", nickName);
		obj.put("email", email);
		obj.put("mobliePhone", mobliePhone);
		return obj;
	}
	
	public User parse( JSONObject obj ){
		userId = obj.getString( "userId" );
		passWord = obj.getString( "passWord" );
		nickName = obj.getString( "nickName" );
		email = obj.getString( "email" );
		mobliePhone = obj.getString( "mobliePhone" );
		
		return this;
	}

}
