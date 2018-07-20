package com.trs.core.db;

import com.trs.client.TRSConnection;
import com.trs.client.TRSConstant;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;
import com.trs.core.util.SearchResult;
import com.trs.core.util.TRSUtil;
import com.trs.core.util.Util;

import java.util.*;

/**
 * Created by epro on 2017/8/19.
 */
public class TRSConnect {

    /**
     * 首页搜索
     * @param tableName
     * @param sql
     * @param sortkey
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static List<Map<String, Object>> select(String tableName, String sql, String sortkey, int pageIndex, int pageSize, boolean reconnect, String licenceCode) {
        List<Map<String, Object>> list = new ArrayList<>();

        SearchResult searchResult = new SearchResult();
        TRSUtil conn = new TRSUtil();
        TRSConnection connect = null;
        TRSResultSet rs = null;
        List trsList = new ArrayList();
        // TRS数据库读出条数
        int TRSCount = 0;
        try {
            connect = conn.getTrsconnect();
            if (reconnect == true) {
                connect = conn.getReTrsConnect(connect, licenceCode);
            }
            String licenceCodes = connect.getLicenceCode();
            searchResult.setLicenceCode(licenceCodes);
            rs = connect.executeSelect(tableName, sql, sortkey, "", "", 0, TRSConstant.TCE_OFFSET, reconnect);
            //"可读取的记录数":rs.getRecordCount()
            Map<String, Object> mapMsg = new HashMap<String, Object>();
            mapMsg.put("count", rs.getRecordCount());
            list.add(mapMsg);
            TRSCount = new Long(rs.getRecordCount()).intValue();
            for (int i = (pageIndex - 1)*pageSize; i < pageIndex*pageSize && i < rs.getRecordCount(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                rs.moveTo(0, i);
                for(int k= 0;k < rs.getColumnCount();k++){
                    map.put(rs.getColumnName(k), rs.getString(k,"#e79332"));
                }
                list.add(map);
            }
        } catch (TRSException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (connect != null) {
                connect.clean();
                connect.close();
                connect = null;
            }
        }

        return list;
    }

    /**
     * 将全部结果取出
     * @param tableName
     * @param sql
     * @return
     */
    public static List<Map<String, Object>> select(String tableName, String sql) {
        List<Map<String, Object>> list = new ArrayList<>();

        SearchResult searchResult = new SearchResult();
        TRSUtil conn = new TRSUtil();
        TRSConnection connect = null;
        TRSResultSet rs = null;
        List trsList = new ArrayList();
        // TRS数据库读出条数
        int TRSCount = 0;
        try {
            connect = conn.getTrsconnect();
            String licenceCodes = connect.getLicenceCode();
            searchResult.setLicenceCode(licenceCodes);
            rs = connect.executeSelect(tableName, sql, false);
            rs.setReadOptions(TRSConstant.TCE_OFFSET, "organ;bookyear;bookname;people;source;keyword", ";");
            //"可读取的记录数":rs.getRecordCount()
            Map<String, Object> mapMsg = new HashMap<String, Object>();
            mapMsg.put("count", rs.getRecordCount());
            list.add(mapMsg);
            TRSCount = new Long(rs.getRecordCount()).intValue();
            for (int i = 0; i < rs.getRecordCount(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                rs.moveTo(0, i);
                map.put("organ", rs.getString("organ"));
                map.put("bookyear", rs.getString("bookyear"));
                map.put("bookname", rs.getString("bookname"));
                map.put("people", rs.getString("people"));
                map.put("source",rs.getString("source"));
                map.put("keyword", rs.getString("keyword"));
                list.add(map);
            }
        } catch (TRSException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (connect != null) {
                connect.clean();
                connect.close();
                connect = null;
            }
        }
        return list;
    }

    public List<Map<String, Object>> backup(String tableName,String sql){
        List<Map<String, Object>> list = new ArrayList<>();

        SearchResult searchResult = new SearchResult();
        TRSUtil conn = new TRSUtil();
        TRSConnection connect = null;
        TRSResultSet rs = null;
        try
        {
            connect = conn.getTrsconnect();
            rs = connect.executeSelect(tableName, sql, false);
//            System.out.println("共检索到 " + rs.getRecordCount() + "条记录");
            for (int i = 0; i < 10 && i < rs.getRecordCount(); i++)
            {
                Map<String, Object> map = new HashMap<String, Object>();
                rs.moveTo(0, i);
//                System.out.println("第" + i + "条记录");
//                System.out.println(rs.getString("fid"));
//                System.out.println(rs.getString("title","red"));
                map.put("fid",rs.getString("fid"));
                map.put("title",rs.getString("title","red"));
                list.add(map);
            }
        }
        catch (TRSException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (connect != null) {
                connect.clean();
                connect.close();
                connect = null;
            }
        }

        return list;
    }

    /**
     * 查询搜索词出现次数
     * @param tableName
     * @param sql
     * @return
     */
    public int getCount(String tableName,String sql){
        int count=0;

        SearchResult searchResult = new SearchResult();
        TRSUtil conn = new TRSUtil();
        TRSConnection connect = null;
        TRSResultSet rs = null;
        List trsList = new ArrayList();
        // TRS数据库读出条数
        int TRSCount = 0;
        try {
            connect = conn.getTrsconnect();
            String licenceCodes = connect.getLicenceCode();
            searchResult.setLicenceCode(licenceCodes);
            rs = connect.executeSelect(tableName, sql, "", false);
            //"可读取的记录数":rs.getRecordCount()
            count=  new Long(rs.getRecordCount()).intValue();
            TRSCount = new Long(rs.getRecordCount()).intValue();
        } catch (TRSException e) {
            e.printStackTrace();
            return count;
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (connect != null) {
                connect.clean();
                connect.close();
                connect = null;
            }
        }

        return count;
    }

    /**
     * 根据Detail表zid查询数据
     * @param tableName
     * @param sql
     * @param sortkey
     * @return
     */
    public   List<Map<String, Object>> selectDetail(String tableName,String sql, String sortkey) {
        List<Map<String, Object>> list = new ArrayList<>();

        SearchResult detailResult = new SearchResult();
        TRSUtil conn = new TRSUtil();
        TRSConnection connect = null;
        TRSResultSet rs = null;
        try
        {
            connect = conn.getTrsconnect();
            String licenceCodes = connect.getLicenceCode();
            detailResult.setLicenceCode(licenceCodes);
            rs = connect.executeSelect(tableName, sql, sortkey, false);
            System.out.println("共检索到 " + rs.getRecordCount() + "条记录");
            for (int i = 0; i < 10 && i < rs.getRecordCount(); i++)
            {
                Map<String, Object> map = new HashMap<String, Object>();
                rs.moveTo(0, i);

                map.put("fid",rs.getString("fid"));
                map.put("title",rs.getString("title"));
                map.put("zid",rs.getString("zid"));
                map.put("content",rs.getString("content"));
                map.put("isMinNode",rs.getString("is_min_node"));
                map.put("contentPdf",rs.getString("content_pdf"));
                map.put("entry",rs.getString("entry"));
                map.put("bookcode",rs.getString("bookcode"));
                map.put("zidTitle",rs.getString("zid_title"));
                map.put("browsingHistory",rs.getString("browsing_history"));
                System.out.println("第" + i + "条记录");
                System.out.println(rs.getString("fid"));
                System.out.println(rs.getString("title" ));
                System.out.println(rs.getString("bookcode"));
                System.out.println(rs.getString("browsing_history" ));
                list.add(map);
            }
        }
        catch (TRSException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (connect != null) {
                connect.clean();
                connect.close();
                connect = null;
            }
        }

        return list;
    }
    public static void main(String[] args) {
        TRSConnect db = new TRSConnect();
     //   List<Map<String, Object>> list = db.select("details", "","", "RELEVANCE",1,10);
//        System.out.println(list.get(0).get("count"));
//        if(Util.isDBNull(list)){
//            System.out.println("null");
//            return;
//        }
//        for(int i = 1;i<list.size();i++){
//            System.out.println(list.get(i).get("id"));
//        }

        String tabName="cssp_bookdetail";
        String sql="(title/60,TextContent/10,keyword/10)+=like('经济')";
        List<Map<String, Object>> select = db.select(tabName, sql, "", 1, 10,false,"");
        System.out.println(select);
    }
}
