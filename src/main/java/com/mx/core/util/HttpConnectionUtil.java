package com.mx.core.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class HttpConnectionUtil {
	private HttpURLConnection conn;
	private String connectUrl;
	
	public HttpConnectionUtil(String connectUrl){
		this.connectUrl = connectUrl;
	}
	
	public void init() throws Exception{
		URL url = new URL(connectUrl);
    	URLConnection conn = url.openConnection();
    	conn.setDoInput(true);
    	conn.setDoOutput(true);
    	if (conn instanceof HttpsURLConnection){
    		HttpsURLConnection httpsConn = (HttpsURLConnection)conn;
    		httpsConn.setSSLSocketFactory(SSLUtil.getInstance().getSSLSocketFactory());
    	} else if (conn instanceof HttpURLConnection){
    		HttpURLConnection httpConn = (HttpURLConnection)conn;
    	} else {
    		throw new Exception("不是http/https协议的url");
    	}
    	this.conn = (HttpURLConnection)conn;
    	initDefaultPost();
	}
	
	public void destory(){
		try{
			if(this.conn!=null){
				this.conn.disconnect();
			}
		}catch(Exception e){
			
		}
	}
	
    private void initDefaultPost() throws Exception{
    	conn.setDoOutput(true);
    	conn.setDoInput(true);
    	conn.setRequestMethod("POST");
    	conn.setUseCaches(false);
    	conn.setInstanceFollowRedirects(true);
    	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    }
    
    public byte[] postParams(Map<String, String> params,boolean readreturn) throws IOException {
    	StringBuilder outBuf = new StringBuilder();
    	boolean isNotFirst = false;
    	for (Map.Entry<String, String> entry: params.entrySet()){
    		if (isNotFirst)
    			outBuf.append('&');
    		isNotFirst = true;
    		outBuf
    			.append(entry.getKey())
    			.append('=')
    			.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
    	}
    	return postParams(outBuf.toString(),readreturn);
    }
    
    public byte[] postParams(String message,boolean readreturn) throws IOException {
    	DataOutputStream out = new DataOutputStream(conn.getOutputStream());
    	out.writeBytes(message);
    	out.close();
    	if(readreturn){
    		return readBytesFromStream(conn.getInputStream());
    	}else{
    		return null;
    	}
    }
    
    public byte[] postParams(byte[] message,boolean readreturn) throws IOException {
    	DataOutputStream out = new DataOutputStream(conn.getOutputStream());
    	out.write(message);
    	out.close();
    	if(readreturn){
    		return readBytesFromStream(conn.getInputStream());
    	}else{
    		return null;
    	}
    }
    
    private byte[] readBytesFromStream(InputStream is) throws IOException{
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	int readLen;
    	byte[] tmpBuf = new byte[4096];
    	while ((readLen = is.read(tmpBuf)) > 0)
    		baos.write(tmpBuf, 0, readLen);
    	is.close();
    	return baos.toByteArray();
    }

	public HttpURLConnection getConn() {
		return conn;
	}
    
}
