package com.weixin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JFinal自带的http客户端无法设定超时时间
 *
 * @author zhanglf
 */
public class HttpUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    private static final String TAG = "HttpHandler";
    private static final int READ_TIME_OUT = 1000 * 60 * 3; // 3分钟
    private static final int CONNECT_TIME_OUT = 1000 * 10; // 10秒
    private static String CHAR_SET = "utf-8";
    private static final int mRetry = 2; // 默认尝试访问次数

    public static String get(String url) throws Exception {
        return get(url, null);
    }

    public static String get(String url, Map<String, ? extends Object> params) throws Exception {
        return get(url, params, null);
    }

    public static String get(String url, Map<String, ? extends Object> params, Map<String, String> headers)
            throws Exception {
        if (url == null || url.trim().length() == 0) {
            throw new Exception(TAG + ": get url is null or empty!");
        }

        if (params != null && params.size() > 0) {
            if (!url.contains("?")) {
                url += "?";
            }

            StringBuilder sbContent = new StringBuilder();
            if (url.charAt(url.length() - 1) == '?') { // 最后一个字符是 ?
                for (Map.Entry<String, ? extends Object> entry : params.entrySet()) {
                    if (entry.getKey() != null) {
                        sbContent.append(entry.getKey().trim()).append("=").append(entry.getValue()).append("&");
                    }
                }

                if (sbContent.charAt(sbContent.length() - 1) == '&') {
                    sbContent.deleteCharAt(sbContent.length() - 1);
                }
            } else {
                for (Map.Entry<String, ? extends Object> entry : params.entrySet()) {
                    if (entry.getKey() != null) {
                        sbContent.append("&").append(entry.getKey().trim()).append("=").append(entry.getValue());
                    }
                }
            }
            url += sbContent.toString();
        }

        return tryToGet(url, headers);
    }

    private static String tryToGet(String url, Map<String, String> headers) throws Exception {
        int tryTime = 0;
        Exception ex = null;
        while (tryTime < mRetry) {
            try {
                return doGet(url, headers);
            } catch (Exception e) {
                if (e != null) {
                    ex = e;
                }
                tryTime++;
            }
        }
        if (ex != null) {
            throw ex;
        } else {
            throw new Exception("未知网络错误 ");
        }
    }

    private static String doGet(String strUrl, Map<String, String> headers) throws Exception {
        strUrl = urlEncode(strUrl, CHAR_SET);
        HttpURLConnection connection = null;
        InputStream stream = null;
        StringBuilder sb = new StringBuilder();
        try {

            connection = getConnection(strUrl);
            configConnection(connection);
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            connection.setInstanceFollowRedirects(true);
            connection.connect();

            stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, CHAR_SET));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (stream != null) {
                stream.close();
            }
        }
    }

    public static String post(String url) throws Exception {
        return post(url, null);
    }

    public static String post(String url, Map<String, ? extends Object> params) throws Exception {
        return post(url, params, null);
    }

    public static String post(String url, Map<String, ? extends Object> params, Map<String, String> headers)
            throws Exception {
        if (url == null || url.trim().length() == 0) {
            throw new Exception(TAG + ": post url is null or empty!");
        }

        if (params != null && params.size() > 0) {
            StringBuilder sbContent = new StringBuilder();
            for (Map.Entry<String, ? extends Object> entry : params.entrySet()) {
                if (entry.getKey() != null) {
                    sbContent.append("&").append(entry.getKey().trim()).append("=").append(entry.getValue());
                }
            }
            return tryToPost(url, sbContent.substring(1), headers);

        } else {
            return tryToPost(url, null, headers);
        }
    }

    public static String post(String url, String content, Map<String, String> headers) throws Exception {
        return tryToPost(url, content, headers);
    }

    private static String tryToPost(String url, String postContent, Map<String, String> headers) throws Exception {
        int tryTime = 0;
        Exception ex = null;
        while (tryTime < mRetry) {
            try {
                return doPost(url, postContent, headers);
            } catch (Exception e) {
                if (e != null) {
                    ex = e;
                }
                tryTime++;
            }
        }
        if (ex != null) {
            throw ex;
        } else {
            throw new Exception("未知网络错误 ");
        }
    }

    private static String doPost(String strUrl, String postContent, Map<String, String> headers) throws Exception {
        HttpURLConnection connection = null;
        InputStream stream = null;
        StringBuilder sb = new StringBuilder();
        try {
            connection = getConnection(strUrl);
            configConnection(connection);
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            if (null != postContent && !"".equals(postContent)) {
                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                dos.write(postContent.getBytes(CHAR_SET));
                dos.flush();
                dos.close();
            }
            stream = connection.getInputStream();
            //从回执头中获取字符编码
            String contentType = connection.getContentType();
            if (contentType != null) {
                contentType = contentType.toLowerCase();
                if (contentType.contains("charset=gb")) {
                    CHAR_SET = "gbk";
                } else if (contentType.contains("charset=utf")) {
                    CHAR_SET = "utf-8";
                }
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, CHAR_SET));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (stream != null) {
                stream.close();
            }
        }

    }

    private static void configConnection(HttpURLConnection connection) {
        if (connection == null) {
            return;
        }
        connection.setReadTimeout(READ_TIME_OUT);
        connection.setConnectTimeout(CONNECT_TIME_OUT);

        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
    }

    private static HttpURLConnection getConnection(String strUrl) throws Exception {
        if (strUrl == null) {
            return null;
        }
        if (strUrl.toLowerCase().startsWith("https")) {
            try {
                return getHttpsConnection(strUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return getHttpConnection(strUrl);
        }
    }

    private static HttpURLConnection getHttpConnection(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        return conn;
    }

    private static HttpsURLConnection getHttpsConnection(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setHostnameVerifier(hnv);
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        if (sslContext != null) {
            TrustManager[] tm = {xtm};
            sslContext.init(null, tm, null);
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            conn.setSSLSocketFactory(ssf);
        }

        return conn;
    }

    private static X509TrustManager xtm = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    private static HostnameVerifier hnv = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    public static String urlEncode(String str, String charset) throws UnsupportedEncodingException {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]+");
        Matcher mathcer = pattern.matcher(str);
        StringBuffer buffer = new StringBuffer();
        while (mathcer.find()) {
            mathcer.appendReplacement(buffer, URLEncoder.encode(mathcer.group(0), charset));
        }
        mathcer.appendTail(buffer);
        return buffer.toString();
    }
    public static String postRequest(String url, Map<String, String> params) {
        URL u = null;
        HttpURLConnection con = null;
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            Iterator i$ = params.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry<String, String> e = (Map.Entry)i$.next();
                sb.append((String)e.getKey());
                sb.append("=");
                sb.append((String)e.getValue());
                sb.append("&");
            }
            sb.substring(0, sb.length() - 1);
        }
        try {
            u = new URL(url);
            con = (HttpURLConnection)u.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept-Language", "zh-cn");
            con.setRequestProperty("Accept-Encoding", "gzip, deflate");
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(sb.toString());
            osw.flush();
            osw.close();
        } catch (Exception var11) {
            LOGGER.error(var11.getMessage(), var11);
        } finally {
            if (con != null) {
                con.disconnect();
            }

        }

        StringBuffer buffer = new StringBuffer();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            String temp;
            while((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
        } catch (Exception var13) {
            LOGGER.error(var13.getMessage(), var13);
        }
        return buffer.toString();
    }
}
