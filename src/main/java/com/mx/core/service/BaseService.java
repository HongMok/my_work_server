package com.mx.core.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mx.core.dao.IBaseDao;

public class BaseService {
	
	public Log log = LogFactory.getLog(this.getClass());

	protected IBaseDao dao;

	public IBaseDao getDao() {
		return dao;
	}

	public void setDao(IBaseDao dao) {
		this.dao = dao;
	}
}
