package com.trs;

import com.alibaba.fastjson.JSON;
import com.trs.core.util.Config;
import com.trs.core.util.FileUtil;
import com.trs.model.BookAnalyse;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zly on 2017-3-29.
 */
public class BookXmlTest {

    @Resource
    private static final String STATIC_RESOURCE = "C:\\Users\\zly\\upload\\images";
    private static final String BASE_DATA = Config.getKey("app.base.folder");


    @Test
    public void testXMl() {
//        String path = "C:\\Users\\zly\\Downloads\\新书\\00021-1-1-系统生物学基础";
//        String path = "C:\\Users\\zly\\Downloads\\新书\\00022-1-1-实用中药知识与技术";
//        String path = "C:\\Users\\zly\\Downloads\\新书\\00043-1-1-走进中医数字时代——中医辨证论治规律研究";
        String path = "C:\\Users\\zly\\Downloads\\新书\\00063-1-1-药物合成反应——理论与实践";
//        String path = "C:\\Users\\zly\\Downloads\\新书\\00067-1-1-装配钳工";
//        String path = "C:\\Users\\zly\\Downloads\\50种";
//        String path = "C:\\Users\\zly\\Downloads\\50种\\07012-1-1-纯碱制造技术";
//        String path = "C:\\Users\\zly\\Downloads\\50种\\00983-1-1-材料设计教程";
//        String path = "C:\\Users\\zly\\Downloads\\50种\\12748-1-1-混凝土外加剂";
        List list = xmlTest5(path,new ArrayList());
//        Object o = list.get(0);
//        List list1 = xmlAllTest(path);
//        System.out.println(list1);
//        copyImages(path);

    }

    public List xmlTest(String path) {

        List list = new ArrayList();
        File[] listFiles = new File(path).listFiles();

        for (int i = 0; i < listFiles.length; i++) {

            if (listFiles[i].isDirectory()) {
                xmlTest(listFiles[i].getPath());
            } else {
                String fileName = listFiles[i].getName();
                String filePath = listFiles[i].getPath();

                if (!filePath.contains("__MACOSX")) {
                    if (fileName.contains(".xml") && fileName.endsWith(".xml")) {
                        String xmlFilePath = listFiles[i].getPath();


                        Map map = new HashMap();
                        map.put("book", "http://docbook.org/ns/docbook");
                        SAXReader saxReader = new SAXReader();
                        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);

                        try {
                            Document document = saxReader.read(new File(xmlFilePath));
                            /*获取标题*/
                            String book_name = document.selectSingleNode("//book:title").getText();

                            Element root = null;
                            if (document.getRootElement().element("book") != null) {
                                root = document.getRootElement().element("book");
                            }else{
                                root = document.getRootElement();
                            }
                            /*提取目录信息*/
                            String catalog = "";
                            List catalogList = new ArrayList();
                            List<Element> tocdivList = root.element("toc").elements("tocdiv");

                            for (Element element : tocdivList) {
                                Map parMap = new HashMap();
                                String title = element.element("title").getTextTrim();
                                List<Element> childtocdicList = element.elements("tocdiv");
                                if (childtocdicList.size()>0) {
                                    parMap.put("title", title);
                                    List childList = new ArrayList();
                                    for(Element childElement:childtocdicList){
                                        Map ccmap = new HashMap();
                                        List<Element> ccElement = childElement.elements("tocdiv");
                                        if (ccElement.size() > 0) {
                                            ccmap.put("title", childElement.element("title").getTextTrim());
                                            List ccList = new ArrayList();
                                            for (Element cce : ccElement) {
                                                Map ccMap = new HashMap();
                                                ccMap.put("title", cce.element("title").getTextTrim());
                                                ccList.add(ccMap);
                                            }
                                            ccmap.put("children", ccList);
                                            childList.add(ccmap);
                                        }else {
                                            ccmap.put("title", childElement.element("title").getTextTrim());
                                            childList.add(ccmap);
                                        }
                                    }
                                    parMap.put("children", childList);
                                    catalogList.add(parMap);

                                }else {
                                    parMap.put("title", title);
                                    catalogList.add(parMap);

                                }
                            }
                            catalog = JSON.toJSONString(catalogList);
                            System.out.println(book_name+"---"+catalog);
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                }
            }
        }
        return list;
    }

    public List xmlTest1(String path, List list) {

        File[] listFiles = new File(path).listFiles();

        for (int i = 0; i < listFiles.length; i++) {

            if (listFiles[i].isDirectory()) {
                xmlTest1(listFiles[i].getPath(), list);
            } else {
                String fileName = listFiles[i].getName();
                String filePath = listFiles[i].getPath();

                if (!filePath.contains("__MACOSX")) {
                    if (fileName.contains(".xml") && fileName.endsWith(".xml")) {
                        String xmlFilePath = listFiles[i].getPath();

                        Map map = new HashMap();
                        map.put("book", "http://docbook.org/ns/docbook");
                        SAXReader saxReader = new SAXReader();
                        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);

                        try {
                            Document document = saxReader.read(new File(xmlFilePath));
                            /*获取标题*/
                            String book_name = document.selectSingleNode("//book:title").getText();

                            /*获取参考文献*/
                            List<Element> blockquoteList = document.selectNodes("//book:bibliography//book:blockquote");
                            if (blockquoteList.size() != 0) {
                                Iterator<Element> iterator = blockquoteList.iterator();
                                Map tmpMap = new HashMap();
                                String ckwx = "";
                                while (iterator.hasNext()) {
                                    Element element = iterator.next();
                                    List<Element> para = element.elements("para");
                                    for (Element e : para) {
                                        if (!"".equals(e.getTextTrim())) {
                                            ckwx += e.getTextTrim() + ";";
                                        }
                                    }
                                }
                                System.out.println("标准的参考文献：" + ckwx);
                            } else {
                                List<Element> sectList = document.selectNodes("//book:sect1");
                                if (sectList.size() != 0) {
                                    Iterator<Element> iterator = sectList.iterator();
                                    String ckwx = "";
                                    while (iterator.hasNext()) {
                                        Element sect = iterator.next();
                                        Element title = sect.element("title");
                                        if ("参考文献".equals(title.getTextTrim())) {
                                            List<Element> para = sect.elements("para");
                                            for (Element element : para) {
                                                ckwx += element.getTextTrim() + ";";
                                            }
                                        }

                                    }
                                    System.out.println("分段的参考文献：" + ckwx);

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                }
            }
        }
        return list;
    }

    public List xmlTest2(String path, List list) {

        File[] listFiles = new File(path).listFiles();

        for (int i = 0; i < listFiles.length; i++) {

            if (listFiles[i].isDirectory()) {
                System.out.println(listFiles[i].getPath());
                xmlTest2(listFiles[i].getPath(), list);
            } else {
                String fileName = listFiles[i].getName();
                String filePath = listFiles[i].getPath();

                if (fileName.contains(".jpg") && fileName.endsWith(".jpg")) {
                    break;
                }

                if (!filePath.contains("__MACOSX")) {
                    if (fileName.contains(".xml") && fileName.endsWith(".xml")) {
                        String xmlFilePath = listFiles[i].getPath();
                        System.out.println("xml:" + xmlFilePath);
                        Map map = new HashMap();
                        map.put("book", "http://docbook.org/ns/docbook");

                        Map temp = new HashMap();

                        SAXReader saxReader = new SAXReader();
                        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);

                        try {

                            Document document = saxReader.read(new File(xmlFilePath));
                            /*获取标题*/
                            String book_name = document.selectSingleNode("//book:title").getText();
                            temp.put("book_name", book_name.replaceAll(" ", ""));
                            /*获取ISBN*/
                            String regEx = "[ISBN ]";
                            Pattern p = Pattern.compile(regEx);
                            String isbn_full = document.selectSingleNode("//book:biblioid[@class='isbn']").getText();
                            Matcher m = p.matcher(isbn_full);
                            temp.put("isbn_full", m.replaceAll("").trim());

                            /*获取短ISBN*/
                            String isbn = document.selectSingleNode("//book:releaseinfo[@role='shortisbn']").getText();
                            temp.put("isbn", isbn);

                            /*创建pdf和cover的存放路径和重命名文件*/
                            String uuName = m.replaceAll("").trim().replaceAll("-", "");
                            String relativePath = createRelativePath();
                            String targetPath = FileUtil.getPathWithSystem(BASE_DATA + relativePath);
                            /*获取pdf*/
                            List<Element> pdfList = document.selectNodes("//book:releaseinfo[@role='singlelowerpdf' or @role='doublelowerpdf']");
                            String pdf = "";
                            for (Element e : pdfList) {
                                pdf = e.attributeValue("href");
                            }
                            String targetPdf = uuName + FileUtil.getExtend(pdf);
                            /*获取封面*/
                            List<Element> coverList = document.selectNodes("//book:cover//book:imagedata");
                            String cover = "";
                            for (Element e : coverList) {
                                cover = e.attributeValue("fileref");
                            }
                            String targetCover = uuName + FileUtil.getExtend(cover);

                            /*创建pdf和cover存放的目录*/
                            File targetFile = new File(targetPath);
                            File staticFile = new File(STATIC_RESOURCE);
                            if (!targetFile.exists()) {
                                targetFile.mkdirs();
                            }
                            if (!staticFile.exists()) {
                                staticFile.mkdirs();
                            }

                            /*获取完整pdf和cover的路径信息*/
                            String regExfile = "[/\\\\]";
                            Pattern pf = Pattern.compile(regExfile);
                            if (pdf != null && !"".equals(pdf)) {
                                pdf = pf.matcher(pdf).replaceAll("/");

                                String pdfPath = FileUtil.getPathWithSystem(filePath.replaceAll(fileName, pdf));
                                temp.put("pdf", relativePath + uuName + "/viewAdvance.html");
                                /*移动文件*/
                                if (FileUtil.fileisExist(pdfPath)) {
                                    /*移动解析后的pdf文件夹*/
                                    String pdfFilePath = FileUtil.getPathWithSystem(filePath.replaceAll(fileName, pdf.substring(0, 4)));
                                    File[] pdflist = new File(pdfFilePath).listFiles();
                                    if (pdflist != null && pdflist.length > 0) {
                                        for (File f : pdflist) {
                                            if (f.isDirectory()) {
                                                String path1 = FileUtil.getPathWithSystem(f.getPath());
                                                FileUtil.copyDirectiory(path1, targetPath + uuName);
                                            }
                                        }
                                    }
                                } else if (FileUtil.fileisExist(FileUtil.getPathWithSystem(filePath.replaceAll(fileName, "PDF/" + pdf.split("/")[1])))) {
                                    /*移动解析后的pdf文件夹*/
                                    String pdfFilePath = FileUtil.getPathWithSystem(filePath.replaceAll(fileName, "PDF/"));
                                    File[] pdflist = new File(pdfFilePath).listFiles();
                                    if (pdflist != null && pdflist.length > 0) {
                                        for (File f : pdflist) {
                                            if (f.isDirectory()) {
                                                String path1 = FileUtil.getPathWithSystem(f.getPath());
                                                FileUtil.copyDirectiory(path1, targetPath + uuName);
                                            }
                                        }
                                    }
                                } else if (FileUtil.fileisExist(FileUtil.getPathWithSystem(filePath.replaceAll(fileName, "pdf/" + pdf.split("/")[1])))) {
                                    /*移动解析后的pdf文件夹*/
                                    String pdfFilePath = FileUtil.getPathWithSystem(filePath.replaceAll(fileName, "pdf/"));
                                    File[] pdflist = new File(pdfFilePath).listFiles();
                                    if (pdflist != null && pdflist.length > 0) {
                                        for (File f : pdflist) {
                                            if (f.isDirectory()) {
                                                String path1 = FileUtil.getPathWithSystem(f.getPath());
                                                FileUtil.copyDirectiory(path1, targetPath + uuName);
                                            }
                                        }
                                    }
                                } else if (FileUtil.fileisExist(FileUtil.getPathWithSystem(filePath.replaceAll(fileName, "Pdf/" + pdf.split("/")[1])))) {
                                    /*移动解析后的pdf文件夹*/
                                    String pdfFilePath = FileUtil.getPathWithSystem(filePath.replaceAll(fileName, "Pdf/"));
                                    File[] pdflist = new File(pdfFilePath).listFiles();
                                    if (pdflist != null && pdflist.length > 0) {
                                        for (File f : pdflist) {
                                            if (f.isDirectory()) {
                                                String path1 = FileUtil.getPathWithSystem(f.getPath());
                                                FileUtil.copyDirectiory(path1, targetPath + uuName);
                                            }
                                        }
                                    }
                                }
                            }
                            if (cover != null && !"".equals(cover)) {
                                cover = pf.matcher(cover).replaceAll("/");

                                String coverPath = FileUtil.getPathWithSystem(filePath.replaceAll(fileName, cover));

                                String targetCoverPath = FileUtil.getPathWithSystem(STATIC_RESOURCE + "/" + targetCover);
                                if (FileUtil.fileisExist(coverPath)) {
                                } else if (FileUtil.fileisExist(FileUtil.getPathWithSystem(filePath.replaceAll(fileName, "Cover/" + cover.split("/")[1])))) {
                                } else if (FileUtil.fileisExist(FileUtil.getPathWithSystem(filePath.replaceAll(fileName, "cover/" + cover.split("/")[1])))) {
                                }
                            }


                            list.add("0");

                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                }
            }
        }
        return list;
    }

    public List xmlTest3(String path, List list) {

        File[] listFiles = new File(path).listFiles();

        for (int i = 0; i < listFiles.length; i++) {

            if (listFiles[i].isDirectory()) {
                xmlTest3(listFiles[i].getPath(), list);
            } else {
                String fileName = listFiles[i].getName();
                String filePath = listFiles[i].getPath();

                if (!filePath.contains("__MACOSX")) {
                    if (fileName.contains(".xml") && fileName.endsWith(".xml")) {
                        String xmlFilePath = listFiles[i].getPath();

                        Map map = new HashMap();
                        map.put("book", "http://docbook.org/ns/docbook");
                        SAXReader saxReader = new SAXReader();
                        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);

                        try {
                            Document document = saxReader.read(new File(xmlFilePath));
                            /*获取标题*/
                            String book_name = document.selectSingleNode("//book:title").getText();
                            /*获取images*/
                            Node node = document.selectSingleNode("//book:mediaobject//book:imageobject//book:imagedata");
                            String image = "";
//                            String n = node.();
//                            System.out.println(n);
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                }
            }
        }
        return list;
    }

    public List xmlTest4(String path,List list) {

        File[] listFiles = new File(path).listFiles();

        for (int i = 0; i < listFiles.length; i++) {

            if (listFiles[i].isDirectory()) {
                System.out.println(listFiles[i].getPath());
                xmlTest4(listFiles[i].getPath(),list);
            } else {
                String fileName = listFiles[i].getName();
                String filePath = listFiles[i].getPath();

                if (fileName.contains(".jpg") && fileName.endsWith(".jpg")) {
                    break;
                }
                if (!filePath.contains("__MACOSX")) {
                    if (fileName.contains(".xml") && fileName.endsWith(".xml")) {
                        String xmlFilePath = listFiles[i].getPath();
                        System.out.println("xml:" + xmlFilePath);
                        Map map = new HashMap();
                        map.put("book", "http://docbook.org/ns/docbook");

                        SAXReader saxReader = new SAXReader();
                        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);


                        try {

                            Document document = saxReader.read(new File(xmlFilePath));

                            Element root = null;
                            if (document.getRootElement().element("book") != null) {
                                root = document.getRootElement().element("book");
                            }else{
                                root = document.getRootElement();
                            }
                            /*获取标题*/
                            String book_name = document.selectSingleNode("//book:title").getText();

                            /*根据目录信息提取章节内容*/
                            List<BookAnalyse> tmpList = new ArrayList();
                            List<Element> tocdivList = root.element("toc").elements("tocdiv");
                            for (Element element : tocdivList) {
                                Map parMap = new HashMap();
                                String title = element.element("title").getTextTrim();
                                String href = "";
                                Attribute attribute = element.element("tocentry").attribute("href");
                                if (attribute != null) {
                                    href = attribute.getText().trim();
                                }
                                List<Element> nodes = document.selectNodes("//book:chapter//*[@xml:id='" + href + "']");
//                            while (iterator.hasNext()) {
//                                Element element = iterator.next();
//                                String href = "";
//                                Attribute attribute = element.element("tocentry").attribute("href");
//                                if (attribute != null) {
//                                    href = attribute.getText().trim();
//                                }
//                                List<Element> nodes = document.selectNodes("//book:chapter//*[@xml:id='" + href + "']");
//                                if (nodes != null) {
//                                    for (Element e : nodes) {
//                                        String content = "";
//                                        String title = "";
//                                        if (e.element("title") != null) {
//                                            title = e.element("title").getTextTrim();
//                                        }
//                                        List<Element> para = e.elements("para");
//                                        for (Element ee : para) {
//                                            content += ee.getTextTrim();
//                                        }
//                                        if (!"".equals(content)) {
//                                            BookAnalyse bookAnalyse = new BookAnalyse();
//                                            bookAnalyse.setBookCatalog(title);
//                                            bookAnalyse.setContent(content);
//                                            tmpList.add(bookAnalyse);
//                                        }
//                                    }
//                                }
//                            }
                                List<Element> childtocdicList = element.elements("tocdiv");
                                if (childtocdicList.size()>0) {
                                    parMap.put("title", title);
                                    List childList = new ArrayList();
                                    for(Element childElement:childtocdicList){
                                        Map ccmap = new HashMap();
                                        List<Element> ccElement = childElement.elements("tocdiv");
                                        if (ccElement.size() > 0) {
                                            ccmap.put("title", childElement.element("title").getTextTrim());
                                            List ccList = new ArrayList();
                                            for (Element cce : ccElement) {
                                                Map ccMap = new HashMap();
                                                ccMap.put("title", cce.element("title").getTextTrim());
                                            }
                                            ccmap.put("children", ccList);
                                        }else {
                                            ccmap.put("title", childElement.element("title").getTextTrim());
                                        }
                                    }
                                    parMap.put("children", childList);

                                }else {
                                    parMap.put("title", title);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                }
            }
        }
        return list;
    }

    public List xmlTest5(String path,List list) {

        File[] listFiles = new File(path).listFiles();

        for (int i = 0; i < listFiles.length; i++) {

            if (listFiles[i].isDirectory()) {
                System.out.println(listFiles[i].getPath());
                xmlTest4(listFiles[i].getPath(),list);
            } else {
                String fileName = listFiles[i].getName();
                String filePath = listFiles[i].getPath();

                if (fileName.contains(".jpg") && fileName.endsWith(".jpg")) {
                    break;
                }
                if (!filePath.contains("__MACOSX")) {
                    if (fileName.contains(".xml") && fileName.endsWith(".xml")) {
                        String xmlFilePath = listFiles[i].getPath();
                        System.out.println("xml:" + xmlFilePath);
                        Map map = new HashMap();
                        map.put("book", "http://docbook.org/ns/docbook");

                        SAXReader saxReader = new SAXReader();
                        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);


                        try {

                            Document document = saxReader.read(new File(xmlFilePath));

                            Element root = null;
                            if (document.getRootElement().element("book") != null) {
                                root = document.getRootElement().element("book");
                            }else{
                                root = document.getRootElement();
                            }
                            /*获取标题*/
                            String book_name = document.selectSingleNode("//book:title").getText();

                            /*根据目录信息提取章节内容*/
                            List<BookAnalyse> tmpList = new ArrayList();
                            List<Element> tocdivList = document.selectNodes("//book:toc//book:tocdiv");
                            Iterator<Element> iterator = tocdivList.iterator();
                            while (iterator.hasNext()) {
                                Element element = iterator.next();
                                String href = "";
                                Attribute attribute = element.element("tocentry").attribute("href");
                                if (attribute != null) {
                                    href = attribute.getText().trim();
                                }
                                List<Element> nodes = document.selectNodes("//book:chapter//*[@xml:id='" + href + "']");
                                if (nodes != null) {
                                    for (Element e : nodes) {
                                        String content = "";
                                        String title = "";
                                        String pdfpage = "";
                                        if (e.element("title") != null) {
                                            String pt = "";
                                            Element ee = e.getParent().getParent();
                                            if (ee.element("title") != null) {
                                                pt = ee.element("title").getTextTrim()/*+" "+e.getParent().element("title").getTextTrim()*/;
                                            }else {
                                                pt = e.getParent().element("title").getTextTrim();
                                            }
                                            title =pt+";"+ e.element("title").getTextTrim();
                                        }
                                        List<Element> para = e.elements("para");
                                        for (Element ee : para) {
                                            content += ee.getTextTrim();
                                        }

                                        Element info = e.element("info");

                                        if (info != null) {
                                            List<Element> pagenums = info.elements("pagenums");
                                            if (pagenums != null) {
                                                for (Element p : pagenums) {
                                                    if ("pdfpage".equals(p.attributeValue("role"))) {
                                                        pdfpage = p.getTextTrim();
                                                    }
                                                }
                                            }
                                        }
                                        if (!"".equals(content)) {
                                            BookAnalyse bookAnalyse = new BookAnalyse();
                                            bookAnalyse.setBookCatalog(title);
                                            bookAnalyse.setContent(content);
                                            bookAnalyse.setPdfpage(pdfpage);
                                            tmpList.add(bookAnalyse);
                                        }
                                    }
                                }
                            }
                            list.add(tmpList);
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                }
            }
        }
        return list;
    }


    public void copyImages(String path) {

        File[] listFiles = new File(path).listFiles();

        for (int i = 0; i < listFiles.length; i++) {

            if (listFiles[i].isDirectory()) {
                copyImages(listFiles[i].getPath());
            } else {
                String filePath = listFiles[i].getPath();

                if (!filePath.contains("__MACOSX")) {

                    if (filePath.contains("image") || filePath.contains("Image")) {
                        File[] fs = new File(path).listFiles();
                        File imgFile = new File(STATIC_RESOURCE);
                        if (!imgFile.exists()) {
                            imgFile.mkdirs();
                        }
                        for (int j = 0; j < fs.length; j++) {
                            String imagesPath = fs[j].getPath();
                            String imagesName = fs[j].getName();

                            try {
                                copyFile(FileUtil.getPathWithSystem(imagesPath), FileUtil.getPathWithSystem(STATIC_RESOURCE + "/" + imagesName));
//                                FileUtil.copy(FileUtil.getPathWithSystem(imagesPath), FileUtil.getPathWithSystem(STATIC_RESOURCE + "/" + imagesName));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    private static void copyFile(final String path, final String targetPath) {


        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    FileUtil.copy(FileUtil.getPathWithSystem(path), FileUtil.getPathWithSystem(targetPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private static String createRelativePath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "/book/" + sdf.format(new Date()) + "/";
    }

    @Test
    public void sp() {
        String s = "16 卫生部.卫生部公布2003年全国重大食物中毒情况（新闻稿）、卫生部关于2002年重大食物中毒情况的通报，2003.［2004年8月30日］";

        String ss = "［" + 1 + "］ " + s.replaceAll(" ", " ").substring(s.replaceAll(" ", " ").indexOf(" ") + 1, s.length());
        String sss = "［" + 1 + "］" + s.substring(s.indexOf("］") + 1, s.length());

        System.out.println("s---:" + s);
        System.out.println("ss---:" + ss);
        System.out.println("sss---:" + sss);
    }
}