package com.trs.web.client;

import com.trs.core.util.VerifyCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created by Rain on 2016/7/17.
 */
@Controller
public class VerifyCodeController {

    /**
     * 验证码生成
     * @param request
     * @param response
     */
    @RequestMapping(value = "/imagecode", method = RequestMethod.GET)
    public void imageCode(HttpServletRequest request, HttpServletResponse response) {
        String createTypeFlag = request.getParameter("createTypeFlag");//接收客户端传递的createTypeFlag标识
        //1.在内存中创建一张图片
        BufferedImage bi = new BufferedImage(VerifyCodeUtil.WIDTH, VerifyCodeUtil.HEIGHT, BufferedImage.TYPE_INT_RGB);
        //2.得到图片
        Graphics g = bi.getGraphics();
        //3.设置图片的背影色
        VerifyCodeUtil.setBackGround(g);
        //4.设置图片的边框
        VerifyCodeUtil.setBorder(g);
        //5.在图片上画干扰线
        VerifyCodeUtil.drawRandomLine(g);
        //6.写在图片上随机数
        //String random = drawRandomNum((Graphics2D) g,"ch");//生成中文验证码图片
        //String random = drawRandomNum((Graphics2D) g,"nl");//生成数字和字母组合的验证码图片
        //String random = drawRandomNum((Graphics2D) g,"n");//生成纯数字的验证码图片
        //String random = drawRandomNum((Graphics2D) g,"l");//生成纯字母的验证码图片
        String
                random = VerifyCodeUtil.drawRandomNum((Graphics2D) g, createTypeFlag);//根据客户端传递的createTypeFlag标识生成验证码图片
        //7.将随机数存在session中
        request.getSession().setAttribute("validateCode", random);
        //8.设置响应头通知浏览器以图片的形式打开
        response.setContentType("image/jpeg");//等同于response.setHeader("Content-Type", "image/jpeg");
        //9.设置响应头控制浏览器不要缓存
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        //10.将图片写给浏览器
        try {
            ImageIO.write(bi, "jpg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证  验证码
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value="/validateCode",method=RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        String validateC = (String) request.getSession().getAttribute("validateCode");
        String veryCode = request.getParameter("c");
        PrintWriter out = response.getWriter();
        if(veryCode==null||"".equals(veryCode)){
            out.println("验证码为空");
        }else{
            if(validateC.equalsIgnoreCase(veryCode)){
                out.println("1");
            }else{
                out.println("0");
            }
        }
        out.flush();
        out.close();
    }
}
