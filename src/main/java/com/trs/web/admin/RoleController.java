
package com.trs.web.admin;

import com.trs.core.util.Config;
import com.trs.mapper.MemberMapper;
import com.trs.mapper.UserMapper;
import com.trs.model.Permission;
import com.trs.model.Role;
import com.trs.model.RolePermission;
import com.trs.service.MemberService;
import com.trs.service.PermissionService;
import com.trs.service.RoleService;
import com.trs.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by zly on 2017-3-20.
 */
@Controller
public class RoleController extends AbstractAdminController {

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private UserService userService;

    @Resource
    private MemberService memberService;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private UserMapper userMapper;

    //在线状态
    private static final String ONLINESTATUS = Config.getKey("online");

    /*跳转角色页面*/
    @com.trs.core.annotations.Permission(url = "/role")
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public ModelAndView toRole() {
        ModelAndView modelAndView = new ModelAndView("/admin/role/role");
        modelAndView.addObject("title", "角色管理");

        return modelAndView;
    }

    /**
     * 获取数据数据并分页展示
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public Map getValue(HttpServletRequest request, HttpServletResponse response) {
        // 接收分页参数（如果分页参数为null或者“”，那么分页参数默认值为0，10，反之则为接收到的实际参数x,y）
        String pageSize = request.getParameter("pageSize");
        String currPage = request.getParameter("currPage");
        String search = request.getParameter("search");
        // String sort = request.getParameter("sort");
        // String order = request.getParameter("order");
        String state = request.getParameter("state");

        // 处理分页参数（将接收到的参数存放到map中，为了在sql中实现#{pageSize},#{currPage}的值的获取）
        Map map = new HashMap();
        map.put("pageSize", Integer.parseInt(pageSize));
        map.put("currPage", Integer.parseInt(currPage));
        map.put("search", search);
        //map.put("sort", sort);
        //map.put("order", order);
        map.put("state", state);


        // 获取数据
        Map allPeriodical = roleService.findAll(map);

        return allPeriodical;
    }


    /**
     * 添加，修改 页面跳转
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public ModelAndView toAdd(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView();
        StringBuffer sb = new StringBuffer();
        StringBuffer append = new StringBuffer();

        // 获取权限菜单(result_1:后台，result_2:前台)
        Map<String, List<Permission>> result_1 = adminMenu(1);
        Map<String, List<Permission>> result_2 = adminMenu(2);

        if ("add".equals(id)) {
            modelAndView.setViewName("admin/role/addrole");
            modelAndView.addObject("title", "角色管理");
            modelAndView.addObject("stitle", "添加角色");
        } else {
            Role role = roleService.findRoleById(Integer.parseInt(id));
            List<RolePermission> rolePermissions = permissionService.listPermission(Integer.parseInt(id));
            if (null != rolePermissions && rolePermissions.size() > 0) {
                for (int i = 0; i < rolePermissions.size(); i++) {
                    int permissionId = rolePermissions.get(i).getPermissionId();
                    append = sb.append(rolePermissions.get(i).getPermissionId()).append(";");
                }
                modelAndView.addObject("functions", append.substring(0, append.toString().lastIndexOf(";")));
            }

            modelAndView.setViewName("admin/role/addrole");
            modelAndView.addObject("title", "角色管理");
            modelAndView.addObject("stitle", "修改角色");
            modelAndView.addObject("role", role);
            modelAndView.addObject("id", id);
        }
        modelAndView.addObject("online",ONLINESTATUS);
        modelAndView.addObject("functionMap", result_1);
        modelAndView.addObject("functionMap_2", result_2);
        return modelAndView;
    }

    /**
     * 添加角色
     *
     * @param role
     * @param chkfunctions
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/{id}", method = RequestMethod.POST)
    public Map change(@PathVariable("id") String id, Role role, String[] chkfunctions) {
        // 定义Map存放返回值
        Map result = new HashMap();
        Map add = null;
        // 执行插入方法
        add = roleService.add(role, chkfunctions, id);

        result.put("code", add.get("code"));
        return result;
    }

    /**
     * 删除角色
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/delete/{id}", method = RequestMethod.POST)
    public Map delete(@PathVariable("id") Integer id) {
        Map<String, Object> resultMap = roleService.deleteById(id);
        return resultMap;
    }

    /**
     * 禁用，启用角色
     *
     * @param ids
     * @param state
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/{id}", method = RequestMethod.PUT)
    public Map pubResource(@PathVariable("id") List<Integer> ids, @RequestParam(value = "state", required = false) String state, HttpServletRequest request) {

        Map mapValue = getMapValue(ids, state, request);
        Map result = roleService.change(mapValue);
        return result;
    }

    /**
     * 封装数据
     *
     * @param ids
     * @param state
     * @param request
     * @return
     */
    public static Map getMapValue(List<Integer> ids, String state, HttpServletRequest request) {

        Map paramMap = new HashMap();
        paramMap.put("ids", ids);
        paramMap.put("state", state);
        paramMap.put("request", request);

        return paramMap;
    }

    /**
     * 获取权限菜单
     *
     * @param value
     * @return
     */
    public Map<String, List<Permission>> adminMenu(int value) {
        Map<String, List<Permission>> result = new LinkedHashMap<>();
        Permission permission = new Permission();
        permission.setAttribute(value);
        List<String> pList = permissionService.findAllParentName(permission);
        for (Iterator iterator = pList.iterator(); iterator.hasNext(); ) {
            String pName = (String) iterator.next();
            List<Permission> fList = permissionService.findAllName(pName);
            System.out.println(fList);
            result.put(pName, fList);
        }
        return result;
    }

}
