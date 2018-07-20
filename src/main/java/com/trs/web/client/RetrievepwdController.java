package com.trs.web.client;

import com.trs.core.util.Config;
import com.trs.core.util.SendCloud44;
import com.trs.core.util.StringUtil;
import com.trs.model.Member;
import com.trs.service.MemberService;
import org.nlpcn.commons.lang.util.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zly on 2017-3-30.
 */
@Controller
@RequestMapping(value = "/retrievepwd")
public class RetrievepwdController {

    @Resource
    private MemberService memberService;

    /**
     * 跳转找回页面
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public ModelAndView index(String username) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/client/retrievepwd/retrievepwd_1");
        return modelAndView;
    }

    /**
     * 发送找回邮件
     *
     * @param email
     * @param checkCode
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sendemail", method = RequestMethod.POST)
    public Map email(String email, String checkCode, HttpSession session) {

        /*
            * 定义map封装接收的参数，进行传参并保存返回值
            * 检测验证码是否正确，如果不正确则返回状态码2并提示验证码错误
            * 检测邮箱是否被注册过，如果未被使用返回状态码1并提示邮箱未被使用
            * 将验证码存入到用户中用于后续的验证步骤
            * 处理需要发送邮件的参数以及验证码的MD5加密，并捕获异常。
         */

        // 定义map用于传参以及保存返回值
        Map resultMap = new HashMap();
        checkCode = checkCode.toLowerCase();
        String sessionCheckcode = (String) session.getAttribute("checkcode");

        // 检测验证码
        if ("resend".equals(checkCode)) {
            checkCode = sessionCheckcode;
        } else {
            if (!sessionCheckcode.equalsIgnoreCase(checkCode)) {
                resultMap.put("status", "2");
                resultMap.put("msg", "验证码错误");
                return resultMap;
            }
        }

        // 检测邮箱是否被使用
        List<Member> memberByEmail = memberService.findMemberByEmail(email);
        if (memberByEmail.size() == 0) {
            resultMap.put("status", "1");
            resultMap.put("msg", "该邮箱未被注册。");
            return resultMap;
        }

        // 将验证码存入用户信息中
        memberByEmail.get(0).setUuCode(MD5.code(checkCode));
        memberService.updateInfo(memberByEmail.get(0));

        try {
            // 封装数据进行发送邮件
            resultMap.put("to", email);
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
            String format = fm.format(new Date());
            Date parse = fm.parse(format);
            long time = parse.getTime();
            resultMap.put("url", Config.getKey("website") + "/retrievepwd/resetpwd?email=" + email + "&code=" + MD5.code(checkCode) + "&&timeOut=" + time);
            SendCloud44.send_template(resultMap, Config.getKey("template.retrievepwd"));
            resultMap.put("status", "0");
            resultMap.put("email", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     *找回密码
     * @param email
     * @return
     */
    @RequestMapping(value = "/confirmemail", method = RequestMethod.GET)
    public ModelAndView to(String email) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("client/retrievepwd/retrievepwd_2");
        modelAndView.addObject("email", email);
        return modelAndView;
    }

    /**
     * 邮件跳转链接
     *
     * @param email
     * @param code
     * @param session
     * @return
     */
    @RequestMapping(value = "/resetpwd", method = RequestMethod.GET)
    public ModelAndView resetpwd(String email, String code, HttpSession session, String timeOut) {
        /*
            * 定义模型视图对象用于将数据封装到request域
            * 检测验证码以及时间是否完整，如果无效则返回404界面
            * 通过email获取用户名称用于向ids中更新密码，如果用户不存在则返回404界面
            * 检测验证码是否正确，如果不正确则返回404界面
            * 检测链接是否过期，如果过期则返回404界面，反之将数据封装并发送
         */

        // 定义模型视图对象
        ModelAndView modelAndView = new ModelAndView();

        // 通过email查询用户名称
        List<Member> memberByEmail = memberService.findMemberByEmail(email);
        if (memberByEmail.size() == 0) {
            modelAndView.setViewName("/client/error/404");
            return modelAndView;
        }

        // 检测验证码是否正确
        String uuCode = memberByEmail.get(0).getUuCode();
        if (!code.equals(uuCode)) {
            modelAndView.setViewName("/client/error/404");
            return modelAndView;
        }
        // 检测链接是否过期
        if (StringUtil.isEmpty(timeOut)) {
            modelAndView.setViewName("/client/error/404");
        }
        long l = Long.parseLong(timeOut);
        long l1 = System.currentTimeMillis();
        Long s = (l1 - l) / (1000 * 60);

        if (s > 30) {
            modelAndView.setViewName("/client/error/404");
        } else {
            modelAndView.setViewName("/client/retrievepwd/retrievepwd_3");
            modelAndView.addObject("email", email);
            modelAndView.addObject("userName", memberByEmail.get(0).getUsername());
            modelAndView.addObject("userName", memberByEmail.get(0).getUsername());
        }

        return modelAndView;
    }

    /**
     * 重置密码操作
     *
     * @param email
     * @param password
     * @param password_again
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pwdreset", method = RequestMethod.POST)
    public Map submit(String userName, String email, String password, String password_again) {
        Map<String, Object> resultMap = new HashMap();

        // 检测用户是否存在
        Member memberByUserName = memberService.findMemberByUserName(userName);
        String oldPwd = memberByUserName.getPwd();
        if (null == memberByUserName) {
            resultMap.put("status", "1");
            resultMap.put("msg", "密码重置失败，请稍后再试");
            return resultMap;
        }
        // 检测邮箱是否正确
        if (!memberByUserName.getEmail().equals(email)) {
            resultMap.put("status", "1");
            resultMap.put("msg", "密码重置失败，请稍后再试");
            return resultMap;
        }


        // 封装新密码
        memberByUserName.setPwd(MD5.code(password));
        memberByUserName.setUuCode("");
        memberService.updateInfo(memberByUserName);

       /* // 删除ids用户
        resultMap = memberService.idsDelete(userName);
        // 检测是否删除成功
        String status = (String) resultMap.get("status");
        if(!status.equals("0")){
            resultMap.put("status","1");
            resultMap.put("msg", "密码重置失败，请稍后再试");
            memberByUserName.setPwd(oldPwd);
            memberService.updateInfo(memberByUserName);
            return resultMap;
        }

        // 注册ids用户
        memberByUserName.setPwd(password);
        resultMap = memberService.idsRegister(memberByUserName);*/

        resultMap.put("status", "0");
        resultMap.put("msg", "成功找回密码");
        return resultMap;
    }

}
