package kr.co.crewmate.core.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hash 관련 유틸 모음
 * @author 정슬기
 *
 */
public class HashUtil {
	
	private static Logger log = LoggerFactory.getLogger(HashUtil.class);
	
	public static String KEY_SPEC_DES		= "DES";
	public static String KEY_SPEC_TripleDES	= "TripleDES";
	
	
	public static void main(String[] args) throws Exception {
		String key	= "nepaAppCheckKey";
		String value	= "nepaAppCheckKey";
		
		/*
		System.out.println("===========================>");
		String encrypt = HashUtil.desEncrypt(key, value, "DESede");
		System.out.println(encrypt);

		System.out.println("--------------------------->");
		String decrypt = HashUtil.desDecrypt(key, encrypt, "DESede");
		System.out.println(decrypt);
		*/
		
		System.out.println("--------------------------->");
		System.out.println(HashUtil.sha256("rlatpgml1", "rlatpgml1"));		
		
		System.out.println("--------------------------->");
		System.out.println(HashUtil.sha256("rlatpgml1"));

		/*
		String enc = HashUtil.aesEncryptForPhp("1111111111111111", "2222222222222222", "test");
		String dec = HashUtil.aesDecryptForPhp("1111111111111111", "2222222222222222", enc);
		System.out.println(enc);
		System.out.println(dec);

		enc = HashUtil.aesEncrypt("1111111111111111", "2222222222222222", "test");
		dec = HashUtil.aesDecrypt("1111111111111111", "2222222222222222", enc);
		System.out.println(enc);
		System.out.println(dec);
		*/	
	}
	
	
	/**
	 * AES - php의 
	 * 	$sCipher = mcrypt_encrypt(MCRYPT_RIJNDAEL_128, $sKey, $sStr, MCRYPT_MODE_CFB, $sIV);
	 * 	return bin2hex($sCipher);
	 * @param keyString
	 * @param initialVectorParam
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String aesEncryptForPhp(String keyString, String initialVectorParam, String value) throws Exception{
		SecretKeySpec key = new SecretKeySpec(keyString.getBytes(), "AES");
		IvParameterSpec initalVector = new IvParameterSpec(initialVectorParam.getBytes());
		Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");		// AES/CFB/NoPadding | AES/CFB8/NoPadding
		cipher.init(Cipher.ENCRYPT_MODE, key, initalVector);
		byte[] byteData = cipher.doFinal(value.getBytes());
		
		return bytes2Hex(byteData);
	}

	/**
	 * AES - php의 mcrypt_decrypt(MCRYPT_RIJNDAEL_128, $sKey, pack('H*', $sStr), MCRYPT_MODE_CFB, $sIV); 에 대응되는 암호화 처리
	 * @param keyString
	 * @param initialVectorParam
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String aesDecryptForPhp(String keyString, String initialVectorParam, String value) throws Exception{
		SecretKeySpec key = new SecretKeySpec(keyString.getBytes(), "AES");
		IvParameterSpec initalVector = new IvParameterSpec(initialVectorParam.getBytes());
		Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");		// AES/CFB/NoPadding | AES/CFB8/NoPadding
		cipher.init(Cipher.DECRYPT_MODE, key, initalVector);
		byte[] byteData = cipher.doFinal(hex2byte(value));

		return new String(byteData);
	}
	
	
	/**
	 * AES128
	 * @param keyString
	 * @param initialVectorParam
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String aesEncrypt(String keyString, String initialVectorParam, String value) throws Exception{
		SecretKeySpec key = new SecretKeySpec(keyString.getBytes(), "AES");
		IvParameterSpec initalVector = new IvParameterSpec(initialVectorParam.getBytes());
		Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");		// AES/CFB/NoPadding | AES/CFB8/NoPadding
		cipher.init(Cipher.ENCRYPT_MODE, key, initalVector);
		byte[] byteData = cipher.doFinal(value.getBytes("UTF8"));
		return new String(Base64.encodeBase64(byteData), "UTF-8");
	}	

	
	/**
	 * AES128
	 * @param keyString
	 * @param initialVectorParam
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String aesDecrypt(String keyString, String initialVectorParam, String value) throws Exception{
		SecretKeySpec key = new SecretKeySpec(keyString.getBytes(), "AES");
		IvParameterSpec initalVector = new IvParameterSpec(initialVectorParam.getBytes());
		Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");		// AES/CFB/NoPadding | AES/CFB8/NoPadding
		cipher.init(Cipher.DECRYPT_MODE, key, initalVector);
		byte[] byteData = cipher.doFinal(Base64.decodeBase64(value));
		
		return new String(byteData, "UTF-8");
	}
	
	

	private static byte[] hex2byte(String s){
		if(s == null) return null;
		int l = s.length();
		if(l%2 == 1) return null;
		byte[] b = new byte[l/2];
		for(int i = 0 ; i < l/2 ;i++) {
			b[i] = (byte)Integer.parseInt(s.substring(i*2,i*2+2),16);
		}
		return b;
	}

	private static String byte2Hex(byte b) {
		String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};    
		int nb = b & 0xFF;
		int i_1 = (nb >> 4) & 0xF;
		int i_2 = nb & 0xF;
		return HEX_DIGITS[i_1] + HEX_DIGITS[i_2];
	}

	private static String bytes2Hex(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int x = 0; x < b.length; x++) {
			sb.append(byte2Hex(b[x]));
		}
		return sb.toString();
	}
	
	
	
	
	
	
	/**
	 * MD5 처리
	 * @param str
	 * @return
	 */
	public static String md5(String str){
		String MD5 = ""; 
		try{
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(str.getBytes()); 
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			MD5 = sb.toString();
			
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			MD5 = null; 
		}
		return MD5;
	}


	/**
	 * SHA-256 처리
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String sha256(String str, String salt) {
		String SHA = ""; 
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.reset();
			md.update(salt.getBytes("UTF-8")); 
			
			byte byteData[] = md.digest(str.getBytes("UTF-8"));
			SHA = new String(Base64.encodeBase64(byteData), "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			log.error("NoSuchAlgorithmException : {}", e.getMessage());
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException : {}", e.getMessage());
		} finally {
			if(StringUtils.isEmpty(SHA)){
				SHA = null;
			}
		}
		return SHA;
	}

	public static String sha256(String str) {
		String SHA = ""; 
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte byteData[] = md.digest(str.getBytes("UTF-8"));
			SHA = new String(Base64.encodeBase64(byteData), "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			log.error("NoSuchAlgorithmException : {}", e.getMessage());
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException : {}", e.getMessage());
		} finally {
			if(StringUtils.isEmpty(SHA)){
				SHA = null;
			}
		}
		return SHA;
	}
	
	
	/**
	 * DES 복호화 처리
	 * @param key
	 * @param value
	 * @param keySpec
	 * @return
	 * @throws Exception
	 */
	public static String desDecrypt(String key, String value, String keySpec) throws Exception {
		String instance = (keySpec.equals("DESede") || keySpec.equals("TripleDES")) ? "TripleDES/ECB/PKCS5Padding" : "DES/ECB/PKCS5Padding";
		
		Cipher cipher = Cipher.getInstance(instance);
		
		cipher.init(Cipher.DECRYPT_MODE, getDESKey(key, keySpec));
		byte[] byteData = cipher.doFinal(Base64.decodeBase64(value));

		return new String(byteData, "UTF-8");
	}
	
	

	
	/**
	 * DES 암호화 처리
	 * @param key
	 * @param value
	 * @param keySpec : TripleDES | DESede | DES
	 * @return
	 * @throws Exception
	 */
	public static String desEncrypt(String key, String value, String keySpec) throws Exception{
		String instance = (keySpec.equals("DESede") || keySpec.equals("TripleDES")) ? "TripleDES/ECB/PKCS5Padding" : "DES/ECB/PKCS5Padding";
		
		Cipher cipher = Cipher.getInstance(instance);
		cipher.init(Cipher.ENCRYPT_MODE, getDESKey(key, keySpec));
		byte[] byteData = cipher.doFinal(value.getBytes("UTF8"));
		
		return new String(Base64.encodeBase64(byteData), "UTF-8");
	}
	
	
	
	
	/**
	 * DES 암호화에 필요한 키값을 반환함.
	 * @param keyValue
	 * @param keySpec : DES | TripleDES
	 * @return
	 * @throws Exception
	 */
	private static Key getDESKey(String key, String keySpec) throws Exception {
		String keyValue = key;
		
		// TripleDES 이면서 키의 길이가 24가 아닌 경우 키의 길이를 24로 처리함.
		if((keySpec.equals("DESede") || keySpec.equals("TripleDES")) && keyValue.length() != 24){
			DESedeKeySpec desKeySpec = new DESedeKeySpec(getDESKeyValue(key, keySpec).getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(keySpec);
			return keyFactory.generateSecret(desKeySpec);
		}else{
			DESKeySpec desKeySpec = new DESKeySpec(getDESKeyValue(key, keySpec).getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(keySpec);
			return keyFactory.generateSecret(desKeySpec);			
		}
	}
	
	/**
	 * DES 암호화에 필요한 키의 길이를 반환함.
	 * @param key
	 * @param keySpec
	 * @return
	 */
	private static String getDESKeyValue(String key, String keySpec){
		String keyValue = key;
		
		if((keySpec.equals("DESede") || keySpec.equals("TripleDES")) && keyValue.length() != 24){
			for(int i = keyValue.length() ; i < 24 ; i++){ keyValue += " "; }
		}else if(keyValue.length() < 8){
			for(int i = keyValue.length() ; i < 8 ; i++){ keyValue += " "; }
		}
		
		return keyValue;
	}
	
	/**
	 * mysql password() function
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String mysqlPassword(String str) throws UnsupportedEncodingException {
		byte[] utf8 = str.getBytes("UTF-8");
		return "*" + DigestUtils.shaHex(DigestUtils.sha(utf8)).toUpperCase();
	}
}