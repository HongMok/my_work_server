package com.mx.core.service.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mx.core.pojo.Shop;
import com.mx.core.util.SpringUtil;

public class ShopConfig {

	private static Map<Integer, Shop> mapConfig ;
	
	public static Map<Integer, Shop> getAllShopConfig(){
		if( mapConfig != null ){
			return mapConfig;
		}
		
		mapConfig = new HashMap<Integer, Shop>();
//		BaseDao dao = (BaseDao)SpringUtil.getBean("dao");
//		ArrayList<Shop> list = (ArrayList<Shop>)dao.findList( sqlGetShopConfig, Shop.class );
		ShopServiceImpl shopService = (ShopServiceImpl)SpringUtil.getBean("shopservice");
		List<Shop> list = shopService.getAllShop();
		if( null != list && list.isEmpty() == false ){
			for( Shop s : list ){
				mapConfig.put( s.getShopId(), s );
			}
		}
		return mapConfig;
	}

	public static Shop getShopById(int id) {
		return mapConfig.get( id );
	}

	public static ArrayList<Shop> getShopsByType(int typeFirst, int typeSecond) {
		ArrayList<Shop> list = new ArrayList<Shop>();
		for( Shop shop : mapConfig.values() ){
			if (shop.getTypeFirst() == typeFirst && shop.getTypeSecond() == typeSecond) {
				list.add(shop);
			}
		}
		return list;
	}
}
