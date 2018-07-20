package com.trs.web.admin;

import com.trs.core.breakxml.XmlDataCut;
import com.trs.core.util.Config;
import com.trs.core.util.Status;
import com.trs.core.util.ZipUtil;
import com.trs.service.BookEpubService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class BooksReadPathController {

    @Resource
    private BookEpubService bookEpubService;

    @RequestMapping(value = "uploadXMLFilePath", method = RequestMethod.GET)
    public String uploadXMLFile(@RequestParam("path") String path) throws IOException {
        Map map = new HashMap();
        //       try {
        Instant start = Instant.now();
        String sourceTemp = Config.getKey("book.sourceTemp");
        String source = Config.getKey("book.source");

        String originalFilename = path.substring(path.lastIndexOf("/"));

        System.out.println(originalFilename);

        
        String bookSoruce = source + "/" + originalFilename.split("\\.")[0];

        // 上传文件时,需要删除之前已经上传过的文件夹和文件
        //File fileOld = new File(bookSoruce);
        //bookEpubService.deleteAll(fileOld);
        
        //解压文件
        try {
            ZipUtil.unZip(source, path + "/" + originalFilename, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", 5);
            return "/admin/epub/addepub";
        }
        Instant end = Instant.now();

        // 上传文件后,解析上传的xml文件取得数据
        // 根据方正标准xml文件名需为Main.xml,位置为主文件夹下
        XmlDataCut.putXmlData(bookSoruce + "/Main.xml","1");

        map.put("msg", 1);
        return "/admin/epub/addepub";
    }
}
