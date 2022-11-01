package com.weijia.zh.qa.utils;

import com.weijia.zh.common.utils.JsonPare;
import com.weijia.zh.common.utils.JsonUtil;
import com.weijia.zh.qa.vo.TranslateResp;
import io.seata.saga.statelang.parser.impl.FastjsonParser;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 中英文互译
 */
public class FanyiV3 {

    private static Logger logger = LoggerFactory.getLogger(FanyiV3.class);

    private static final String YOUDAO_URL = "https://openapi.youdao.com/api";

    private static final String APP_KEY = "25ac62f04c1a8e50";

    private static final String APP_SECRET = "CLAoyZRkh62NzXCmgFljanWypR45p4ee";

    public static void main(String[] args) throws IOException {

    }

    /**
     * 中文翻译成英文
     * @param input
     * @return
     */
    public static String CHS_TO_EN(String input){
        Map<String, String> params = new HashMap<String, String>();
        String q = input;
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("from", "zh-CHS");
        params.put("to", "en");
        params.put("signType", "v3");
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", curtime);
        String signStr = APP_KEY + truncate(q) + salt + curtime + APP_SECRET;
        String sign = getDigest(signStr);
        params.put("appKey", APP_KEY);
        params.put("q", q);
        params.put("salt", salt);
        params.put("sign", sign);
        params.put("vocabId", "您的用户词表ID");
        /** 处理结果 */
        try {
            return  requestForHttp(YOUDAO_URL, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 英文翻译为中文
     * @param input
     * @return
     */
    public static String EN_TO_CHS(String input){
        Map<String, String> params = new HashMap<String, String>();
        String q = input;
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("from", "en");
        params.put("to", "zh-CHS");
        params.put("signType", "v3");
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", curtime);
        String signStr = APP_KEY + truncate(q) + salt + curtime + APP_SECRET;
        String sign = getDigest(signStr);
        params.put("appKey", APP_KEY);
        params.put("q", q);
        params.put("salt", salt);
        params.put("sign", sign);
        params.put("vocabId", "您的用户词表ID");
        /** 处理结果 */
        try {
            return  requestForHttp(YOUDAO_URL, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }


    public static String requestForHttp(String url, Map<String, String> params) throws IOException {

        /** 创建HttpClient */
        CloseableHttpClient httpClient = HttpClients.createDefault();

        /** httpPost */
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> en = it.next();
            String key = en.getKey();
            String value = en.getValue();
            paramsList.add(new BasicNameValuePair(key, value));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
        System.out.println("url:"+url);
        paramsList.forEach(val -> {
            System.out.println("name:"+val.getName()+",value:"+val.getValue());
        });
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try {
            Header[] contentType = httpResponse.getHeaders("Content-Type");
            logger.info("Content-Type:" + contentType[0].getValue());
            if ("audio/mp3".equals(contentType[0].getValue())) {
                //如果响应是wav
                HttpEntity httpEntity = httpResponse.getEntity();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(baos);
                byte[] result = baos.toByteArray();
                EntityUtils.consume(httpEntity);
                if (result != null) {//合成成功
                    String file = "合成的音频存储路径" + System.currentTimeMillis() + ".mp3";
                    logger.info("合成音频");
                    byte2File(result, file);
                    return null;
                }
            } else {
                /** 响应不是音频流，直接显示结果 */
                HttpEntity httpEntity = httpResponse.getEntity();
                String json = EntityUtils.toString(httpEntity, "UTF-8");
                EntityUtils.consume(httpEntity);
                logger.info(json);
                System.out.println(json);
                Map map = JsonUtil.jsonStr2Object(json, Map.class);
                logger.info("JSON转换后的格式:" + map);
                if (!"0".equals(map.get("errorCode")))
                    return null;
                List translations = JsonPare.pare(map.get("translation"), List.class);
                logger.info("translations:{}",translations);
                if (translations == null || translations.size()<=0)
                    return null;
                logger.info("translations.get(0):{}",translations.get(0));
                return (String) translations.get(0);
            }
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                logger.info("## release resouce error ##" + e);
            }
        }
        return null;
    }

    /**
     * 生成加密字段
     */
    public static String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * @param result 音频字节流
     * @param file   存储路径
     */
    private static void byte2File(byte[] result, String file) {
        File audioFile = new File(file);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(audioFile);
            fos.write(result);

        } catch (Exception e) {
            logger.info(e.toString());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        String result;
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }
}

