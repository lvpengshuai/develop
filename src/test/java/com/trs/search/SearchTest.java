package com.trs.search;

import com.trs.client.*;
import org.junit.Test;

/**
 * TRS Server Test
 * Created by root on 17-3-15.
 */
public class SearchTest {

    @Test
    public void demo1() {
        String sHost = "192.168.210.55";
        String sPort = "8888";
        String sUserName = "system";
        String sPassWord = "manager";
        TRSConnection trscon = null;
        TRSResultSet trsrs = null;
        try {
            // 建立连接
            trscon = new TRSConnection();
            trscon.connect(sHost, sPort, sUserName, sPassWord);
            // 从 demo3 中检索标题中含有"中国"的记录
            trsrs = trscon.executeSelect("demo3", "标题=中国", "", "",
                    "正文", 0, TRSConstant.TCE_OFFSET, false);
            // 输出记录数
            System.out.println("记录数:" + trsrs.getRecordCount());
            // 设置概览/细览字段, 提高记录的读取效率 trsrs.setReadOptions("日期;版次;作者;标题 ", "正文", ";", TRSConstant.TCE_OFFSET, 0);
            // 输出前 20 条记录
            for (int i = 0; i < 20 && i < trsrs.getRecordCount(); i++) {
                trsrs.moveTo(0,i);
                System.out.println("第" + i + "条记录");
                System.out.println(trsrs.getString ("日期"));
                System.out.println(trsrs.getString("版次"));
                System.out.println(trsrs.getString("作者"));
                System.out.println(trsrs.getString("标题", "red"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭结果集
            if (trsrs != null) trsrs.close(); trsrs = null;
            // 关闭连接
            if (trscon != null) trscon.close(); trscon = null;
        }
    }



    @Test
    public void demo2() {
        String sHost = "127.0.0.1";
        String sPort = "8888";
        String sUserName = "system";
        String sPassWord = "manager";
        TRSConnection trscon = null;
        TRSResultSet trsrs = null;
        try {
            // 建立连接
            trscon = new TRSConnection();
            trscon.connect(sHost, sPort, sUserName, sPassWord);
            trsrs = trscon.executeSelect("demo3", "中国", "", "", "", 0, TRSConstant.TCE_OFFSET, false);

            for (int i = 0; i < 20 && i < trsrs.getRecordCount(); i++) {
                System.out.println("--------------------------------------------");
                trsrs.moveTo(0, i);
                // 获取正文+命中点
                TRSHitPoint[] aHitPoints = trsrs.getHitPoints("正文");
                StringBuffer strHitValues = new StringBuffer(trsrs.getString("正文"));
                // 遍历命中点, 添加标记 if (aHitPoints != null) {
                int iBegin, iEnd, iAddLen, k1, k2;
                iAddLen = 0;
                // 前后标记
                String openStr = "<font color=\"#FF0000\">";
                String closeStr = "</font>";
                String hitWord = null;
                // 遍历命中位置, 添加反显标记
                for (int j = 0; j < aHitPoints.length; j++) {
                    System.out.println("the " + j + " record");
                    // 获取偏移量
                    iBegin = aHitPoints[j].iCStart + iAddLen;
                    iEnd = iBegin + aHitPoints[j].iCLength;
                    // 获取命中词
                    //hitWord = strHitValues.substring(iBegin, iEnd);
                    //System.out.println(hitWord);
                    // 添加 color 标记
                    k1 = strHitValues.length();
                    strHitValues.insert(iEnd, closeStr);
                    strHitValues.insert(iBegin, openStr);
                    k2 = strHitValues.length();
                    // 增加长度
                    iAddLen += (k2 - k1);
                }
                // 输出
                System.out.println(strHitValues.toString());
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭结果集
            if (trsrs != null) trsrs.close(); trsrs = null;
            // 关闭连接
            if (trscon != null) trscon.close(); trscon = null;
        }
    }

    @Test
    public void selectWord() {
        String sHost = "127.0.0.1";
        String sPort = "8888";
        String sUserName = "system";
        String sPassWord = "manager";
        TRSConnection trscon = null;
        TRSResultSet trsrs = null;

        try {
            // 建立连接
            trscon = new TRSConnection();
            trscon.connect(sHost, sPort, sUserName, sPassWord);

            trsrs = trscon.executeSelectWord("CHEMICAL_THESAURUS_UTF8", "SYSTEM", "%安全","+NAME",0);
            for (int i = 0; i < 20 && i < trsrs.getRecordCount(); i++) {
                trsrs.moveTo(0,i);
                System.out.println("总命中词数：" + trsrs.getRecordCount());
                System.out.println("当前索引词为：" + trsrs.getIndexWord());
                System.out.println("当前索引词得词频数为：" + trsrs.getIndexWordFrequence());
                System.out.println("当前索引词得命中数为：" + trsrs.getIndexWordHitNum());
                System.out.println("当前索引词相对于主题词的深度为：" + trsrs.getIndexWordIndentNum());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void selectWord2() {
        String sHost = "192.168.210.55";
        String sPort = "8888";
        String sUserName = "system";
        String sPassWord = "manager";
        TRSConnection trscon = null;
        TRSResultSet trsrs = null;

        try {
            // 建立连接
            trscon = new TRSConnection();

            trscon.connect(sHost, sPort, sUserName, sPassWord);
            trscon.setAutoExtend("CHEMICAL_THESAURUS_UTF8", "", "", TRSConstant.TCM_KAXALLBT | TRSConstant.TCM_KAXALLNT);
            trsrs = trscon.executeSelect("SEARCH_ALLVALUE", "(name/100,keyword/20,content/1)+=like('暗室',w80) OR (name,keyword,content)+=('暗室')", false);
            System.out.println(trsrs.getRecordCount());

            System.out.println(trsrs.getRecordCount());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Relevant () {
        String sHost = "192.168.210.55";
        String sPort = "8888";
        String sUserName = "system";
        String sPassWord = "manager";
        TRSConnection trscon = null;
        TRSResultSet trsrs = null;

        try {
            // 建立连接
            trscon = new TRSConnection();

            trscon.connect(sHost, sPort, sUserName, sPassWord);
            trscon.SetExtendOption("RELEVANTMODE","32768");
            trsrs = trscon.executeSelect("SEARCH_ALLVALUE", "(name/10,keyword/2,content/1)+=(学校)", false);
            System.out.println(trsrs.getRecordCount());

            for (int i = 0; i < 20 && i < trsrs.getRecordCount(); i++) {
                trsrs.moveTo(0,i);
                System.out.println("总命中词数：" + trsrs.getRecordCount());
                System.out.println("当前索引词为：" + trsrs.getIndexWord());
                System.out.println("当前索引词得词频数为：" + trsrs.getIndexWordFrequence());
                System.out.println("当前索引词得命中数为：" + trsrs.getIndexWordHitNum());
                System.out.println("当前索引词相对于主题词的深度为：" + trsrs.getIndexWordIndentNum());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void coulmn () {
        String sHost = "192.168.210.55";
        String sPort = "8888";
        String sUserName = "system";
        String sPassWord = "manager";
        TRSConnection trscon = null;
        TRSResultSet trsrs = null;

        try {
            trscon = new TRSConnection();
            trscon.connect(sHost, sPort, sUserName, sPassWord);
            // 建立连接
            trsrs = trscon.executeSelect("SEARCH_ALLVALUE", "(name/10,keyword/2,content/1)+=(化学) and type =1","", "", "", 0, TRSConstant.TCE_OFFSET, false);
//            trsrs.setCutSize(200, true);

            for (int i = 0; i < 50 && i < trsrs.getRecordCount(); i++) {
                trsrs.moveTo(0,i);
                System.out.println("第" + i + "条记录");
                System.out.println(trsrs.getStringWithCutsize("content", 200,"red"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void culum2() throws TRSException {
        String sHost = "192.168.210.55";
        String sPort = "8888";
        String sUserName = "system";
        String sPassWord = "manager";
        TRSConnection trscon = null;
        TRSResultSet trsrs = null;
        try {
            // 建立连接
            trscon = new TRSConnection();
            trscon.connect(sHost, sPort, sUserName, sPassWord, "T10");
            // 从demo3中检索正文含有"中国"或"人民"的记录
            trsrs = trscon.executeSelect("demo2", "中国", "RELEVANCE", "", "", 0, TRSConstant.TCE_OFFSET, false);

            // 设置包含命中点的文本长度(必须调用)
//            trsrs.setCutSize(200, true);

            // 输出前20条记录
            System.out.println("记录数:" + trsrs.getRecordCount());
            for (int i = 0; i < 20 && i < trsrs.getRecordCount(); i++) {
                trsrs.moveTo(0, i);
                System.out.println("第" + i + "条记录");

//                System.out.println(trsrs.getString("日期"));
//                System.out.println(trsrs.getString("版次"));
//                System.out.println(trsrs.getString("作者"));
                System.out.println(trsrs.getStringWithCutsize("正文", 200, "red"));
            }
        } catch (TRSException ex) {
            // 输出错误信息
            System.out.println(ex.getErrorCode() + ":" + ex.getErrorString());
            ex.printStackTrace();
        } finally {
            // 关闭结果集
            if (trsrs != null) trsrs.close();
            trsrs = null;

            // 关闭连接
            if (trscon != null) trscon.close();
            trscon = null;
        }
    }

    @Test
    public void searchResult() throws TRSException {
        String sHost = "192.168.210.55";
        String sPort = "8888";
        String sUserName = "system";
        String sPassWord = "manager";
        TRSConnection conn = null;
        TRSResultSet  rs   = null;
        try
        {
            conn = new TRSConnection();
            conn.connect(sHost, sPort, "system", "manager", "T10");
            String strWhere = "(中国 or 人民) and 版次=1";
            rs = conn.executeSelect("Demo2", strWhere, "RELEVANCE", "", "标题;正文", 0, TRSConstant.TCE_OFFSET, false);
            rs.setReadOptions(TRSConstant.TCE_OFFSET, "日期;版次;版名;标题;作者", ";");
            long num = rs.getRecordCount();
            String sLicenceCode = conn.getLicenceCode();
            System.out.println("RecordCount: " + num);
            System.out.println("LicenseCode: " + sLicenceCode);

            //输出前20条记录
            for (int i = 0; i < 20 && i < rs.getRecordCount(); i++)
            {
                rs.moveTo(0, i);
                System.out.println("第" + i + "条记录");

                System.out.println(rs.getString("日期"));
                System.out.println(rs.getString("版次"));
                System.out.println(rs.getString("作者"));
                System.out.println(rs.getString("标题", "red"));
            }
        }
        catch(TRSException e)
        {
            System.out.println("ErrorCode: " + e.getErrorCode());
            System.out.println("ErrorString: " + e.getErrorString());
        }
        finally{
            if (rs != null) rs.close();
            if (conn != null) conn.close();
            rs = null;
            conn = null;
        }
    }

    @Test
    public void empty(){
        boolean b = hasBlank("纳米材料");
        if(b){
            System.out.println(b);
        }else {
            System.out.println(1111);
        }
    }


    public static boolean hasBlank(String str) {
        if (str.startsWith(" ") || str.endsWith(" ")) {
            return true;
        } else {
            String s[] = str.split(" +");
            if (s.length == 1) {
                return false;
            } else {
                return true;
            }
        }
    }
    @Test
    public void selctWord(){
        TRSConnection conn = null;
        TRSResultSet  rs   = null;
        try
        {
            conn = new TRSConnection();
            conn.connect("192.168.210.55", "8888", "system", "manager", "T10");

            rs = conn.executeSelectWord("CHEMICAL_THESAURUS_UTF8", "system", "绿色化工", "+NAME", 1000);
            for (int i = 0; i < 10 && i < rs.getRecordCount(); i++)
            {
                rs.moveTo(0, i);
                System.out.println("总命中词数：" + rs.getRecordCount());
                String aaa = rs.getString("B");

                System.out.println("当前索引词为：" + rs.getIndexWord());
                System.out.println("当前索引词得词频数为：" + rs.getIndexWordFrequence());
                System.out.println("当前索引词得命中数为：" + rs.getIndexWordHitNum());
                System.out.println("当前索引词相对于主题词的深度为：" + rs.getIndexWordIndentNum());
            }
        }
        catch (TRSException e)
        {
            System.out.println("ErrorCode: " + e.getErrorCode());
            System.out.println("ErrorString: " + e.getErrorString());
        }
        finally
        {
            if (rs != null) rs.close();
            rs = null;
            if (conn != null) conn.close();
            conn = null;
        }

    }

    /**
     *
     * @param str
     * @return
     */
    public String trimInnerSpaceStr(String str){
        str = str.trim();
        while(str.startsWith(" ")){
            str = str.substring(1,str.length()).trim();
        }
        while(str.endsWith(" ")){
            str = str.substring(0,str.length()-1).trim();
        }
        return str;
    }


    @Test
    public void testtrim(){
        String word = " 化学 \"纳米\" (物理) ";
        String s = trimInnerSpaceStr(word);
        System.out.println(s);
    }
    @Test
    public void selectwordcount(){
        TRSConnection conn = null;
        TRSResultSet rs    = null;
        try
        {
            conn = new TRSConnection();
            conn.connect("127.0.0.1", "8888", "system", "manager", "T10");
            rs = conn.executeSelectWord("cssp_bookdetail", "system", "TextContent='经济'");
            System.out.println("总命中词个数为：" + rs.getRecordCount());

            for (int i = 0, n = (int)rs.getRecordCount(); i < n; i++)
            {
                rs.moveTo(0, i);
                System.out.println(rs.getIndexWord());
            }
        }
        catch(TRSException e)
        {
            System.out.println("ErrorCode: " + e.getErrorCode());
            System.out.println("ErrorString: " + e.getErrorString());
        }
        finally
        {
            if (rs != null) rs.close();
            if (conn != null) conn.close();

            rs = null;
            conn = null;
        }

    }
}
