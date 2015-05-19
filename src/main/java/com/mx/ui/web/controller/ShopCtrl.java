package com.mx.ui.web.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mx.core.pojo.Shop;
import com.mx.core.service.shop.ShopConfig;

@Controller
@RequestMapping("shop")
public class ShopCtrl extends PageController{

	protected Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping("findShopById")
	public void findShopById( HttpServletRequest req, HttpServletResponse rep ) throws Exception
	{
		Integer shopId = Integer.valueOf( req.getParameter("shopId") );
		if( null == shopId ){
			return ;
		}
		Shop shop = ShopConfig.getShopById( shopId );
		
		putResultResponse( rep, shop.toJsonObj() );
	}
	
	@RequestMapping("findShopByType")
	public void findUser( HttpServletRequest req, HttpServletResponse rep ) throws Exception
	{
		Integer typeFirst = Integer.valueOf( req.getParameter("typeFirst") );
		Integer typeSecond = Integer.valueOf( req.getParameter("typeSecond") );
		if( null == typeFirst || null == typeSecond ){
			return ;
		}
		ArrayList<Shop> shopList = ShopConfig.getShopsByType(typeFirst, typeSecond);
		JSONArray jsonList = new JSONArray();
		for( Shop s : shopList ){
			jsonList.add( s.toJsonObj() );
		}
		putResultResponse(rep, jsonList);
	}
	
}
