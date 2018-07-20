package com.trs.core.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by 李春雨 on 2017/3/7.
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    public String[] getParameterValues(String parameter) {

        String[] values = super.getParameterValues(parameter);

        if (values==null)  {

            return null;

        }

        int count = values.length;

        String[] encodedValues = new String[count];

        for (int i = 0; i < count; i++) {

            encodedValues[i] = cleanXSS(values[i]);

        }

        return encodedValues;

    }

    public String getParameter(String parameter) {

        String value = super.getParameter(parameter);

        if (value == null) {

            return null;

        }

        return cleanXSS(value);

    }

    public String getHeader(String name) {

        String value = super.getHeader(name);

        if (value == null)

            return null;

        return cleanXSS(value);

    }

    private String cleanXSS(String value) {

        //You'll need to remove the spaces from the html entities below

        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");

        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
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

}
