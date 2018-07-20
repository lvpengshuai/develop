package com.trs.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created on 2017/2/28.
 */
@Controller
public class HomeController extends AbstractAdminController {

    /**
     * 初始化左侧导航栏
     *
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView login(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("admin/home");

        return modelAndView;
    }
}
