package com.trs.service.impl;

import com.trs.core.util.AnsjUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/4/13.
 */
public class AnsjServiceImpl {
    public static Map<String, String> ansj(String keyWord, HttpServletRequest request) {
        Map<String, String> result = new HashMap<String, String>();
        String value = "";
        try {
            Map<String, List<String>> map = AnsjUtil.getValue(keyWord);
            value = keyWord;
            List<String> name = map.get("name");
            for (int i = 0; i < name.size(); i++) {
                String[] split = name.get(i).split(";");
                if (i == 0) {
                    if (split[2].equals("1")) {
                        value = replace(keyWord, split[1], split[0], "book", request);
                    } else if (split[2].equals("2")) {
                        value = replace(keyWord, split[1], split[0], "journal", request);

                    } else if (split[2].equals("3")) {
                        value = replace(keyWord, split[1], split[0], "standard", request);

                    } else if (split[2].equals("4")) {
                        value = replace(keyWord, split[0], split[0], "knowledge", request);

                    }
                } else if (i >= 1) {
                    if (split[2].equals("1")) {
                        value = replace(value, split[1], split[0], "book", request);
                    } else if (split[2].equals("2")) {
                        value = replace(value, split[1], split[0], "journal", request);

                    } else if (split[2].equals("3")) {
                        value = replace(value, split[1], split[0], "standard", request);

                    } else if (split[2].equals("4")) {
                        value = replace(value, split[0], split[0], "knowledge", request);

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("name", value);
        return result;
    }

    /**
     * 替换方法
     *
     * @param value
     * @param id
     * @param name
     * @return
     */
    public static String replace(String value, String id, String name, String url, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return value.replaceFirst(name, "<a class='sku-color' href='" + contextPath + "/" + url + "/" + id + "'>" + name + "</a>");
    }
}
