package com.trs.core.interceptor;

/**
 * Created by root on 17-3-19.
 */

import com.trs.core.annotations.Log;
import com.trs.core.util.IPUtil;
import com.trs.model.SysContext;
import com.trs.model.User;
import com.trs.service.LogService;
import com.trs.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志记录器
 */
@Aspect
@Component
public class SystemLogInterceptor {

    @Resource
    private LogService logService;
    @Resource
    private UserService userService;

    @Pointcut("@annotation(com.trs.core.annotations.Log)")
    private void log() {

    }

    @After("log()")
    public void after(JoinPoint joinPoint) {
        try {
            Log log = getAnnotation(joinPoint, Log.class);
            HttpServletRequest request = SysContext.getRequest();
            String adminUserName = String.valueOf(request.getSession().getAttribute("adminUserName"));
            String userName = String.valueOf(request.getSession().getAttribute("userName"));
            String ip = IPUtil.getIpAddr(request);

            String description = log.description();
            String operationType = log.operationType();
            String targetType = log.targetType();
            String foreground = log.isForeground();

            User userByName = userService.findUserByName(adminUserName);

            User user = new User();
            if (!"".equals(foreground) && "0".equals(foreground)) {
                user.setUsername(userName);

                long endLongTime = System.currentTimeMillis();
                long beginLongTime = endLongTime - 600000L;
                String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(endLongTime));
                String begin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(beginLongTime));
                Map parMap = new HashMap();
                parMap.put("ip", ip);
                parMap.put("oper", operationType);
                parMap.put("target", targetType);
                parMap.put("begin", begin);
                parMap.put("end", end);
                int countByIpAndType = logService.getCountByIpAndType(parMap);
                if (countByIpAndType > 80) {
                    logService.addLog(user, description, operationType, targetType, ip, "0");
                } else {
                    logService.addLog(user, description, operationType, targetType, ip, "1");
                }
            } else {
                logService.addLog(userByName, description, operationType, targetType, ip, "1");
            }

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
