package com.mx.core.pojo;

public class Shop {

	private int shopId;
	private int typeFirst;
	private int typeSecond;
	private String name;
	private String addressCN;
	private String addressCoordinate;
	private String description;
	private String special;
	private String phoneList;
	private String averagePrice;
	private String extObj;
	
	public Shop parse( Object o )
	{
		return this;
	}
	
	
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public int getTypeFirst() {
		return typeFirst;
	}
	public void setTypeFirst(int typeFirst) {
		this.typeFirst = typeFirst;
	}
	public int getTypeSecond() {
		return typeSecond;
	}
	public void setTypeSecond(int typeSecond) {
		this.typeSecond = typeSecond;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddressCN() {
		return addressCN;
	}
	public void setAddressCN(String addressCN) {
		this.addressCN = addressCN;
	}
	public String getAddressCoordinate() {
		return addressCoordinate;
	}
	public void setAddressCoordinate(String addressCoordinate) {
		this.addressCoordinate = addressCoordinate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
	public String getPhoneList() {
		return phoneList;
	}
	public void setPhoneList(String phoneList) {
		this.phoneList = phoneList;
	}
	public String getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(String averagePrice) {
		this.averagePrice = averagePrice;
	}
	public String getExtObj() {
		return extObj;
	}
	public void setExtObj(String extObj) {
		this.extObj = extObj;
	}
	
	
}
