package com.realid.sdk.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.realid.sdk.RealidException;

import java.util.Map.Entry;

/**
 * 系统工具类
 */
public abstract class RealidUtils {
    private static String localIp;

    private RealidUtils() {
    }
    
    
    /**
	 * @Description: joint request parameter for generating signature
	 * @param params
	 *            request parameter map
	 * @return String
	 */
	public static String jointParams(Map<String, String> params) {
		params.remove("sign");
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
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
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
     * sha256_HMAC加密
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     * @throws RealidException 
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
    

	public static Map<String, String> toSortedMap(Object obj) {
		TreeMap<String, String> dataTreeMap = JSON.parseObject(JSON.toJSON(obj).toString(),
				new TypeReference<TreeMap<String, String>>() {});
		return dataTreeMap;
	}
	


    /**
     * 获取文件的真实后缀名。目前只支持JPG, GIF, PNG, BMP四种图片文件。
     *
     * @param bytes 文件字节流
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
     * 获取文件的真实媒体类型。目前只支持JPG, GIF, PNG, BMP四种图片文件。
     *
     * @param bytes 文件字节流
     * @return 媒体类型(MEME-TYPE)
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
     * 清除字典中值为空的项。
     *
     * @param <V> 泛型
     * @param map 待清除的字典
     * @return 清除后的字典
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
    

    /**
     * 获取本机的网络IP
     */
    public static String getLocalNetWorkIp() {
        if (localIp != null) {
            return localIp;
        }
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (netInterfaces.hasMoreElements()) {// 遍历所有的网卡
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                if (ni.isLoopback() || ni.isVirtual()) {// 如果是回环和虚拟网络地址的话继续
                    continue;
                }
                Enumeration<InetAddress> addresss = ni.getInetAddresses();
                while (addresss.hasMoreElements()) {
                    InetAddress address = addresss.nextElement();
                    if (address instanceof Inet4Address) {// 这里暂时只获取ipv4地址
                        ip = address;
                        break;
                    }
                }
                if (ip != null) {
                    break;
                }
            }
            if (ip != null) {
                localIp = ip.getHostAddress();
            } else {
                localIp = "127.0.0.1";
            }
        } catch (Exception e) {
            localIp = "127.0.0.1";
        }
        return localIp;
    }

    private static String RADIX_62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 将数字转为62进制
     *
     * @param num    Long 型数字
     * @return 62进制字符串
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

    /**
     * 62进制字符串转为数字
     *
     * @param str 编码后的62进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long fromRadix62Str(String str) {
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            index = RADIX_62_CHARS.indexOf(str.charAt(i));
            num += (long) (index * (Math.pow(62, str.length() - i - 1)));
        }

        return num;
    }
    
    public final static Random r = new Random();
    
    public static String randomNumber(int charCount){
        StringBuilder charValue = new StringBuilder();
        for (int i = 0; i < charCount; i++) {
            charValue.append(r.nextInt(10));
        }
        return charValue.toString();
    }
    
    public static String randomString(){
    		String value = randomNumber(5) + System.currentTimeMillis() ;
    		return toRadix62Str(Long.parseLong(value));
    }
}
