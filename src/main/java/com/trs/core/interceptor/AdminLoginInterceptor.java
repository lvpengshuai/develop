package com.trs.core.interceptor;

import com.trs.service.RoleService;
import com.trs.service.UserService;
import com.trs.web.admin.LoginController;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AdminLoginInterceptor implements HandlerInterceptor {
    private static final String LOGIN_URL = "/admin/login";
    private static final String LOGOUT_URL = "/admin/logout";


    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        String path = request.getContextPath();

        //释放登录界面
        if (uri.endsWith(LOGIN_URL) || uri.endsWith(LOGOUT_URL)) {
            return true;
        }

        //释放静态资源路径
        if (uri.startsWith("/static")) {
            return true;
        }
        boolean b = new LoginController().readCookieAndAdminLogon(userService, roleService, request, response);

        if (b) {
            return true;
        }

        //检测userName
        if (uri.startsWith("/admin")) {
            Object userName = request.getSession().getAttribute("adminUserName");
            if (StringUtils.isEmpty(userName)) {
                response.addHeader("location", "/admin/login");
                response.setStatus(302);
                return false;
            }
        }


        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
