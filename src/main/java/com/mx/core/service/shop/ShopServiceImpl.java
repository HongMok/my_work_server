package com.mx.core.service.shop;

import java.util.List;

import com.mx.core.pojo.Shop;
import com.mx.core.service.BaseService;

public class ShopServiceImpl extends BaseService {

	private static final String sqlGetAllShop = "select * from Shop";
	
	public List<Shop> getAllShop( ){
		return dao.findList( sqlGetAllShop, Shop.class );
	}
	
}
