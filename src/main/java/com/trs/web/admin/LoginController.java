package com.trs.web.admin;

import com.itextpdf.xmp.impl.Base64;
import com.trs.core.annotations.Log;
import com.trs.core.util.Config;
import com.trs.model.Permission;
import com.trs.model.User;
import com.trs.service.PermissionService;
import com.trs.service.RoleService;
import com.trs.service.UserService;
import org.nlpcn.commons.lang.util.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created on 2017/2/28.
 */
@Controller
public class LoginController extends AbstractAdminController {

    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RoleService roleService;

    private final static long cookieMaxAge = 7200;

    private final static String webKey = "123456";

    private final static String AdmincookieName = "adminUserName";

    //在线状态
    private static final String ONLINESTATUS = Config.getKey("online");

    /**
     * 将传递进来的字节数组转换成十六进制的字符串形式并返回
     *
     * @param buffer
     * @return
     */
    private static String toHex(byte[] buffer) {
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
        }
        return sb.toString();
    }


    /**
     * 获取Cookie组合字符串的MD5码的字符串
     *
     * @param value
     * @return
     */
    public static String getMD5(String value) {
        String result = null;
        try {
            byte[] valueByte = value.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(valueByte);
            result = toHex(md.digest());
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
        }
        return result;
    }


    /**
     * 登录页面跳转
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() throws Exception {
        ModelAndView modelAndView = new ModelAndView("admin/login");
        return modelAndView;
    }


    /**
     * 执行登录操作
     *
     * @param request
     * @param session
     * @return
     */
    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_LOGIN, targetType = com.trs.model.Log.LOG_TARGETTYPE_USERMANAGER, description = "用户登录")
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map submit(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        // 定义map用于保存返回值
        Map<String, Integer> result = new HashMap<>();

        // 接受登录参数
        String userName = request.getParameter("username");
        String password = request.getParameter("pwd");

        // 定义map保存登录参数
        Map<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("pwd", MD5.code(password));
        User user = userService.findUserByNameAndPwd(map);

        if (result.size() != 0) {
            return result;
        } else {


            // 检查登录参数是否正确
            if (null != user) {
                if (user.getStatus() == 1) {
                    result.put("code", 2);
                    return result;
                }
                int roleId = user.getRoleId();
//                String url=(String )request.getSession().getAttribute("url");
//                if(url==null){
//                    result.put("code", 0);
//                }
                result.put("code", 0);
                request.getSession().setAttribute("adminUserName", userName);
                User users = userService.findUserByName(userName);
                request.getSession().setAttribute("userid", users.getId());

                List<Permission> list = roleService.findPermissionListByRoleId(roleId);
                // 设置系统左侧导航栏菜单
                systemMenu(session, list);
                //设置session
                long validTime = System.currentTimeMillis() + (cookieMaxAge * 5000);
                //MD5加密用户详细信息
                String cookieValueWithMd5 = getMD5(userName + ":" + MD5.code(password) + ":" + validTime + ":" + webKey);

                //将要被保存的完整的Cookie值
                String cookieValue = userName + ":" + validTime + ":" + cookieValueWithMd5;
                //再一次对Cookie的值进行BASE64编码

                String cookieValueBase64 = new String(Base64.encode(cookieValue.getBytes()));
                //开始保存Cookie
                Cookie cookie = new Cookie(AdmincookieName, cookieValueBase64);
                //存两年(这个值应该大于或等于validTime)
                cookie.setMaxAge(60 * 60 * 24);

                //cookie有效路径是网站根目录
                cookie.setPath("/");

                //向客户端写入
                response.addCookie(cookie);
                return result;
            }
        }


        result.put("code", 1);
        return result;
    }

    /**
     * 设置系统左侧导航栏
     *
     * @param session
     */
    public void systemMenu(HttpSession session, List list) {
        Map<String, List<Permission>> result = new LinkedHashMap<>();
        for (Iterator<Permission> iterator = list.iterator(); iterator.hasNext(); ) {
            Permission permission = iterator.next();
            String pName = permission.getParentName();
            List fListResult_ = result.get(pName);
            if (fListResult_ != null) {
                if (ONLINESTATUS.equals("OFF")) {
                    if (!(permission.getName().equals("机构用户"))) {
                        fListResult_.add(permission);
                        result.put(pName, fListResult_);
                    }
                }else {
                    fListResult_.add(permission);
                    result.put(pName, fListResult_);
                }
            } else {
                List fListResult = new ArrayList();
                if (ONLINESTATUS.equals("OFF")) {
                    if (!(permission.getName().equals("机构用户"))) {
                        fListResult.add(permission);
                        result.put(pName, fListResult);
                    }
                }else {
                    fListResult.add(permission);
                    result.put(pName, fListResult);
                }

            }
        }

        session.setAttribute("nav", result);
    }


    /**
     * 后台用户注销
     *
     * @return
     */
    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_LOGOUT, targetType = com.trs.model.Log.LOG_TARGETTYPE_USERMANAGER, description = "用户退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        session.invalidate();
        modelAndView.setViewName("redirect:/admin/login");
        return modelAndView;
    }


    /**
     * 清除cookie
     *
     * @param response
     */
    public static void clearCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(AdmincookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 检测cookie
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public boolean readCookieAndAdminLogon(UserService userService, RoleService roleService, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //根据cookieName取cookieValue
        Cookie cookies[] = request.getCookies();
        String cookieValue = null;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (AdmincookieName.equals(cookies[i].getName())) {
                    cookieValue = cookies[i].getValue();
                    break;
                }
            }
        }
        //如果cookieValue为空,返回,
        if (cookieValue == null) {
            return false;
        }
        //如果cookieValue不为空,才执行下面的代码
        //先得到的CookieValue进行Base64解码
        String cookieValueAfterDecode = new String(Base64.decode(cookieValue));
        //对解码后的值进行分拆,得到一个数组,如果数组长度不为3,就是非法登陆
        String cookieValues[] = cookieValueAfterDecode.split(":");
        if (cookieValues.length != 3) {
            /*response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("你正在用非正常方式进入本站...");
            out.close();*/
            return false;
        }
        //判断是否在有效期内,过期就删除Cookie
        long validTimeInCookie = new Long(cookieValues[1]);
        if (validTimeInCookie < System.currentTimeMillis()) {
            //删除Cookie
            clearCookie(response);
           /* response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("");//你的Cookie已经失效,请重新登陆
            out.close();*/
            return false;
        }
        //取出cookie中的用户名,并到数据库中检查这个用户名,
        String username = cookieValues[0];
        //根据用户名到数据库中检查用户是否存在
        User user = userService.findUserByName(username);

        //如果user返回不为空,就取出密码,使用用户名+密码+有效时间+ webSiteKey进行MD5加密
        if (user != null) {
            String md5ValueInCookie = cookieValues[2];
            String md5ValueFromUser = getMD5(user.getUsername() + ":" + user.getPwd()
                    + ":" + validTimeInCookie + ":" + webKey);
            //将结果与Cookie中的MD5码相比较,如果相同,写入Session,自动登陆成功,并继续用户请求
            if (md5ValueFromUser.equals(md5ValueInCookie)) {
                HttpSession session = request.getSession();
                session.setAttribute("adminUserName", user.getUsername());
                // 查询用户机构角色id
                /*Map<String,Object> map = new HashMap<>();
                map.put("userName", user.getUsername());
                map.put("pwd", user.getPwd());*/
                /*User user = userService.findUserByNameAndPwd(map);*/
                List<Permission> list = roleService.findPermissionListByRoleId(user.getRoleId());
                systemMenu(session, list);

            }
        } else {
            //返回为空执行
            /*response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("cookie验证错误！");
            out.close();*/
            return false;
        }
        return true;
    }


}
