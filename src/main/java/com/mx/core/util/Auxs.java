package com.mx.core.util;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

@SuppressWarnings("rawtypes")
public class Auxs
{
	final public static String MONLOGNAME="aux.monitor.log";
	final public static String TRXLOGNAME="aux.trans.log";
	
	final public static SimpleDateFormat Y4M2D2=new SimpleDateFormat("yyyyMMdd");
	final public static SimpleDateFormat Y2M2D2=new SimpleDateFormat("yyMMdd");
	final public static SimpleDateFormat M2D2=new SimpleDateFormat("MMdd");
	final public static SimpleDateFormat DTREAD=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final public static SimpleDateFormat YMDHMS=new SimpleDateFormat("yyyyMMddHHmmss");
	final public static SimpleDateFormat YMDHMSL=new SimpleDateFormat("HHmmssSSS");
	final public static SimpleDateFormat HHMMSS=new SimpleDateFormat("HHmmss");
	final public static SimpleDateFormat Y2M2D2HMSL=new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public static int count = 0;
	public static String GBK="GBK";
	final public static long DAY_MILL=(24*60*60*1000);
	final public static String STR62="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	final public static int RADIX62=62;
	final public static Pattern ptn=Pattern.compile("[^0-9]");
	public static Logger lg=Logger.getLogger(Auxs.class);
	public static Logger MONLOG=Logger.getLogger(MONLOGNAME);
	public static Logger TRXLOG=Logger.getLogger(TRXLOGNAME);
	
	public static void main(String[] args)
	{
		try
		{
			//System.out.println(getBean("testBeanXmlMap"));
			//System.out.println(springProp(LoginRq.class, "cib.login"));
			String str=toStr62(9998,3);
			System.out.println(str);
			System.out.println(fromStr62(str));
			System.out.println(Auxs.pureDT("2008-123-123:33T233"));
			System.out.println(yuan2Fen("0.10"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static String ev(String[] strs,int i,String defval)
	{
		if(strs.length>i) return strs[i];else return defval;
	}
	public static Object ev(Map m,Object key,Object def)
	{
		Object o=m.get(key);
		if(o==null) return def; else return o;
	}
	public static Object fetch(Map<String,Object> m,String name,Object def)
	{
		Object o=m.get(name);
		if(o==null) return def; else return o;
	}
	public static void sleep(long lt)
	{
		try
		{
			Thread.sleep(lt);
		}
		catch (InterruptedException e)
		{
			lg.error("Sleeping Error:"+e.getMessage(),e);
			Auxs.MONLOG.error("Sleeping Error:"+e.getMessage(),e);
		}
	}
	public static String right(String src,int len)
	{
		return src.substring(src.length()-len);
	}
	public static String limitStr(String charset,String src, int len)
	{
		return limitStrEx(charset,src,len,false);
	}
	public static byte[] rPadB(byte[] ba,int b,int len)
	{
		if(len<=ba.length) return ba;
		byte[] rb=new byte[len];
		System.arraycopy(ba, 0, rb, 0, ba.length);
		for(int i=ba.length;i<len;++i) rb[i]=(byte)(b&0xFF);
		return rb;
	}
	public static byte[] trimb(byte[] ba,int b)
	{
		int start=0,end=0,i;
		for(i=0;i<ba.length;++i)
		{
			if((ba[i]&0xFF)!=(b&0xFF))
			{
				start=i;
				break;
			}
		}
		for(i=ba.length;i>0;--i)
		{
			if((ba[i-1]&0xFF)!=(b&0xFF))
			{
				end=i;
				break;
			}
		}
		if(start==0&&end==ba.length) return ba;
		byte[] rb=new byte[end-start];
		System.arraycopy(ba, start, rb, 0, end-start);
		return rb;
	}	
	public static byte[] trimb(byte[] ba)
	{
		return trimb(ba,0);
	}
	public static String replace(String str,int start,int end,String strrep)
	{
		StringBuilder sb=new StringBuilder(end);
		sb.append(StringUtils.substring(str, 0,start))
		.append(strrep).append(StringUtils.substring(str,end));
		return sb.toString();
	}
	public static byte[] lPadB(byte[] ba,int b,int len)
	{
		if(len<=ba.length) return ba;
		byte[] rb=new byte[len];
		System.arraycopy(ba, 0, rb, len-ba.length, ba.length);
		for(int i=0;i<len-ba.length;++i) rb[i]=(byte)(b&0xFF);
		return rb;
	}
	public static int indexOfIg(String[] strs,String toFind)
	{
		if(strs==null) return -1;
		for(int i=0;i<strs.length;++i)
		{
			if(toFind==null&&strs[i]==null||toFind.equalsIgnoreCase(strs[i])) return i;
		}
		return -1;
	}
	public static String underscoreName(String name)
	{
		StringBuilder result = new StringBuilder("`");
		if (name != null && name.length() > 0)
		{
			result.append(name.substring(0, 1).toLowerCase());
			for (int i = 1; i < name.length(); i++)
			{
				String s = name.substring(i, i + 1);
				if (s.equals(s.toUpperCase())&&Character.isLetter(s.charAt(0)))
				{
					result.append("_");
					result.append(s.toLowerCase());
				}
				else
				{
					result.append(s);
				}
			}

		}
		result.append("`");
		return result.toString();
	}
	public static String limitStrEx(String charset,String src, int len,boolean fromEnd)
	{
		StringBuilder sb = new StringBuilder();
		char[] c = src.toCharArray();
		CharsetEncoder cse=Charset.forName(charset).newEncoder();
		CharBuffer cb=CharBuffer.allocate(1);
		int blen=0;
		try
		{
			for (int x = 0; x < c.length; x++)
			{
				int idx;
				if(fromEnd)
				{
					idx=c.length-x-1;
					sb.insert(0,c[idx]);
				}
				else
				{
					idx=x;
					sb.append(c[idx]);
				}
				blen+=cse.encode(cb.put(0, c[idx])).position();
				if (sb.toString().getBytes(charset).length > len)
					return fromEnd?sb.substring(1):sb.substring(0, x);
			}
		}
		catch(Exception ex)
		{
			lg.error("error",ex);
			blen=blen>0?blen:0;
		}
		return src;
	}
	public static void arraySet(byte[] dst,int pos,int len,byte ch)
	{
		for(int i=0;i<len;++i) dst[pos+i]=ch;
	}
	public static String gbkLPad(String str,int len,char ch)
	{
		return localPad(str,len,ch,false,"GBK");
	}
	public static String gbkRPad(String str,int len,char ch)
	{
		return localPad(str,len,ch,true,"GBK");
	}
	
	/**
	 * 字符串填充，字符串超过len长度的内容会截掉
	 * @param src 要填充的字符串
	 * @param ch 填充的字符
	 * @param len 总长度，按照字节计算
	 * @param charset 编码
	 * @param isRight 右填充
	 * @return src为空时，返回null 否则按照填充的返回
	 * **/
	public static String padString(String src, char ch, int len, String charset, boolean isRight){
		if(src == null){
			return src;
		}
		String dst = "";
		int pos = 0;
		int srclen = 0;
		try {
			srclen = src.getBytes(charset).length;
			if(srclen>len){
				for(int i=0; i<len; i++){
					String tmp = src.substring(pos, pos+1);
					if(tmp.getBytes(charset).length>1){//汉字
						i++;
					}
					if(i<len){
						dst += tmp;
					}
					pos++;
				}
			}else if(srclen<len){
				dst = src;
			}else{
				dst = src;
			}
			
			String tmp = "";
			int dstlen = dst.getBytes(charset).length;
			for(int i=0;i<len-dstlen;i++){
				tmp += ch;
			}
			if(isRight){
				dst += tmp;
			}else{
				dst = tmp + dst;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return dst;
	}
	
	public static byte[] bPad(byte[] b,int len,byte pb,boolean isRight)
	{
		byte [] br=new byte[len];
		if(isRight)
		{
			System.arraycopy(b, 0, br, 0, b.length);
			arraySet(br,b.length,len-b.length,pb);
		}
		else
		{
			arraySet(br,0,len-b.length,pb);			
			System.arraycopy(b, 0, br, len-b.length, b.length);
		}
		return br;
	}
	public static byte[] bLPad(byte[] b,int len,byte pb)
	{
		return bPad(b,len,pb,false);
	}
	public static byte[] bRPad(byte[] b,int len,byte pb)
	{
		return bPad(b,len,pb,true);
	}
	public static String decimalFmt(String fmt,double val)
	{
		DecimalFormat df=new DecimalFormat(fmt);
		return df.format(val);
	}
	public static String decimalFmt(String fmt,long val)
	{
		DecimalFormat df=new DecimalFormat(fmt);
		return df.format(val);		
	}
	public static String localPad(String str,int len,char ch,boolean isRight,String encoding)
	{
		try
		{
			byte b[] = str.getBytes(encoding);
			return new String(bPad(b,len,(byte) ch,isRight),encoding);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return str;
	}
	public static Date parseDT(SimpleDateFormat sdf,String dtStr)
	{
		try
		{
			return sdf.parse(dtStr);
		}
		catch(Exception ex)
		{
			lg.error("Parse Time Error",ex);
		}
		return new Date();
	}
	public static String subGBK(String str,int idx,int len)
	{
		try
		{
			return new String(str.getBytes("GBK"),idx,len,"GBK");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return "";
		//return str.substring(idx, idx+len).trim();
	}
	public static String toStr62(long l,int fixlen)
	{
		long idx;
		StringBuffer sb=new StringBuffer();
		while(l>0)
		{
			idx=l%RADIX62;
			l=l/RADIX62;
			sb.insert(0, STR62.charAt((int)idx));
		}
		if(fixlen!=-1) return StringUtils.leftPad(sb.toString(), fixlen, '0');
		return sb.toString();
	}
	public static String repeat(String str,int count)
	{
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<count;++i) sb.append(str);
		return sb.toString();
	}
	public static long fromStr62(String str)
	{
		long l=0,lb=0;
		int i,len;
		len=str.length();
		for(i=0;i<len;++i)
		{
			char ch=str.charAt(i);
			lb=STR62.indexOf(ch);
			l*=62;
			l+=lb;
		}
		return l;
	}
	public static boolean dgtEmpty(String str)
	{
		return str==null||str.equals("")||str.equals("0")||str.equals("0.0")||str.equals("0.00");
	}
	public static String yuan2Fen(String str)
	{
		int idx=str.indexOf('.');
		if(idx==-1)
		{
			return str+"00";
		}
		else
		{
			str=str+"000";
			String tmpstr = str.substring(0,idx)+str.substring(idx+1, idx+3);
			str = tmpstr;
			if(str.startsWith("00")){
				str = str.substring(2);
			}else if(str.startsWith("0")){
				str = str.substring(1);
			}
			return str;
		}
	}
	public static String pureDT(String dt)
	{
		if(dt==null) return "";
		return ptn.matcher(dt).replaceAll("");
	}
	public static String fen2Yuan(String str)
	{
		final String ZEROS="000000000";
		int len=str.length();
		if(len<=2)
		{
			str=ZEROS.substring(0,3-len)+str;
			len=3;
		}
		return str.substring(0,len-2)+"."+str.substring(len-2,len);
	}
	public static boolean emptyZ(String str)
	{
		int i,len;
		if(str==null) return true;
		len=str.length();
		if(len==0) return true;
		for(i=0;i<len;++i)
		{
			if(str.charAt(i)!='0') return false;
		}
		return true;
	}
	public static boolean empty(String str)
	{
		return str==null||str.equals("");
	}
	
	public static boolean empty(Object[] val) {
		if (val == null || val.length == 0) {
			return true;
		}
		for (int i = 0; i < val.length; i++) {
			if (val[i] != null && !"".equals(val[i])) {
				return false;
			}
		}
		return true;
	}
	public static int parseHMS(String str)
	{
		return (Integer.parseInt(str.substring(0,2))*60+Integer.parseInt(str.substring(2,4)))*60+Integer.parseInt(str.substring(4,6));
	}
	public static String microHMS(Date dt)
	{
		return YMDHMSL.format(dt)+ "012";
	}
	public static String fmtDate(String fmt,Date dt)
	{
		return new SimpleDateFormat(fmt).format(dt);
	}
	public static Date parseDate(String fmt,String dt) throws ParseException
	{
		return new SimpleDateFormat(fmt).parse(dt);
	}
	public static String bean2Str(Object bean)
	{
		return ReflectionToStringBuilder.toString(bean,ToStringStyle.MULTI_LINE_STYLE);
	}
	public static String fmtdayLimit(Date dtIn,int addDay)
	{
		Calendar cld=Calendar.getInstance();
		cld.setTimeInMillis(dtIn.getTime());
		cld.add(Calendar.DAY_OF_MONTH, addDay);
		if(cld.getTimeInMillis()>System.currentTimeMillis()) 
			cld.setTimeInMillis(System.currentTimeMillis());
		return Y4M2D2.format(cld.getTime());
	}
	public static String fmtday(Date dtIn,int addDay,String fmt)
	{
		Calendar cld=Calendar.getInstance();
		cld.setTimeInMillis(dtIn.getTime());
		cld.add(Calendar.DAY_OF_MONTH, addDay);
		if(cld.getTimeInMillis()>System.currentTimeMillis()) cld.setTimeInMillis(System.currentTimeMillis());
		return new SimpleDateFormat(fmt).format(cld.getTime());		
	}
	public static String fmtday(Date dtIn,int addDay)
	{
		return fmtday(dtIn,addDay,"yyyyMMdd");		
	}
	public static String today()
	{
		Date dt = new Date(System.currentTimeMillis());
		return Y4M2D2.format(dt);
	}
	/**
	 * 返回HHmmss  时间格式
	 * */
	public static String time()
	{
		Date dt = new Date(System.currentTimeMillis());
		return HHMMSS.format(dt);
	}
	public static String today(String fmt)
	{
		Date dt = new Date(System.currentTimeMillis()+DAY_MILL);
		return new SimpleDateFormat(fmt).format(dt);
	}
	public static String date6To8(String dateStr)
	{
		String dateRet,tdStr,cent;
		tdStr=today();
		cent=tdStr.substring(0,2);
		dateRet=cent+dateStr;
		if(dateRet.compareTo(tdStr)>0) 
			dateRet=String.valueOf(Integer.valueOf(cent).intValue()-1)+dateStr;
		return dateRet;
	}
	
	/**
	 * 生成20位交易流水号
	 * */
	public synchronized static String mkTrsSn(){
		DecimalFormat df = new DecimalFormat("000");
		String trssn = "";
		Date dt = new Date(System.currentTimeMillis());
		trssn = Y2M2D2HMSL.format(dt);
		count++;
		if(count>999){
			count = 0;
		}
		trssn += df.format(count);
		if(trssn.length()>20){
			trssn = trssn.substring(0,20);
		}
		return trssn;
	}
	
	/**
	 * 获取昨日日期
	 * */
	public static String yesterday(){
		String day = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		day = sdf.format(cal.getTime());
		return day;
	}
	
	/**
	 * 获取date日期的前一日
	 * date的类型yyyyMMdd格式
	 * @param date 日期，格式为yyyyMMdd
	 * @return 前一日的日期
	 * */
	public static String beforeDay(String date) throws Exception{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		cal.setTime(sdf.parse(date));
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 将文件父目录补齐  例如：原/usr/local   更新为/usr/local/ 
	 * */
	public static String fullPrePath(String fpath){
		String fp = fpath;
		if(!(fpath.endsWith("\\")||fpath.endsWith("/"))){
			fp += "/";
		}
		return fp;
	}

	/**
	 * 判断是否全为数字
	 * @param source 要判断的字符串
	 * @return
	 * **/
	public static boolean isNum(String source){
		if(source==null){
			return false;
		}
		return Pattern.matches("[0-9]*", source);
	}
	
	public static boolean empty(List ls){
		if(ls==null||ls.size()==0)return true;
		return false;
	}
	public static Calendar newCld(long mills)
	{
		Calendar cld=Calendar.getInstance();
		cld.setTimeInMillis(mills);
		return cld;
	}
	public static Calendar newCld(Date dt)
	{
		Calendar cld=Calendar.getInstance();
		cld.setTime(dt);
		return cld;
	}
	
	@SuppressWarnings("all")
	public static <T> List<T> json2List(String jsonstr,Class<T> cls){
    	JSONArray ja =JSONArray.fromObject(jsonstr);
		List<T> list = JSONArray.toList(ja, cls);
		return list;
    }
    
    @SuppressWarnings("all")
    public static <T> T json2Obj(String jsonstr,Class<T> cls){
    	JSONObject jo =JSONObject.fromObject(jsonstr);
		T obj = (T)JSONObject.toBean(jo, cls);
		return obj;
    }
    
    public static String obj2json(Object obj){
    	return JSONObject.fromObject(obj).toString();
    }
    
    public static String array2json(Object obj){
    	return JSONArray.fromObject(obj).toString();
    }
    
    /**
	 * 四位随机码
	 * @return
	 */
	public static String getValidatecode(int n) {
		Random random = new Random();
		String sRand = "";
		n = n == 0 ? 4 : n;// default 4
		for (int i = 0; i < n; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}
	
	public static String buiderOrderId(String userid){
		return fmtDate("yyyyMMddHHmmssSSS", new Date())+userid;
	}
	
	/**
     * 填充字符串，字符串格式xxxxxx{key1}xxxxxx{key2}
     * @param str
     * @param data
     * @return
     */
    public static String fillStr(String str,Map<String,Object> data){
    	Pattern pattern = Pattern.compile("([^\\{]*)([^\\}]*)([\\}]+)([^\\{]*)");
		Matcher macher = pattern.matcher(str);
		StringBuilder sb = new StringBuilder();
		boolean find = false;
		while(macher.find()){
			find = true;
			String key = macher.group(2).substring(1);
			if(data.containsKey(key)){
				sb.append(macher.group(1)).append(data.get(key)).append(macher.group(4));
			}else{
				sb.append(macher.group(1)).append(macher.group(2)).append(macher.group(3)).append(macher.group(4));
			}
		}
		if(find){
			return sb.toString();
		}else{
			return str;
		}
    }
    
	public static Timestamp getCurrTimestamp(){
		return new Timestamp(new Date().getTime());
	}
	
	public static Timestamp parseStampTime(String datestr,String formatter) throws Exception{
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(formatter);
			return new Timestamp(sdf.parse(datestr).getTime());
		}
		catch(Exception ex){
			throw ex;
		}
	}
    
    public static byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }  
}

