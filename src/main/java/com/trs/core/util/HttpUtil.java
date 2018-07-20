package com.trs.core.util;

import com.trs.model.Greeting;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mxp on 2017/9/25.
 */
public class HttpUtil {

//http://www.zving.com/c/2016-10-31/260985.shtml

    public static void main(String[] args){



        // 超时间隔
        int connectTimeOut = 60000;

        boolean alwaysClose = false;

        // 返回数据编码格式
        String encoding = "UTF-8";

        String url = "http://www.sklib.cn/sso/doLogin";
        String data = "{'client_id':'526157d544b048a3ab5acf3d5b15efd6','response_type':'code','scope':'user_info','LoginID':'mxppp','Password':'23456','ContentType':'json','state':'1','redirect_uri':'http://172.16.0.12:8080/login'}";
        System.out.println(zapost(url,data));

        /*
        *
        *
        * {"username":"syncuser","password":"syncuser","params":{"LoginID":"ceshi80","Password":"666666"},"method":"ZAS.UserInfo"}
        * */
//        Map map = new HashMap();
//        map.put("username","syncuser");
//        map.put("password","syncuser");
//        Map params = new HashMap();
//        map.put(params,params.put("LoginID","mxppp"));
//        map.put(params,params.put("Password","123456"));
//        map.put("method","ZAS.UserInfo");
//
//
//        Greeting greeting = post(url,map);
//        System.out.println(greeting.getStatus()+"====="+greeting.getContent());
    }

    public static Greeting get(String url) {
        int status = 0;
        StringBuffer body = new StringBuffer();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet method = new HttpGet(url);
        try {
            method.addHeader("accept", "application/json");

            long startTime = System.currentTimeMillis();
            HttpResponse response = httpClient.execute(method);
            long endTime = System.currentTimeMillis();
            System.out.println("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
            if (response.getStatusLine().getStatusCode() != 200) {
                status = response.getStatusLine().getStatusCode();
                return new Greeting(status, "Method failed:" + response.getStatusLine());
            }


            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

            String output;
            while ((output = br.readLine()) != null) {
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

    public static String zapost(String url,String data){
        HttpClient client = new HttpClient(new SimpleHttpConnectionManager());

        String result = null;
        PostMethod postMethod = new PostMethod(url);
        try {
            StringRequestEntity requestEntity = new StringRequestEntity(data, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            int statusCode = client.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                result = postMethod.getResponseBodyAsString();
            } else {
                System.out.println("Method failed: " + postMethod.getStatusLine());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }


}
