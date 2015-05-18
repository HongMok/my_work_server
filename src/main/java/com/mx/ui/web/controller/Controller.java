package com.mx.ui.web.controller;


/**
 * 用于定义一些常量
 * @author 朱天裕
 *
 */
public interface Controller {
	public static final String CUSTOM = "custom";//提交后反馈的自定义信息可以放在这里
	
   	public static final String MESSAGE = "message";//提交后的反馈信息变量名称
   	
   	public static final String  SEARPARAM="searparam"; //提交后按“返回”按钮要跳转到的页面的参数
   	
   	public static final String TARGET = "target"; // 自定义放回连接
   	
   	public static final String RESULT = "result";//查询结果放在request中的变量名称
   	
   	public static final String AJAX_MSG = "jsonString";
   	
   	public static final String MESSAGE_URL = "main/ShowMessage";
   	public static final String AJAX_URL = "main/ajax";
   	
   	
   	public static final String COMMON_LOGIN_URL = "";
}
