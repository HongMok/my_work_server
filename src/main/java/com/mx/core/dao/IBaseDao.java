package com.mx.core.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author zhuty
 */
public interface IBaseDao {

	/**
	 * 计算数据条数
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int count(String sql);
	public int count(String sql, Object[] params);

	/**
	 * 查询一个整数
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public long amout(String sql);
	public long amout(String sql, Object[] params);

	/**
	 * 插入单条记录
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int add(String sql);
	public int add(String sql, Object[] params);
	public Map<String, Object> addRntKey(final String sql, final Object[] params,final String[] retCols);
	public int add(Object o);
	public Map<String, Object> addRntKey(Object o,final String[] retCols);
	

	/**
	 * 更新相关记录
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String sql);
	public int update(String sql, Object[] params);
	
	/**
	 * 单个 sql 修改 参数为ModelBean
	 * 
	 * @param sql
	 * @param params: Object[] (sql= ?, ?)/Bean(sql= :userId, :password)
	 * @return
	 */
	public int updateByBean(String sql, Object params);

	/**
	 * 删除相关记录
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int delete(String sql);
	public int delete(String sql, Object[] params);

	/**
	 * 更新或删除相关记录
	 * 
	 * @param sql
	 * @param batchArgs
	 * @return
	 */
	public int[] updateBatch(String sql, List<Object[]> batchArgs);

	/**
	 * 批量: 执行不同形式的sql和参数,参数和sql记录匹配
	 * 
	 * @param sqls
	 * @param batchArgs
	 * @return
	 */
	public int[] updateBatch(List<String> sqls, List<Object[]> batchArgs);

	/**
	 * 返回Bean对象
	 * 
	 * @param <T>
	 * @param sql
	 * @param cls
	 * @param params
	 * @return
	 */
	public <T> T findObject(String sql, Class<T> cls);
	public <T> T findObject(String sql, Class<T> cls, Object[] params);
	public <T> T findSimpleObj(String sql,Class<T> clz,Object... args);

	/**
	 * 将记录组成一个POJO数据对象返回
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	public Object[] findObject(String sql);
	public Object[] findObject(String sql, Object[] objects);

	/**
	 * 单条记录的一个Map
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public Map<String, Object> findMap(String sql);
	public Map<String, Object> findMap(String sql, Object[] params);

	/**
	 * 查询所有记录List<Bean>: 无参
	 * 
	 * @param <T>
	 * @param sql
	 * @param bean
	 * @return
	 */
	public <T> List<T> findAll(String sql, Class<T> bean);

	/**
	 * 查询相关记录List<Bean>: 带参数
	 * 
	 * @param <T>
	 * @param sql
	 * @param bean
	 * @param params
	 * @return
	 */
	public <T> List<T> findList(String sql, Class<T> bean);
	public <T> List<T> findList(String sql, Class<T> bean, Object[] params);
	public <T> List<T> findSimpleList(String sql,Class<T> cls,Object[] params);

	/**
	 * 翻页查询: 带参数
	 * 
	 * @param <T>
	 * @param filter
	 * @param bean
	 * @param start
	 * @param size
	 * @param params
	 * @return
	 */
	public <T> List<T> findList(String filter, Class<T> bean, int start, int size);
	public <T> List<T> findList(String filter, Class<T> bean, int start,
			int size, Object[] params);

	/**
	 * 查询相关记录List<Map>: 带参数
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findListMap(String sql);
	public List<Map<String, Object>> findListMap(String sql, Object[] params);
	public List<Map<String, Object>> findListMap(String sql,int start, int size, Object[] params);

	/**
	 * 将记录组成一个数据对象并放入List中
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	public List<Object[]> findList(String sql);
	public List<Object[]> findList(String sql, Object[] objects);
	public List<Object[]> findList(String sql,int start, int size, Object[] params);
}
