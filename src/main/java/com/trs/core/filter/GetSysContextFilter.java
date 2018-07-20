package com.trs.core.filter;

import com.trs.model.SysContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pz on 2017/5/17.
 */
public class GetSysContextFilter implements Filter {
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
                         FilterChain arg2) throws IOException, ServletException {
        SysContext.setRequest((HttpServletRequest) arg0);
        SysContext.setResponse((HttpServletResponse) arg1);
        arg2.doFilter(arg0, arg1);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
    }
}
