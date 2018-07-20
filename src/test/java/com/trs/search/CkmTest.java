package com.trs.search;


import com.trs.ckm.soap.CkmSoapException;
import com.trs.ckm.soap.PyListWordResult;
import com.trs.ckm.soap.TrsCkmSoapClient;
import com.trs.core.util.CKMUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by xubo on 2017/3/21.
 */
public class CkmTest {
    @Test

    public void getCkmPy() throws Exception {
        long startTime = System.currentTimeMillis();

        String[] result = CKMUtil.getPyList("zhonghuarenmingongheguo", "demo", 5);
        long endTime = System.currentTimeMillis();
        System.out.println("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }

//    /**
//     * 关键词测试
//     *
//     * @throws Exception
//     */
//    @Test
//    public void getCkmkeyword() throws Exception {
//        long startTime = System.currentTimeMillis();
//        String con = "虽然电脑病毒数以万计，但真正流行并令许多用户中招的病毒就那么几百种，正是基于此，有的杀毒软件引入了闪电杀毒的概念，即只扫描当前最为流行的病毒，而对其他非流行病毒予以忽略，从而极大提高杀毒速度。除了需要付费的杀毒软件外，我们还有两个免费的专杀流行病毒的好东东，可以将当前流行的病毒一扫而光。";
//        CKMResult ckmResult = CKMUtil.keyWord(con, 5, 0);
//        String scopKey = ckmResult.getScopKey();
//        long endTime = System.currentTimeMillis();
//        System.out.println("调用花费时间");
//        System.out.println(scopKey);
//    }


    public void DySearch_Test(String sQuery, String dictname) throws Exception {
        TrsCkmSoapClient _client = new TrsCkmSoapClient("http://192.168.210.55:8070", "admin", "trsadmin");
    	/* 参数验证 */
        if (sQuery == null || sQuery.equals("")) {
            return;
        }


    	/* 混合检索 */
        String[] result = _client.DySearch(sQuery, dictname, 0);
        if (result != null) {
            for (int i = 0; i < result.length; i++) {
                System.out.println(result[i]);
            }
        }
    	/* 相似短语检索 */
        result = _client.DySearch(sQuery, dictname, 1);
        if (result != null) {
            for (int i = 0; i < result.length; i++) {
                System.out.println("相似短语----" + result[i]);
            }
        }

    	/* 相关短语检索 */
        result = _client.DySearch(sQuery, dictname, 2);
        if (result != null) {
            for (int i = 0; i < result.length; i++) {
                System.out.println("相关短语----" + result[i]);
            }
        }
    }

    @Test
    public void dytest() throws Exception {
        DySearch_Test("自动操作", "chemical");
    }


    /**
     * 添加、删除短语
     *
     * @throws Exception
     */
    @Test
    public void dyMaintain_test() throws Exception {
        //添加/删除相似短语
        TrsCkmSoapClient _client = new TrsCkmSoapClient("http://192.168.210.56:8000", "admin", "trsadmin");
        int ret = _client.DyInsertWord("demo", "测试222", 0);
        if (ret == 0) {
            /*System.out.println("短语词条添加成功");
            ret = _client.DyDeleteWord("demo", "测试222", 0);
            if (ret == 0) {
                System.out.println("短语词条删除成功");
            } else {
                System.out.println("短语词条删除失败");
            }*/

        } else {
            System.out.println("短语词条添加失败");
        }
    }

    @Test
    public void readline() {
        readFileByLines("C:\\Users\\Administrator\\Desktop\\testdy.txt");
    }

    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                TrsCkmSoapClient _client = new TrsCkmSoapClient("http://192.168.210.56:8000", "admin", "trsadmin");
                try {
                    int ret = _client.DyInsertWord("chemical", tempString, 0);
                } catch (CkmSoapException e) {
                    e.printStackTrace();
                }
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 创建短语词典
     *
     * @throws Exception
     */
    @Test
    public void CreateRelevantDictTest() throws Exception {
        TrsCkmSoapClient _client = new TrsCkmSoapClient("http://192.168.210.56:8000", "admin", "trsadmin");

    	/* 参数校验 */
        int ret = _client.CreateRelevantDict("chemical", 0);
        if (ret == 0) {
            System.out.println("创建相关短语词典成功!");
        } else {
            System.out.println("相关短语词典创建失败，错误号为：" + ret);
        }
    }

    /**
     * 短语组
     * @throws DocumentException
     */
    @Test
    public void getdygorup() throws DocumentException {

        TrsCkmSoapClient _client = new TrsCkmSoapClient("http://192.168.210.55:8070", "admin", "trsadmin");

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File("C:\\Users\\Administrator\\Desktop\\test3.xml"));
        Element root = document.getRootElement();
        ArrayList arrayList = new ArrayList();
        for (Iterator iter = root.elementIterator(); iter.hasNext(); ) {

            Element element = (Element) iter.next();
            HashMap hm = new HashMap<String, String>();
            for (Iterator iterInner = element.elementIterator(); iterInner.hasNext(); ) {
                Element elementInner = (Element) iterInner.next();

                hm.put(elementInner.getName(), elementInner.getText());

            }
            arrayList.add(hm);
        }
        for(int i=0;i<arrayList.size();i++){
            Map valuemap = (Map) arrayList.get(i);
            ArrayList list = new ArrayList();
            if(valuemap.get("CTM")!=null){
                list.add(valuemap.get("CTM"));
            }
            if(valuemap.get("CLT")!=null){
                list.add(valuemap.get("CLT"));
            }
            if(valuemap.get("CBT")!=null){
                list.add(valuemap.get("CBT"));
            }
            if(valuemap.get("CNT")!=null){
                list.add(valuemap.get("CNT"));
            }
            if(valuemap.get("CPT")!=null){
                list.add(valuemap.get("CPT"));
            }
            if(valuemap.get("CUF")!=null){
                list.add(valuemap.get("CUF"));
            }
            if(valuemap.get("CCC")!=null){
                list.add(valuemap.get("CCC"));
            }
            if(valuemap.get("CAB")!=null){
                list.add(valuemap.get("CAB"));
            }
            if(valuemap.get("CABF")!=null){
                list.add(valuemap.get("CABF"));
            }
            if(valuemap.get("CRT")!=null){
                list.add(valuemap.get("CRT"));
            }
            if(valuemap.get("CHN")!=null){
                list.add(valuemap.get("CHN"));
            }
            if(valuemap.get("CSN")!=null){
                list.add(valuemap.get("CSN"));
            }
            if(valuemap.get("CLE")!=null){
                list.add(valuemap.get("CLE"));
            }
            if(valuemap.get("CPCC")!=null){
                list.add(valuemap.get("CPCC"));
            }
            String[] strings = (String[]) list.toArray(new String[list.size()]);
            int ret= 0;
            try {
                ret = _client.DyInsertGroup("chemical", strings, 0);
            } catch (CkmSoapException e) {
                e.printStackTrace();
            }
            if(ret==0){
                System.out.println("相关短语组添加成功"+i);
            }
        }
    }
    /**
     * 创建短语词
     *
     * @throws Exception
     */
    @Test
    public void CreateRelevantGroupTest() throws Exception {
        TrsCkmSoapClient _client = new TrsCkmSoapClient("http://192.168.210.56:8000", "admin", "trsadmin");

        String[] str=new String[2];
        str[0]="相关短语";
        str[1]="关联短语";
        int ret=_client.DyInsertGroup("chemical", str, 0);
        if(ret==0)
        {
            System.out.println("相关短语组添加成功");
            str[1]="关联短语1";
            ret=_client.DyModifyGroup("chemical", 0, str, 0);
            if(ret==0)
            {
                System.out.println("相关短语组修改成功");
            }
            else
            {
                System.out.println("相关短语组修改失败");
            }
            ret=_client.DyDeleteGroup("chemical", 0, str, 0);
            if(ret==0)
            {
                System.out.println("相关短语组删除成功");
            }
        }
    }

    /**
     * 删除拼音词库
     * @throws CkmSoapException
     */
    @Test

    public void deletePy() throws CkmSoapException {
        TrsCkmSoapClient _client = new TrsCkmSoapClient("http://192.168.210.55:8070", "admin", "trsadmin");
        for(int j=0;j<1000;j++){
            PyListWordResult _wordList=_client.PyListWord("demo2", "", 1000, 0, 0);
            if(_wordList!=null)
            {
                System.out.println("总词条数："+_wordList.getDGTotal());
                for(int i=0;i<_wordList.getDGList().length;i++)
                {
                    System.out.println(_wordList.getDGList()[i]);
                    int num = _client.PyDeleteWord("demo2", _wordList.getDGList()[i], 0);
                    System.out.println(i);
                }
            }
        }
    }

    public void insertPy() throws CkmSoapException {
        TrsCkmSoapClient _client = new TrsCkmSoapClient("http://192.168.210.56:8000", "admin", "trsadmin");
        int ret=_client.PyInsertWord("chemical", "行武", 0);
    }

}