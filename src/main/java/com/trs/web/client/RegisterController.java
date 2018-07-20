package com.trs.web.client;

import com.trs.core.util.StringUtil;
import com.trs.model.Member;
import com.trs.service.MemberService;
import org.apache.commons.io.FileUtils;
import org.nlpcn.commons.lang.util.MD5;
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
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/4/5.
 */
@Controller
public class RegisterController {

    @Resource
    private MemberService memberService;

    /**
     * 注册页面
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(HttpSession session) {
        /*
            * 检测是否登录，如果登录则跳转到首页
            * 如果没登录则跳转到注册页
         */
        String userName = (String) session.getAttribute("userName");
        ModelAndView modelAndView = new ModelAndView("/client/register/register");

        if (!StringUtil.isEmpty(userName)) {
            modelAndView.setViewName("client/index");
            return modelAndView;
        }
        return modelAndView;
    }

    /**
     * IDS注册用户
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Map submit(Member member, HttpSession session, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        /*
            * 获取用户名称。检测是否名称被占用,如果被占用返回code=0
            * 获取验证码信息，检测验证码，如果不匹配返回code=-1
            * 获取表单信息进行注册业务，如果成功返回200，并跳转到登录界面
            * 执行本地或者ISD注册方法进行添加会员
         */

        // 检测用户名称是否被占用
        String username = member.getUsername();
        Member memberByUserName = memberService.findMemberByUserName(username);
        if (null != memberByUserName) {
            result.put("code", 0);
            result.put("msg", "账号被占用，请重新填写");
            return result;
        }

        // 检测邮箱是否被占用
        List<Member> memberByEmail = memberService.findMemberByEmail(member.getEmail());
        if (memberByEmail.size() != 0) {
            result.put("code", -2);
            result.put("msg", "邮箱被占用，请重新填写");
            return result;
        }

        // 检测验证码是否正确
        String vaCode = request.getParameter("code");
        String checkcode = (String) session.getAttribute("checkcode");
        if (!vaCode.equalsIgnoreCase(checkcode)) {
            result.put("code", -1);
            result.put("msg", "验证码错误");
            return result;
        }

        // 执行IDS注册方法
        //Map<String, Object> resultMap = memberService.idsRegister(member);

        // 执行本地注册方法
        member.setRoleId(0);
        member.setOrganizaId(0);
        member.setStatus(0);
        member.setPwd(MD5.code(member.getPwd()));
        member.setGmtCreate(new Date());
        memberService.add(member);
        result.put("code", 1);
        return result;
    }

    /**
     * 协议下载
     *
     * @return
     */
    @RequestMapping(value = "/agreement", method = RequestMethod.GET)
    public ResponseEntity<byte[]> agreement(HttpServletRequest request) {
        /*获取文件存放的目录*/
        String filePath = request.getSession().getServletContext().getRealPath("/static/client/json/");

        ResponseEntity<byte[]> entity = null;
        try {
             /*获取文件名*/
            String fileName = new String("化工知识服务平台用户服务协议.docx".getBytes("utf-8"), "utf-8");
            /*通过FileUtils工具把指定文件转换成字节数组*/
            File file = new File(filePath, fileName);
            byte[] body = FileUtils.readFileToByteArray(file);
            /*设置Http协议响应头信息*/
            HttpHeaders headers = new HttpHeaders();
            /*设置下戟内容为字节流*/
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            fileName = URLEncoder.encode(fileName, "utf-8");
            /*设置下载方式为附件另存为方式*/
            headers.setContentDispositionFormData("attachment", fileName);
            /*设置响应代码为正常*/
            HttpStatus statusCode = HttpStatus.OK;
            entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
}
