package com.trs.httpuntil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

public class TestZAS {

	// 超时间隔
	int connectTimeOut = 60000;
	boolean alwaysClose = false;
	String encoding = "UTF-8";
	String url = "http://www.sklib.cn/sso/api/json";
	HttpClient client = new HttpClient(new SimpleHttpConnectionManager());


	/**
	 *
	 */

	public void login(){
		Map par = new HashMap();
		par.put("method", "");
		par.put("method", "");

		String data = "{'method':'ZAS.AddUser', 'username':'syncuser', 'password':'syncuser', 'params':{'UserName':'a123','Password':'666666', 'Email':'823@qq.com', 'BID':'48'}}";
		String result = null;
		PostMethod postMethod = new PostMethod("http://172.16.8.247/sso/doLogin");
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(data, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			} else {
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * 添加会员信息
	 */
	@Test
	public void addUser(){
		String data = "{'method':'ZAS.UserInfo', 'username':'syncuser', 'password':'syncuser', 'params':{'LoginID':'m1','Password':'123456'}}";
		String result = null;
		PostMethod postMethod = new PostMethod(url);
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(data, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			} else {
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	/**
	 * 获取会员信息
	 */
	@Test
	public void getUserInfo() {
		String data = "{'username':'syncuser','password':'syncuser','params':{'LoginID':'hello3','Password':'666666'},'method':'ZAS.UserInfo'}";
		String result = null;
		PostMethod postMethod = new PostMethod(url);
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(data, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			} else {
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}


	@Test
	public void getUserInfo1() {
		String data = "{'method':'ZAS.AddBranch','username':'syncuser','password':'syncuser','params':{'Name':'ce12','Code':'a1111'}}";
		String result = null;
		PostMethod postMethod = new PostMethod(url);
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(data, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			} else {
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}

	
	/**
	 * 修改会员信息
	 */
	@Test
	public void modifyUserInfo(){
		String data = "{'method':'ZAS.ModifyUser', 'username':'syncuser', 'password':'syncuser', 'params':{'LoginID':'hello','Mobile':'18999999999'}}";
		String result = null;
		PostMethod postMethod = new PostMethod(url);
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(data, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			} else {
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	/**
	 * 找回密码
	 */
	@Test
	public void Findpwd(){
		String data = "{'method':'ZAS.Findpwd', 'username':'syncuser', 'password':'syncuser', 'params':{'AddressType':'Email','Address':'821728010@qq.com'}}";
		String result = null;
		PostMethod postMethod = new PostMethod(url);
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(data, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			} else {
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	/**
	 * 重置密码
	 */
	@Test
	public void ResetPwd(){
		String data = "{'method':'ZAS.Findpwd', 'username':'syncuser', 'password':'syncuser', 'params':{'VerifyMode':'Code', 'VerifyValue':'VerifyValue' 'AddressType':'Email' 'Address':'Address' 'NewPwd':'NewPwd'}}";
		String result = null;
		PostMethod postMethod = new PostMethod(url);
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(data, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			} else {
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * 添加机构
	 */
	@Test
	public void addOrganize(){
		String data = "{'method':'ZAS.AddBranch', 'username':'syncuser', 'password':'syncuser', 'params':{'Name':'机构1234','Code':'7bf382d6-9650-4f38-90ad-f05424ae6711','Address':'北京','Phone':'1230032123','MaxOnline':'100'}}";
		String result = null;
		PostMethod postMethod = new PostMethod(url);
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(data, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			} else {
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}





	/**
	 * 机构ip
	 */
	@Test
	public void IpOrganize(){
		String data = "{'method':'ZAS.GetBranch', 'username':'syncuser', 'password':'syncuser', 'params':{'IPRange':'192.168.1.2'}}";
//		String data = "{'method':'ZAS.GetBranch', 'username':'syncuser', 'password':'syncuser', 'params':{'IPRange':'192.168.1.1-192.168.1.4'}}";
		String result = null;
		PostMethod postMethod = new PostMethod(url);
		try {
			StringRequestEntity requestEntity = new StringRequestEntity(data, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				result = postMethod.getResponseBodyAsString();
			} else {
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * 转码
	 */
	@Test
	public void gbktoutf8(){
		String s = "你好哦!";

		System.out.println("编码测试s==="+s);
	}

}
