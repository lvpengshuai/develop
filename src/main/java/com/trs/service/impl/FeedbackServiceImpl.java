package com.trs.service.impl;


import com.trs.core.util.FileUtil;
import com.trs.core.util.IPUtil;
import com.trs.core.util.Status;
import com.trs.mapper.FeedbackMapper;
import com.trs.model.Feedback;
import com.trs.service.FeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Created by pz on 2017/3/10.
 */
@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    FeedbackMapper feedbackMapper;

    public Status saveFeedback(HttpServletRequest request) {
        Feedback feedback = new Feedback();
        Status status = new Status(Status.SUCCESS_CODE, "反馈成功");
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iters = multipartHttpServletRequest.getFileNames();
            while (iters.hasNext()) {
                MultipartFile file = multipartHttpServletRequest.getFile(iters.next());
                if (file != null) {
                    String filename = file.getOriginalFilename();
                    feedback.setFilename(filename);
                    if (!"".equals(filename)) {
                        String newFilename = UUID.randomUUID().toString().replaceAll("-", "") + FileUtil.getExtend(filename);
                        String uploadPath = FileUtil.getPathWithSystem(System.getProperty("java.io.tmpdir") + "/" + newFilename);
                        File localFile = new File(uploadPath);

                        if (!localFile.exists()) {
                            localFile.mkdirs();
                        }

                        try {
                            file.transferTo(localFile);
                            feedback.setUploadfilepath(uploadPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        try {
            String content = request.getParameter("username");
            String realName = request.getParameter("name");
            String phonenumber = request.getParameter("mobile");
            feedback.setContent(content);
            feedback.setRealname(realName);
            feedback.setIp(IPUtil.getIpAddr(request));
            feedback.setPhonenumber(phonenumber);
            feedback.setDate(new Date());
            feedback.setStatus(0);
            if (feedbackMapper.insert(feedback) != 1) {
                status.setCode(Status.ERROR_CODE);
                status.setMsg("反馈失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public Map findAll(Map map) {

        List<Feedback> knowledgeElementsList = feedbackMapper.findAll(map);
        int total = feedbackMapper.total(map);
        Map resultMap = new HashMap();

        resultMap.put("total", total);
        resultMap.put("rows", knowledgeElementsList);
        resultMap.put("pageSize", map.get("pageSize"));
        resultMap.put("currPage", map.get("currPage"));
        resultMap.put("search", map.get("search"));
        resultMap.put("sort", map.get("sort"));
        resultMap.put("order", map.get("order"));

        return resultMap;
    }


}