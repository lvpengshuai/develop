package com.trs.web.client;

import com.trs.client.TRSException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xubo on 2017/3/20.
 */
@Controller
@RequestMapping(value = "/yb")
public class YearBookController {
    /**
     * 跳转单本图书详情页
     * @param request
     * @param modelMap
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/detail")
    public ModelAndView searchanalysis (HttpServletRequest request, ModelMap modelMap) throws TRSException {
        return new ModelAndView("client/yearbooks/detail",modelMap);
    }
}
