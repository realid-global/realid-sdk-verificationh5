package com.realid.sdk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.realid.sdk.model.FileItem;

public abstract class WebUtils {

    private static final String     DEFAULT_CHARSET = "UTF-8";
    private static final String     METHOD_POST     = "POST";
    private static final String     METHOD_GET      = "GET";

    private static SSLContext       ctx             = null;

    private static HostnameVerifier verifier        = null;

    private static SSLSocketFactory socketFactory   = null;

    private static class DefaultTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }
    }

    static {

        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() },
                    new SecureRandom());

            ctx.getClientSessionContext().setSessionTimeout(15);
            ctx.getClientSessionContext().setSessionCacheSize(1000);

            socketFactory = ctx.getSocketFactory();
        } catch (Exception e) {

        }

        verifier = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return false;//not verify centification
            }
        };

    }

    private WebUtils() {
    }
    
    
    public static String doPost(String url, String json) throws IOException {
    		return doPost(url, json,DEFAULT_CHARSET, 5000, 10000, null, 0);
    }

    /**
     * http post request
     *
     * @param url 
     * @param json json body
     * @param charset UTF-8, GBK, GB2312
     * @param connectTimeout 
     * @param readTimeout 
     * @param proxyHost null means no proxy
     * @param proxyPort 0 means no proxy
     * @return response string
     * @throws IOException
     */
    public static String doPost(String url, String json, String charset,
                                int connectTimeout, int readTimeout, String proxyHost,
                                int proxyPort) throws IOException {
        String ctype = "application/json";
        byte[] content = {};
        if (json != null) {
            content = json.getBytes(charset);
        }
        return doPost(url, ctype, content, connectTimeout, readTimeout, proxyHost, proxyPort);
    }


    public static String doPost(String url, String ctype, byte[] content, int connectTimeout,
                                int readTimeout, String proxyHost, int proxyPort) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            conn = null;
            if (!StringUtils.isEmpty(proxyHost)) {
                conn = getConnection(new URL(url), METHOD_POST, ctype, proxyHost, proxyPort);
            } else {
                conn = getConnection(new URL(url), METHOD_POST, ctype);
            }
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);

        } catch (IOException e) {
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();

            }
        }

        return rsp;
    }

    /**
     * http post with file upload 
     *
     * @param url 
     * @param params 
     * @param fileParams 
     * @param charset UTF-8, GBK, GB2312
     * @param connectTimeout 
     * @param readTimeout 
     * @param proxyHost null means no proxy
     * @param proxyPort 0 means no proxy
     * @return response string
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params,
                                Map<String, FileItem> fileParams, String charset,
                                int connectTimeout, int readTimeout, String proxyHost,
                                int proxyPort) throws IOException {

        String boundary = System.currentTimeMillis() + ""; // 随机分隔线
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            try {
                String ctype = "multipart/form-data;boundary=" + boundary + ";charset=" + charset;
                conn = null;
                if (!StringUtils.isEmpty(proxyHost)) {
                    conn = getConnection(new URL(url), METHOD_POST, ctype, proxyHost, proxyPort);
                } else {
                    conn = getConnection(new URL(url), METHOD_POST, ctype);
                }
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            } catch (IOException e) {
                throw e;
            }

            try {
                out = conn.getOutputStream();

                byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n").getBytes(charset);

                // 组装文本请求参数
                Set<Entry<String, String>> textEntrySet = params.entrySet();
                for (Entry<String, String> textEntry : textEntrySet) {
                    byte[] textBytes = getTextEntry(textEntry.getKey(), textEntry.getValue(),
                            charset);
                    out.write(entryBoundaryBytes);
                    out.write(textBytes);
                }

                // 组装文件请求参数
                Set<Entry<String, FileItem>> fileEntrySet = fileParams.entrySet();
                for (Entry<String, FileItem> fileEntry : fileEntrySet) {
                    FileItem fileItem = fileEntry.getValue();
                    byte[] fileBytes = getFileEntry(fileEntry.getKey(), fileItem.getFileName(),
                            fileItem.getMimeType(), charset);
                    out.write(entryBoundaryBytes);
                    out.write(fileBytes);
                    out.write(fileItem.getContent());
                }

                // 添加请求结束标志
                byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n").getBytes(charset);
                out.write(endBoundaryBytes);
                rsp = getResponseAsString(conn);
            } catch (IOException e) {
                throw e;
            }

        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    private static byte[] getTextEntry(String fieldName, String fieldValue,
                                       String charset) throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
        entry.append(fieldValue);
        return entry.toString().getBytes(charset);
    }

    private static byte[] getFileEntry(String fieldName, String fileName, String mimeType,
                                       String charset) throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\";filename=\"");
        entry.append(fileName);
        entry.append("\"\r\nContent-Type:");
        entry.append(mimeType);
        entry.append("\r\n\r\n");
        return entry.toString().getBytes(charset);
    }

    /**
     * HTTP GET
     */
    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, DEFAULT_CHARSET);
    }

    /**
     * HTTP GET
     */
    public static String doGet(String url, Map<String, String> params,
                               String charset) throws IOException {
        HttpURLConnection conn = null;
        String rsp = null;

        try {
            String ctype = "application/x-www-form-urlencoded;charset=" + charset;
            String query = buildQuery(params, charset);
            try {
                conn = getConnection(buildGetUrl(url, query), METHOD_GET, ctype);
            } catch (IOException e) {
                throw e;
            }

            try {
                rsp = getResponseAsString(conn);
            } catch (IOException e) {
                throw e;
            }

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    public static HttpURLConnection getConnection(URL url, String method, String ctype) throws IOException {
        return getConnection(url, method, ctype, null);
    }

    public static HttpURLConnection getConnection(URL url, String method, String ctype,
                                                  String proxyHost, int proxyPort) throws IOException {
        Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        return getConnection(url, method, ctype, proxy);
    }

    private static HttpURLConnection getConnection(URL url, String method, String ctype, Proxy proxy) throws IOException {
        HttpURLConnection conn = null;
        if ("https".equals(url.getProtocol())) {
            HttpsURLConnection connHttps = null;
            if (proxy != null) {
                connHttps = (HttpsURLConnection) url.openConnection(proxy);
            } else {
                connHttps = (HttpsURLConnection) url.openConnection();
            }
            connHttps.setSSLSocketFactory(socketFactory);
            connHttps.setHostnameVerifier(verifier);
            conn = connHttps;
        } else {
            conn = null;
            if (proxy != null) {
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
        }

        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", ctype);
        conn.setRequestProperty("User-Agent", "realid-sdk-java");
        conn.setRequestProperty("Content-Type", ctype);
        return conn;
    }

    private static URL buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if (StringUtils.isEmpty(query)) {
            return url;
        }

        if (StringUtils.isEmpty(url.getQuery())) {
            if (strUrl.endsWith("?")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "?" + query;
            }
        } else {
            if (strUrl.endsWith("&")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "&" + query;
            }
        }

        return new URL(strUrl);
    }

    public static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // ignore empty param
            if (StringUtils.areNotEmpty(name, value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }

                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }

        return query.toString();
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if (StringUtils.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();

            char[] chars = new char[256];
            int count = 0;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }

            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;

        if (!StringUtils.isEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!StringUtils.isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }

    public static String decode(String value) {
        return decode(value, DEFAULT_CHARSET);
    }

    public static String encode(String value) {
        return encode(value, DEFAULT_CHARSET);
    }

    public static String decode(String value, String charset) {
        String result = null;
        if (!StringUtils.isEmpty(value)) {
            try {
                result = URLDecoder.decode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static String encode(String value, String charset) {
        String result = null;
        if (!StringUtils.isEmpty(value)) {
            try {
                result = URLEncoder.encode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> map = null;
        if (url != null && url.indexOf('?') != -1) {
            map = splitUrlQuery(url.substring(url.indexOf('?') + 1));
        }
        if (map == null) {
            map = new HashMap<String, String>();
        }
        return map;
    }

    /**
     * get params from URL
     */
    public static Map<String, String> splitUrlQuery(String query) {
        Map<String, String> result = new HashMap<String, String>();

        String[] pairs = query.split("&");
        if (pairs != null && pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param != null && param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }

        return result;
    }

}
