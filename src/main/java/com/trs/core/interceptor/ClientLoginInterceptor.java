package com.trs.core.interceptor;

import com.trs.service.MemberOnlineService;
import com.trs.service.MemberService;
import com.trs.web.client.ClientLoginController;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by lcy on 2017/5/24.
 */
public class ClientLoginInterceptor implements HandlerInterceptor {

    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_URL = "/logout";

    @Resource
    private MemberService memberService;

    @Resource
    private MemberOnlineService memberOnlineService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        String uri = httpServletRequest.getRequestURI();
        String path = httpServletRequest.getContextPath();

        //释放静态资源路径
        if (uri.startsWith("/static")) {
            return true;
        }
        if (uri.indexOf(".")>0) {
            if(uri.indexOf(".html")>0 || uri.indexOf(".png")>0 || uri.indexOf(".js")>0||uri.indexOf(".css")>0||uri.indexOf(".jpg")>0||uri.indexOf(".woff")>0||uri.indexOf(".gif")>0){
                return true;
            }else {
                httpServletResponse.sendError(404);
            }

        }
        // 读取cookie
        boolean b = new ClientLoginController().readCookieAndLogon(memberService,memberOnlineService, httpServletRequest, httpServletResponse);
        if (b) {
            return true;
        }
        //释放登录界面
        if (uri.endsWith(LOGIN_URL) || uri.endsWith(LOGOUT_URL)) {
            return true;
        }

        //检测userName
        if (uri.startsWith("/user")) {
            Object userName = httpServletRequest.getSession().getAttribute("userName");
            if (StringUtils.isEmpty(userName)) {
                httpServletResponse.addHeader("location", "/login");
                httpServletResponse.setStatus(302);
                return false;
            }
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
