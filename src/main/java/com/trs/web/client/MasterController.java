package com.trs.web.client;

import com.trs.client.TRSException;
import com.trs.core.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2017/3/20.
 */
@Controller
@RequestMapping(value = "/view")
public class MasterController {
//
//    @RequestMapping(value = "/login")
//    public ModelAndView login (HttpServletRequest request, ModelMap modelMap) throws TRSException {
//        return new ModelAndView("client/login/login",modelMap);
//    }
//
//    @RequestMapping(value = "/home")
//    public ModelAndView home (HttpServletRequest request, ModelMap modelMap) throws TRSException {
//        System.out.println("start");
//        return new ModelAndView("client/view/home",modelMap);
//    }
//
//    @RequestMapping(value = "/book")
//    public ModelAndView details (HttpServletRequest request, ModelMap modelMap) throws TRSException {
//
//        return new ModelAndView("client/book/book",modelMap);
//    }
//    @RequestMapping(value = "/analysis")
//    public ModelAndView analysis (HttpServletRequest request, ModelMap modelMap) throws TRSException {
//        return new ModelAndView("client/book/analysis",modelMap);
//    }
//
//
//    @RequestMapping(value = "/searchanalysis")
//    public ModelAndView searchanalysis (HttpServletRequest request, ModelMap modelMap) throws TRSException {
//
//        return new ModelAndView("client/view/searchanalysis",modelMap);
//
//    }
//
//    @RequestMapping(value = "/bookslist")
//    public ModelAndView bookslist (HttpServletRequest request, ModelMap modelMap) throws TRSException {
//        return new ModelAndView("client/book/bookslist",modelMap);
//    }
//
//    @RequestMapping(value = "/datecenter")
//    public ModelAndView datecenter (HttpServletRequest request, ModelMap modelMap) throws TRSException {
//        return new ModelAndView("client/data/datecenter",modelMap);
//    }
//
//    @RequestMapping(value = "/detail")
//    public ModelAndView detail (HttpServletRequest request, ModelMap modelMap) throws TRSException {
//        return new ModelAndView("client/book/bookdetail",modelMap);
//    }
//
    @RequestMapping(value = "/repassword")
    public ModelAndView repassword (HttpServletRequest request, ModelMap modelMap) throws TRSException {

        return new ModelAndView("client/usercenter/repassword",modelMap);
    }

    @RequestMapping(value = "/logshowpage")
    @ResponseBody
    public Object logshowpage (HttpServletRequest request, ModelMap modelMap) {
        return new ModelAndView("client/view/catlog",modelMap);
    }

    @RequestMapping(value = "/logshow")
    @ResponseBody
    public Object logshow (HttpServletRequest request, ModelMap modelMap) {
        Map<String, Object> map = new HashMap<>();
        String ip = "";
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        }else {
            ip = request.getHeader("x-forwarded-for");
        }
        map.put("ip", ip);
        String path = Util.toStr(request.getParameter("path"));
        if(path.equals("")){
            return null;
        }else{
            path = path.replace("\\","/");
        }

        List<String> list = Util.readLastNLine(new File(path), 50L);
        map.put("list", list);
        return map;
    }

}
