package com.trs.web.admin;

import com.trs.core.annotations.Permission;
import com.trs.core.util.Config;
import com.trs.core.util.HttpClientUtil;
import com.trs.model.Greeting;
import com.trs.model.Member;
import com.trs.model.Role;
import com.trs.model.User;
import com.trs.service.MemberOnlineService;
import com.trs.service.MemberService;
import com.trs.service.RoleService;
import com.trs.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/3/30.
 */
@Controller
public class MemberController extends AbstractAdminController {

    //在线状态
    private static final String ONLINESTAUS = Config.getKey("online");
    //用户api接口
    private static final String USERURL = Config.getKey("zas.userUrl");

    @Resource
    private MemberOnlineService memberOnlineService;
    @Resource
    private MemberService memberService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    /**
     * 跳转会员管理页面
     *
     * @return
     */
    @Permission(url = "/member-user")
    @RequestMapping(value = "/member-user", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/admin/member/member");
        modelAndView.addObject("title", "个人用户");
        modelAndView.addObject("online", ONLINESTAUS);
        return modelAndView;
    }
    /**
     * 查询所有用户
     */
    @ResponseBody
    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public Map members(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        if (ONLINESTAUS.equals("ON")) {
            Map resultMap = new HashMap();
            Object username = session.getAttribute("adminUserName");

            User userInfo = userService.findUserByName((String) username);


            String pageSize = request.getParameter("pageSize");
            String currPage = request.getParameter("currPage");
            String search = request.getParameter("search");
            String sort = request.getParameter("sort");
            String order = request.getParameter("order");
            String state = request.getParameter("state");
            String orgId = request.getParameter("orgId");

            Map map = new HashMap();
            map.put("pageSize", Integer.parseInt(pageSize));
            map.put("currPage", Integer.parseInt(currPage));
            map.put("search", search);
            map.put("sort", sort);
            map.put("order", order);
            map.put("state", state);
            map.put("orgId", orgId);

            if (userInfo.getOrganizeId() == null){
                resultMap = memberOnlineService.findAllMember(map);
            }else {
                map.put("organiza_id", userInfo.getOrganizeId());
                resultMap = memberOnlineService.findAllOrgainzeMember(map);
            }
            return resultMap;
        } else {
            String pageSize = request.getParameter("pageSize");
            String currPage = request.getParameter("currPage");
            String search = request.getParameter("search");
            String sort = request.getParameter("sort");
            String order = request.getParameter("order");
            String state = request.getParameter("state");
            String orgId = request.getParameter("orgId");

            Map map = new HashMap();
            map.put("pageSize", Integer.parseInt(pageSize));
            map.put("currPage", Integer.parseInt(currPage));
            map.put("search", search);
            map.put("sort", sort);
            map.put("order", order);
            map.put("state", state);
            map.put("orgId", orgId);

            Map resultMap = memberService.findAllMember(map);

            return resultMap;
        }

    }

    /**
     * 查询当前机构下的用户
     */
    @ResponseBody
    @RequestMapping(value = "/membersOrg", method = RequestMethod.GET)
    public Map membersOrg(HttpServletRequest request, HttpServletResponse response) {
        if (ONLINESTAUS.equals("ON")) {
            String pageSize = request.getParameter("pageSize");
            String currPage = request.getParameter("currPage");
            String search = request.getParameter("search");
            String sort = request.getParameter("sort");
            String order = request.getParameter("order");
            String state = request.getParameter("state");
            String orgId = request.getParameter("orgId");


            Map map = new HashMap();
            map.put("pageSize", Integer.parseInt("999999999"));
            map.put("currPage", Integer.parseInt("0"));
            map.put("search", search);
            map.put("sort", sort);
            map.put("order", order);
            map.put("state", state);
            map.put("orgId", orgId);

            Map resultMap = memberOnlineService.findMemberByOrgId(map);

            return resultMap;
        } else {
            String pageSize = request.getParameter("pageSize");
            String currPage = request.getParameter("currPage");
            String search = request.getParameter("search");
            String sort = request.getParameter("sort");
            String order = request.getParameter("order");
            String state = request.getParameter("state");
            String orgId = request.getParameter("orgId");

            Map map = new HashMap();
            map.put("pageSize", Integer.parseInt(pageSize));
            map.put("currPage", Integer.parseInt(currPage));
            map.put("search", search);
            map.put("sort", sort);
            map.put("order", order);
            map.put("state", state);
            map.put("orgId", orgId);

            Map resultMap = memberService.findMemberByOrgId(map);

            return resultMap;
        }

    }

    /**
     * 更改资源状态信息
     *
     * @param ids
     * @param status
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/member/{ids}/{status}", method = RequestMethod.POST)
    public Map change(@PathVariable List<Integer> ids, @PathVariable String status, HttpServletResponse response) {
        if (ONLINESTAUS.equals("ON")) {
            // 定义map存储接收的值
            Map map = new HashMap();
            map.put("status", status);
            map.put("ids", ids);

            // 获取返回值
            Map resultMap = memberOnlineService.changeStatus(map);
            // 将信息输出到页面中
            return resultMap;
        } else {
            // 定义map存储接收的值
            Map map = new HashMap();
            map.put("status", status);
            map.put("ids", ids);

            // 获取返回值
            Map resultMap = memberService.changeStatus(map);
            // 将信息输出到页面中
            return resultMap;
        }
    }

    /**
     * 删除会员
     *
     * @param ids
     */
    @ResponseBody
    @RequestMapping(value = "/member/{ids}", method = RequestMethod.DELETE)
    public Map delete(@PathVariable List<Integer> ids, HttpServletResponse response) {
        if (ONLINESTAUS.equals("ON")) {
            //删除会员没有删除接口中的会员
            Map map = memberOnlineService.deleteMemberByid(ids);
            return map;
        } else {
            Map map = memberService.deleteMemberByid(ids);
            return map;
        }

    }

    /**
     * 设置会员密码
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/member/resetpassword", method = RequestMethod.POST)
    public Map resetPassword(HttpServletRequest request, HttpServletResponse response) {
        Map paramMaps = new HashMap();
        Map resultMap = new HashMap();
        String newpassword = request.getParameter("newpassword");
        String id = request.getParameter("id");
        /*
        *强制修改密码
        *把验证密码去掉直接修改密码
         */
        if (ONLINESTAUS.equals("ON")){
            Member member = memberOnlineService.findMemberById(Integer.valueOf(id));
            Map modifyUserMap = new HashMap();
            Map modifyUserParam = new HashMap();
            modifyUserParam.put("LoginID", member.getUsername());
            modifyUserParam.put("Password", newpassword);
            JSONObject paramMap = JSONObject.fromObject(modifyUserParam);
            modifyUserMap.put("method", "ZAS.ModifyUser");
            modifyUserMap.put("username", "syncuser");
            modifyUserMap.put("password", "syncuser");
            modifyUserMap.put("params", paramMap.toString());
            //请求修改用户接口
            Greeting modifyUser = HttpClientUtil.post(USERURL, modifyUserMap);
            if (modifyUser.getStatus() != 1){
                resultMap.put("msg", "网络异常请联系管理员");
                return resultMap;
            }
            String modifyUserContent = (String) modifyUser.getContent();
            JSONObject modifyUserJson = JSONObject.fromObject(modifyUserContent);
            Map modifyUserinfo = (Map) modifyUserJson;
            //请求成功则修改本地
            if(modifyUserinfo.get("Success").equals(true)){
                paramMaps.put("id", id);
                paramMaps.put("newpassword", newpassword);
                resultMap = memberOnlineService.resetPwd(paramMaps);
                return resultMap;
            }
        }else {
            paramMaps.put("id", id);
            paramMaps.put("newpassword", newpassword);
            resultMap = memberService.resetPwd(paramMaps);
            return resultMap;
        }
        return resultMap;
    }

    /**
     * 更新用户权限
     *
     * @param ids
     * @param attribute
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/member/{id}", method = RequestMethod.PUT)
    public Map changeRoleId(@PathVariable("id") List<Integer> ids, @RequestParam(value = "attribute", required = false) String attribute) {
        if (ONLINESTAUS.equals("ON")) {
            Map map = new HashMap();
            map.put("ids", ids);
            map.put("roleId", attribute);
            Map<String, String> resultMap = memberOnlineService.changRoleId(map);
            return resultMap;
        } else {
            Map map = new HashMap();
            map.put("ids", ids);
            map.put("roleId", attribute);
            Map<String, String> resultMap = memberService.changRoleId(map);
            return resultMap;
        }
    }

    /**
     * 查询前台角色
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/member/role", method = RequestMethod.POST)
    public List findRole() {
        List<Role> roleList = roleService.findAllRole(2);
        List list = new ArrayList();
        for (Role role : roleList) {
            if (0 == role.getStatus()) {
                list.add(role);
            }
        }
        return list;
    }

    /**
     * 通过id查询用户信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/member/{id}", method = RequestMethod.POST)
    public Map findMember(@PathVariable("id") Integer id) {
        if (ONLINESTAUS.equals("ON")) {
            Map<String, Object> resultMap = new HashMap<>();
            Member member = memberOnlineService.findMemberById(id);
            resultMap.put("member", member);
            return resultMap;
        } else {
            Map<String, Object> resultMap = new HashMap<>();
            Member member = memberService.findMemberById(id);
            resultMap.put("member", member);
            return resultMap;
        }
    }

    /**
     * 跳转到添加/修改页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/member/{id}", method = RequestMethod.GET)
    public ModelAndView memberAdd(@PathVariable("id") String id, Model model) {
        ModelAndView modelAndView = new ModelAndView();

        if (ONLINESTAUS.equals("ON")) {
            if ("add".equals(id)) {
                modelAndView.addObject("title", "个人用户");
                modelAndView.addObject("stitle", "添加用户");
            } else {
                modelAndView.addObject("title", "个人用户");
                modelAndView.addObject("stitle", "修改用户");
                Member user = memberOnlineService.findMemberById(Integer.parseInt(id));
                Role roleById = roleService.findRoleById(user.getRoleId());
                model.addAttribute("user", user);
                model.addAttribute("role", roleById);
            }

            List<Role> roleList = roleService.findAllRole(2);
            List list = new ArrayList();
            for (Role role : roleList) {
                if (0 == role.getStatus()) {
                    list.add(role);
                }
            }

            modelAndView.addObject("roleList", list);
            modelAndView.setViewName("/admin/member/addmember");
            return modelAndView;
        } else {
            if ("add".equals(id)) {
                modelAndView.addObject("title", "个人用户");
                modelAndView.addObject("stitle", "添加用户");
            } else {
                modelAndView.addObject("title", "个人用户");
                modelAndView.addObject("stitle", "修改用户");
                Member user = memberService.findMemberById(Integer.parseInt(id));
                Role roleById = roleService.findRoleById(user.getRoleId());
                model.addAttribute("user", user);
                model.addAttribute("role", roleById);
            }

            List<Role> roleList = roleService.findAllRole(2);
            List list = new ArrayList();
            for (Role role : roleList) {
                if (0 == role.getStatus()) {
                    list.add(role);
                }
            }

            modelAndView.addObject("roleList", list);
            modelAndView.setViewName("/admin/member/addmember");
            return modelAndView;
        }

    }

    /**
     * 保存用户信息
     *
     * @param member
     */
    @ResponseBody
    @RequestMapping(value = "/member", method = RequestMethod.POST)
    public Map save(Member member) {
        Map resultMap = new HashMap();
            /*
            * 在线版本
            * 修改用户
            * 传入信息
            * 请求接口
            * 返回参数
            * 修改本地
            * */
        if (ONLINESTAUS.equals("ON")) {
        /*保存修改的用户*/
            if (member.getId() != null && !"".equals(member.getId())) {
                Map modifyUserMap = new HashMap();
                Map modifyUserParam = new HashMap();
                modifyUserParam.put("LoginID", member.getUsername());
                modifyUserParam.put("RealName", member.getRealname());
                modifyUserParam.put("Email", member.getEmail());
                modifyUserParam.put("Mobile", member.getTelephone());
                modifyUserParam.put("Address", member.getAddress());
                if (member.getGender().equals(1)) {
                    modifyUserParam.put("Gender", "M");
                } else {
                    modifyUserParam.put("Gender", "F");
                }
                JSONObject paramMap = JSONObject.fromObject(modifyUserParam);
                modifyUserMap.put("method", "ZAS.ModifyUser");
                modifyUserMap.put("username", "syncuser");
                modifyUserMap.put("password", "syncuser");
                modifyUserMap.put("params", paramMap.toString());
                //请求修改用户接口
                Greeting modifyUser = HttpClientUtil.post(USERURL, modifyUserMap);
                if (modifyUser.getStatus() != 1){
                    resultMap.put("msg", "网络异常请联系管理员");
                    return resultMap;
                }
                String modifyUserContent = (String) modifyUser.getContent();
                JSONObject modifyUserJson = JSONObject.fromObject(modifyUserContent);
                Map modifyUserinfo = (Map) modifyUserJson;
                /*成功则修改本地*/
                if (modifyUserinfo.get("Success").equals(true)) {
                    resultMap = memberOnlineService.updateInfo(member);
                }
            }
            return resultMap;
        } else {
            /*离线版*/
            if (member.getId() != null && !"".equals(member.getId())) {
                resultMap = memberService.updateInfo(member);
            }
            else{
                /*
                * 添加用户默认权限
                * 收藏、拼接、关注
                * */
                member.setRoleId(2);
                resultMap = memberService.add(member);
            }
            return resultMap;
        }

    }
}
