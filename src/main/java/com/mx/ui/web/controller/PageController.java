package com.mx.ui.web.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mx.core.util.Auxs;

public class PageController implements Controller{
	
	public static final String PAGE_PARAM = "page_param";
	
   	@ModelAttribute(PAGE_PARAM)
	public PageParam dealPageParam(String pageIndex,String pageSize){
   		if(pageIndex==null||pageSize==null||Auxs.empty(pageSize)||Auxs.empty(pageIndex))
   			return null;
   		try{
   			int index = Integer.parseInt(pageIndex);
   			int size = Integer.parseInt(pageSize);
   			PageParam pp = new PageParam();
   	   		pp.setPageIndex(index);
   	   		pp.setPageSize(size);
   	   		return pp;
   		}catch(Exception e){
   			e.printStackTrace();
   			return null;
   		}
	}
   	
   	protected PageParam getPageParam(ModelMap m){
   		PageParam pp = (PageParam)m.get(PAGE_PARAM);
		if(pp==null){
			pp = new PageParam();
			m.put(PAGE_PARAM, pp);
		}
		return pp;
   	}
   	
   	protected void putResultResponse( HttpServletResponse rep, Object res ) throws Exception{
   		PrintWriter writer = rep.getWriter();
		writer.println(res.toString()); 
		writer.flush(); 
		writer.close();
	}
}
