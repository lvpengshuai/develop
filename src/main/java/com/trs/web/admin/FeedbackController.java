package com.trs.web.admin;

import com.trs.core.util.Status;
import com.trs.mapper.FeedbackMapper;
import com.trs.model.Feedback;
import com.trs.service.FeedbackService;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by panzhen on 2017/3/9.
 */
@Controller
public class FeedbackController extends AbstractAdminController {
    private static final Logger LOGGER = Logger.getLogger(FeedbackController.class);
    @Resource
    private FeedbackService feedbackService;
    @Resource
    FeedbackMapper feedbackMapper;

    /**
     * 反馈首页
     *
     * @param response
     */
    /*@Permission(url = "/feedback")*/
    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public ModelAndView feedback(HttpServletResponse response, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("admin/feedback/feedback");
        modelAndView.addObject("title", "反馈信息");

        return modelAndView;
    }

    /**
     * 反馈信息列表
     *
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/feedback/list", method = RequestMethod.GET)
    public Map list(HttpServletResponse response, HttpServletRequest request) {
        String pageSize = request.getParameter("pageSize");
        String currPage = request.getParameter("currPage");
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String status = request.getParameter("state");//1是发布，0是未发布

        Map map = new HashMap();
        map.put("pageSize", Integer.parseInt(pageSize));
        map.put("currPage", Integer.parseInt(currPage));
        map.put("search", search);
        map.put("sort", sort);
        map.put("order", order);
        map.put("status", status);
        try {
            Map resultMap = feedbackService.findAll(map);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/feedback/update", method = RequestMethod.GET)
    public Status update(HttpServletResponse response, HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            Feedback feedback = feedbackMapper.selectByPrimaryKey(Integer.valueOf(id));
            feedback.setStatus(1);
            feedbackMapper.updateByPrimaryKey(feedback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Status("10000", "1");
    }

    @RequestMapping(value = "/feedback/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> down(HttpServletRequest request) {
        String id = request.getParameter("id");
        Feedback feedback = feedbackMapper.selectByPrimaryKey(Integer.valueOf(id));
        ResponseEntity<byte[]> entity = null;
        try {
            // 获取文件名
            File tmp = new File(feedback.getUploadfilepath());
            String fileName = tmp.getName();
            byte[] body = FileUtils.readFileToByteArray(tmp);

            //设置Http协议响应头信息
            HttpHeaders headers = new HttpHeaders();
            //设置下戟内容为字节流
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            fileName = URLEncoder.encode(fileName, "utf-8");
            //设置下载方式为附件另存为方式
            headers.setContentDispositionFormData("attachment", fileName);
            //设置响应代码为正常
            HttpStatus statusCode = HttpStatus.OK;
            entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return entity;
    }
}