package com.mx.ui.web.init;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mx.core.dao.BaseDao;
import com.mx.core.service.shop.ShopConfig;
import com.mx.core.util.SpringUtil;



public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 111111L;
	protected static final Log log = LogFactory.getLog(InitServlet.class);
	
	
	public void init() throws ServletException
	{

		System.out.println("system initttttttttt");
		log.info("开始加载系统参数");
		
		initConfig();
		
		log.info("加载系统参数成功");
	}
	
	private void initConfig()
	{
		ShopConfig.getAllShopConfig();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	
}