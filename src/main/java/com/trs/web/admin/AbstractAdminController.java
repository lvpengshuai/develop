package com.trs.web.admin;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created on 2017/2/28.
 */
@RequestMapping(value = "/admin")
public abstract class AbstractAdminController {

    /*封装response*/
    public static void out(HttpServletResponse response, String str) {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
                out = null;
            }
        }
    }
}
