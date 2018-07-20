package com.trs.core.util;

import com.trs.model.Greeting;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;


/**
 * Created on 16/1/21.
 */
public class HttpClientUtil {

    public static void main(String[] args) throws MalformedURLException, URISyntaxException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("xzqh", "140000");
        Greeting get = get("http://10.10.19.96/crms/OauthCommon.do?method=CRMS_ZZDWXX&data={\"xzqh\":\"120000\"}");
        System.out.println(get.getContent());
        Date date = new Date();

    }

    public static Greeting get(String url) throws MalformedURLException, URISyntaxException {
        int status = 0;
        StringBuffer body = new StringBuffer();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        URL url1 = new URL(url);
        URI uri1 = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
        HttpGet method = new HttpGet(uri1.toString());
        try {
            method.addHeader("accept", "application/json");
            long startTime = System.currentTimeMillis();
            HttpResponse response = httpClient.execute(method);
            long endTime = System.currentTimeMillis();
            if (response.getStatusLine().getStatusCode() != 200) {
                status = response.getStatusLine().getStatusCode();
                return new Greeting(status, "Method failed:" + response.getStatusLine());
            }


            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()),"utf-8"));

            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                body.append(output);
            }
            status = 1;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            status = 3;//网络错误
            body.append(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            status = 2;//IO错误
            body.append(e.getMessage());
        } finally {
            method.releaseConnection();
            httpClient.getConnectionManager().shutdown();
        }

        return new Greeting(status, body.toString());
    }


    public static Greeting post(String url, Map<String, String> paramsMap) {
        int status = 0;
        String body = null;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost method = new HttpPost(url);
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            Set<String> set = paramsMap.keySet();
            for (String p : set) {
                params.add(new BasicNameValuePair(p, paramsMap.get(p)));
            }
            //建立一个NameValuePair数组，用于存储欲传送的参数
            //设置编码
            method.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            long startTime = System.currentTimeMillis();
            HttpResponse response = httpClient.execute(method);
            long endTime = System.currentTimeMillis();
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
            if (statusCode != HttpStatus.SC_OK) {
                status = response.getStatusLine().getStatusCode();
                return new Greeting(status, "Method failed:" + response.getStatusLine());
            }

            //Read the response body
            body = EntityUtils.toString(response.getEntity());
            status = 1;
        } catch (IOException e) {
            //发生网络异常
            e.printStackTrace();
            body = e.getMessage();
            status = 3;//网络错误
        } finally {
            method.releaseConnection();
            httpClient.getConnectionManager().shutdown();
        }
        return new Greeting(status, body);
    }

}
