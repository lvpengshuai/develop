package com.trs.web.client;

import com.trs.core.util.Status;
import com.trs.service.FeedbackService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 知识图谱模块
 * Created by pz on 2017/3/29.
 */
@Controller
public class FeedBackController {
    private static final Logger LOGGER = Logger.getLogger(FeedBackController.class);
    @Resource
    private FeedbackService feedbackService;

    /**
     * 到意见反馈页面
     */
    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public ModelAndView atlas() {
        ModelAndView mv = new ModelAndView("/client/suggestion/suggestion");
        return mv;
    }


    /**
     * 提交反馈信息
     */
    @ResponseBody
    @RequestMapping(value = "/feedback/save", method = RequestMethod.POST)
    public Status saveFeedBack(HttpServletRequest request, @RequestParam(name = "file", required = false) MultipartFile file) {
        Status status = feedbackService.saveFeedback(request);
        return status;
    }


}
