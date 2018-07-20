package com.trs.web.client;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfStream;
import com.itextpdf.text.pdf.PdfWriter;
import com.trs.core.util.*;
import com.trs.core.util.Config;
import com.trs.model.*;
import com.trs.service.*;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2017/3/20.
 */
@Controller
public class ReaderController {
    //在线状态
    private static final String ONLINESTATUS = Config.getKey("online");
    @Resource
    private BookEpubService bookEpubService;
    @Resource
    private EpubSrcService epubSrcService;
    @Resource
    private BookDetailsService bookDetailsService;
    @Resource
    private BookService bookService;
    @Resource
    private OrganizeOnlineService organizeOnlineService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private MemberOnlineService memberOnlineService;
    @Resource
    private FileUploadService fileUploadService;

    /**
     * 原文阅读（epub）
     * @param bookId
     * @param title
     * @param bookwhere
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/book/reader_test")
    public String index1(String bookId, String title, String bookwhere, Model model, HttpServletRequest request) {
        Epub book = null;
        String zid = request.getParameter("title");
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        try {
            book = bookEpubService.getBookById(bookId);
            String url = book.getBookUrl();
            String rr = url.substring(url.length() - 41);
            book.setVerId(rr.substring(0, rr.length() - 9));
            book.setOwner(true);
            book.setCommentUser(true);
            String bookCode = book.getBookCode();
            // 根据zid查询出title
            BookDetails bookDetails = bookDetailsService.getBookDetailsByZid(title, bookCode);
            //跳转到指定页面需要的src
            String titleEpub = "封面";
            if (bookwhere == null || bookwhere == "") {
                titleEpub = bookDetails.getTitle();
            }
            // 改成跳转到封面
            List<EpubSrc> epSr = epubSrcService.selectByTitle(bookCode, titleEpub);
            String src = "";
            if (epSr.size() != 0) {
                src = epSr.get(0).getHtmlSRC();
            }
            book.setSrc(src);
            model.addAttribute("book", book);
        } catch (Exception e) {
//            log.error("book detail error:" + e.getMessage());
        }
        //单双页 双页返回得页面
        String readMode = CookieUtils.getCookie(request, "readMode");
        if (readMode != null && "hor".equals(readMode)) {
            return "client/reader/horizontal";
        }
        return "client/reader/vertical";
    }
    /**
     * 原文阅读。PDF
     * @param bid
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/book/readpdf",produces = "application/json; charset=utf-8")
    public ModelAndView readPdf(@RequestParam(value = "bid") Integer bid, @RequestParam(value = "page") Integer page, HttpSession session,HttpServletRequest request){

      /*  验证用户pdf阅读权限
        一.用户为空
            1.检查用户是否在机构IP下
        二.用户不为空
            1.检查用户是否存在机构
            2.检查机构权限
            2.检查用户自身权限*/

        Book book = bookService.getBookById(bid);
        String pdfpath = book.getEpub();
        ModelAndView modelAndView = new ModelAndView();
        //获取用户
        Object username = session.getAttribute("userName");
        //查找所有机构ip
        List<Organize> ip = organizeOnlineService.selectAllIP();
            //用户为空
            if (username == null){
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
                                List<Organize> org = organizeOnlineService.selectById(Integer.valueOf(id));
                                if (org.size() != 0) {
                                    //通过角色ip查找对应权限id
                                    List<RolePermission> organizePermission = permissionService.listPermission(org.get(0).getRoleId());
                                    //遍历所有的权限id
                                    for (RolePermission rolePermission : organizePermission) {
                                        System.out.println(rolePermission.getPermissionId());
                                        //根据权限id查找所有对应的权限
                                        List<Permission> permission = permissionService.selectPermissionById(rolePermission.getPermissionId());
                                        for (Permission per : permission) {
                                            if (per.getName().equals("原文阅读") || per.getName().equals("在线阅读")) {
                                                if (pdfpath != null){
                                                    modelAndView.addObject("startPage",page);
                                                    modelAndView.addObject("pdfname",pdfpath);
                                                    modelAndView.setViewName("/client/book/readPdfJsp");
                                                }else {
                                                    modelAndView.addObject("startPage",page);
                                                    modelAndView.addObject("pdfname","../../../../static/client/pdf.js/noPdf.pdf");
                                                    modelAndView.setViewName("/client/book/readPdfJsp");
                                                }
                                                return modelAndView;
                                            }
                                            Util.log(per.getName(), "authorization" , 0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else {
                    return null;
                }
            }else {
                //通过用户名获取用户信息
                Member member = memberOnlineService.findMemberByUserName(String.valueOf(username));
                //验证用户是否存在机构
                if (member.getOrganizaId() != null) {
                    //获取机构ip
                    Map organizeIp = organizeOnlineService.getOrganizeById(member.getOrganizaId());
                    List<Map> organize = (List) organizeIp.get("organizeIp");
                    //遍历
                    System.out.println(organize);
                    for (Map organizes : organize) {
                        //获取起始ip
                        String startIp = (String) organizes.get("ipStart");
                        //获取结束ip
                        String endIp = (String) organizes.get("ipEnd");
                        if (startIp != null && endIp != null && startIp != "" && endIp != "") {
                            //判断当前用户的ip是否在机构ip下
                            if (IPUtil.getIp2long(startIp) <= IPUtil.getIp2long(IPUtil.getIpAddr(request)) && IPUtil.getIp2long(IPUtil.getIpAddr(request)) <= IPUtil.getIp2long(endIp)) {
                              /*  如果存在则查找机构角色
                                 *
                                 * 并且查找自身角色
                                 * */
                                //获取机构信息
                                Organize getorganize = (Organize) organizeIp.get("organize");
                                //通过角色ip查找对应权限id
                                List<RolePermission> organizePermission = permissionService.listPermission(getorganize.getRoleId());
                                //遍历所有的权限id
                                for (RolePermission rolePermission : organizePermission) {
                                    System.out.println(rolePermission.getPermissionId());
                                    //根据权限id查找所有对应的权限
                                    List<Permission> permission = permissionService.selectPermissionById(rolePermission.getPermissionId());
                                    for (Permission per : permission) {
                                        if (per.getName().equals("原文阅读") || per.getName().equals("在线阅读")) {
                                            if (pdfpath != null){
                                                modelAndView.addObject("startPage",page);
                                                modelAndView.addObject("pdfname",pdfpath);
                                                modelAndView.setViewName("/client/book/readPdfJsp");
                                            }else {
                                                modelAndView.addObject("startPage",page);
                                                modelAndView.addObject("pdfname","../../../../static/client/pdf.js/noPdf.pdf");
                                                modelAndView.setViewName("/client/book/readPdfJsp");
                                            }
                                            return modelAndView;
                                        }
                                    }
                                }
                            }
                            //如果上面的方法没有获取到对应权限则查找自身角色
                            //通过角色id查找对应权限id
                            List<RolePermission> userPermission = permissionService.listPermission(member.getRoleId());
                            //遍历所有的权限id
                            for (RolePermission rolePermission : userPermission) {
                                System.out.println(rolePermission.getPermissionId());
                                //根据权限id查找所有对应的权限
                                List<Permission> permission = permissionService.selectPermissionById(rolePermission.getPermissionId());
                                for (Permission per : permission) {
                                    if (per.getName().equals("原文阅读") || per.getName().equals("在线阅读")) {
                                        if (pdfpath != null){
                                            modelAndView.addObject("startPage",page);
                                            modelAndView.addObject("pdfname",pdfpath);
                                            modelAndView.setViewName("/client/book/readPdfJsp");
                                        }else {
                                            modelAndView.addObject("startPage",page);
                                            modelAndView.addObject("pdfname","../../../../static/client/pdf.js/noPdf.pdf");
                                            modelAndView.setViewName("/client/book/readPdfJsp");
                                        }
                                        return modelAndView;
                                    }
                                    System.out.println(per.getName());
                                }
                            }
                        }

                        System.out.println(IPUtil.getServerIp() + "====" + IPUtil.getIp2long(startIp) + "====" + IPUtil.getIp2long(endIp));
                    }
                }else {
                    //不存在则查找自身角色
                    //通过角色ip查找对应权限id
                    List<RolePermission> userPermission = permissionService.listPermission(member.getRoleId());
                    //遍历所有的权限id
                    for (RolePermission rolePermission : userPermission) {
                        System.out.println(rolePermission.getPermissionId());
                        //根据权限id查找所有对应的权限
                        List<Permission> permission = permissionService.selectPermissionById(rolePermission.getPermissionId());
                        for (Permission per : permission) {
                            if (per.getName().equals("原文阅读") || per.getName().equals("在线阅读")) {
                                if (pdfpath != null){
                                    modelAndView.addObject("startPage",page);
                                    //io流读取pdf放啊
//                                    byte[] bytes=bookEpubService.ioPDf(pdfpath);
//                                    modelAndView.addObject("pdfname",bytes);
                                    modelAndView.addObject("pdfname",pdfpath);
                                    modelAndView.setViewName("/client/book/readPdfJsp");
                                }else {
                                    modelAndView.addObject("startPage",page);
                                    modelAndView.addObject("pdfname","../../../../static/client/pdf.js/noPdf.pdf");
                                    modelAndView.setViewName("/client/book/readPdfJsp");
                                }
                                return modelAndView;
                            }
                            System.out.println(per.getName());
                        }
                    }
                }
                return null;
            }
        return null;
    }


    /**
     * 原文阅读。PDF
     * @param bid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/book/readpdfOFF",produces = "application/json; charset=utf-8")
    public void  readpdfOFF(@RequestParam(value = "bid") Integer bid, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        Book book = bookService.getBookById(bid);
        String pdfpath = book.getEpub().replace(".pdf",".txt");
        ModelAndView modelAndView = new ModelAndView();
        //获取用户
        Object username = session.getAttribute("userName");
        //查找所有机构ip
        List<Organize> ip = organizeOnlineService.selectAllIP();
        //用户为空
        if (username != null){
//            pdfpath="D:\\cssp_resource\\中国文学年鉴2016年\\pdf\\中国文学年鉴2016年_l.pdf";
            File file=new File(pdfpath);
            byte[] data=null;
            try{
                FileInputStream input=new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                InputStream in = new ByteArrayInputStream(data);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                for(int i;(i=in.read())!=-1;){
                    baos.write(i);
                }
                response.getOutputStream().write(baos.toByteArray());
                input.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
