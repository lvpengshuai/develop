package com.trs.core.interceptor;

/**
 * Created by root on 17-3-19.
 */

import com.trs.core.util.IPUtil;
import com.trs.model.SysContext;
import com.trs.service.AccessLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 记录搜索历史aop
 */
@Aspect
@Component
public class AccessLogInterceptor {
    @Resource
    private AccessLogService accessLogService;

    @Pointcut("@annotation(com.trs.core.annotations.AccessLog)")
    private void AccessLog() {
    }

    @After("AccessLog()")
    public void after(JoinPoint joinPoint) {
        //TODO 从session中获取用户权限查看是否有权限可以访问
        try {
            Object[] arguments = joinPoint.getArgs();
            HttpServletRequest request = SysContext.getRequest();
            HttpSession session = request.getSession();
            String ipAddr = IPUtil.getIpAddr(request);
            String url = String.valueOf(request.getRequestURL());
            Map paramMap = new HashMap();
//            paramMap.put("name", book.getBookName());
            paramMap.put("type", 1);
            paramMap.put("username", session.getAttribute("userName"));
            paramMap.put("ip", IPUtil.ip2Long(ipAddr));
            paramMap.put("url", url);
            paramMap.put("gmt_create", new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T extends Annotation> T getAnnotation(JoinPoint jp, Class<T> clazz) {
        MethodSignature sign = (MethodSignature) jp.getSignature();
        Method method = sign.getMethod();
        return method.getAnnotation(clazz);
    }


}
