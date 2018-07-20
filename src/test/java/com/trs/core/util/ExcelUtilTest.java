package com.trs.core.util;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created on 2017/2/26.
 */
public class ExcelUtilTest {

    @Test
    public void readTest() {
//        String file = "C:\\Users\\zly\\Desktop\\主题词表\\889-917分类简表.xlsx";
//        String file = "C:\\Users\\zly\\Desktop\\工程技术卷1-728.xls";
        String file = "C:\\Users\\zly\\Desktop\\工程技术卷正文.xls";
//        String file = "C:\\Users\\zly\\Desktop\\11.xls";

        String outPath = "C:\\Users\\zly\\Desktop\\";
        String outFileName = "化工主题词.trs";
        StringBuffer sb = new StringBuffer();

//        List ignore = Arrays.asList("*");
        String regEx = "[*•→⑴⑵⑶⑷⑸⑹⑺⑻⑼⑽⑾⑿⒀]";
        Pattern p = Pattern.compile(regEx);
        try {
            List<Map> maps = ExcelUtils.readExcel2003(file);

            for (int i = 0; i < maps.size(); i++) {

                if (maps.get(i).get("优选词") != null) {
                    sb.append("<REC>" + "\r\n");

                    Matcher m = p.matcher(maps.get(i).get("优选词").toString());
                    String ctm = m.replaceAll("").trim();
                    sb.append("<CTM>=" + ctm + "\r\n");

//                    sb.append("<CTM>=" + maps.get(i).get("优选词") + "\r\n");
                }
                if (maps.get(i).get("英文翻译") != null) {

                    Matcher m = p.matcher(maps.get(i).get("英文翻译").toString());
                    String cle = m.replaceAll("").trim();
                    sb.append("<CLE>=" + cle + "\r\n");

//                    sb.append("<CLE>=" + maps.get(i).get("英文翻译") + "\r\n");
                }
                if (maps.get(i).get("分类号1") != null) {
                    sb.append("<CSN>=" + maps.get(i).get("分类号1"));
                    if (maps.get(i).get("分类号2") != null) {
                        sb.append(";" + maps.get(i).get("分类号2"));
                        if (maps.get(i).get("分类号3") != null) {
                            sb.append(";" + maps.get(i).get("分类号3"));
                            if (maps.get(i).get("分类号4") != null) {
                                sb.append(";" + maps.get(i).get("分类号4"));
                                if (maps.get(i).get("分类号5") != null) {
                                    sb.append(";" + maps.get(i).get("分类号5"));
                                    if (maps.get(i).get("分类号6") != null) {
                                        sb.append(";" + maps.get(i).get("分类号6"));
                                    }
                                }
                            }
                        }
                    }
                    sb.append("\r\n");
                }
                if (maps.get(i).get("关系") != null && "s".equalsIgnoreCase((String) maps.get(i).get("关系"))) {
                        /*上位词*/
                    Matcher m = p.matcher(maps.get(i).get("相关词").toString());
                    String cbt = m.replaceAll("").trim();
                    sb.append("<CBT>=" + cbt+"\r\n");

//                    sb.append("<CBT>=" + maps.get(i).get("相关词")+"\r\n");
                }
                if (maps.get(i).get("关系") != null && "z".equalsIgnoreCase((String) maps.get(i).get("关系"))) {
                        /*族首词*/
                    Matcher m = p.matcher(maps.get(i).get("相关词").toString());
                    String clt = m.replaceAll("").trim();
                    sb.append("<CLT>=" + clt+"\r\n");

//                    sb.append("<CLT>=" + maps.get(i).get("相关词")+"\r\n");
                }
                if (maps.get(i).get("关系") != null && "f".equalsIgnoreCase((String) maps.get(i).get("关系"))) {
                        /*下位词*/
                    Matcher m = p.matcher(maps.get(i).get("相关词").toString());
                    String cnt = m.replaceAll("").trim();
                    sb.append("<CNT>=" + cnt+"\r\n");

                    sb.append("<CNT>=" + maps.get(i).get("相关词")+"\r\n");
                }
                if (maps.get(i).get("关系") != null && "d".equalsIgnoreCase((String) maps.get(i).get("关系"))) {
                        /*替代词*/
                    Matcher m = p.matcher(maps.get(i).get("相关词").toString());
                    String cuf = m.replaceAll("").trim();
                    sb.append("<CUF>=" + cuf+"\r\n");

                    sb.append("<CUF>=" + maps.get(i).get("相关词")+"\r\n");
                }
                if (maps.get(i).get("关系") != null && "c".equalsIgnoreCase((String) maps.get(i).get("关系"))) {
                        /*相关词*/
                    Matcher m = p.matcher(maps.get(i).get("相关词").toString());
                    String ctr = m.replaceAll("").trim();
                    sb.append("<CRT>=" + ctr+"\r\n");

//                    sb.append("<CRT>=" + maps.get(i).get("相关词")+"\r\n");
                }
                if(maps.get(i).get("关系") == null){

                    String mm = "";
                    if (maps.get(i).get("相关词") != null) {
                        mm = maps.get(i).get("相关词").toString();
                    }
                    Matcher m = p.matcher(mm);
                    String ccc = m.replaceAll("").trim();
                    sb.append(";" + ccc +"\r\n");

//                    sb.append(";" + maps.get(i).get("相关词")+"\r\n");
                }


            }
            System.out.println(sb.toString());
            System.out.println("长度：" + maps.size());
            outTrs(outPath,outFileName,sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*输出.trs文件*/
    public void outTrs(String outPath, String fileName, StringBuffer sb) {

        File f = new File(outPath, fileName);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
            outputStreamWriter.flush();
            outputStreamWriter.write(sb.toString());
            outputStreamWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//<REC>
//<CTM>=民族矛盾
//<CBT>=矛盾
//<CPT>=Minzu maodun
//<CUF>=民族纠纷
//<CRT>=111
//<CSN>=13B
//<CLE>=National contradictions
//<REC>


}