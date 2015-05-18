package com.mx.ui.web.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mx.core.pojo.Shop;
import com.mx.core.service.shop.ShopServiceImpl;
import com.mx.core.util.ResultCode;

@Controller
@RequestMapping("shop")
public class ShopCtrl extends PageController{

protected Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private ShopServiceImpl shopService;
	
	@RequestMapping("findShopById")
	public String findShop(@RequestParam("shop") int shopId, ModelMap m,HttpSession session) throws Exception
	{
		int code=1;
		String msg="查询用户失败";
		Shop shop=null;
		
		
		try
		{
			shop=shopService.getShopById(shopId);
			code= ResultCode.SUCCESS;
			msg="查询用户成功";
		}catch(Exception ex)
		{
			code = ResultCode.NOT_SUCH_USER;
			log.error("查询用户失败", ex);
		}
		String json=String.format("{code:'%d',msg:'%s'}", code,msg);
		if(shop!=null){
			json=String.format("{code:'%d',msg:'%s',userid:'%s'}", code,msg,shop.getName() );
		}
		m.put(AJAX_MSG, json);
		return AJAX_URL;
	}
	
	@RequestMapping("findShopByType")
	public String findShop(@RequestParam("typeFirst") int typeFirst, @RequestParam("typeSecond") int typeSecond, ModelMap m,HttpSession session) throws Exception
	{
		int code=1;
		String msg="查询用户失败";
		ArrayList<Shop> shopList = null;
		
		
		try
		{
			shopList = shopService.getShopList(typeFirst, typeSecond);
			code= ResultCode.SUCCESS;
			msg="查询用户成功";
		}catch(Exception ex)
		{
			code = ResultCode.NOT_SUCH_USER;
			log.error("查询用户失败", ex);
		}
		String json=String.format("{code:'%d',msg:'%s'}", code,msg);
		if(shopList!=null){
			json=String.format("{code:'%d',msg:'%s',len:'%d'}", code,msg, shopList.size()  );
		}
		m.put(AJAX_MSG, json);
		return AJAX_URL;
	}
	
}
