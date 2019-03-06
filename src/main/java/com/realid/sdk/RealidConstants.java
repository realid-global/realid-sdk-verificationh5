
package com.realid.sdk;


public class RealidConstants {
	
	public static final String SIGN_HMACSHA256_ALGORITHMS     = "HmacSHA256";

    public static final String SIGN_TYPE_RSA                  = "RSA";

    public static final String SIGN_ALGORITHMS                = "SHA1WithRSA";

    public static final String SIGN_SHA256RSA_ALGORITHMS      = "SHA256WithRSA";

    public static final String ENCRYPT_TYPE_AES               = "AES";

    /** 默认时间格式 **/
    public static final String DATE_TIME_FORMAT               = "yyyy-MM-dd HH:mm:ss";

    /**  Date默认时区 **/
    public static final String DATE_TIMEZONE                  = "GMT";

    /** UTF-8字符集 **/
    public static final String CHARSET_UTF8                   = "UTF-8";

    /** GBK字符集 **/
    public static final String CHARSET_GBK                    = "GBK";

    /** JSON 应格式 */
    public static final String FORMAT_JSON                    = "json";

    /** XML 应格式 */
    public static final String FORMAT_XML                     = "xml";

    /** SDK版本号 */
    public static final String SDK_VERSION                    = "1.0";
    
    
    public static final String PARAM_SIGN                     = "sign";
    
    public static final String PARAM_SIGN_TYPE                = "signType";
    
    public static final String PARAM_RESPONSE                 = "response";
    
    public static final String PARAM_RESULT                   = "result";

    
    public static final String REQUEST_BASE_URL = "https://api.realid.global/sdk/";
    
    public static final String REQUEST_CREATE_ORDER = "createWebOrder";
    
    public static final String REQUEST_QUERY_VERIFICATION_DATA = "queryVerificationData";
    

}
