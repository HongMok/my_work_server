package com.mx.ui.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mx.core.pojo.Shop;
import com.mx.core.service.shop.ShopConfig;

@Controller
@RequestMapping("ctlShop")
public class ShopCtrl extends PageController{

	/**
	 * 根据shopId，找出配置
	 * 
	 * @param shopId:int              商店ID    
	 * 
	 * @return shop:{@link Shop}     商店的信息
	 * */
	private static final String reqFindShopById = "reqFindShopById";
	
	/**
	 * 根据一级，二级，类型，找出配置
	 * 
	 * @param typeFirst:int                  一级id
	 * @param typeSecond:int                 二级id
	 * 
	 * @return list:Shop[] {@link Shop }
	 * */
	private static final String reqFindShopByType = "reqFindShopByType";
	
	/**
	 * 从所有配置中获取指定一段
	 * 
	 * @param fromIndex:int             指定数据段开始id
	 * @param count:int                 数据段数量
	 * 
	 * @return list:Shop[] {@link Shop }
	 * */
	private static final String reqGetPartFromAll = "reqGetPartFromAll";
	
	/**
	 * 从【某一类型的商店】中获取指定一段
	 * 
	 * @param typeFirst:int             指定一级类型
	 * @param fromIndex:int             指定数据段开始id
	 * @param count:int                 数据段数量
	 * 
	 * @return list:Shop[] {@link Shop }
	 * */
	private static final String reqGetPartFromTypeFirst = "reqGetPartFromTypeFirst";
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(reqFindShopById)
	public void findShopById( HttpServletRequest req, HttpServletResponse rep ) throws Exception
	{
		Integer shopId = Integer.valueOf( req.getParameter("shopId") );
		if( null == shopId ){
			return ;
		}
		Shop shop = ShopConfig.getShopById( shopId );
		JSONObject res = new JSONObject();
		res.put("shop", shop.toJsonObj() );
		
		putResultResponse( rep, res );
	}
	
	@RequestMapping(reqFindShopByType)
	public void findUser( HttpServletRequest req, HttpServletResponse rep ) throws Exception
	{
		Integer typeFirst = Integer.valueOf( req.getParameter("typeFirst") );
		Integer typeSecond = Integer.valueOf( req.getParameter("typeSecond") );
		if( null == typeFirst || null == typeSecond ){
			return ;
		}
		List<Shop> shopList = ShopConfig.getShopsByType(typeFirst, typeSecond);
		JSONArray jsonList = new JSONArray();
		for( Shop s : shopList ){
			jsonList.add( s.toJsonObj() );
		}
		JSONObject res = new JSONObject();
		res.put( "list", jsonList );
		putResultResponse(rep, res );
	}
	
	@RequestMapping(reqGetPartFromAll)
	public void getPartFromAll( HttpServletRequest req, HttpServletResponse rep ) throws Exception {
		Integer fromIndex = Integer.valueOf( req.getParameter("fromIndex") );
		Integer count = Integer.valueOf( req.getParameter("count") );
		if( null == fromIndex || null == count ){
			return ;
		}
		
		List<Shop> shopList = ShopConfig.getPartFromListAll(fromIndex, count);
		JSONArray jsonList = new JSONArray();
		for( Shop s : shopList ){
			jsonList.add( s.toJsonObj() );
		}
		JSONObject res = new JSONObject();
		res.put( "list", jsonList );
		putResultResponse(rep, res );
	}
	
	@RequestMapping(reqGetPartFromTypeFirst)
	public void getPartFromTypeFirst( HttpServletRequest req, HttpServletResponse rep ) throws Exception {
		Integer typeFirst = Integer.valueOf( req.getParameter("typeFirst") );
		Integer fromIndex = Integer.valueOf( req.getParameter("fromIndex") );
		Integer count = Integer.valueOf( req.getParameter("count") );
		if( null == typeFirst || null == fromIndex || null == count ){
			return ;
		}
		
		List<Shop> shopList = ShopConfig.getPartFromListFirstType( typeFirst, fromIndex, count);
		JSONArray jsonList = new JSONArray();
		for( Shop s : shopList ){
			jsonList.add( s.toJsonObj() );
		}
		JSONObject res = new JSONObject();
		res.put( "list", jsonList );
		putResultResponse(rep, res );
	}
	
}

