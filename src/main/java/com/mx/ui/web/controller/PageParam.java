package com.mx.ui.web.controller;

public class PageParam {

	public static final int DEFAULT_PAGE_SIZE = 20;
	// 翻页字段
	private int pageIndex = 1;
	private int pageSize = DEFAULT_PAGE_SIZE;
	private int pageCount = -1;
	private int itemCount = -1;
   	
   	/**
   	 * 处理翻页时的起始记录
   	 * @param itemCount
   	 * @return
   	 */
   	public int getStart(int itemCount){
   		if(itemCount<0){
   			itemCount = 0;
   		}
		this.itemCount = itemCount;
		this.pageCount = (int) Math.ceil(((double) itemCount)
				/ ((double) pageSize));
		
		if (pageCount < pageIndex) {
			if (pageCount <= 0) {
				pageIndex = 1;
			} else {
				pageIndex = pageCount;
			}
		}
		return  pageSize* (pageIndex - 1);
	}
	
	// ======== getter/setter =================
	public void setPageIndex(int pageIndex){
		this.pageIndex=pageIndex;
	}
	public int getPageIndex(){
		return this.pageIndex;
	}
	public void setPageSize(int pageSize){
		this.pageSize=pageSize;
	}
	public int getPageSize(){
		return this.pageSize;
	}
	public void setPageCount(int pageCount){
		this.pageCount=pageCount;
	}
	public int getPageCount(){
		return this.pageCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public int getItemCount(){
		return this.itemCount;
	}
}
