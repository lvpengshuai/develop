package com.trs.core.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendCloud44 {

	private static String url = Config.getKey("sendcloud.url");
	private static String apiUser = Config.getKey("sendcloud.apiUser");
	private static String apiKey = Config.getKey("sendcloud.apiKey");
	private static String from = Config.getKey("sendcloud.from");
	private static String fromName = Config.getKey("sendcloud.fromName");
//	private static String template_active = Config.getKey("template.active");

	public static String convert(Map dataMap) {

		JSONObject ret = new JSONObject();

		JSONArray to = new JSONArray();
		JSONArray active_url = new JSONArray();

		to.put(dataMap.get("to"));
		active_url.put(dataMap.get("url"));

		JSONObject sub = new JSONObject();
		sub.put("%url%", active_url);

		ret.put("to", to);
		ret.put("sub", sub);

		return ret.toString();
	}

	public static void send_common(String rcpt_to, String subject, String html) throws IOException {

		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient httpClient = HttpClients.createDefault();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("apiUser", apiUser));
		params.add(new BasicNameValuePair("apiKey", apiKey));
		params.add(new BasicNameValuePair("to", rcpt_to));
		params.add(new BasicNameValuePair("from", from));
		params.add(new BasicNameValuePair("fromName", fromName));
		params.add(new BasicNameValuePair("subject", subject));
		params.add(new BasicNameValuePair("html", html));

		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		HttpResponse response = httpClient.execute(httpPost);

		// 处理响应
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// 正常返回, 解析返回数据
			System.out.println(EntityUtils.toString(response.getEntity()));
		} else {
			System.err.println("error");
		}
		httpPost.releaseConnection();
	}


	public static void send_template(Map dataMap, String template_active) throws ClientProtocolException, IOException {

		final String xsmtpapi = convert(dataMap);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("apiUser", apiUser));
		params.add(new BasicNameValuePair("apiKey", apiKey));
		params.add(new BasicNameValuePair("xsmtpapi", xsmtpapi));
		params.add(new BasicNameValuePair("templateInvokeName", template_active));
		params.add(new BasicNameValuePair("from", from));
		params.add(new BasicNameValuePair("fromName", fromName));

		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		HttpResponse response = httpClient.execute(httpPost);

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
			System.out.println(EntityUtils.toString(response.getEntity()));
		} else {
			System.err.println("error");
		}
		httpPost.releaseConnection();
	}

//	public static void main(String[] args) throws Exception {
//		Map datamap = new HashMap();
//		datamap.put("to", "18310421009@163.com");
//		datamap.put("url", Config.getKey("website") + "active?email=18310421009@163.com&active=");
//		send_template(datamap,Config.getKey("template.retrievepwd"));
//	}
}
