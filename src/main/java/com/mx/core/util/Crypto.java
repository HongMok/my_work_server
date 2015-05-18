package com.mx.core.util;


import java.util.Random;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Crypto {
	private static Random m_rndGen = new Random();
	private static String defualt_key = "12345678"; 
	public static String decode(String password) throws Exception{
		return decode(password, defualt_key);
	}
	
	public static String decode(String password,String keystr) throws Exception {
		CEA cea = new CEA();
		BASE64Decoder b64 = new BASE64Decoder();
		byte[] decode = b64.decodeBuffer(password);
		byte[] res = new byte[decode.length];
		byte[] key = keystr.getBytes();
		cea.Decrypt(decode, res, decode.length, key, key.length);
		return new String(res);
	}
	
	public static String encode(String vClearText) throws Exception {
		return encode(vClearText, defualt_key);
	}

	public static String encode(String vClearText,String keystr) throws Exception {
		String encode = "";
		CEA cea = new CEA();
		byte[] key = keystr.getBytes();
		int intStrLen = vClearText.length();
		if (intStrLen < 16) {
			for (int i = 0; i < 16 - intStrLen; i++)
				vClearText = String.valueOf(String.valueOf(vClearText)).concat(
						" ");
		}
		byte plain[] = vClearText.getBytes();
		byte cipher[] = new byte[plain.length];
		cea.Encrypt(plain, cipher, plain.length, key, key.length);
		BASE64Encoder b64Enc = new BASE64Encoder();
		encode = b64Enc.encode(cipher);
		return encode;
	}
	
	public static String encryptString(String password)throws Exception{
		String rg;
        synchronized (m_rndGen)
        {
        	rg = m_rndGen.nextInt(99999999)+"";
        	rg = Auxs.padString(rg, '0', 8, "UTF-8", false);
        }
        String pwd1 = encode(rg, password);
        String pwd2 = encode(password, rg);
        return pwd1+pwd2;
	}
	
	/**
	 * 验证密码是否匹配
	 * @param sPlainText 明文
	 * @param sCipherText 密文
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyCrypt(String sPlainText,String sCipherText)throws Exception{
		if(sCipherText==null||sCipherText.length()!=48){
			return false;
		}
		String pwd1 = sCipherText.substring(0, 24);
		String pwd2 = sCipherText.substring(24);
		String rg = decode(pwd1, sPlainText).trim();
		String password = decode(pwd2, rg).trim();
		return password.equals(sPlainText);
	}
	
	public static void main(String[] args) throws Exception{
		String es = encode(System.currentTimeMillis()+"");
		System.out.println(es);
	}
}
