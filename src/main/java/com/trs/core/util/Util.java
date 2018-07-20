package com.trs.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.trs.client.TRSConnection;
import com.trs.client.TRSConstant;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.dom4j.Attribute;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangpeng on 15/5/8.
 */
public class Util {

    public static SimpleDateFormat fmtAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 生成MD5加密的字符串密码
     *
     * @param plainText
     * @return
     */
    public static String toMD5(String plainText) {
        try {
            // 生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            // 通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            // 生成具体的md5密码到buf数组
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
//			System.out.println("32位: " + buf.toString());// 32位的加密
//			System.out.println("16位: " + buf.toString().substring(8, 24));// 16位的加密，其实就是32位加密后的截取
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param file
     * @return
     */
    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    /**
     * 查询日期是否为周末
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static boolean weekendMethod(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            //System.out.println("是周末");
            return true;
        } else
            //System.out.println("不是周末！");
            return false;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取字符出现次数
     * @param str
     * @param keyword
     * @return
     */
    public static int findStr(String str, String keyword) {
        int count = 0;
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(str);
        while (m.find()) {
            count++;
        }
        return count;
    }

    /**
     * 非null化
     * @param inStr
     * @return
     */
    public static String rmNull(String inStr) {
        if (inStr == null) {
            return "";
        } else {
            return inStr.trim();
        }
    }

    /**
     * 获取真实IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 将Arrays.toString()方法转换的字符串转换为List
     *
     * @param value
     * @return
     */
    public static List string2List(String value) {
        value = value.replace("[", "");
        value = value.replace("]", "");
        value = value.replaceAll(" ", "");
        return Arrays.asList(value.split(","));
    }

    /**
     * 判断列表中是否包含字符串
     */
    public static boolean isContain(List<String> list, String word) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(word)) {
                return true;
            } else {
            }
        }
        return false;
    }

    /**
     * list非空化
     * @param list
     * @return
     */
    public static boolean isDBNull(List list){
        if(list == null || list.size() == 1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * list非空化
     * @param list
     * @return
     */
    public static boolean isNull(List list){
        if(list == null || list.size() == 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * map非空化
     * @param map
     * @return
     */
    public static boolean isNull(Map map){
        if(map == null || map.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 更新点击量
     * @param tableName
     * @param upValue
     * @param upWhere
     * @return
     */
    public static int updateReadCount(String tableName,String upValue,String upWhere) {
        TRSUtil conn = new TRSUtil();
        TRSConnection connect = null;
        int iUpdate=0;
        // TRS数据库读出条数
        int TRSCount = 0;
        try {
            connect = conn.getTrsconnect();
//            long startTime = System.currentTimeMillis();
//            connect.setMaintOptions('\n', null, null, -1, -1);
//		    int iUpdate11=connect.executeUpdate("Demo2","system","版名=摄影11-17","标题=学超杯网球赛举行");
//            System.out.println("成功的更新了 " + iUpdate + "个记录");

            iUpdate = connect.executeUpdate(tableName,"SYSTEM",upValue,upWhere);

//            long endTime = System.currentTimeMillis();
//            System.out.println("调用API花费时间(单位：毫秒)：" + (endTime - startTime));
            //System.out.println(rs.getTotalHitPoint());

        } catch (TRSException e) {
            e.printStackTrace();
        } finally {
            if (connect != null) {
                connect.clean();
                connect.close();
            }
        }
        return iUpdate;
    }

    /**
     * 处理标签
     * @param info
     * @return
     */
    public static List<String> getFootImport(String info){
        List<String> list = new ArrayList<>();
        if(toStr(info).equals("")){
            return list;
        }
        org.jsoup.nodes.Document doc = Jsoup.parse(info);
        Elements msgs = doc.getElementsByTag("span");
        for (org.jsoup.nodes.Element msg : msgs) {
            String cla = msg.attr("class");
            if(cla != null && cla.equals("f-in-line-boot")){
                if (!"".equals(toStr(msg.attr("title")))){
                    list.add(toStr(msg.attr("title")));
                }
            }
        }
        return list;
    }

    /**
     * 防止脚本注入
     * @param info
     * @return
     */
    public static String setAgnattacks(Object info){
        String rtn = Util.toStr(info);
        if(rtn.equals("")){
            return "";
        }else{
            rtn = rtn.replace("\"", "").replace("'", "").replace("<", "").replace(">", "");
            rtn = rtn.replace("[", "").replace("]", "");
        }
        return rtn;
    }

    /**
     * obj——int转换
     * @param obj
     * @param defaultValue
     * @return
     */
    public static int toInt(Object obj, int defaultValue) {
        if (obj == null) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(obj.toString());
            } catch (Exception e) {
                return defaultValue;
            }
        }
    }
    
    /**
     * Map转Json
     * @param map
     * @return
     */
    public static String mapToJson(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        try {
            Map<String, String> mapJson = new HashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                mapJson.put(entry.getKey(), toStr(entry.getValue()));
            }
            JSONObject json = JSONObject.parseObject(JSON.toJSONString(mapJson));
            return json.toJSONString();
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * json转map
     * @param str
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String str) {
        Map<String, Object> map = new HashMap<>();
        if(str == null || str.equals("")){
            return map;
        }
        try {
            JSONObject json = JSON.parseObject(str);
            map = JSONObject.toJavaObject(json, Map.class);
            return map;
        } catch (Exception e) {
            return map;
        }
    }

    /**
     * 防止空指针
     * @param attribute
     * @return
     */
    public static String nte(Attribute  attribute) {
        if (attribute == null) {
            return "";
        } else {
            return attribute.getValue();
        }
    }

    /**
     * 防止空指针
     * @param obj
     * @return
     */
    public static String toStr(Object obj) {

        if (obj == null) {
            return "";
        } else {
            return obj.toString().trim();
        }
    }

    /**
     * 防止空指针
     * @param obj
     * @param dft
     * @return
     */
    public static String toStr(Object obj, Object dft) {
        if (obj == null) {
            return toStr(dft);
        } else if (obj.toString().trim().equals("")) {
            return toStr(dft);
        } else {
            return obj.toString().trim();
        }
    }
    public static String getNowTime() {
        return fmtAll.format(new Date());
    }

    /**
     * LOG输出
     * @param in
     * @param name
     * @param type 0:打印控制台
     */
    public static void log(Object in,String name, int type){
        if(name.equals("")){
            name = "debug";
        }
        String content = Util.toStr(in);
        if(type == 0) {
            System.out.println(content);
        }
        String path = Config.getKey("book.local.log");
        path = path.replace("\\", "/");
        if(!path.endsWith("/")){
            path += "/";
        }
        File folder = new File(path);    
        if(!folder.exists() && !folder.isDirectory())    
        {    
            folder.mkdirs();
        }
        
        String date = Util.getNowTime().substring(0, 10);
        
        File file = new File(path + date + "-" + name + ".log");
        if(!file.exists())    
        {    
            try {    
                file.createNewFile();    
            } catch (IOException e) {    
                e.printStackTrace();    
            }    
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
            writer.write(Util.getNowTime() + " " + content + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 去掉html标签
     * @param inputString
     * @return
     */
    public static String rmHTMLTag(Object inputString) {
        String htmlStr = toStr(inputString);
        if(htmlStr.equals("")){
            return "";
        }
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 剔除空格行
        textStr = textStr.replaceAll("[ ]+", " ");
        textStr = textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        textStr = textStr.replace("&nbsp;", "");
        textStr = textStr.replace("&emsp;", "");
        return textStr;
    }

    /**
     * 日期格式化
     * @param date
     * @return
     */
    public static String fmtDate(String date){
        String rtn = "";
        try{
            date = date.replace("年", ",").replace("月", ",").replace("日", "");
            String [] dates = date.split(",");
            if(dates.length >= 3){
                String day = "";
                if(dates[2].indexOf("—") > -1){
                    day = dates[2].substring(0, dates[2].indexOf("—"));
                    day = day.length()==1?"0"+day:day;
                }else{
                    day = dates[2].length()==1?"0"+dates[2]:dates[2];
                }
                rtn = dates[0]
                        + "/"
                        + (dates[1].length()==1?"0"+dates[1]:dates[1])
                        + "/"
                        + day;
            }
        }catch(Exception e){
            return "";
        }
        return rtn;
    }

    /**
     * 省份
     */
    private static String []pros = {"北京",
            "浙江",
            "天津",
            "安徽",
            "上海",
            "福建",
            "重庆",
            "江西",
            "香港",
            "山东",
            "澳门",
            "河南",
            "内蒙古",
            "湖北",
            "新疆",
            "湖南",
            "宁夏",
            "广东",
            "西藏",
            "海南",
            "广西",
            "四川",
            "河北",
            "贵州",
            "山西",
            "云南",
            "辽宁",
            "陕西",
            "吉林",
            "甘肃",
            "黑龙江",
            "青海",
            "江苏",
            "台湾"};

    /**
     * 省份遍历非空
     * @param info
     * @return
     */
    public static String getProv(String info){
        String rtn = "";
        if (info == null || info.trim().equals("")) {
            return "";
        }
        try {
            String[] infos = info.split(";");
            for (String _info : infos) {
                for(String str:pros){
                    if(_info.indexOf(str) > -1){
                        return str;
                    }
                }
            }
        } catch (Exception e) {
            return rtn;
        }
        return rtn;
    }

    /**
     * 汉字阿拉伯数字判断
     * @param info
     * @return
     */
    public static String rmNumIndex(String info){
        if(info == null || info.trim().equals("")){
            return "";
        }
        String rtn = info;
        try {
            rtn = rtn.replaceAll("^（[一|二|三|四|五|六|七|八|九|十]）", "");
            rtn = rtn.replaceAll("^（[0-9]*）", "");
            rtn = rtn.replaceAll("^[0-9]*[ |\\.]", "");
            if(rtn.replaceAll("^【.*】$", "").length()==0){
                rtn = rtn.substring(1, rtn.length()-1);
            }
        }catch(Exception e){
            return info;
        }
        return rtn;
    }

    /**
     * 中文，字母大小写转换
     * @param zn_str
     * @return
     */
    public static String getPinYinHeadChar(String zn_str) {  
        if(zn_str != null && !zn_str.trim().equalsIgnoreCase("")) {  
            char[] strChar = zn_str.toCharArray();  
            // 汉语拼音格式输出类  
            HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();  
            // 输出设置，大小写，音标方式等  
            //hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
            hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);  
            hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
            hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);  
            StringBuffer pyStringBuffer = new StringBuffer();  
            for(int i=0; i<strChar.length; i++) {  
                char c = strChar[i];  
                char pyc = strChar[i];  
                if(String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {//是中文或者a-z或者A-Z转换拼音  
                    try {  
                        String[] pyStirngArray = PinyinHelper.toHanyuPinyinStringArray(strChar[i], hanYuPinOutputFormat);  
                        if(null != pyStirngArray && pyStirngArray[0]!=null) {  
                            pyc = pyStirngArray[0].charAt(0);  
                            pyStringBuffer.append(pyc);  
                        }  
                    } catch(BadHanyuPinyinOutputFormatCombination e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
            return pyStringBuffer.toString();  
        }  
        return null;  
    }

    /**
     * 计算百分比
     * @param min
     * @param max
     * @return
     */
    public static String random(int min, int max){
        Random random = new Random();
        int rd = random.nextInt(max) % (max - min + 1) + min;
        return toStr(rd);
    }

    /**
     * 用时计算
     * @param l1
     * @param l2
     * @return
     */
    public static String taste(long l1, long l2) {
        long number = l2 - l1;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(number);
        String dt = formatter.format(calendar.getTime());
        String rtn = Util.toInt(dt.substring(14,16),0) + "分" + Util.toInt(dt.substring(17,19),0) + "秒";
        if(rtn.equals("0分0秒")){
            rtn = "不足1秒";
        }
        if(rtn.startsWith("0分")){
            rtn = rtn.replace("0分", "");
        }
        return rtn;
    }

    /**
     * 上传文件处理
     * @param file
     * @param numRead
     * @return
     */
    public static List<String> readLastNLine(File file, long numRead) {
        // 定义结果集
        List<String> result = new ArrayList<String>();
        List<String> resultTmp = new ArrayList<String>();
        // 行数统计
        long count = 0;

        // 排除不可读状态
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            resultTmp.add("输入路径不正确");
            return resultTmp;
        }

        // 使用随机读取
        RandomAccessFile fileRead = null;
        try {
            // 使用读模式
            fileRead = new RandomAccessFile(file, "r");
            // 读取文件长度
            long length = fileRead.length();
            // 如果是0，代表是空文件，直接返回空结果
            if (length == 0L) {
                return result;
            } else {
                // 初始化游标
                long pos = length - 1;
                while (pos > 0) {
                    pos--;
                    // 开始读取
                    fileRead.seek(pos);
                    // 如果读取到\n代表是读取到一行
                    if (fileRead.readByte() == '\n') {
                        // 使用readLine获取当前行
                        String line = fileRead.readLine();
                        // 保存结果
                        resultTmp.add(new String(line.getBytes(("ISO-8859-1")),"utf8"));

                        // 打印当前行
                        //System.out.println(new String(line.getBytes("ISO8859-1")) );

                        // 行数统计，如果到达了numRead指定的行数，就跳出循环
                        count++;
                        if (count == numRead) {
                            break;
                        }
                    }
                }
                if (pos == 0) {
                    fileRead.seek(0);
                    result.add(fileRead.readLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileRead != null) {
                try {
                    // 关闭资源
                    fileRead.close();
                } catch (Exception e) {
                }
            }
        }
        if (resultTmp.size() > 0) {
            for (int i = resultTmp.size() - 1; i >= 0; i--) {
                result.add(resultTmp.get(i));
            }
        }
        result.remove(0);
        return result;
    }


    /**
     * LOG输出xml
     * @param in
     * @param name
     * @param type 0:打印控制台
     */
    public static void logXml(Object in,String name, int type,int Identification){
        if(name.equals("")){
            name = "debug";
        }
        String content = Util.toStr(in);
        if(type == 0) {
            System.out.println(content);
        }
        String path="";
        if(Identification == 1){
            path = Config.getKey("book.temporary.log");
        }else if(Identification == 2) {
            path = Config.getKey("book.forever.log");
        }
        path = path.replace("\\", "/");
        if(!path.endsWith("/")){
            path += "/";
        }
        File folder = new File(path);
        if(!folder.exists() && !folder.isDirectory())
        {
            folder.mkdirs();
        }
        File file=null;
        String date = Util.getNowTime().substring(0, 10);
        if(Identification == 2){
            file = new File(path + name + ".log");
        }else {
            file = new File(path + date + "-" + name + ".log");
        }
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
            writer.write( "\r\n" + content + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
