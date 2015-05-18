package com.mx.core.service.shop;

import java.util.ArrayList;

import com.mx.core.pojo.Shop;
import com.mx.core.service.BaseService;

public class ShopServiceImpl extends BaseService {

	private static final String sqlGetShopsByType = "select * from shop where typeFirst = ? and typeSecond = ? ";
	
	private static final String sqlGetShopById = "select * from shop where shopId = ? ";
	
	public ArrayList<Shop> getShopList( int typeFirst, int typeSecond ) throws Exception
	{
		return (ArrayList<Shop>) dao.findSimpleList( sqlGetShopsByType, Shop.class, new Object[]{ typeFirst, typeSecond } );
	}
	
	public Shop getShopById( int shopId ) throws Exception
	{
		return dao.findObject( sqlGetShopById, Shop.class, new Object[]{ shopId } );
	}
}
