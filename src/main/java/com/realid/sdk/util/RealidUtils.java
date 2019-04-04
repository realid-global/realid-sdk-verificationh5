package com.realid.sdk.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.realid.sdk.RealidConstants;
import com.realid.sdk.RealidException;

import java.util.Map.Entry;

public final class RealidUtils {
    
    
    /**
	 * @Description: joint request parameter for generating signature
	 * @param params
	 *            request parameter map
	 * @return String
	 */
	public static String jointParams(Map<String, String> params) {
		params.remove(RealidConstants.PARAM_SIGN);
		final String splliter = "&", jointer = "=";
		StringBuilder sb = new StringBuilder();
		for (String k : params.keySet()) {
			String v = params.get(k);
			if (v != null) {
				if (sb.length() > 0) {
					sb.append(splliter);
				}
				sb.append(k).append(jointer).append(v);
			}
		}

		return sb.toString();
	}
	
	
	/**
     * byte array to hex string
     */
    public static String encodeHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
    
    /**
     * sha256_HMAC encrypt
     */
    public static String sha256_HMAC(String message, String secret) throws RealidException {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes("UTF-8"));
            hash = encodeHexString(bytes);
        } catch (Exception e) {
        		throw new RealidException("Error HmacSHA256 ===========",e);
        }
        return hash;
    }
    
    /**
     * JSON strings are in the same order when multiple outputs
     */
    public static String toJsonString(Object obj) {
    		return JSON.toJSON(obj).toString();
    }
    

	public static Map<String, String> toSortedMap(Object obj) {
		TreeMap<String, String> dataTreeMap = JSON.parseObject(toJsonString(obj),
				new TypeReference<TreeMap<String, String>>() {});
		return dataTreeMap;
	}
	


    /**
     * get the suffix of file。only support: JPG, GIF, PNG, BMP
     *
     * @param bytes 
     * @return JPG, GIF, PNG or null
     */
    public static String getFileSuffix(byte[] bytes) {
        if (bytes == null || bytes.length < 10) {
            return null;
        }

        if (bytes[0] == 'G' && bytes[1] == 'I' && bytes[2] == 'F') {
            return "GIF";
        } else if (bytes[1] == 'P' && bytes[2] == 'N' && bytes[3] == 'G') {
            return "PNG";
        } else if (bytes[6] == 'J' && bytes[7] == 'F' && bytes[8] == 'I' && bytes[9] == 'F') {
            return "JPG";
        } else if (bytes[0] == 'B' && bytes[1] == 'M') {
            return "BMP";
        } else {
            return null;
        }
    }

    /**
     * Get MIME-TYPE。only support JPG, GIF, PNG, BMP
     *
     * @param bytes 
     * @return MIME-TYPE
     */
    public static String getMimeType(byte[] bytes) {
        String suffix = getFileSuffix(bytes);
        String mimeType;

        if ("JPG".equals(suffix)) {
            mimeType = "image/jpeg";
        } else if ("GIF".equals(suffix)) {
            mimeType = "image/gif";
        } else if ("PNG".equals(suffix)) {
            mimeType = "image/png";
        } else if ("BMP".equals(suffix)) {
            mimeType = "image/bmp";
        } else {
            mimeType = "application/octet-stream";
        }

        return mimeType;
    }

    /**
     * clear null value items
     */
    public static <V> Map<String, V> cleanupMap(Map<String, V> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, V> result = new HashMap<String, V>(map.size());
        Set<Entry<String, V>> entries = map.entrySet();

        for (Entry<String, V> entry : entries) {
            if (entry.getValue() != null) {
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }
    

    private static String RADIX_62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * long to 62 digits
     */
    public static String toRadix62Str(long num) {
        StringBuilder sb = new StringBuilder();
        int remainder = 0;

        while (num >= 62) {
            remainder = Long.valueOf(num % 62).intValue();
            sb.append(RADIX_62_CHARS.charAt(remainder));

            num = num / 62;
        }

        sb.append(RADIX_62_CHARS.charAt(Long.valueOf(num).intValue()));
        return sb.reverse().toString();
    }
    
    private static char[] digits = new char[]{ '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','J','K','L','M','N','P','R','S','T','U','W','X','Y','Z'} ;
    
    public static String toRadix32Str(long left) {
  	  int shift = 5;//32进制，每进制位占5个bit
  	  int length = 64/shift+1;
  	  StringBuilder sb = new StringBuilder();
  	  long bits = (1L << shift) -1;
  	  for(int i=0;i<length;i++) {
  		 sb.append(digits[(int)(left & bits)]);
  		 left >>= shift;
  	  	 if(left == 0)break;
  	  }
  	  return sb.toString();
    }
    
    public final static Random r = new Random();
    
    public static String randomNumber(int charCount){
        StringBuilder charValue = new StringBuilder();
        for (int i = 0; i < charCount; i++) {
            charValue.append(r.nextInt(10));
        }
        return charValue.toString();
    }
    
    /**
     * generate a random string base on currentTimeMillis and random number
     */
    public static String randomString(){
    		String value =  nextSequence() + randomNumber(10);
		StringBuilder builder = new StringBuilder();
		for(String subString: StringUtils.splitLength(value, 18)){
			builder.append(toRadix62Str(Long.parseLong(StringUtils.reverse(subString))));
		}
		
		return builder.toString();
    }
    
    /**
     * generate a random string base on currentTimeMillis and random number
     */
    public static String randomStringUpperCase(){
		String value =  nextSequence() + randomNumber(10);
		
		StringBuilder builder = new StringBuilder();
		for(String subString: StringUtils.splitLength(value, 18)){
			builder.append(toRadix32Str(Long.parseLong(StringUtils.reverse(subString))));
		}
		return builder.toString();
	}
    
    
    private static long sequence;
    private static long referenceTime;
    private static synchronized long nextSequence() {
    		long currentTime = System.currentTimeMillis();
		if(currentTime > referenceTime) {
			sequence = 0;
			referenceTime = currentTime;
		}else {
			sequence++;
		}
		return  (sequence << 41 | referenceTime) & Long.MAX_VALUE;
    }
    
}
