package com.mx.core.service.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mx.core.pojo.Shop;
import com.mx.core.util.SpringUtil;

public class ShopConfig {

	private static Map<Integer, Shop> mapConfig ;
	
	private static List<Shop> listAll;
	private static Map<Integer, List<Shop>> mapFirstType;
	
	public static Map<Integer, Shop> getAllShopConfig(){
		if( mapConfig != null ){
			return mapConfig;
		}
		
		mapConfig = new HashMap<Integer, Shop>();
		mapFirstType = new HashMap<Integer, List<Shop>>();
		ShopServiceImpl shopService = (ShopServiceImpl)SpringUtil.getBean("shopservice");
		listAll = shopService.getAllShop();
		if( null != listAll && listAll.isEmpty() == false ){
			for( Shop s : listAll ){
				mapConfig.put( s.getShopId(), s );
				
				if( mapFirstType.get( s.getShopId() ) != null ){
					mapFirstType.get( s.getShopId() ).add( s );
				}
				else{
					List<Shop> l = new ArrayList<Shop>();
					l.add( s );
					mapFirstType.put( s.getShopId(), l );
				}
			}
		}
		return mapConfig;
	}
	
	/** 从所有商店配置数组中，取出一段 */
	public static List<Shop> getPartFromListAll( int fromIndex, int count ){
		return listAll.subList( fromIndex, fromIndex + count );
	}
	
	/** 从某一类型商店配置中，取出一段 */
	public static List<Shop> getPartFromListFirstType( int typeFirst, int fromIndex, int count ){
		return mapFirstType.get( typeFirst ).subList(fromIndex, fromIndex + count );
	}

	/** 根据shopId找配置 */
	public static Shop getShopById(int id) {
		return mapConfig.get( id );
	}

	/** 根据一级，二级，类型id找到配置 */
	public static List<Shop> getShopsByType(int typeFirst, int typeSecond) {
		List<Shop> list = new ArrayList<Shop>();
		for( Shop shop : mapFirstType.get( typeFirst ) ){
			if (shop.getTypeFirst() == typeFirst && shop.getTypeSecond() == typeSecond) {
				list.add(shop);
			}
		}
		return list;
	}
}
