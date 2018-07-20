package com.trs.web.client;

import com.trs.core.util.*;
import com.trs.core.util.Config;
import com.trs.model.*;
import com.trs.service.*;
import net.sf.json.JSONObject;
import org.nlpcn.commons.lang.util.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lcy on 2017/4/5.
 */
@Controller
public class UserCenterController {

    //在线状态
    private final static String onlineStatus = Config.getKey("online");

    private static final String USERURL =  Config.getKey("zas.userUrl");

    @Resource
    private MemberOnlineService memberOnlineService;

    @Resource
    private MemberService memberService;

    @Resource
    private MyCollectService myCollectService;

    @Resource
    private SearchHistoryService searchHistoryService;

    @Resource
    private UserCenterAdminService userCenterAdminService;


    @RequestMapping(value = "/user/code")
    public ModelAndView usercode() {
        System.out.println("666");
        return null;
    }

    /**
     * 个人中心首页
     *
     * @return
     */
    @RequestMapping(value = "/user/center", method = RequestMethod.GET)
    public ModelAndView show(HttpSession session, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/client/usercenter/usercenter");
        RequestUtils requestUtils = new RequestUtils(request);
        String flag = requestUtils.getParameterAsString("flag", "");
        if (StringUtil.isEmpty(flag)) {
            flag = "0";
        }
        Member member = memberService.findMemberByUserName((String) session.getAttribute("userName"));
        modelAndView.addObject("member", member);
        modelAndView.addObject("flag", flag);
        return modelAndView;
    }

    /**
     * 修改资料
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center", method = RequestMethod.PUT)
    public Map updateInfo(Member member, HttpServletRequest request, @RequestParam(value = "userName", required = false) String userName) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            /********************************格式化当前系统日期**********************************************/
            String format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            Date parse = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(format);

            /*
                * 设置会员修改参数并通过名称查询会员是否存在
                * 执行自身修改资料方法，并将数据保存到数据库中
                * 执行ids修改资料方法，将修改的信息推送到ids
                * 如果返回只为“0”修改成功，否则修改失败
             */
            member.setUsername(userName);
            Member memberByUserName = memberService.findMemberByUserName(userName);
            if (null == memberByUserName) {
                resultMap.put("status", 1);
                return resultMap;
            }

            // 检测邮箱是否被占用
            List<Member> memberByEmail = memberService.findMemberByEmail(member.getEmail());
            if (memberByEmail.size() != 0 && !member.getEmail().equals(memberByUserName.getEmail())) {
                resultMap.put("status", -2);
                resultMap.put("msg", "邮箱被占用，请重新填写");
                return resultMap;
            }

            member.setGmtCreate(memberByUserName.getGmtCreate());

            // 应用更新方法
            String email = member.getEmail();
            String education = member.getEducation();
            String telephone = member.getTelephone();
            String organization = member.getOrganization();
            String major = member.getMajor();
            String address = member.getAddress();
            String realname = member.getRealname();
            memberByUserName.setEmail(email);
            memberByUserName.setEducation(education);
            memberByUserName.setTelephone(telephone);
            memberByUserName.setOrganization(organization);
            memberByUserName.setMajor(major);
            memberByUserName.setAddress(address);
            memberByUserName.setGmtModified(parse);
            memberByUserName.setRealname(realname);
            // 本地修改
            memberService.updateInfo(memberByUserName);
            resultMap.put("status", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultMap;
    }

    /**
     * 修改密码
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/pwd", method = RequestMethod.POST)
    public Map updatePwd(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        /*
           * 检测用户是否存在
           * 检测原密码是否正确
           * 检测新密码和原密码是否相同，相同则不进行修改
           * 调用本地修改方法或者ids修改方法进行修改
         */

        if (onlineStatus.equals("ON")) {
        /*
           * 检测用户是否存在
           * 检测原密码是否正确
           * 检测新密码和原密码是否相同，相同则不进行修改
           * 调用接口修改方法进行修改
         */
            String uName = request.getParameter("userName");
            String upassword = request.getParameter("password");
            String oldpassword = request.getParameter("oldpassword");
            Map map = new HashMap();
            Map mapParam = new HashMap();
            mapParam.put("LoginID", uName);
            mapParam.put("Password", oldpassword);
            JSONObject paramJson = JSONObject.fromObject(mapParam);
            map.put("method", "ZAS.UserInfo");
            map.put("username", "syncuser");
            map.put("password", "syncuser");
            map.put("params", paramJson.toString());
            //请求获取用户信息接口
            Greeting userinfo = HttpClientUtil.post(USERURL, map);
            if (userinfo.getStatus() != 1){
                result.put("msg", "网络异常请联系管理员");
                return result;
            }

            String userinfoContent = (String) userinfo.getContent();
            System.out.println(userinfoContent);
            JSONObject userJson = JSONObject.fromObject(userinfoContent);
            Map usermap = (Map) userJson;
            if (usermap.get("Success").equals(true)) {
                Map modifyUserMap = new HashMap();
                Map modifyUserParam = new HashMap();
                modifyUserParam.put("LoginID", uName);
                modifyUserParam.put("Password", upassword);
                JSONObject paramMap = JSONObject.fromObject(modifyUserParam);
                modifyUserMap.put("method", "ZAS.ModifyUser");
                modifyUserMap.put("username", "syncuser");
                modifyUserMap.put("password", "syncuser");
                modifyUserMap.put("params", paramMap.toString());
                //请求修改用户接口
                Greeting modifyUser = HttpClientUtil.post(USERURL, modifyUserMap);
                if (modifyUser.getStatus() != 1){
                    result.put("status", 1);
                    return result;
                }
                String modifyUserContent = (String) modifyUser.getContent();
                JSONObject modifyUserJson = JSONObject.fromObject(modifyUserContent);
                Map modifyUserinfo = (Map) modifyUserJson;
                if (modifyUserinfo.get("Success").equals(true)) {
                    // 应用修改密码方法
                    Member memberByUserName = memberOnlineService.findMemberByUserName(uName);
                    memberByUserName.setPwd(MD5.code(upassword));
                    memberOnlineService.updatePassword(memberByUserName);
                    result.put("status", 0);
                    return result;
                }
            } else {
                result.put("status", 1);
                return result;
            }
            result.put("status", 1);
            return result;
        } else {
            String uName = request.getParameter("userName");
            String upassword = request.getParameter("password");
            String oldpassword = request.getParameter("oldpassword");
            String oldPwd = oldpassword;
            String userName = uName;
            String newPwd = upassword;
            result.put("userName", userName);
            result.put("password", oldPwd);
            boolean memberByUserNameAndPwd = memberService.findMemberByUserNameAndPwd(result, session);
            if (!memberByUserNameAndPwd) {
                result.put("status", 1);
                return result;
            }

            // 应用修改密码方法
            Member memberByUserName = memberService.findMemberByUserName(userName);
            memberByUserName.setPwd(MD5.code(newPwd));
            memberService.updatePassword(memberByUserName);
            result.put("status", 0);

            return result;
        }
    }
//    /**
//     * 在线修改密码
//     */
//    @ResponseBody
//    @RequestMapping(value = "/user/center/onlinepwd", method = RequestMethod.POST)
//    public Map onlineUpdatePwd(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> result = new HashMap<>();
//        /*
//           * 检测用户是否存在
//           * 检测原密码是否正确
//           * 检测新密码和原密码是否相同，相同则不进行修改
//           * 调用接口修改方法进行修改
//         */
//
//
//        String uName = request.getParameter("userName");
//        String upassword = request.getParameter("password");
//        String oldpassword = request.getParameter("oldpassword");
//        Map map = new HashMap();
//        Map mapParam = new HashMap();
//        mapParam.put("LoginID",uName);
//        mapParam.put("Password",oldpassword);
//        JSONObject paramJson = JSONObject.fromObject(mapParam);
//        map.put("method","ZAS.UserInfo");
//        map.put("username","syncuser");
//        map.put("password","syncuser");
//        map.put("params",paramJson.toString());
//        //请求获取用户信息接口
//        Greeting  userinfo = HttpClientUtil.post(USERURL,map);
//        String userinfoContent = (String) userinfo.getContent();
//        System.out.println(userinfoContent);
//        JSONObject userJson = JSONObject.fromObject(userinfoContent);
//        Map usermap = (Map)userJson;
//        if (usermap.get("Success").equals(true)){
//            Map modifyUserMap = new HashMap();
//            Map modifyUserParam = new HashMap();
//            modifyUserParam.put("LoginID",uName);
//            modifyUserParam.put("Password",upassword);
//            JSONObject paramMap = JSONObject.fromObject(modifyUserParam);
//            modifyUserMap.put("method","ZAS.ModifyUser");
//            modifyUserMap.put("username","syncuser");
//            modifyUserMap.put("password","syncuser");
//            modifyUserMap.put("params",paramMap.toString());
//            //请求修改用户接口
//            Greeting  modifyUser = HttpClientUtil.post(USERURL,modifyUserMap);
//            String modifyUserContent = (String) modifyUser.getContent();
//            JSONObject modifyUserJson = JSONObject.fromObject(modifyUserContent);
//            Map modifyUserinfo = (Map)modifyUserJson;
//            if(modifyUserinfo.get("Success").equals(true)){
//                // 应用修改密码方法
//                Member memberByUserName = memberService.findMemberByUserName(uName);
//                memberByUserName.setPwd(MD5.code(upassword));
//                memberService.updatePassword(memberByUserName);
//                result.put("status", 0);
//                return result;
//            }
//        }else {
//            result.put("status", 1);
//            return result;
//        }
//        result.put("status", 1);
//        return result;
//    }

    /**
     * 搜索历史
     *
     * @param request
     * @param userName
     * @param keyWord
     * @param PageIndex
     * @param PageSize
     * @return
     */
    @RequestMapping(value = "/user/center/searchhistory", method = RequestMethod.POST)
    public ModelAndView searchHistory(HttpServletRequest request, String userName, String keyWord, String PageIndex, String PageSize) {
        ModelAndView modelAndView = new ModelAndView("client/usercenter/searchhistorypage");

        int pageIndex = Util.rmNull(PageIndex).equals("") ? 1 : Integer.parseInt(PageIndex);
        int pageSize = Util.rmNull(PageSize).equals("") ? 10 : Integer.parseInt(PageSize);
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");

        Map map = new HashMap();
        map.put("pageSize", pageSize);
        map.put("currPage", (pageIndex - 1) * pageSize);
        map.put("search", userName);
        map.put("sort", sort);
        map.put("order", order);
        map.put("status", 1);

        Map resultMap = searchHistoryService.findAll(map);
        int pageCount = 0;
        Map itemsMap = new HashMap();
        // 查询资源总数
        int total = (int) resultMap.get("total");
        pageCount = (total % pageSize) == 0 ? total / pageSize : total / pageSize + 1;

        // 分页查询数据
        itemsMap.put("searchHistory", resultMap.get("rows"));

        modelAndView.addObject("items", itemsMap);
        modelAndView.addObject("name", userName);
        modelAndView.addObject("PageIndex", pageIndex);
        modelAndView.addObject("PageSize", pageSize);
        modelAndView.addObject("PageCount", pageCount);
        return modelAndView;
    }

    /**
     * type==0删除所有历史记录否则
     * 按照id删除历史记录
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/removesearchhistory", method = RequestMethod.GET)
    public Status remove(HttpServletResponse response, HttpServletRequest request, String userName) {
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        if (type != null && type.equals("1")) {
            searchHistoryService.remove(0, true, userName);
        } else {
            try {
                searchHistoryService.remove(Integer.valueOf(id), false, userName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Status("10000", "移除成功");
    }

    /**
     * 收藏
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/collect", method = RequestMethod.POST)
    public Map myCollect(String userName, String type, String name, String nameId) {
        /*
            * 定义map用于封装参数，并保存返回值
            * 检查是否被此用户收藏过，如果收藏过提示用户不能再次收藏
            * 检查是否收藏成功并返回用户提示
         */
        Map<String, Object> result = new HashMap<>();
        result.put("userName", userName);
        result.put("name", name);
        result.put("type", type);
        result.put("time", new Date());
        result.put("nameId", nameId);


        // 检查此用户是否收藏过此资源
        boolean collectByUserNameAndTypeAndNameId = myCollectService.findCollectByUserNameAndTypeAndNameId(result);
        if (collectByUserNameAndTypeAndNameId) {
            result.put("code", 2);
            return result;
        }
        // 是否收藏成功
        boolean add = myCollectService.add(result);
        if (!add) {
            result.put("code", 0);
            return result;
        }
        result.put("code", 1);
        return result;
    }

    /**
     * 取消收藏
     *
     * @param userName
     * @param nameId
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/collect", method = RequestMethod.DELETE)
    public Map clearCollect(@RequestParam(value = "userName", required = true) String userName, @RequestParam(value = "nameId", required = true) String nameId, @RequestParam(value = "type", required = true) String type) {
        Map<String, Object> result = new HashMap<>();
        result.put("userName", userName);
        result.put("nameId", nameId);
        result.put("type", type);
        myCollectService.delete(result);
        result.put("code", 1);
        return result;
    }

    /**
     * 我的收藏-数据分页
     *
     * @param keyWord
     * @param PageIndex
     * @param PageSize
     * @return
     */
    @RequestMapping(value = "/user/center/resource", method = RequestMethod.POST)
    public ModelAndView getResource(String userName, String keyWord, String PageIndex, String PageSize, String type) {
        int pageIndex = Util.rmNull(PageIndex).equals("") ? 1 : Integer.parseInt(PageIndex);
        int pageSize = Util.rmNull(PageSize).equals("") ? 10 : Integer.parseInt(PageSize);

        int pageCount = 0;
        Map itemsMap = new HashMap();

        Map paramMap = new HashMap();
        paramMap.put("userName", userName);
        paramMap.put("type", type);
        paramMap.put("keyWord", keyWord);
        paramMap.put("currPage", (pageIndex - 1) * pageSize);
        paramMap.put("pageSize", pageSize);

        Map totalParam = new HashMap();
        totalParam.put("userName", userName);
        totalParam.put("type", type);
        totalParam.put("keyWord", keyWord);

        // 查询资源总数
        int total = myCollectService.total(totalParam);
        pageCount = (total % pageSize) == 0 ? total / pageSize : total / pageSize + 1;

        // 分页查询数据
        List<MyCollect> myCollects = myCollectService.pageList(paramMap);
        itemsMap.put("myCollects", myCollects);

        ModelAndView modelAndView = new ModelAndView("client/usercenter/collectpage");
        modelAndView.addObject("PageIndex", pageIndex);
        modelAndView.addObject("items", itemsMap);
        modelAndView.addObject("name", userName);
        modelAndView.addObject("type", type);
        modelAndView.addObject("PageSize", pageSize);
        modelAndView.addObject("PageCount", pageCount);
        modelAndView.addObject("myCollects", myCollects.size());
        return modelAndView;
    }

    /**
     * 用户中心 我得关注  查询关注
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/adminConcern", method = RequestMethod.POST)
    public List<Concern> selectConcern(HttpServletRequest request) {
        String userName = request.getParameter("username");
        List list = new ArrayList();
        // 根据用户名获取关注词
        List<Concern> listConcern = userCenterAdminService.selectConcern(userName);
        List<Collect> lsitCollects = userCenterAdminService.collectShowWeek(userName);
        List<Concern> concernList = userCenterAdminService.selectConcernWeek(userName);
        List<Splice> splice = userCenterAdminService.collectSpliceWeek(userName);
        list.add(listConcern);
        //关注个数
        list.add(listConcern.size());
        //一周关注个数
        list.add(concernList.size());
        //收藏个数
        list.add(lsitCollects.size());
        //拼接个数
        list.add(splice.size());
        return list;
    }

    /**
     * 用户中心 我得收藏查询展示
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/collectShow", method = RequestMethod.POST)
    public List<Collect> collectShow(HttpServletRequest request) {
        String userName = request.getParameter("username");
        String foldername = request.getParameter("folderName");
        String PageIndex = request.getParameter("PageIndex");
        String currPage = request.getParameter("currPage");

        Map map = new HashMap();
        int pageBegin = (Integer.parseInt(PageIndex) - 1) * Integer.parseInt(currPage);
        map.put("pageSize", pageBegin);//从第几个开始查询
        map.put("currPage", Integer.parseInt(currPage));//向下查询四条
        map.put("foldername", foldername);
        map.put("userName", userName);
        List list = new ArrayList();
        // 查询数据
        List<Collect> listCollent = userCenterAdminService.collectShow(map);
        //查询 总个数
        int allCollect = userCenterAdminService.collectShowCount(map);
        List li = new ArrayList();
        li.add(listCollent);
        li.add(allCollect);
        li.add(PageIndex);
        return li;

    }


    /**
     * 用户中心 我得关注  删除关注
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/deleteConcern", method = RequestMethod.POST)
    public Map deleteConcern(HttpServletRequest request) {
        String id = request.getParameter("id");
        String username = request.getParameter("username");
        // 删除关注词
        Map map = userCenterAdminService.deleteConcern(Integer.parseInt(id));
        return map;
    }

    /**
     * 用户中心 我的收藏删除
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/collectDelete", method = RequestMethod.POST)
    public Map collectDelete(HttpServletRequest request) {
        String id = request.getParameter("id");
        String userName = request.getParameter("username");
        // 我的收藏删除
        Map map = userCenterAdminService.collectDelete(Integer.parseInt(id));
        return map;
    }

    /**
     * 用户中心 我的收藏文件夹查找
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/collectFolder", method = RequestMethod.POST)
    public List<Collect> collectFolder(HttpServletRequest request) {
        String userName = request.getParameter("username");
        // 我的收藏文件夹查找
        List<Collect> listCollect = userCenterAdminService.collectFolder(userName);
        return listCollect;
    }

    /**
     * 用户中心 我的收藏  文件夹删除
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/collectFolderDelete", method = RequestMethod.POST)
    public Map collectFolderDelete(HttpServletRequest request) {
        String folderName = request.getParameter("folderName").trim();
        String username = request.getParameter("username");
        Map map1 = new HashMap();
        map1.put("folderName", folderName);
        map1.put("username", username);
        // 文件夹删除
        Map map = userCenterAdminService.collectFolderDelete(map1);
        return map;
    }

    /**
     * 用户中心 我的收藏  文件夹重命名
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/collectFolderReName", method = RequestMethod.POST)
    public Map collectFolderReName(HttpServletRequest request) {
        String folderName = request.getParameter("folderName").trim();
        String oldFolderName = request.getParameter("oldFolderName").trim();
        String userName = request.getParameter("username");
        Map map = new HashMap();
        map.put("folderName", folderName);
        map.put("oldFolderName", oldFolderName);
        map.put("username", userName);
        // 文件夹重命名
        Map mapResult = userCenterAdminService.collectFolderReName(map);
        return mapResult;
    }

    /**
     * 用户中心 我的收藏  文件夹添加
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/insertFolder", method = RequestMethod.POST)
    public Map insertFolder(HttpServletRequest request) {
        String folderName = request.getParameter("folderName").trim();
        String userName = request.getParameter("userName").trim();
        Date d = new Date();
        System.out.println(d);
        Collect collect = new Collect();
        collect.setFoldername(folderName);
        collect.setGmtCreate(d);
        collect.setUsername(userName);

        // 添加收藏夹
        Map mapResult = userCenterAdminService.insertFolder(collect);
        return mapResult;
    }


    /**
     * 收藏添加
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/addCollect", method = RequestMethod.POST)
    public Map addCollect(HttpServletRequest request) {
        String tid = request.getParameter("tid").trim();
        String title = request.getParameter("title").trim();
        String subtitle = request.getParameter("subtitle").trim();
        String source = request.getParameter("source").trim();
        String abs = request.getParameter("abs").trim();
        String userName = request.getParameter("username").trim();
        String foldername = request.getParameter("foldername").trim();
        String bookcode = request.getParameter("bookcode");
        System.out.println(bookcode);
        Map mapResult = null;
        if (foldername == "") {
            mapResult.put("state", "1");
            mapResult.put("msg", "添加失败");
            return mapResult;
        }
        Date d = new Date();
        System.out.println(d);
        Collect collect = new Collect();
        collect.setTid(tid);
        collect.setTitle(title);
        collect.setSubtitle(subtitle);
        collect.setSource(source);
        collect.setUsername(userName);
        collect.setAbs(abs);
        collect.setFoldername(foldername);
        collect.setGmtCreate(d);
        collect.setBookcode(bookcode);
        Map mapUserAndTid = new HashMap();
        mapUserAndTid.put("userName", userName);
        mapUserAndTid.put("tid", tid);
        //添加之前先查找看看该收藏是否已经收藏
        Map mapCollect = userCenterAdminService.selectByUserNameAndTID(mapUserAndTid);
        if (mapCollect.get("state").equals("2")) {
            return mapCollect;
        }
        // 收藏添加
        mapResult = userCenterAdminService.addCollect(collect);
        return mapResult;
    }


    /**
     * 收藏拼接
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/mySpliceAdd", method = RequestMethod.POST)
    public Map mySpliceAdd(HttpServletRequest request) {
        String userName = request.getParameter("username");
        String zid = request.getParameter("zid");
        String title = request.getParameter("title");
        String bookcode = request.getParameter("bookcode");
        Map mapResult = null;
        Date d = new Date();
        System.out.println(d);
        Splice splice = new Splice();
        splice.setZid(zid);
        splice.setTitle(title);
        splice.setUsername(userName);
        splice.setGmtCreate(d);
        splice.setBookcode(bookcode);

        Map mapUserAndTid = new HashMap();
        mapUserAndTid.put("userName", userName);
        mapUserAndTid.put("zid", zid);
        mapUserAndTid.put("bookcode", bookcode);
        //添加之前先查找看看该收藏是否已经拼接（已经拼接  更新现有，未为拼接 新增）
        Map mySplice = userCenterAdminService.selectSpliceByUserNameAndTID(mapUserAndTid);
        if (mySplice.get("state").equals("2")) {
            //更新
            mapResult = userCenterAdminService.upSplice(splice);
        } else {
            // 新增
            mapResult = userCenterAdminService.mySpliceAdd(splice);
        }
        return mapResult;
    }

    /**
     * 用户中心 我得拼接查找
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/spliceShow", method = RequestMethod.POST)
    public List<Splice> spliceShow(HttpServletRequest request) {
        String username = request.getParameter("username");
        String PageIndex = request.getParameter("PageIndex");
        String currPage = request.getParameter("currPage");
        int pageBegin = (Integer.parseInt(PageIndex) - 1) * Integer.parseInt(currPage);
        Map map = new HashMap();
        map.put("username", username);
        map.put("pageSize", pageBegin);//从第几个开始查询
        map.put("currPage", Integer.parseInt(currPage));//向下查询四条
        // 我的拼接
        List<Splice> listSplice = userCenterAdminService.spliceShow(map);
        //查询 总个数
        int allSplice = userCenterAdminService.allSpliceShowCount(map);
        List li = new ArrayList();
        li.add(listSplice);
        li.add(allSplice);
        li.add(PageIndex);
        return li;
    }

    /**
     * 用户中心 我得拼接删除
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/deleteSplice", method = RequestMethod.POST)
    public Map deleteSplice(HttpServletRequest request) {
        String id = request.getParameter("id");
        // 删除我得拼接
        Map map = userCenterAdminService.deleteSplice(Integer.parseInt(id));
        return map;
    }


    /**
     * 用户中心 我的关注添加
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/insertConcern", method = RequestMethod.POST)
    public Map insertConcern(HttpServletRequest request) {
        String concern = request.getParameter("concern").trim();
        String userName = request.getParameter("userName").trim();
        Date d = new Date();
        System.out.println(d);
        Concern concern1 = new Concern();
        concern1.setName(concern);
        concern1.setGmtCreate(d);
        concern1.setUserName(userName);
        Map map = new HashMap();
        map.put("concern", concern);
        map.put("username", userName);
        Map mapResult = new HashMap();
        // 查询关注词数量 最多30
        int count = userCenterAdminService.allConcern(map);
        if (count >= 30) {
            mapResult.put("state", "30");
            return mapResult;
        }
        //查询我得关注是否已经添加
        Map mapConcern = userCenterAdminService.selectConcernByUserNameAndConcern(map);
        if (mapConcern.get("state").equals("2")) {
            //更新
            mapResult = userCenterAdminService.upConcern(concern1);
        } else {
            // 添加我得关注
            mapResult = userCenterAdminService.insertConcern(concern1);
        }
        return mapResult;
    }

    /**
     * 在线用户注册
     */
    @RequestMapping("/onlineRegister")
    public String reurnOnlineRegister() {
        return "/client/onlineRegister/onlineRegister";
    }

    @ResponseBody
    @RequestMapping(value = "/onlineRegister", method = RequestMethod.POST)
    public Map onlineRegister(Member member) {
        Map resultMap = new HashMap();
        if (onlineStatus.equals("ON")) {
            Map registerMap = new HashMap();
            Map userInfo = new HashMap();
            //"{'method':'ZAS.AddUser', 'username':'syncuser', 'password':'syncuser', 'params':{'UserName':'hello3','Password':'666666', 'Email':'82@qq.com', 'BID':'1'}}";
            userInfo.put("UserName", member.getUsername());
            userInfo.put("Password", member.getPwd());
            userInfo.put("RealName", member.getRealname());
            //性别M表示男，F表示女
            if (member.getGender() == 1) {
                userInfo.put("Gender", "M");
            }
            if (member.getGender() == 2) {
                userInfo.put("Gender", "F");
            }

            userInfo.put("Email", member.getEmail());
            userInfo.put("Mobile", member.getTelephone());
            userInfo.put("Address", member.getAddress());
            userInfo.put("PwdIsEncryption", "N");
        /*
        用户状态,默认为1
        1表示启用，2表示邮箱已邮件验证，4表示手机号码已短信验证
        多个状态请使用位或,如:
        1|2=3，提交参数Status=3表示该用户是启用的并且邮箱已经验证
        1|4=5，提交参数Status=5表示用户已启用且手机号码已短信验证
        * */
            userInfo.put("Status", "1");
            JSONObject userJson = JSONObject.fromObject(userInfo);
            registerMap.put("method", "ZAS.AddUser");
            registerMap.put("username", "syncuser");
            registerMap.put("password", "syncuser");
            registerMap.put("params", userJson.toString());
            JSONObject json = JSONObject.fromObject(registerMap);
            System.out.println(json.toString());
            //请求注册接口
            Greeting content = HttpClientUtil.post(USERURL, registerMap);
            System.out.println(content.getStatus());
            if (content.getStatus() != 1){
                resultMap.put("state", "1");
                resultMap.put("msg","网络异常请联系管理员");
                return resultMap;
            }
            //接口返回信息
            JSONObject comtentJson = JSONObject.fromObject(content.getContent().toString());
            Map contentMap = (Map) comtentJson;
            //注册成功操作
            if (contentMap.get("Success").equals(true)) {
                //填写本地库必须信息
                member.setStatus(0);
                member.setPwd(Util.toMD5(member.getPwd()));
                String uuid = UUID.randomUUID().toString().replace("-", "");
                member.setUuCode(uuid);
                Date date = new Date();
                member.setGmtCreate(date);
                member.setGmtModified(date);
                member.setRoleId(2);
                memberOnlineService.add(member);
                resultMap.put("state", "0");
            } else {
                resultMap.put("state", "1");
                resultMap.put("msg", contentMap.get("Message").toString().replaceAll("\"", "'"));
            }
            contentMap.get("Success");
            return resultMap;
        }else{
            resultMap.put("state", "1");
            resultMap.put("msg","只能注册在线用户，当前状态为离线");
            return resultMap;
        }
    }
    /**
     * 在线用户忘记密码
     */
    @ResponseBody
    @RequestMapping(value = "/repassword")
    public Map repassword(@RequestParam(value = "usermail") String userEmail){
        Map resultMap = new HashMap<>();
        //"{'username':'syncuser','password':'syncuser','params':{'AddressType':'Email','Address':'1395124893@qq.com','VerifyMode':'Link','VarTemplate':'EmailResetPasswdTemplate'},'method':'ZAS.Findpwd'}";
        Map repasswd = new HashMap<>();
        Map repasswdParam = new HashMap();
        repasswd.put("method", "ZAS.Findpwd");
        repasswd.put("username", "syncuser");
        repasswd.put("password", "syncuser");
        repasswdParam.put("AddressType","Email");
        repasswdParam.put("Address",userEmail);
        repasswdParam.put("VerifyMode","Link");
        repasswdParam.put("VarTemplate","EmailResetPasswdTemplate");
        JSONObject repasswdJson = JSONObject.fromObject(repasswdParam);
        repasswd.put("params",repasswdJson.toString());
        //请求发送邮件接口
        Greeting content = HttpClientUtil.post(USERURL,repasswd);
        if (content.getStatus() != 1){
            resultMap.put("state", "1");
            resultMap.put("msg","网络异常请联系管理员");
            return resultMap;
        }
        //接口返回信息
        JSONObject comtentJson = JSONObject.fromObject(content.getContent().toString());
        Map contentMap = (Map) comtentJson;
        //_Status
        if (contentMap.get("_Status").equals(0)){
            resultMap.put("status",0);
            return resultMap;
        }else {
            resultMap.put("status",1);
            resultMap.put("msg",contentMap.get("Message"));
            System.out.println(contentMap.get("Message"));
            return resultMap;
        }
    }
}
