package com.trs.web.client;

import com.itextpdf.xmp.impl.Base64;
import com.trs.core.annotations.Log;
import com.trs.core.util.Config;
import com.trs.core.util.HttpClientUtil;
import com.trs.core.util.IPUtil;
import com.trs.core.util.Util;
import com.trs.model.Greeting;
import com.trs.model.Member;
import com.trs.model.Organize;
import com.trs.service.MemberOnlineService;
import com.trs.service.MemberService;
import com.trs.service.OrganizeOnlineService;
import com.trs.service.OrganizeService;
import net.sf.json.JSONObject;
import org.nlpcn.commons.lang.util.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/3/30.
 */
@Controller
public class ClientLoginController {

    //在线状态
    private final static String onlineStatus = Config.getKey("online");
    //在线状态
    private static final String ONLINESTATUS = Config.getKey("online");

    //请求url
    private static final String USERURL = Config.getKey("zas.userUrl");

    private final static String webKey = "123456";
    private final static long cookieMaxAge = 60 * 60 * 24 * 3;
    private final static String GETLOGIN = Config.getKey("zas.getLogin");
    //登陆接口
    private final static String LOGINURL = Config.getKey("zas.doLogin");
    //获取token接口
    private final static String GETTOKEN = Config.getKey("zas.getTonken");
    //获取用户信息接口
    private final static String GETUSERINFO = Config.getKey("zas.getUserInfo");
    //保存cookie时的cookieName
    private final static String cookieDomainName = "laizhi";

    @Resource
    private MemberService memberService;
    @Resource
    private MemberOnlineService memberOnlineService;
    @Resource
    private OrganizeOnlineService organizeOnlineService;
    @Resource
    private OrganizeService organizeService;


    /**
     * 登录页面
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        // 获取登录参数，回传数据
        String userNmae = request.getParameter("userName");
        String password = request.getParameter("password");
        String info = request.getParameter("info");

        // 检测session是否存在用户信息，如果存在直接跳转到目标页面
        String username = (String) session.getAttribute("userName");
        if (username != null) {
            modelAndView.setViewName("redirect:/index");
        } else {
            modelAndView.addObject("userName", userNmae);
            modelAndView.addObject("password", password);
            modelAndView.addObject("info", info);
            modelAndView.setViewName("/client/login/login");
        }
        modelAndView.addObject("online", onlineStatus);
        return modelAndView;
    }

    /**
     * 在线登录
     *
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/OnlineLogin", method = RequestMethod.POST)
//    public Map OnlineLogin(HttpServletRequest request,HttpSession session) throws MalformedURLException, URISyntaxException {
//        // ModelAndView modelAndView = new ModelAndView();
//        /*
//        获取用户名密码
//        &AuthCode="+code+"
//         */
//        //?client_id=526157d544b048a3ab5acf3d5b15efd6&response_type=code&scope=user_info
//        Map result = new HashMap();
//        String userName = request.getParameter("userName");
//        String password = request.getParameter("password");
//        String code = request.getParameter("code");
//        Map login_info = new HashMap();
//        login_info.put("client_id","526157d544b048a3ab5acf3d5b15efd6");
//        login_info.put("response_type","code");
//        login_info.put("scope","user_info");
//        login_info.put("LoginID",userName);
//        login_info.put("Password",password);
//        System.out.println(code);
//        //login_info.put("AuthCode",code);
//        login_info.put("ContentType","json");
//        login_info.put("redirect_uri","http://172.16.0.12:8080/login");
//        login_info.put("state","1");
//        //获取接口返回信息
//        Greeting content = HttpClientUtil.post(LOGINURL,login_info);
//        //接受接口信息
//        System.out.println(content.getContent());
//        JSONObject jasonObject = JSONObject.fromObject(content.getContent());
//        Map map = (Map) jasonObject;
//        //获取接口详细信息
//        JSONObject info = JSONObject.fromObject(map.get("Report"));
//        Map detainfo = (Map) info;
//        //接受返回信息参数
//        if (detainfo.get("success").equals(true)) {
//            //获取url
//            String user_code = (String) detainfo.get("redirectURL");
//            //切割获取code和mark
//            String codeandmark = user_code.substring(user_code.indexOf("?") + 1);
//            //获取code
//            String codes = codeandmark.substring(codeandmark.indexOf("=") + 1, codeandmark.indexOf("&") + 1);
//            //获取mark
//            String marks = codeandmark.substring(codes.lastIndexOf("=") + 1, codeandmark.lastIndexOf("&"));
//            //截取code
//            String code_info = codes.substring(0, codes.lastIndexOf("&"));
//            //截取mark
//            String mark_info = marks.substring(marks.lastIndexOf("=") + 1);
//            System.out.println(code_info + "===" + mark_info);
//            //获取token
//            Greeting tonkenContent = HttpClientUtil.get(GETTOKEN+"&code="+code_info+"&grant_type=authorization_code&redirect_uri=http://172.16.0.12:8080/login");
//            String tokenString = (String) tonkenContent.getContent();
//            //转换token为map
//            JSONObject tonkenJson = JSONObject.fromObject(tokenString);
//            Map tonken_info = (Map)tonkenJson;
//            //获取用户信息
//            if (tonken_info.get("expires_in").equals(600)){
//                //获取access_token
//                String access_token = (String) tonken_info.get("access_token");
//                Map user_map = new HashMap();
//                user_map.put("client_id","526157d544b048a3ab5acf3d5b15efd6");
//                user_map.put("method","user_info");
//                user_map.put("token",access_token);
//                user_map.put("mark",mark_info);
//                Greeting userContent = HttpClientUtil.post(GETUSERINFO,user_map);
//
//                String user=(String) userContent.getContent();
//                JSONObject userJSON = JSONObject.fromObject(user);
//                Map usermap = (Map)userJSON;
//                //获取用户详细信息
//                JSONObject userDataJSON = JSONObject.fromObject(usermap.get("data"));
//                Map userdata = (Map)userDataJSON;
//                System.out.println(userdata.get("UserName")+"==="+userdata.get("UserBranch"));
//                session.setAttribute("userName", userdata.get("UserName"));
//                session.setAttribute("organization", userdata.get("UserBranch"));
//                result.put("code", 1);
//               // result.put("userName", userdata.get("UserName"));
//              //  result.put("organization",  userdata.get("UserBranch"));
//                return result;
//
//            }
//        } else {
//            result.put("code", detainfo.get("message"));
//            System.out.println(detainfo.get("message"));
//            return result;
//        }
//        System.out.println();
//
//
//        // String S = (String) report.get("success");
//
//        //  System.out.println(S);
//
//        return result;
//    }


    /**
     * ZAS回调
     *
     * @throws java.io.IOException
     */
    //  @Priv(login = false)
    @RequestMapping("member/SSOBack")
    public String SSOBack(HttpServletRequest request) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader bufferedReader = null;
        String content = "";
        try {
            bufferedReader = request.getReader();
            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                sb.append(charBuffer, 0, bytesRead);
            }

        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        content = sb.toString();
        System.out.println(content);
        return null;
    }


    /**
     * 登录操作
     *
     * @param request
     * @return
     */
    @Log(targetType = com.trs.model.Log.LOG_TARGETTYPE_MEMBERMANAGER, operationType = com.trs.model.Log.LOG_OPERTYPE_LOGIN, description = "前台会员登录", isForeground = "0")
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map submit(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, URISyntaxException, ParseException {
        /*
            * 1、定义map对象用于保存状态码
            * 2、接受登录参数（用户名，密码，验证码）
            * 3、封装登录参数
            * 4、验证用户是否存在（如果用户名不存在，提示该用户不存在）
            * 5、验证用户名和密码（如果出现验证不通过，提示用户和密码错误）
            * 6、验证验证码是否正确（如果错误，提示验证码错误）
            * 7、验证用户状态（如果用户被禁用，提示用户被禁用，请联系管理员）
            * 8、如果下次自动登录被选中则执行添加cookie方法
        */
        Map result = new HashMap();
        response.setContentType("text/html;charset=utf-8");
        System.out.println(onlineStatus);
        HttpSession session = request.getSession();
        String flag = request.getParameter("flag");
        String iiiPP = request.getParameter("iiiPP");
        String retUrl = request.getParameter("retUrl");
        String url = request.getParameter("url");
        String veryCode = request.getParameter("c");
        String validateC = (String) request.getSession().getAttribute("validateCode");
        if(veryCode==null||"".equals(veryCode)){
            result.put("msg","验证码不能为空");
            result.put("code",3);
        }else{
            if(validateC.equalsIgnoreCase(veryCode)){
                if (onlineStatus.equals("ON")) {

                    String userName = request.getParameter("userName");
                    String password = request.getParameter("password");
                    String code = request.getParameter("code");
                    Map login_info = new HashMap();
                    login_info.put("client_id", "526157d544b048a3ab5acf3d5b15efd6");
                    login_info.put("response_type", "code");
                    login_info.put("scope", "user_info");
                    login_info.put("LoginID", userName);
                    login_info.put("Password", password);
                    System.out.println(code);
                    //login_info.put("AuthCode",code);
                    login_info.put("ContentType", "json");
                    login_info.put("redirect_uri", GETLOGIN);
                    login_info.put("state", "1");
                    //获取接口返回信息
                    Greeting content = HttpClientUtil.post(LOGINURL, login_info);
                    System.out.println(content);
                    //接受接口信息
                    System.out.println(content.getStatus());
                    if (content.getStatus() != 1) {
                        result.put("msg", "网络异常请联系管理员");
                        return result;
                    }
                    JSONObject jasonObject = JSONObject.fromObject(content.getContent());
                    Map map = (Map) jasonObject;
                    //获取接口详细信息
                    JSONObject info = JSONObject.fromObject(map.get("Report"));
                    Map detainfo = (Map) info;
                    //接受返回信息参数
                    if (detainfo.get("success").equals(true)) {
                        //获取url
                        String user_code = (String) detainfo.get("redirectURL");
                        //切割获取code和mark
                        String codeandmark = user_code.substring(user_code.indexOf("?") + 1);
                        //获取code
                        String codes = codeandmark.substring(codeandmark.indexOf("=") + 1, codeandmark.indexOf("&") + 1);
                        //获取mark
                        String marks = codeandmark.substring(codes.lastIndexOf("=") + 1, codeandmark.lastIndexOf("&"));
                        //截取code
                        String code_info = codes.substring(0, codes.lastIndexOf("&"));
                        //截取mark
                        String mark_info = marks.substring(marks.lastIndexOf("=") + 1);
                        //获取token
                        Map tokenMap = new HashMap();
                        tokenMap.put("code", code_info);
                        tokenMap.put("grant_type", "authorization_code");
                        tokenMap.put("redirect_uri", "http://172.16.0.12:8080/login");
                        Greeting tonkenContent = HttpClientUtil.post(GETTOKEN, tokenMap);
                        System.out.println(tonkenContent);
                        Util.log("调用泽元接口", "LoginClientOnline", 0);
                        Util.log(tonkenContent, "LoginClientOnline", 0);
                        String tokenString = (String) tonkenContent.getContent();
                        //转换token为map
                        JSONObject tonkenJson = JSONObject.fromObject(tokenString);
                        Map tonken_info = (Map) tonkenJson;
                        //获取用户信息
                        if (tonken_info.get("expires_in").equals(600)) {
                            //获取access_token
                            String access_token = (String) tonken_info.get("access_token");
                            Map user_map = new HashMap();
                            user_map.put("client_id", "526157d544b048a3ab5acf3d5b15efd6");
                            user_map.put("method", "user_info");
                            user_map.put("token", access_token);
                            user_map.put("mark", mark_info);
                            Greeting userContent = HttpClientUtil.post(GETUSERINFO, user_map);
                            System.out.println(userContent);
                            String user = (String) userContent.getContent();
                            JSONObject userJSON = JSONObject.fromObject(user);
                            Map usermap = (Map) userJSON;
                            //下次自动登录
                            Cookie cookie = request.getCookies()[0];//获取cookie
                            cookie.setMaxAge(0);//让cookie过期
                            if ("0".equals(flag)) {
                                //cookie的有效期
                                long validTime = System.currentTimeMillis() + (cookieMaxAge);
                                //MD5加密用户详细信息
                                String cookieValueWithMd5 = getMD5(userName + ":" + MD5.code(password) + ":" + validTime + ":" + webKey);
                                //将要被保存的完整的Cookie值
                                String cookieValue = userName + ":" + validTime + ":" + cookieValueWithMd5;
                                //再一次对Cookie的值进行BASE64编码

                                String cookieValueBase64 = new String(Base64.encode(cookieValue.getBytes()));
                                //开始保存Cookie
                                cookie = new Cookie(cookieDomainName, cookieValueBase64);
                                //存两年(这个值应该大于或等于validTime)
                                cookie.setMaxAge(60 * 60 * 24 * 3);

                                //cookie有效路径是网站根目录
                                cookie.setPath("/");

                                //向客户端写入
                                response.addCookie(cookie);
                            }
                            //获取用户详细信息
                            JSONObject userDataJSON = JSONObject.fromObject(usermap.get("data"));
                            //创建用户对象
                            Member member = new Member();
                            //转换接口返回信息
                            Map userdata = (Map) userDataJSON;
                            System.out.println(userdata);
                            System.out.println(userdata.get("UserName") + "===" + userdata.get("UserBranch"));
                            //查找本地是否存在接口用户
                            Member memberByUserName = memberOnlineService.findMemberByUserName(userName);
                            //admin用户返回UserName为空
                            if (userdata.get("UserName") == null) {
                                if (memberByUserName == null) {
                                    member.setUsername(userdata.get("LongID").toString());
                                    member.setRealname(userdata.get("RealName").toString());
                                    if (userdata.get("Gender").equals("M")) {
                                        member.setGender(1);
                                    } else {
                                        member.setGender(2);
                                    }
                                    member.setEmail(userdata.get("Email").toString());
                                    member.setTelephone(userdata.get("Mobile").toString());
                                    member.setAddress(((userdata.get("Address").toString() == null) ? "" : userdata.get("Address").toString()));
                                    member.setStatus(0);
                                    member.setRoleId(2);
                                    //获取机构id进行绑定
//                            Organize organize = organizeOnlineService.getOrganizeByBid(Integer.valueOf(userdata.get("BID").toString()));
//                            if (organize != null){
//                                member.setOrganizaId(organize.getId());
//                                member.setOrganization(organize.getName());
//                            }else  {
//                                Organize SysOrganized = new Organize();
//                                if (Integer.valueOf(userdata.get("BID").toString()) != 0) {
//                                    Map zasOrganized = (Map) userdata.get("UserBranch");
//                                    SysOrganized.setName(zasOrganized.get("BranchName").toString());
//                                }
//                            }
                                    //member.setUuCode(userdata.get());
                                    memberOnlineService.add(member);
                                }
                                session.setAttribute("userName", userdata.get("LongID"));
                            } else {
                                //接口返回为UserName
                                if (memberByUserName == null) {
                                    member.setUsername(userdata.get("UserName").toString());
                                    member.setPwd(getMD5(password));
                                    member.setRealname(userdata.get("RealName").toString());
                                    if (userdata.get("Gender").equals("M")) {
                                        member.setGender(1);
                                    } else {
                                        member.setGender(2);
                                    }
                                    member.setEmail(userdata.get("Email").toString());
                                    member.setTelephone(userdata.get("Mobile").toString());
                                    member.setAddress(userdata.get("Address").toString());
                                    member.setStatus(0);
                                    member.setRoleId(2);

                                    //member.setUuCode(userdata.get());
                                    memberOnlineService.add(member);
                                }
                                //获取机构id进行绑定
//                        Organize organize = organizeOnlineService.getOrganizeByBid(Integer.valueOf(userdata.get("BID").toString()));
//                        if (organize != null){
//                            member.setOrganizaId(organize.getId());
//                            member.setOrganization(organize.getName());
//                        }else  {
//                            Organize SysOrganized = new Organize();
//                            if (Integer.valueOf(userdata.get("BID").toString()) != 0) {
//                                Map zasOrganized = (Map) userdata.get("UserBranch");
//                                SysOrganized.setName(zasOrganized.get("BranchName").toString());
//                                //创建日期
//                                Long addTime = Long.valueOf(zasOrganized.get("AddDate").toString());
//                                SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
//                                String d = format.format(addTime);
//                                Date createTime = format.parse(d);
//                                SysOrganized.setGmtCreate(createTime);
//                                //最后修改日期
//                                Long modiTime = Long.valueOf(zasOrganized.get("AddDate").toString());
//                                SimpleDateFormat format1 =  new SimpleDateFormat("yyyy-MM-dd");
//                                String d1 = format1.format(modiTime);
//                                Date modisTime = format.parse(d1);
//                                SysOrganized.setGmtModified(modisTime);
//                                //状态
//                                SysOrganized.setStatus(0);
//                                //最大在线数
//                                SysOrganized.setMaxOnline(Integer.valueOf(zasOrganized.get("MaxOnline").toString()));
//                                //code
//                                SysOrganized.setCode(zasOrganized.get("BranchCode").toString());
//                                //bid
//                                SysOrganized.setBid(userdata.get("BID").toString());
//                                SysOrganized.setFile("0");
//                                //机构ip处理
//                                String endIpTemp = "";
//                                String startIpTemp = "";
//                                if (zasOrganized.get("IPRange") != null) {
//                                    String ip = zasOrganized.get("IPRange").toString();
//                                    System.out.println(ip);
//                                    for (String ipc:ip.split(",")) {
//                                        String endIp = ipc.substring(ipc.indexOf("-")+1);
//                                        String startIp = ipc.substring(0,ipc.indexOf("-"));
//                                        endIpTemp+=endIp+",";
//                                        startIpTemp+=startIp+",";
//                                    }
//                                }
//                                SysOrganized.setIpStart(startIpTemp);
//                                SysOrganized.setIpEnd(endIpTemp);
//                                //q权限设置
//                                SysOrganized.setRoleId(18);
//                                Map organizeMap = new HashMap();
//                                organizeMap.put("organize",SysOrganized);
//                                organizeOnlineService.addOrganizeUser(organizeMap);
//                            }
//                        }
                                session.setAttribute("userName", userdata.get("UserName"));
                            }
                            // 登陆绑定机构  根据IP
                            List<Organize> ip = organizeOnlineService.selectAllIP();
                            String ss = IPUtil.getIpAddr(request);
                            System.out.println("登录者ip=========>>>>"+ss);
                            if (ip.size() != 0) {
                                for (int ii = 0; ii < ip.size(); ii++) {
                                    //获取起始ip
                                    String startIp = ip.get(ii).getIpStart();
                                    //获取结束ip
                                    String endIp = ip.get(ii).getIpEnd();

                                    String id = ip.get(ii).getOrgName();
                                    if (startIp != null && endIp != null && startIp != "" && endIp != "") {
                                        //判断当前用户的ip是否在机构ip下
                                        if (IPUtil.getIp2long(startIp) <= IPUtil.getIp2long(IPUtil.getIpAddr(request)) && IPUtil.getIp2long(IPUtil.getIpAddr(request)) <= IPUtil.getIp2long(endIp)) {
                                            //  如果在某一个机构ip下，该用户不存在机构  则绑定机构
                                            Member mber = memberOnlineService.findMemberByUserName(userName);
                                            Util.log("返回得memeber内容", "LoginClientOnline", 0);
                                            Util.log(mber.toString(), "LoginClientOnline", 0);
                                            if (mber.getOrganization() == null && mber.getOrganizaId() == null) {
                                                List<Organize> org = organizeOnlineService.selectById(Integer.parseInt(id));
                                                //Organize org = (Organize) orgs.get("organize");
                                                if (org.size() != 0) {
                                                    Map m = new HashMap();
                                                    m.put("username", userName);
                                                    m.put("orgId", org.get(0).getId());
                                                    m.put("orgName", org.get(0).getName());
                                                    Map pm = memberOnlineService.updataOrganizeByUserName(m);

                                                    if (ONLINESTATUS.equals("ON")) {
                                                        Map zasApi = new HashMap();
                                                        Map setUserBid = new HashMap();
                                                        setUserBid.put("LoginID", userName);
                                                        String bid = organizeOnlineService.selectBid(Integer.valueOf(org.get(0).getId()));
                                                        setUserBid.put("BID", bid);
                                                        JSONObject userMap = JSONObject.fromObject(setUserBid);
                                                        zasApi.put("method", "ZAS.ModifyUser");
                                                        zasApi.put("username", "syncuser");
                                                        zasApi.put("password", "syncuser");
                                                        zasApi.put("params", userMap.toString());
                                                        //   Greeting contnet = HttpClientUtil.post(USERURL,zasApi);
                                                        Util.log("登陆：绑定机构调用泽元接口返回结果", "LoginClientOnline", 0);
                                                        //      Util.log(contnet,"LoginClientOnline" , 0);
                                                        //     if (contnet.getStatus() != 1){
                                                        System.out.println("在线：登陆绑定机构网络异常请联系管理员");
                                                        //     }
                                                        //    String contentInfo = (String) contnet.getContent();
                                                        //    JSONObject cotentMap = JSONObject.fromObject(contentInfo);
                                                    }

                                                }
                                            }

                                        }
                                    }
                                }
                            }
                            //session.setAttribute("organization", userdata.get("UserBranch"));
                            result.put("code", 1);
                            result.put("returnUrl", retUrl);
                            result.put("url", url);
                            result.put("userName", userdata.get("UserName"));
                            //result.put("organization",  userdata.get("UserBranch"));
                            return result;
                        }
                    } else {
                        result.put("msg", detainfo.get("message"));
                        System.out.println(detainfo.get("message"));
                        return result;
                    }
                    System.out.println();
                    // String S = (String) report.get("success");
                    //  System.out.println(S);
                    return result;
                } else {


                    // 定义map对象用于返回状态码

                    // 接受参数用户名和密码
                    String userName = request.getParameter("userName");
                    String password = request.getParameter("password");
                    String code = request.getParameter("code");

                    System.out.println(flag);
                    // 验证用户名是否存在
                    Member member = memberService.findMemberByUserName(userName);
                    // 用户所属机构单位
                    if (null == member) {
                        result.put("code", 0);
                        result.put("msg", "用户不存在");
                        return result;
                    }
                    String organization = member.getOrganization();
                    // 验证用户名和密码
                    Map<String, Object> map = new HashMap<>();
                    map.put("userName", userName);
                    map.put("password", password);
                    boolean memberByUserNameAndPwd = memberService.findMemberByUserNameAndPwd(map, session);
                    if (!memberByUserNameAndPwd) {
                        result.put("code", -1);
                        result.put("msg", "账号密码错误");
                        return result;
                    }

                    // 验证验证码
                    String checkCode = (String) session.getAttribute("validateCode");
                    if (!checkCode.equalsIgnoreCase(code)) {
                        result.put("code", -2);
                        result.put("msg", "验证码错误");
                        return result;
                    }

//        // 检验用户状态
//        if (member.getStatus() == 1) {
//            result.put("code", -3);
//            result.put("msg", "用户被禁用，请联系管理员");
//            return result;
//        }
                    //flag = "0";
                    // 登录成功
                    Object orgFlag = session.getAttribute("orgFlag");
                    Object gid = session.getAttribute("gid");
                    Object role_flag = session.getAttribute("role_flag");
                    session.invalidate();//清空session
                    session = request.getSession(true);
                    session.setAttribute("orgFlag", orgFlag);
                    session.setAttribute("gid", gid);
                    session.setAttribute("role_flag", role_flag);
                    Cookie cookie = request.getCookies()[0];//获取cookie
//        Cookie cookie = new Cookie("cookie", null);
                    cookie.setMaxAge(0);//让cookie过期
                    session.setAttribute("userName", userName);
                    session.setAttribute("organization", organization);

                    if ("0".equals(flag)) {
                        //cookie的有效期
                        long validTime = System.currentTimeMillis() + (cookieMaxAge);
                        //MD5加密用户详细信息
                        String cookieValueWithMd5 = getMD5(userName + ":" + MD5.code(password) + ":" + validTime + ":" + webKey);
                        //将要被保存的完整的Cookie值
                        String cookieValue = userName + ":" + validTime + ":" + cookieValueWithMd5;
                        //再一次对Cookie的值进行BASE64编码

                        String cookieValueBase64 = new String(Base64.encode(cookieValue.getBytes()));
                        //开始保存Cookie
                        cookie = new Cookie(cookieDomainName, cookieValueBase64);
//            Cookie cookie = new Cookie(cookieDomainName, cookieValueBase64);
                        //存两年(这个值应该大于或等于validTime)
                        cookie.setMaxAge(60 * 60 * 24 * 3);

                        //cookie有效路径是网站根目录
                        cookie.setPath("/");

                        //向客户端写入
                        response.addCookie(cookie);
                    }

                    result.put("code", 1);
                    result.put("returnUrl", retUrl);
                    result.put("url", url);
                    result.put("userName", userName);
                    result.put("organization", organization);
                    return result;
                }
            }else{
                result.put("msg","验证码错误");
                result.put("code",5);
            }
        }
        return result;
    }

    /**
     * 注销操作
     *
     * @param session
     * @return
     */
    @Log(targetType = com.trs.model.Log.LOG_TARGETTYPE_MEMBERMANAGER, operationType = com.trs.model.Log.LOG_OPERTYPE_LOGOUT, description = "前台会员退出", isForeground = "0")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session, HttpServletResponse response, HttpServletRequest request, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView("", modelMap);
        String url = request.getParameter("SKT");
        System.out.println("url======" + url);
        request.getSession().setAttribute("urlC", url);
        if ("usercenter".indexOf(url) != 0) {// 包含用户中心路径
            session.invalidate();
            clearCookie(response);
            modelAndView.addObject("urlG", url);
            modelAndView.setViewName("client/login/login");
        } else {// 不包含用户中心路径
            session.invalidate();
            clearCookie(response);
            modelAndView.addObject("urlG", url);
            modelAndView.setViewName("redirect:" + url + "");
        }
        modelAndView.addObject("online", onlineStatus);
        return modelAndView;
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
     * 清除cookie
     *
     * @param response
     */
    public static void clearCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieDomainName, null);
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
    public static boolean readCookieAndLogon(MemberService memberService, MemberOnlineService memberOnlineService, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, URISyntaxException {

        //根据cookieName取cookieValue
        Cookie cookies[] = request.getCookies();
        String cookieValue = null;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookieDomainName.equals(cookies[i].getName())) {
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
        Member user = null;
        System.out.println(username);
        //根据用户名到数据库中检查用户是否存在
        if (onlineStatus.equals("OFF")) {
            user = memberService.findMemberByUserName(username);
        } else {
            user = memberOnlineService.findMemberByUserName(username);
        }

        //如果user返回不为空,就取出密码,使用用户名+密码+有效时间+ webSiteKey进行MD5加密
        if (user != null) {

            if (onlineStatus.equals("OFF")) {
                String md5ValueInCookie = cookieValues[2];
                String md5ValueFromUser = getMD5(user.getUsername() + ":" + user.getPwd()
                        + ":" + validTimeInCookie + ":" + webKey);
                //将结果与Cookie中的MD5码相比较,如果相同,写入Session,自动登陆成功,并继续用户请求
                if (md5ValueFromUser.equals(md5ValueInCookie)) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userName", user.getUsername());
                }
            } else {
                String md5ValueInCookie = cookieValues[2];
                String md5ValueFromUser = getMD5(user.getUsername() + ":" + user.getPwd()
                        + ":" + validTimeInCookie + ":" + webKey);
                //将结果与Cookie中的MD5码相比较,如果相同,写入Session,自动登陆成功,并继续用户请求
                if (md5ValueFromUser.equals(md5ValueInCookie)) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userName", user.getUsername());
//                    String userName = user.getUsername();
//                    String password = user.getPwd();
//                    Map result = new HashMap();
//                    String code = request.getParameter("code");
//                    Map login_info = new HashMap();
//                    login_info.put("client_id", "526157d544b048a3ab5acf3d5b15efd6");
//                    login_info.put("response_type", "code");
//                    login_info.put("scope", "user_info");
//                    login_info.put("LoginID", userName);
//                    login_info.put("Password", password);
//                    System.out.println(code);
//                    //login_info.put("AuthCode",code);
//                    login_info.put("ContentType", "json");
//                    login_info.put("redirect_uri", "http://172.16.0.12:8080/login");
//                    login_info.put("state", "1");
//                    //获取接口返回信息
//                    Greeting content = HttpClientUtil.post(LOGINURL, login_info);
//                    //接受接口信息
//                    System.out.println(content.getStatus());
//                    JSONObject jasonObject = JSONObject.fromObject(content.getContent());
//                    Map map = (Map) jasonObject;
//                    //获取接口详细信息
//                    JSONObject info = JSONObject.fromObject(map.get("Report"));
//                    Map detainfo = (Map) info;
//                    //接受返回信息参数
//                    if (detainfo.get("success").equals(true)) {
//                        //获取url
//                        String user_code = (String) detainfo.get("redirectURL");
//                        //切割获取code和mark
//                        String codeandmark = user_code.substring(user_code.indexOf("?") + 1);
//                        //获取code
//                        String codes = codeandmark.substring(codeandmark.indexOf("=") + 1, codeandmark.indexOf("&") + 1);
//                        //获取mark
//                        String marks = codeandmark.substring(codes.lastIndexOf("=") + 1, codeandmark.lastIndexOf("&"));
//                        //截取code
//                        String code_info = codes.substring(0, codes.lastIndexOf("&"));
//                        //截取mark
//                        String mark_info = marks.substring(marks.lastIndexOf("=") + 1);
//                        System.out.println(code_info + "===" + mark_info);
//                        //获取token
//                        Greeting tonkenContent = HttpClientUtil.get(GETTOKEN + "&code=" + code_info + "&grant_type=authorization_code&redirect_uri=http://172.16.0.12:8080/login");
//                        String tokenString = (String) tonkenContent.getContent();
//                        //转换token为map
//                        JSONObject tonkenJson = JSONObject.fromObject(tokenString);
//                        Map tonken_info = (Map) tonkenJson;
//                        //获取用户信息
//                        if (tonken_info.get("expires_in").equals(600)) {
//                            //获取access_token
//                            String access_token = (String) tonken_info.get("access_token");
//                            Map user_map = new HashMap();
//                            user_map.put("client_id", "526157d544b048a3ab5acf3d5b15efd6");
//                            user_map.put("method", "user_info");
//                            user_map.put("token", access_token);
//                            user_map.put("mark", mark_info);
//                            Greeting userContent = HttpClientUtil.post(GETUSERINFO, user_map);
//
//                            String users = (String) userContent.getContent();
//                            JSONObject userJSON = JSONObject.fromObject(users);
//                            Map usermap = (Map) userJSON;
//                            //获取用户详细信息
//                            JSONObject userDataJSON = JSONObject.fromObject(usermap.get("data"));
//                            Map userdata = (Map) userDataJSON;
//                            System.out.println(userdata.get("UserName") + "===" + userdata.get("UserBranch"));
//                            session.setAttribute("userName", user.getUsername
                }
            }

        } else {
            return false;
        }
        return true;
    }

}
