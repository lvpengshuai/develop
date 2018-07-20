package com.trs.web.admin;

import com.alibaba.fastjson.JSON;
import com.trs.core.annotations.Permission;
import com.trs.core.util.Config;
import com.trs.core.util.Util;
import com.trs.model.Organize;
import com.trs.model.Role;
import com.trs.model.User;
import com.trs.service.OrganizeOnlineService;
import com.trs.service.RoleService;
import com.trs.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by zly on 2017-3-20.
 */
@Controller
public class UserController extends AbstractAdminController {

    //在线状态
    private static final String ONLINESTATUS = Config.getKey("online");

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private OrganizeOnlineService organizeOnlineService;

    /**
     * 跳转到后台用户页面
     *
     * @return
     */
    @Permission(url = "/admin-user")
    @RequestMapping(value = "/admin-user", method = RequestMethod.GET)
    public ModelAndView toUser() {
        ModelAndView modelAndView = new ModelAndView("/admin/user/user");
        modelAndView.addObject("title", "管理用户");

        return modelAndView;
    }

    /**
     * 查询所有用户
     */
    @ResponseBody
    @RequestMapping(value = "/admin-users", method = RequestMethod.GET)
    public Map users(HttpServletRequest request) {

        String pageSize = request.getParameter("pageSize");
        String currPage = request.getParameter("currPage");
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String state = request.getParameter("state");

        Map map = new HashMap();
        map.put("pageSize", Integer.parseInt(pageSize));
        map.put("currPage", Integer.parseInt(currPage));
        map.put("search", search);
        map.put("sort", sort);
        map.put("order", order);
        map.put("state", state);

        Map resultMap = userService.findAllUser(map);

        return resultMap;

    }

    /**
     * 删除用户
     *
     * @param ids
     */
    @ResponseBody
    @RequestMapping(value = "/admin-user/{ids}", method = RequestMethod.POST)
    public Map deleteUser(@PathVariable("ids") List ids) {
        Util.log("删除用户开始-----------------------", "user-delete" , 0);
        Map resultMap = userService.deleteUserById(ids);
        return resultMap;
    }

    /**
     * 启用禁用用户
     *
     * @param status
     * @param usersId
     */
    @ResponseBody
    @RequestMapping(value = "/admin-user/{usersId}/{status}", method = RequestMethod.POST)
    public Map changeUserStatus(@PathVariable String status, @PathVariable List usersId) {
        Map map = new HashMap();
        map.put("status", status);
        map.put("ids", usersId);

        Map resultMap = userService.changeStatus(map);

        return resultMap;
    }

    /**
     * 跳转到添加/修改页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/admin-user/{id}", method = RequestMethod.GET)
    public ModelAndView toAdd(@PathVariable("id") String id, Model model) {
        ModelAndView modelAndView = new ModelAndView();

        if ("add".equals(id)) {
            modelAndView.addObject("title", "管理用户");
            modelAndView.addObject("stitle", "添加用户");
        } else {
            modelAndView.addObject("title", "管理用户");
            modelAndView.addObject("stitle", "修改用户");
            User user = userService.findUserById(Integer.parseInt(id));
            Integer orgainsizeid=user.getOrganizeId();
            Role roleById = roleService.findRoleById(user.getRoleId());
            model.addAttribute("user", user);
            model.addAttribute("role", roleById);
            model.addAttribute("orgainsizeid", orgainsizeid);
        }

        List<Role> roleList = roleService.findAllRole(1);
        List list = new ArrayList();
        for (Role role : roleList) {
            if (0 == role.getStatus()) {
                list.add(role);
            }
        }
        if (ONLINESTATUS.equals("ON")) {
            List<Organize> orgList=organizeOnlineService.getOrganizesList();
            modelAndView.addObject("orgList", orgList);
            modelAndView.addObject("line", "ON");
        }
        modelAndView.addObject("roleList", list);
        modelAndView.setViewName("/admin/user/adduser");
        return modelAndView;
    }

    /**
     * 保存用户信息
     *
     * @param user
     */
    @ResponseBody
    @RequestMapping(value = "/admin-user", method = RequestMethod.POST)
    public Map save(User user) {
        Map resultMap = new HashMap();

        /*保存修改的用户*/
        if (user.getId() != null && !"".equals(user.getId())) {
            resultMap = userService.updateUser(user);
        } else {
            resultMap = userService.addUser(user);
        }

        return resultMap;
    }

    /**
     * 重置密码
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/admin-user/resetpassword", method = RequestMethod.POST)
    public Map resetPassword(HttpServletRequest request) {

        String id = request.getParameter("id");
        String oldpassword = request.getParameter("oldpassword");
        String newpassword = request.getParameter("newpassword");

        Map paramMap = new HashMap();
        paramMap.put("id", id);
        paramMap.put("oldpassword", oldpassword);
        paramMap.put("newpassword", newpassword);

        Map resultMap = userService.resetPwd(paramMap);

        return resultMap;
    }

    /**
     * 修改资料
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/admin-edit", method = RequestMethod.GET)
    public ModelAndView edit(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        Object userName = request.getSession().getAttribute("adminUserName");
        User user = userService.findUserByName(userName.toString());
        modelAndView.addObject("user", user);
        try {
            Role role = roleService.findRoleById(user.getRoleId());
            modelAndView.addObject("role", role);
        } catch (Exception e) {
            //      e.printStackTrace();
        }
        modelAndView.setViewName("/admin/user/edituser");
        return modelAndView;
    }

    /**
     * 修改用户信息
     * @param user
     * @param request
     * @param response
     */
    @RequestMapping(value = "/admin-edit/update", method = RequestMethod.POST)
    public void update(User user, HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = new HashMap();

        /*保存修改的资料*/
        Object userName = request.getSession().getAttribute("adminUserName");
        User user_old = userService.findUserByName(userName.toString());
        user_old.setAddress(user.getAddress());
        user_old.setEmail(user.getEmail());
        user_old.setTelephone(user.getTelephone());
        user_old.setRealname(user.getRealname());
        user_old.setGmtModified(new Date());
        try {
            userService.updateByUserId(user_old);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultMap.put("state", "0");

        out(response, JSON.toJSONString(resultMap));
    }

    /**
     * 修改资料
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/admin-reset", method = RequestMethod.GET)
    public ModelAndView resetAdminPassword(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Object userName = request.getSession().getAttribute("adminUserName");
        User user = userService.findUserByName(userName.toString());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/admin/user/resetpassword");
        return modelAndView;
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/admin-reset/password", method = RequestMethod.POST)
    public Map adminResetPassword(HttpServletRequest request) {

        String id = request.getParameter("id");
        String oldpassword = request.getParameter("oldpassword");
        String newpassword = request.getParameter("newpassword");

        Map paramMap = new HashMap();
        paramMap.put("id", id);
        paramMap.put("oldpassword", oldpassword);
        paramMap.put("newpassword", newpassword);

        Map resultMap = userService.resetPwd(paramMap);

        return resultMap;
    }


}
