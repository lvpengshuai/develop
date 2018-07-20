package com.trs.core.util;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RequestUtils {

	private HttpServletRequest request;

	public RequestUtils(HttpServletRequest _request) {
		super();

		this.request = _request;
	}

	public String[] getParameterValues(String _sParamName) {
		return request.getParameterValues(_sParamName);
	}

	public String getParameterAsString(String _sParamName, String _sDefaultValue) {
		String sParamValue = request.getParameter(_sParamName);
		String strParamValue = CMyString.showNull(sParamValue, _sDefaultValue);
		if (_sParamName.equalsIgnoreCase("SearchType")) {
			strParamValue = CMyString.filterForSQL(strParamValue);
		}
		return Util.toStr(cleanXSS(strParamValue), _sDefaultValue);
	}

	public String getParameterAsString2(String _sParamName, String _sDefaultValue) {
		String sParamValue = request.getParameter(_sParamName);
		String strParamValue = CMyString.showNull(sParamValue, _sDefaultValue).replaceAll("，",",");
		/*if (_sParamName.equalsIgnoreCase("SearchType")) {
			strParamValue = CMyString.filterForSQL(strParamValue);
		}*/
		return strParamValue;
	}

	/**
	 * 跨站脚本防御
	 *
	 * @param value
	 * @return
	 */
	private String cleanXSS(String value) {
		value = value.replaceAll("<", "").replaceAll(">", "");
//		value = value.replaceAll("\\(", "").replaceAll("\\)", "");
		value = value.replaceAll("'", "");
		value = value.replaceAll("\"", "");
		value = value.replaceAll("=", "");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll(
				"[\\\"<a>\\\'][\\s]*javascript:(.*)[\\\"\\\'</a>]", "");
		value = value.replaceAll("script", "");
		value = value.replaceAll("%20", "");
		value = value.replaceAll("%22", "");
		value = value.replaceAll("%253c", "");
		value = value.replaceAll("%0e", "");
		value = value.replaceAll("%28", "");
		value = value.replaceAll("%29", "");
		value = value.replaceAll("%3c", "");
		value = value.replaceAll("%3e", "");
		value = value.replaceAll("%2528", "");
		value = value.replaceAll("%24", "");
		value = value.replaceAll("%3f", "");
		value = value.replaceAll("%3f", "");
		value = value.replaceAll("%3b", "");
		value = value.replaceAll("%3d", "");
		value = value.replaceAll("%3D", "");
		return value;
	}


	public int getParameterAsInt(String _sParamName, int _nDefaultValue) {
		int nValue = _nDefaultValue;
		String sParamValue = getParameterAsString(_sParamName, "");
		try {
			nValue = Integer.parseInt(sParamValue);
		} catch (Exception ex) {
		}
		if (_sParamName.trim().equals("pageIndex") && nValue < 1) {
			nValue = 1;
		}
		return nValue;
	}

	public long getParameterAsLong(String _sParamName, long _lDefaultValue) {
		long lValue = _lDefaultValue;
		String sParamValue = getParameterAsString(_sParamName, "");
		try {
			lValue = Long.parseLong(sParamValue);
		} catch (Exception ex) {
		}
		return lValue;
	}

	public String getParameterForHTMLValue(String _sParamName) {
		String sParameterValue = getParameterAsString(_sParamName, "");
		return CMyString.filterForHTMLValue(sParameterValue);
	}

	public String getParameterForJS(String _sParamName) {
		String sParameterValue = getParameterAsString(_sParamName, "");
		return CMyString.filterForJs(sParameterValue);
	}

	/**
	 * 验证参数不能为NULL
	 *
	 * @param sParamName
	 * @param sParamShowName
	 */
	public void validateParameterIsNull(String sParamName, String sParamShowName)
			throws Exception {
		String sParamValue = request.getParameter(sParamName);
		if (sParamValue == null)
			throw new Exception("参数[" + sParamShowName + "]为NULL.");
	}

	/**
	 * 验证参数不能为空
	 *
	 * @param sParamName
	 * @param sParamShowName
	 */
	public void validateParameterIsEmpty(String sParamName,
										 String sParamShowName) throws Exception {
		String sParamValue = getParameterAsString(sParamName, "");
		if (sParamValue.isEmpty())
			throw new Exception("参数[" + sParamShowName + "]为空串.");
	}

	public void validateParameterIsEmail(String sParamName,
										 String sParamShowName) throws Exception {
		validateParameterIsEmpty(sParamName, sParamShowName); // 验证一次空参数

		String sParamValue = getParameterAsString(sParamName, "");
		int nIndexOf = sParamValue.indexOf("@");
		if (nIndexOf == -1)
			throw new Exception("参数[" + sParamShowName + "]不符合指定的格式[邮件格式]");
	}

	public void validateParameterIsInt(String sParamName, String sParamShowName)
			throws Exception {
		validateParameterIsEmpty(sParamName, sParamShowName); // 验证一次空参数

		String sParamValue = getParameterAsString(sParamName, "");
		try {
			Integer.parseInt(sParamValue);
		} catch (Exception ex) {
			throw new Exception("参数[" + sParamShowName + "]不符合指定的格式[整数]");
		}
	}

	public void validateParameterIsLong(String sParamName, String sParamShowName)
			throws Exception {
		validateParameterIsEmpty(sParamName, sParamShowName); // 验证一次空参数

		String sParamValue = getParameterAsString(sParamName, "");
		try {
			Long.parseLong(sParamValue);
		} catch (Exception ex) {
			throw new Exception("参数[" + sParamShowName + "]不符合指定的格式[长整型]");
		}
	}

	public void validParameterIsMobile(String sParamName, String sParamShowName)
			throws Exception {
		validateParameterIsEmpty(sParamName, sParamShowName); // 验证一次空参数

		String sParamValue = getParameterAsString(sParamName, "").trim();
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(sParamValue);
//		System.out.println(m.matches() + "---");
		boolean bMatched = false;
		try {
			bMatched = m.matches();
		} catch (Exception ex) {
			throw new Exception("校验手机号码参数失败");
		}
		if (!bMatched) {
			throw new Exception("参数[" + sParamShowName + "]不符合指定的格式[移动手机号]");
		}
	}

	/**
	 * 将参数转换成Map对象
	 *
	 * @return
	 */
	public Map<String, Object> parseRequestParamsToParamsContext() {
		Map<String, Object> paramContext = new ConcurrentHashMap<String, Object>();
		Enumeration<String> enumNames = request.getParameterNames();
		while (enumNames.hasMoreElements()) {
			String sParamName = (String) enumNames.nextElement();
			String[] sParameterValues = getParameterValues(sParamName);
			if (sParameterValues == null || sParameterValues.length == 1) {
				String sParameterValue = getParameterAsString(sParamName, "");
				paramContext.put(sParamName, sParameterValue);
			} else {
				paramContext.put(sParamName, sParameterValues);
			}
		}
		return paramContext;
	}

	public String parseParamsContextToString(Map<String, Object> paramsContext) {
		Iterator<Map.Entry<String, Object>> it = paramsContext.entrySet()
				.iterator();
		StringBuffer oStringBuffer = new StringBuffer();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof String[]) {
				String[] arValues = (String[]) value;
				String sValues = CMyString.join(arValues, ",");
				oStringBuffer.append("&" + key + "=" + sValues);
			} else {
				oStringBuffer.append("&" + key + "=" + value);
			}
		}

		return oStringBuffer.toString();
	}

	public static String decode(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String decodeBase64(String s) throws Exception {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			b = decoder.decodeBuffer(s);
			result = new String(b, "utf-8");
		}
		return result;
	}

	//是否加密
	public boolean func(String str){
		try {
			String temp = new   String(str.getBytes("base64"),"base64");
			if(!temp.equals(str))return false;
		} catch (UnsupportedEncodingException e) {
			System.out.println("no");
			return false;
		}
		return true;
	}

	// 加密
	public static String getBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}
	public static void main(String[] args) {
		String s = "http://219.238.166.208/gcweb/InterfacePage/UserLinkProcess.ashx?code=b3ec13db7a4ce19ae56956000407cdd4&state=oauthlogin&m_url=/gcweb/wsbs/WsbsMain.aspx?type=3%26flowname=%e6%b0%b8%e4%b9%85%e6%80%a7%e6%b5%8b%e9%87%8f%e6%a0%87%e5%bf%97%e6%8b%86%e8%bf%81%e5%ae%a1%e6%89%b9%26flowtype=%e6%b0%b8%e4%b9%85%e6%80%a7%e6%b5%8b%e9%87%8f%e6%a0%87%e5%bf%97%e6%8b%86%e8%bf%81%e5%ae%a1%e6%89%b9%26sign=1";
		String base64 = getBase64(s);
		System.out.println(base64);
		String decode = decode(base64);
		System.out.println(decode);
	}

	public static String getReal(String str) {
		str = str.replaceAll("&#40;", "\\(").replaceAll("&#41;", "\\)").replaceAll("&#39;","'");
		str = str.replaceAll("（", "\\(").replaceAll("）", "\\)");
		str = str.replaceAll("“", "\"").replaceAll("”", "\"");
		str = str.replaceAll("，", ",");
		return str;
	}

	/**
	 * 去掉前后空格
	 * @param str
	 * @return
	 */
	public static String trimInnerSpaceStr(String str){
		str = str.trim();
		while(str.startsWith(" ")){
			str = str.substring(1,str.length()).trim();
		}
		while(str.endsWith(" ")){
			str = str.substring(0,str.length()-1).trim();
		}
		return str;
	}
}

