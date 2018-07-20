package com.trs.web.admin;

import com.trs.core.annotations.Permission;
import com.trs.core.breakxml.XmlDataCut;
import com.trs.core.util.Config;
import com.trs.core.util.Status;
import com.trs.core.util.Util;
import com.trs.core.util.ZipUtil;
import com.trs.model.Book;
import com.trs.model.Epub;
import com.trs.model.EpubSrc;
import com.trs.model.VerBook;
import com.trs.service.BookEpubService;
import com.trs.service.BookService;
import com.trs.service.EpubSrcService;
import com.trs.service.FileUploadService;
import com.trs.service.impl.EpubServiceImpl;
import com.trs.service.impl.FileUploadServiceImpl;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * Created by xubo on 2017/3/20.
 */
@Controller
@RequestMapping("/admin")
public class BooksEpubController {

    @Resource
    private FileUploadService fileUploadService;
    @Resource
    private BookService bookService;
    @Resource
    private BookEpubService bookEpubService;
    @Resource
    private EpubSrcService epubSrcService;


    // 图片宽尺寸
    public static final int IMAGEW = 390;
    // 图片高尺寸
    public static final int IMAGEH = 520;


    /**
     * 跳转epub录入页面
     * 如果book表没有这本年鉴,那么录入得src表 缺少bookcode,将无法进行在线阅读
     *
     * @return
     */
    @Permission(url = "/admin-epub")
    @RequestMapping(value = "/admin-epub", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/admin/epub/addepub");
        modelAndView.addObject("title", "数据录入");
        return modelAndView;
    }

    /**
     * 数据录入 检查上传文件，并复制到资源文件下
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/source-check", method = RequestMethod.POST)
    public Map sourceCheck(HttpServletRequest request) {
//        String folderName = request.getParameter("folderName").trim();
//        String username = request.getParameter("username");
        Map map = new HashMap();
        //检查上传文件位置文件是否存在
        String FileName = Config.getKey("book.source.file");
        String FileSourceName = Config.getKey("book.source");
        File filecheck = new File(FileName);
        if (filecheck.isDirectory()) {
            String[] files = filecheck.list();
            if (files.length > 0) {
                System.out.println("文件复制开始！");
                fileUploadService.copyDir(FileName, FileSourceName);
                System.out.println("文件复制完成！");
                System.out.println("获取文件名称开始！");
                List list = fileUploadService.FileName(FileName);
                System.out.println("获取文件名称结束！");
                map.put("status", list);
            } else {
                map.put("status", 1);
                System.out.println("目录为空 " + filecheck.getPath() + " 未上传解压资源！ ");
            }
        }
        // 文件夹删除
//        Map map = userCenterAdminService.collectFolderDelete(map1);
        return map;
    }

    /**
     * 数据录入 解析文件xml
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/source-resolve", method = RequestMethod.POST)
    public Map sourceResolve(HttpServletRequest request) {
//        String folderName = request.getParameter("folderName").trim();
//        String username = request.getParameter("username");
        Map map = new HashMap();
        //检查上传文件位置文件是否存在
        String FileName = Config.getKey("book.source.file");
        //临时文件日志路径
        String temporaryFileName = Config.getKey("book.temporary.log");
        File filecheck = new File(FileName);
        if (filecheck.isDirectory()) {
            String[] files = filecheck.list();
            if (files.length > 0) {
                System.out.println("获取文件名称开始！");
                List list = fileUploadService.FileName(FileName);
                System.out.println("获取文件名称结束！");
                if (list.size() > 0) {
                    System.out.println("----------------解析开始！-------------------");
                    Integer end = null;
                    for (int i = 0; i < list.size(); i++) {
                        Object name = list.get(i);
                        //解析开始先删除临时日志下的内容
                        fileUploadService.delFolder(temporaryFileName);
                        end = XmlDataCut.putXmlDataBig(name + "\\Main.xml", "2");
                        //pdf文件生成txt文件
                        String  namePDF=name.toString();
                        String  resourceNamePDF=namePDF.replace("cssp_source_file","cssp_resource").replace("\\","/")+"/pdf";
                        List listPdfname=fileUploadService.FilefoloderName(resourceNamePDF);
                        if(listPdfname.size()>0){
                            for(int p=0;p<listPdfname.size();p++){
                                Object namePdf=listPdfname.get(p);
                                fileUploadService.readPdfjiami(namePdf.toString(),resourceNamePDF);
                            }
                        }
//                        //删除pdf源文件
//                        fileUploadService.delAllFile(FileName);
                        if (end.equals(1)) {
                            map.put("status", 2);
                            System.out.println("上传失败！！！！！！！！！！！");
                            return map;
                        } else {
                            System.out.println("已经处理完------------>" + name.toString());
                            Util.logXml("已经处理完该年鉴,请稍后...", "examination", 0, 1);
                            //fileUploadService.deleteDir(FileName);
                        }
                    }
                    if (end.equals(2)) {
                        map.put("status", 1);
                        //删除上传的文件
                        fileUploadService.delAllFile(FileName);
                    }
                    System.out.println("----------------解析完毕！-------------------");

                }

            }
        } else {
            System.out.println("----------------无需要解析内容！-------------------");
            map.put("status", 0);
        }
        //fileUploadService.deleteDir(FileName);
        // 文件夹删除
//        Map map = userCenterAdminService.collectFolderDelete(map1);
        return map;
    }

    /**
     * 数据录入s刷新读取日志
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/source-refresh", method = RequestMethod.POST)
    public Map sourceRefresh(HttpServletRequest request) {
//        String folderName = request.getParameter("folderName").trim();
//        String username = request.getParameter("username");
        Map map = new HashMap();
        //获取临时日志文件
        String FileName = Config.getKey("book.temporary.log");
        String FileforeverName = Config.getKey("book.forever.log");
        List FileforeverList = fileUploadService.FilefoloderName(FileforeverName);
        map.put("log", FileforeverList);
        List list = fileUploadService.FilefoloderName(FileName);
        if (list.size() > 0) {
            List listlog = fileUploadService.readLog(list.get(0).toString());
            map.put("log", listlog);
        } else {
            map.put("log", 1);
        }
        return map;
    }
    /**
     * 下载日志
     *
     * @return
     */
    @RequestMapping(value = "/source-downloadLog")
    public void sourcedownloadLog(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取临时日志文件
        String FileforeverName = Config.getKey("book.forever.log");
        File filecheck = new File(FileforeverName);

        InputStream fin = null;
        ServletOutputStream out = null;
        if (filecheck.isDirectory()) {
            String[] files = filecheck.list();
            if (files.length > 0) {
                try {
                    fin = new FileInputStream(FileforeverName + "/" + files[0]);
                    out = response.getOutputStream();
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/x-download");
                    response.addHeader("Content-Disposition", "attachment;filename=examination.log");

                    byte[] buffer = new byte[1024];
                    int bytesToRead = -1;
                    // 通过循环将读入的Word文件的内容输出到浏览器中
                    while ((bytesToRead = fin.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesToRead);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fin != null) {
                        fin.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                }
            }
        }

    }

    /**
     * 下载日志文件名称
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/source-download", method = RequestMethod.POST)
    public Map sourcedownload(HttpServletRequest request) {
//        String folderName = request.getParameter("folderName").trim();
//        String username = request.getParameter("username");
        Map map = new HashMap();
        //获取永久日志文件
        String FileforeverName = Config.getKey("book.forever.log");
        List listDewnloadName = fileUploadService.FilefoloderName(FileforeverName);
        map.put("log", listDewnloadName);
        return map;
    }


    // 上传资源包
    @RequestMapping(value = "uploadResourceFile", method = RequestMethod.POST)
    public String uploadResourceFile(MultipartFile multipartFile, Model model) throws IOException {
        try {
            if (multipartFile.getSize() == 0) {
                model.addAttribute("msg", 0);
                return "/admin/epub/addepub";
            }
            long fileSize = multipartFile.getSize();
            String title = multipartFile.getOriginalFilename().replace(".epub", "");
            String s = multipartFile.getOriginalFilename();
            if (s.indexOf("epub") == -1) {
                model.addAttribute("msg", 2);
                return "/admin/epub/addepub";
            }

            String originalFileName = multipartFile.getOriginalFilename();
            // 根据名字查询一下是否已经添加 添加就更新
            List<Epub> selectEpub = bookEpubService.selectEpub(title);
            // 查询book表中得bookcode
            List<Book> bo = bookService.selectBookEpub(title);
            String bookcode = "";
            if (bo.size() != 0) {
                bookcode = bo.get(0).getBookcode();
            }

            FileUploadServiceImpl im = new FileUploadServiceImpl();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            if (selectEpub.size() > 0) {
                uuid = selectEpub.get(0).getBookId();
                Map deleEpubSrc = epubSrcService.deleteSrc(bookcode);
            }
            Book book = new Book();
            book.setTitle(title);
            book.setEpub(uuid);
            book.setBookcode(bookcode);
            VerBook bk = new VerBook();
            Short platform = 9;
            if (selectEpub.size() > 0) {
                //        // 删除相关目录
                File fcomplete = new File(FileUploadServiceImpl.getFileFolderPath(
                        book, platform) + "/complete");
                FileUtils.deleteDirectory(fcomplete);

                File ftemp = new File(FileUploadServiceImpl.getFileFolderPath(book,
                        platform) + "/temp");
                FileUtils.deleteDirectory(ftemp);
                File f = new File(FileUploadServiceImpl.getFileFolderPath(book,
                        platform));
                if (!f.exists()) {
                    f.mkdirs();
                }

            }

            // 上传epub文件

            fileSize = im.uploadFileForZip(book,
                    platform, multipartFile, "epub", FilenameUtils.getBaseName(originalFileName));

            String epubPath = fileUploadService
                    .getFilePathForZip(book, platform)
                    + "book.epub";
            try {
                EpubServiceImpl epubService = new EpubServiceImpl(
                        epubPath);
                epubService.unEpub(fileUploadService
                        .getEpubCompleteURL(book, platform));


                String str = epubService
                        .getEpubCatalog(fileUploadService
                                .getEpubCompleteURL(book,
                                        platform), book);

                //获取到目录得list 存入数据库
                List<Map<String, Object>> list = epubService.getEpubCatalogSrc(fileUploadService.getEpubCompleteURL(book, platform), book);
                Date d = new Date();
                for (Map<String, Object> m : list) {
                    String src = "";
                    String srcTitle = "";
                    for (String k : m.keySet()) {
//                        System.out.println(k + " : " + m.get(k));
                        if ("src".equals(k)) {
                            src = (String) m.get(k);
                        } else {
                            srcTitle = (String) m.get(k);
                        }
                    }
                    EpubSrc er = new EpubSrc();
                    er.setTitle(srcTitle);
                    er.setHtmlSRC(src);
                    er.setBookCode(bookcode);
                    er.setGmtCreate(d);
                    Map mmp = epubSrcService.insertEpubSrc(er);

                }
                // 把uuid插入到book表
                Map map = bookService.upBookEpub(book);
                // 插入epub表中
                Epub epub = new Epub();
                epub.setBookId(uuid);
                epub.setBookName(title);
                String bookurl = title + "/" + "epub" + "/" + platform + "/" + uuid + "/book.zip";
                epub.setBookUrl(bookurl);
                epub.setCatalogue(str);
                epub.setPlatform(platform);
                epub.setBookCode(bookcode);
                if (selectEpub.size() > 0) {
                    epub.setUpdatetime(d);
                    Map epubMap = bookEpubService.updateEpub(epub);
                    model.addAttribute("msg", 1);
                } else {
                    epub.setCreatetime(d);
                    Map epubMap = bookEpubService.insertEpub(epub);
                    model.addAttribute("msg", 1);
                }


            } catch (Exception e) {
                model.addAttribute("msg", 3);
                e.printStackTrace();
                return "/admin/epub/addepub";
            }

        } catch (Exception ex) {
            model.addAttribute("msg", 3);
            ex.printStackTrace();
            return "/admin/epub/addepub";
        }
        return "/admin/epub/addepub";
    }

    @RequestMapping(value = "uploadXMLFile", method = RequestMethod.POST)
    public String uploadXMLFile(MultipartFile multipartFile, Map map) throws IOException {

        try {
            Instant start = Instant.now();
            String sourceTemp = Config.getKey("book.sourceTemp");
            String source = Config.getKey("book.source");
            String originalFilename = multipartFile.getOriginalFilename();
            if (multipartFile.getSize() == 0) {
                map.put("msg", 0);
                return "/admin/epub/addepub";
            }
            if (originalFilename.indexOf("zip") == -1) {
                map.put("msg", 4);
                return "/admin/epub/addepub";
            }

            //上传文件
            if (!multipartFile.isEmpty()) {
                File file = new File(sourceTemp + "/" + originalFilename);
                file.mkdirs();
                multipartFile.transferTo(file);
            }
            String bookSoruce = source + "/" + originalFilename.split("\\.")[0];

            // 上传文件时,需要删除之前已经上传过的文件夹和文件
            File fileOld = new File(bookSoruce);
            bookEpubService.deleteAll(fileOld);
            //解压文件
            try {
                Status status = ZipUtil.unZip(source, sourceTemp + "/" + originalFilename, "GBK");
            } catch (Exception e) {
                e.printStackTrace();
                map.put("msg", 5);
                return "/admin/epub/addepub";
            }
            Instant end = Instant.now();

            // 上传文件后,解析上传的xml文件取得数据
            // 根据方正标准xml文件名需为Main.xml,位置为主文件夹下
            XmlDataCut.putXmlData(bookSoruce + "/Main.xml", "1");
            //pdf文件生成txt文件
            String  resourceNamePDF=bookSoruce+"/pdf";
            List listPdfname=fileUploadService.FilefoloderName(resourceNamePDF);
            if(listPdfname.size()>0){
                for(int p=0;p<listPdfname.size();p++){
                    Object namePdf=listPdfname.get(p);
                    fileUploadService.readPdfjiami(namePdf.toString(),resourceNamePDF);
                }
            }

            map.put("msg", 1);
            return "/admin/epub/addepub";
        } catch (IOException e) {
            e.printStackTrace();
            map.put("msg", 3);
            return "/admin/epub/addepub";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return "/admin/epub/addepub";
    }
}
