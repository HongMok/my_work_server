package com.mx.core.dao;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.mx.core.util.Auxs;


/** 
 * 支持可变长度参数和非原始类型
 * 
 * @see 参数通配符为 ?
 * @author zhuty
 */
@SuppressWarnings("all")
public class BaseDao extends SimpleJdbcDaoSupport implements IBaseDao {

	private static final Log log = LogFactory.getLog(BaseDao.class);

	public BaseDao() {
	}

	/**
	 * 查询记录条数
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int count(String sql) {
		sql = "select count(*) from ("+sql+") t";
		if (log.isInfoEnabled()) {
			log.info("查询记录条数: \n" + printSql(sql, new Object[]{}));
		}
		return getSimpleJdbcTemplate().queryForInt(sql, new Object[]{});
	}
	public int count(String sql, Object[] params) {
		sql = "select count(*) from ("+sql+") t";
		if (log.isInfoEnabled()) {
			log.info("查询记录条数: \n" + printSql(sql, params));
		}
		return getSimpleJdbcTemplate().queryForInt(sql, params);
	}

	/**
	 * 计算一个整型结果
	 */
	public long amout(String sql) {
		sql = "select count(*) from ("+sql+") t";
		if (log.isInfoEnabled()) {
			log.info("计算一个整型结果: \n" + printSql(sql, new Object[]{}));
		}
		return getSimpleJdbcTemplate().queryForLong(sql, new Object[]{});
	}
	public long amout(String sql, Object[] params) {
		sql = "select count(*) from ("+sql+") t";
		if (log.isInfoEnabled()) {
			log.info("计算一个整型结果: \n" + printSql(sql, params));
		}
		return getSimpleJdbcTemplate().queryForLong(sql, params);
	}

	/**
	 * 单个 sql 修改,插入,删除
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int add(String sql) {
		if (log.isInfoEnabled()) {
			log.info("单个 sql 插入: \n" + printSql(sql, new Object[]{}));
		}
		return getSimpleJdbcTemplate().update(sql, new Object[]{});
	}
	public int add(String sql, Object[] params) {
		if (log.isInfoEnabled()) {
			log.info("单个 sql 插入: \n" + printSql(sql, params));
		}
		return getSimpleJdbcTemplate().update(sql, params);
	}
	
	public Map<String, Object> addRntKey(final String sql, final Object[] params,final String[] retCols){
		KeyHolder kh=new GeneratedKeyHolder();
		getSimpleJdbcTemplate().getJdbcOperations().update(new PreparedStatementCreator()
		{
  			public PreparedStatement createPreparedStatement(Connection con) throws SQLException
  			{
  				int i=1;
  				PreparedStatement ps;
  				ps=con.prepareStatement(sql,retCols);
  				for(Object o:params)
  				{
  					StatementCreatorUtils.setParameterValue(ps, i++, SqlTypeValue.TYPE_UNKNOWN, o);
  				}
  				return ps;
  			}			
		}, kh);
		
		return kh.getKeys();
	}
	
	public int add(Object o)
	{
		String sql;
		ArrayList ls=new ArrayList();
		sql=createInsertSql(true,o, ls,1);
		return add(sql, ls.toArray());
	}
	
	public Map<String, Object> addRntKey(Object o,final String[] retCols){
		final String sql;
		final ArrayList ls=new ArrayList();
		sql=createInsertSql(true,o, ls,1);
		return addRntKey(sql, ls.toArray(), retCols);
	}

	
	/**
	 * 单个 sql 修改,插入,删除
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String sql) {
		if (log.isInfoEnabled()) {
			log.info("单个 sql 修改: \n" + printSql(sql, new Object[]{}));
		}
		return getSimpleJdbcTemplate().update(sql, new Object[]{});
	}
	public int update(String sql, Object[] params) {
		if (log.isInfoEnabled()) {
			log.info("单个 sql 修改: \n" + printSql(sql, params));
		}
		return getSimpleJdbcTemplate().update(sql, params);
	}
	
	/**
	 * 单个 sql 修改 参数为ModelBean
	 * 
	 * @param sql
	 * @param params: Object[] (sql= ?, ?)/Bean(sql= :userId, :password)
	 * @return
	 */
	public int updateByBean(String sql, Object params) {
		if (log.isInfoEnabled()) {
			log.info("单个 sql 修改: sql = \n" + printSql(sql, params));
		}
		SqlParameterSource param = new BeanPropertySqlParameterSource(params);
		return getSimpleJdbcTemplate().update(sql, param);
	}

	/**
	 * 单个 sql 修改,插入,删除
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int delete(String sql) {
		if (log.isInfoEnabled()) {
			log.info("单个 sql 删除: \n" + printSql(sql,new Object[]{}));
		}
		return getSimpleJdbcTemplate().update(sql, new Object[]{});
	}
	public int delete(String sql, Object[] params) {
		if (log.isInfoEnabled()) {
			log.info("单个 sql 删除: \n" + printSql(sql, params));
		}
		return getSimpleJdbcTemplate().update(sql, params);
	}

	/**
	 * 批量 sql 修改,插入,删除
	 * 
	 * @param sql
	 * @param batchArgs
	 * @return
	 */
	public int[] updateBatch(String sql, List<Object[]> batchArgs) {
		int[] result = null;
		if (log.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			for (Object[] o : batchArgs) {
				log.info("执行批量 sql 修改,插入,删除: \n" + printSql(sql, o));
				sb.append(printSql(sql, o)).append(";\r\n");
			}
		}
		return getSimpleJdbcTemplate().batchUpdate(sql, batchArgs);
	}

	/**
	 * 批量执行不同形式的sql
	 * 
	 * @param sqls
	 * @param batchArgs
	 * @return
	 */
	public int[] updateBatch(List<String> sqls, List<Object[]> batchArgs) {
		if (sqls.size() == 0 || sqls.size() != batchArgs.size()) {
			return new int[] { 0 };
		}
		int size = sqls.size();
		int[] results = new int[size];
		for (int i = 0; i < size; i++) {
			String sql = sqls.get(i);
			Object[] eachArg = batchArgs.get(i);
			if (log.isInfoEnabled()) {
				log.info("批量执行不同形式的sql: \n" + printSql(sql, eachArg));
			}
			results[i] = getSimpleJdbcTemplate().update(sql, eachArg);
		}
		return results;
	}

	/**
	 * 返回查询的对象: 只有一条记录
	 * 
	 * @param sql
	 * @param cls
	 * @param params
	 * @return
	 */
	public <T> T findObject(String sql, Class<T> cls) {
		T object = null;
		if (log.isInfoEnabled()) {
			log.info("查询一条记录: \n" + printSql(sql, new Object[]{}));
		}
		BeanPropRowMap<T> rm = new BeanPropRowMap<T>(cls);
		rm.setPrimitivesDefaultedForNullValue(true);
		try{
			return getSimpleJdbcTemplate().queryForObject(sql, rm, new Object[]{});
		}catch(Exception e){
			log.info("empty result");
			return null;
		}
	}
	public <T> T findObject(String sql, Class<T> cls, Object[] params) {
		T object = null;
		if (log.isInfoEnabled()) {
			log.info("查询一条记录: \n" + printSql(sql, params));
		}
		BeanPropRowMap<T> rm = new BeanPropRowMap<T>(cls);
		rm.setPrimitivesDefaultedForNullValue(true);
		try{
			return getSimpleJdbcTemplate().queryForObject(sql, rm, params);
		}catch(Exception e){
			log.error("empty result",e);
			return null;
		}
	}
	
	public <T> T findSimpleObj(String sql,Class<T> clz,Object... args){
		try{
			return getSimpleJdbcTemplate().queryForObject(sql, clz, args);
		}catch(Exception e){
			log.info("empty result");
			return null;
		}
	}

	/**
	 * 查询回List<Bean>结果
	 * 
	 * @param sql
	 * @param bean
	 * @param params
	 * @return
	 */
	public <T> List<T> findList(String sql, Class<T> bean) {
		List<T> list = null;
		if (log.isInfoEnabled()) {
			log.info("查询回List<Bean>结果: \n" + printSql(sql, new Object[]{}));
		}
		BeanPropRowMap<T> rm = new BeanPropRowMap<T>(bean);
		rm.setPrimitivesDefaultedForNullValue(true);
		return getSimpleJdbcTemplate().query(sql, rm, new Object[]{});
	}
	public <T> List<T> findList(String sql, Class<T> bean, Object[] params) {
		List<T> list = null;
		if (log.isInfoEnabled()) {
			log.info("查询回List<Bean>结果: \n" + printSql(sql, params));
		}
		BeanPropRowMap<T> rm = new BeanPropRowMap<T>(bean);
		rm.setPrimitivesDefaultedForNullValue(true);
		return getSimpleJdbcTemplate().query(sql, rm, params);
	}

	/**
	 * 用户翻页的查询
	 * 
	 * @param <T>
	 * @param sql
	 * @param bean
	 * @param start
	 * @param size
	 * @param params
	 * @return
	 */
	public <T> List<T> findList(String inSql, Class<T> bean, int start,
			int size) {
		List<T> list = null;
		BeanPropRowMap<T> rm = new BeanPropRowMap<T>(bean);
		rm.setPrimitivesDefaultedForNullValue(true);
		StringBuilder sb = new StringBuilder(inSql);
		sb.append(" limit ").append(start).append(",").append(size);
		return getSimpleJdbcTemplate().query(sb.toString(), rm, new Object[]{});
	}
	public <T> List<T> findList(String inSql, Class<T> bean, int start,
			int size, Object[] params) {
		List<T> list = null;
		BeanPropRowMap<T> rm = new BeanPropRowMap<T>(bean);
		rm.setPrimitivesDefaultedForNullValue(true);
		StringBuilder sb = new StringBuilder();
		sb.append(inSql);
		sb.append(" limit ").append(start).append(",").append(size);
		if (log.isInfoEnabled()) {
			log.info("用户翻页的查询: sql = \n" + printSql(sb.toString(), params));
		}
		list = getSimpleJdbcTemplate().query(sb.toString(), rm, params);
		return list;
	}
	
	public <T> List<T> findSimpleList(String sql,Class<T> cls,Object[] params){
		return getSimpleJdbcTemplate().getJdbcOperations().queryForList(sql, cls, params);
	}

	/**
	 * 查询表中所有数据List<Bean>
	 * 
	 * @param <T>
	 * @param sql
	 * @param bean
	 * @return
	 */
	public <T> List<T> findAll(String sql, Class<T> bean) {
		if (log.isInfoEnabled()) {
			log.info("查询表中所有数据List<Bean>: sql = " + sql);
		}
		BeanPropRowMap<T> rm = new BeanPropRowMap<T>(bean);
		rm.setPrimitivesDefaultedForNullValue(true);
		return getSimpleJdbcTemplate().query(sql, rm);
	}

	/**
	 * 返回Map对象, 只有一条记录
	 */
	public Map<String, Object> findMap(String sql) {
		if (log.isInfoEnabled()) {
			log.info("返回Map对象, 只有一条记录: \n" + printSql(sql, new Object[]{}));
		}
		return getSimpleJdbcTemplate().queryForMap(sql,
					new Object[]{});
	}
	
	public Map<String, Object> findMap(String sql, Object[] params) {
		if (log.isInfoEnabled()) {
			log.info("返回Map对象, 只有一条记录: \n" + printSql(sql, params));
		}
		return getSimpleJdbcTemplate().queryForMap(sql, params);
	}

	/**
	 * 将记录组成一个数组对象返回
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	public Object[] findObject(String sql) {
		if (log.isInfoEnabled()) {
			log.info("将记录组成一个数据对象返回: \n" + printSql(sql, new Object[]{}));
		}
		List<Object> data = new ArrayList<Object>();
		Map<String, Object> map = null;
		map = getSimpleJdbcTemplate().queryForMap(sql, new Object[]{});
		// 效率优于keySet()
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		// 封装数据
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			// 只保留值,非key
			data.add(entry.getValue());
		}
		return data.toArray();
	}
	
	public Object[] findObject(String sql, Object[] params) {
		if (log.isInfoEnabled()) {
			log.info("将记录组成一个数据对象返回: \n" + printSql(sql, params));
		}
		List<Object> data = new ArrayList<Object>();
		Map<String, Object> map = null;
		map = getSimpleJdbcTemplate().queryForMap(sql, params);
		// 效率优于keySet()
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		// 封装数据
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			// 只保留值,非key
			data.add(entry.getValue());
		}
		return data.toArray();
	}

	/**
	 * 查询<key, value>的结果集List
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findListMap(String sql) {
		List<Map<String, Object>> list = null;
		if (log.isInfoEnabled()) {
			log.info("查询<key, value>的结果集List: \n" + printSql(sql, new Object[]{}));
		}
		list = getSimpleJdbcTemplate().queryForList(sql, new Object[]{});
		return list;
	}
	public List<Map<String, Object>> findListMap(String sql, Object[] params) {
		List<Map<String, Object>> list = null;
		if (log.isInfoEnabled()) {
			log.info("查询<key, value>的结果集List: \n" + printSql(sql, params));
		}
		list = getSimpleJdbcTemplate().queryForList(sql, params);
		return list;
	}
	
	public List<Map<String, Object>> findListMap(String sql,int start, int size, Object[] params) {
		List<Map<String, Object>> list = null;
		sql = sql + " limit "+start+","+size;
		if (log.isInfoEnabled()) {
			log.info("查询<key, value>的结果集List: \n" + printSql(sql, params));
		}
		list = getSimpleJdbcTemplate().queryForList(sql, params);
		return list;
	}

	/**
	 * 将记录组成一个数据对象并放入List中
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	public List<Object[]> findList(String sql) {
		List<Object[]> data = new ArrayList<Object[]>();
		if (log.isInfoEnabled()) {
			log.info("将记录组成一个数据对象并放入List中: \n" + printSql(sql, new Object[]{}));
		}
		List<Map<String, Object>> list = getSimpleJdbcTemplate().queryForList(sql, new Object[]{});
		for (Map<String, Object> map : list) {
			List<Object> record = new ArrayList<Object>();
			// 效率优于keySet()
			Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
			// 封装数据
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = it.next();
				// 只保留值,非key
				record.add(entry.getValue());
			}
			data.add(record.toArray());
		}
		return data;
	}
	public List<Object[]> findList(String sql, Object[] params) {
		List<Object[]> data = new ArrayList<Object[]>();
		if (log.isInfoEnabled()) {
			log.info("将记录组成一个数据对象并放入List中: \n" + printSql(sql, params));
		}
		List<Map<String, Object>> list = getSimpleJdbcTemplate().queryForList(sql, params);
		for (Map<String, Object> map : list) {
			List<Object> record = new ArrayList<Object>();
			// 效率优于keySet()
			Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
			// 封装数据
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = it.next();
				// 只保留值,非key
				record.add(entry.getValue());
			}
			data.add(record.toArray());
		}
		return data;
	}
	
	public List<Object[]> findList(String sql,int start, int size, Object[] params) {
		sql = sql+" limit "+start+","+size;
		List<Object[]> data = new ArrayList<Object[]>();
		if (log.isInfoEnabled()) {
			log.info("将记录组成一个数据对象并放入List中: \n" + printSql(sql, params));
		}
		List<Map<String, Object>> list = getSimpleJdbcTemplate().queryForList(sql, params);
		for (Map<String, Object> map : list) {
			List<Object> record = new ArrayList<Object>();
			// 效率优于keySet()
			Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
			// 封装数据
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = it.next();
				// 只保留值,非key
				record.add(entry.getValue());
			}
			data.add(record.toArray());
		}
		return data;
	}

	/**
	 * 打印出Sql
	 * @param sql
	 * @param params
	 * @return
	 */
	public static String printSql(String sql,Object... params){
		if(params==null || params.length==0){
			return sql;
		}
		Pattern pattern = Pattern.compile("([^\\?]*)(\\?)([^\\?]*)");
		Matcher macher = pattern.matcher(sql);
		StringBuilder sb = new StringBuilder();
		int index = 0;
		while(macher.find()){
			if(index==params.length)
				break;
			sb.append(macher.group(1)).append("'").append(params[index]).append("'").append(macher.group(3));
			index++;
		}
		return sb.toString();
	}
	
	
	public static String createInsertSql(boolean underscore,Object o,List ls,int flag)
	{
		int i,num=0;
		StringBuilder sql=new StringBuilder();
		sql.append("insert into ").append(o.getClass().getSimpleName().toLowerCase()).append("(");
		Class clz=o.getClass();
		PropertyDescriptor pda[]=getPDs(clz);
		for(PropertyDescriptor pd:pda)
		{
			String colName=pd.getName();
			if(pd.getName().equals("class")) continue;
			if(underscore) colName=Auxs.underscoreName(colName);
			Object val=readProp(o,pd.getReadMethod());
			if(checkVal(val,flag)) continue;
			sql.append(colName).append(",");
			num++;
			ls.add(val);
		}
		sql.setCharAt(sql.length()-1,')');
		sql.append(" values (");
		for(i=0;i<num;++i) sql.append("?,");
		sql.setCharAt(sql.length()-1,')');
		return sql.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static PropertyDescriptor[] getPDs(Class clz)
	{
		BeanInfo bi=null;
		try
		{
			bi=Introspector.getBeanInfo(clz);
		}
		catch(Exception e)
		{
			return new PropertyDescriptor[0];
		}
		PropertyDescriptor[] descriptors;
		descriptors=bi.getPropertyDescriptors();
		if(descriptors==null) return new PropertyDescriptor[0];
		return descriptors;
	}
	
	public static boolean checkVal(Object val,int flag)
	{
		if(flag == 10){
			flag = 16;
		}
		if((flag&IG_NULL)==IG_NULL&&(val==null||val.toString().equals(""))) return true;
		else if((flag&IG_NULLNOTBLANK)==IG_NULLNOTBLANK&&val==null) return true;
		else if((flag&IG_ZERO)==IG_ZERO&&val!=null&&val.toString().equals("0")) return true;
		else if((flag&IG_ONEn)==IG_ONEn&&val!=null&&val.toString().equals("-1")) return true;
		else if((flag&IG_MIN)==IG_MIN&&val!=null&&val.equals(getMin(val.getClass()))) return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static Object getMin(Class clz)
	{
		if(clz.isAssignableFrom(Character.class)) return new Character(Character.MIN_VALUE);
		else if(clz.isAssignableFrom(Byte.class)) return new Byte(Byte.MIN_VALUE);
		else if(clz.isAssignableFrom(Short.class)) return new Short(Short.MIN_VALUE);
		else if(clz.isAssignableFrom(Integer.class)) return new Integer(Integer.MIN_VALUE);
		else if(clz.isAssignableFrom(Long.class)) return new Long(Long.MIN_VALUE);
		else if(clz.isAssignableFrom(Float.class)) return new Float(Float.MIN_VALUE);
		else if(clz.isAssignableFrom(Double.class)) return new Double(Double.MIN_VALUE);
		//else if(clz.isAssignableFrom(Boolean.class)) return Boolean.
		return null;
	}
	
	public static Object readProp(Object o,Method m)
	{
		try
		{
			return m.invoke(o);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	private PropertyDescriptor[] pdas;
	private String[] keys;
	public static int IG_NULL=1; //忽略null和空字符串
	public static int IG_ZERO=2; //忽略0
	public static int IG_ONEn=4; //忽略-1
	public static int IG_MIN=8;  //忽略该类型最小的
	public static int IG_NULLNOTBLANK=16;  //忽略null但不忽略空字符串
	
}
