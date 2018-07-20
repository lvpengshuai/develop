package com.trs.core.interceptor;

/**
 * Created by root on 17-3-19.
 */

import com.trs.core.annotations.Permission;
import com.trs.core.exception.OntologyException;
import com.trs.model.SysContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 权限拦截器
 * 没有权限抛出异常，到404页面
 */
@Aspect
@Component
public class PermissionInterceptor {

    @Pointcut("@annotation(com.trs.core.annotations.Permission)")
    private void permission() {
    }

    @Around("permission()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Permission permission = getAnnotation(joinPoint, Permission.class);
        //TODO 如果验证类型为前台，进行会员用户的权限判断
        if (permission.type().equals("client")) {
            return joinPoint.proceed();
        }
        //TODO 后台从session中获取用户权限查看是否有权限可以访问
        String url = permission.url();
        HttpSession session = SysContext.getSession();
        Map<String, List<com.trs.model.Permission>> result = (Map<String, List<com.trs.model.Permission>>) session.getAttribute("nav");
        Set<String> set = new HashSet<>();
        if (result != null) {
            for (Map.Entry<String, List<com.trs.model.Permission>> entry : result.entrySet()) {
                List<com.trs.model.Permission> list = entry.getValue();
                for (com.trs.model.Permission permission1 : list) {
                    set.add(permission1.getUrl());
                }
            }
        }
        if (set.contains(url)) {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            throw new OntologyException("没有权限");
        }
        return joinPoint.proceed();

    }

    private <T extends Annotation> T getAnnotation(JoinPoint jp, Class<T> clazz) {
        MethodSignature sign = (MethodSignature) jp.getSignature();
        Method method = sign.getMethod();
        return method.getAnnotation(clazz);
    }


}
