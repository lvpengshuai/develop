package com.trs.core.interceptor;

/**
 * Created by root on 17-3-19.
 */

import com.trs.core.util.IPUtil;
import com.trs.model.SysContext;
import com.trs.service.SearchHistoryService;
import org.apache.commons.lang.StringEscapeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Date;

/**
 * 记录搜索历史aop
 */
@Aspect
@Component
public class SearchHistoryInterceptor {
    @Resource
    SearchHistoryService searchHistoryService;

    @Pointcut("@annotation(com.trs.core.annotations.SearchHistory)")
    private void SearchHistory() {
    }

    @Before("SearchHistory()")
    public void before() {
        //
        try {
            //拼写url
            String url = SysContext.getRequest().getRequestURL() + SysContext.getRequest().getContextPath() + SysContext.getRequest().getServletPath().substring(0, SysContext.getRequest().getServletPath().lastIndexOf("/") + 1);
            if (SysContext.getRequest().getQueryString() != null)
                url += "?" + SysContext.getRequest().getQueryString();
            Object userName = SysContext.getSession().getAttribute("userName");
            String searchName = SysContext.getRequest().getParameter("keyWord");
            if (searchName != null && searchName.contains(" ")) {
                searchName = URLDecoder.decode(searchName, "utf-8").replace(" ", "");
            }
            searchName = StringEscapeUtils.unescapeHtml(searchName);
            String word = SysContext.getRequest().getParameter("word");
            com.trs.model.SearchHistory searchHistory1 = new com.trs.model.SearchHistory();
            if (word != null) {
                searchHistory1.setSearchname(word);
            } else if (searchName != null) {
                searchHistory1.setSearchname(searchName);
            } else {
                return;
            }

            searchHistory1.setSearchurl(url);
            searchHistory1.setStatus(1);
            searchHistory1.setCreatetime(new Date());
            searchHistory1.setIp(String.valueOf(IPUtil.ip2Long(IPUtil.getIpAddr(SysContext.getRequest()))));
            if (userName != null) {
                searchHistory1.setUserid(userName.toString());
            }
            searchHistoryService.insertSearchHistroy(searchHistory1);
        } catch (Exception e) {

        }
    }

    private <T extends Annotation> T getAnnotation(JoinPoint jp, Class<T> clazz) {
        MethodSignature sign = (MethodSignature) jp.getSignature();
        Method method = sign.getMethod();
        return method.getAnnotation(clazz);
    }


}
