package com.trs.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.alibaba.druid.util.StringUtils;
import com.trs.core.util.EncryptUtil;
import com.trs.mapper.EpubSrcMapper;
import com.trs.model.Book;
import com.trs.model.EpubSrc;
import com.trs.service.EpubService;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class EpubServiceImpl implements EpubService {

    private String epubPath;

    public EpubServiceImpl(String epubPath) throws Exception {
        if (StringUtils.isEmpty(epubPath)) {
            File file = new File(epubPath);
            if (!file.exists()) {
                throw new Exception();
            }
        } else {
            this.epubPath = epubPath;
        }
    }

    /**
     * 抽取Epub目录
     *
     * @param targetPath
     * @return
     */
    public String getEpubCatalog(String targetPath, Book book) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        String str = "";
        if (targetPath.lastIndexOf("/") != targetPath.length() - 1) {
            targetPath += "/";
        }
        File file=new File(targetPath);
        File fileXml=new File(targetPath + "METAINF/container.xml");
        if(file.exists()){
            if(!fileXml.exists()){
                this.unEpub(targetPath);
            }
        }
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
//			String s="http://localhost:8080/cssp_resource/世界经济年鉴2015/epub/9/5e7da6b081024b299e90a550e7811013/complete/METAINF/container.xml";
            Document doc = db.parse(targetPath + "METAINF/container.xml");
            // 得到根节点
            Element root = doc.getDocumentElement();
            NodeList nl = root.getElementsByTagName("rootfile");
            String strOpf = "";
            for (int i = 0; i < nl.getLength(); i++) {
                Element e = (Element) nl.item(i);
                if (e.getAttribute("media-type").equals(
                        "application/oebps-package+xml")) {
                    strOpf = e.getAttribute("full-path");
                    break;
                }

            }
            File files= new File(targetPath + strOpf);
            String opfParentUrl = "";
            if (files.exists()) {
                opfParentUrl = files.getParent();
            }
            Document docopf = db.parse(targetPath + strOpf);
            Element rootopf = docopf.getDocumentElement();
            NodeList nlopf = rootopf.getElementsByTagName("spine");
            String ncx = nlopf.item(0).getAttributes().getNamedItem("toc")
                    .getNodeValue();
            NodeList nlopfNcx = rootopf.getElementsByTagName("item");
            String ncxUrl = "";
            for (int j = 0; j < nlopfNcx.getLength(); j++) {
                Element e = (Element) nlopfNcx.item(j);
                if (e.getAttribute("id").equals(ncx)) {
                    ncxUrl = e.getAttribute("href");
                    break;
                }
            }
            Document docncx = db.parse(opfParentUrl + "/" + ncxUrl);
            str = getEpubDirectory(docncx,book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 抽取Epub目录
     *docncx是目录存放路径
     * @param docncx
     * @return
     */
    public  String getEpubDirectory(Document docncx,Book book) {
        StringBuilder str = new StringBuilder();
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList navPointList = null;
        try {
            navPointList = (NodeList) xpath.evaluate("//navMap/navPoint",
                    docncx, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        String bookcode = book.getBookcode();

        for (int i = 0; i < navPointList.getLength(); i++) {
            Element e = (Element) navPointList.item(i);
            NodeList textList = e.getElementsByTagName("text");
            for (int j = 0; j < textList.getLength(); j++) {
                    Element et = (Element) textList.item(j);
                    Text t = (Text) et.getFirstChild();
                    if (t != null) {
                        str.append(t.getNodeValue() + "\n");
                    }
            }
        }
        return str.toString();
    }

    /**
     * 获取目录存放位置
     * @param targetPath
     * @param book
     * @return
     */
    public List<Map<String,Object>> getEpubCatalogSrc(String targetPath, Book book) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        List<Map<String,Object>> str = null;
        if (targetPath.lastIndexOf("/") != targetPath.length() - 1) {
            targetPath += "/";
        }
        File file=new File(targetPath);
        File fileXml=new File(targetPath + "METAINF/container.xml");
        if(file.exists()){
            if(!fileXml.exists()){
                this.unEpub(targetPath);
            }
        }
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
//			String s="http://localhost:8080/cssp_resource/世界经济年鉴2015/epub/9/5e7da6b081024b299e90a550e7811013/complete/METAINF/container.xml";
            Document doc = db.parse(targetPath + "METAINF/container.xml");
            // 得到根节点
            Element root = doc.getDocumentElement();
            NodeList nl = root.getElementsByTagName("rootfile");
            String strOpf = "";
            for (int i = 0; i < nl.getLength(); i++) {
                Element e = (Element) nl.item(i);
                if (e.getAttribute("media-type").equals(
                        "application/oebps-package+xml")) {
                    strOpf = e.getAttribute("full-path");
                    break;
                }

            }
            File files= new File(targetPath + strOpf);
            String opfParentUrl = "";
            if (files.exists()) {
                opfParentUrl = files.getParent();
            }
            Document docopf = db.parse(targetPath + strOpf);
            Element rootopf = docopf.getDocumentElement();
            NodeList nlopf = rootopf.getElementsByTagName("spine");
            String ncx = nlopf.item(0).getAttributes().getNamedItem("toc")
                    .getNodeValue();
            NodeList nlopfNcx = rootopf.getElementsByTagName("item");
            String ncxUrl = "";
            for (int j = 0; j < nlopfNcx.getLength(); j++) {
                Element e = (Element) nlopfNcx.item(j);
                if (e.getAttribute("id").equals(ncx)) {
                    ncxUrl = e.getAttribute("href");
                    break;
                }
            }
            Document docncx = db.parse(opfParentUrl + "/" + ncxUrl);
            str = getSrc(docncx,book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    /**
     * 获取目录
     * @param docncx
     * @param book
     * @return
     */
    public List<Map<String,Object>> getSrc(Document docncx,Book book) {
        List<Map<String,Object>> listdown=new ArrayList<Map<String,Object>>();
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList navPointList = null;
        try {
            navPointList = (NodeList) xpath.evaluate("//navMap/navPoint",
                    docncx, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        String bookcode = book.getBookcode();

        for (int i = 0; i < navPointList.getLength(); i++) {
            Element e = (Element) navPointList.item(i);
            NodeList textList = e.getElementsByTagName("text");
            NodeList textList1 = e.getElementsByTagName("content");
            for (int j = 0; j < textList.getLength(); j++) {
                    Element et = (Element) textList.item(j);
                    Element src=(Element) textList1.item(j);
                    String ad=src.getAttribute("src");
                    Text t = (Text) et.getFirstChild();
                    Map<String,Object> map=new HashMap<String,Object>();
                    map.put("title",t.getNodeValue());
                    map.put("src",ad);
                    listdown.add(map);
            }
        }
        return listdown;
    }
    /**
     * 解压Epub
     *
     * @param targetPath
     */
    public void unEpub(String targetPath) {
        System.out.println("targetPath aaaaaaaaaaaaaaaa=================" + targetPath);
        System.out.println("epubPath aaaaaaaaaaaaaaaa=================" + epubPath);
        if (targetPath.lastIndexOf("/") != targetPath.length() - 1) {
            targetPath += "/";
        }
        try {
            EncryptUtil.unzip(epubPath, targetPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
