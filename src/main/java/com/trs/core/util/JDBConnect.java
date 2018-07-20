package com.trs.core.util;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trs.core.breakxml.XmlDataCut;


public class JDBConnect  {

    /**
     * JDBC查询
     * @param sql
     * @return
     */
    public List<Map<String, Object>> select(String sql){
        System.out.println(sql);
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            conn = JdbcUtil.getConnection();
            statement = conn.prepareStatement(sql);
            rs = statement.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 1; i <= count; i++) {
                    map.put(rsmd.getColumnLabel(i), rs.getObject(i));
                }
                list.add(map);
            }
            if (list == null || list.size() == 0) {
                return new ArrayList<Map<String, Object>>();
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(rs, statement, conn);
        }
        return list;
    }
    
    /**
     * JDBC删除
     * @param sql
     * @return
     */
    public int delete(String sql) {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultset = null;
        int rtnInt = 0;

        try {
            conn = JdbcUtil.getConnection();
            statement = conn.createStatement();
            Util.log(sql, "sql", 0);
            int i = statement.executeUpdate(sql);

            rtnInt = i;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(resultset, statement, conn);
        }
        return rtnInt;
    }

    /**
     * JDBC更新
     * @param sql
     * @throws SQLException
     */
    public void update(String sql) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultset = null;

        try {
            conn = JdbcUtil.getConnection();
            statement = conn.createStatement();
            int i = statement.executeUpdate(sql);
            Util.log(sql, "sql", 0);
        } catch (SQLException e) {
            e.printStackTrace();
            Util.log("-------------" + XmlDataCut.classify==null?"":XmlDataCut.classify + "---------------", "err", 0);
            Util.log(e.getSQLState(), "err", 0);
            Util.log(e.getCause(), "err", 0);
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw,true));
            Util.log(sw.toString(), "err", 0);
        } finally {
            JdbcUtil.free(resultset, statement, conn);
        }
    }

    /**
     * JDBC插入
     * @param sql
     * @return
     */
    public int insert(String sql){
        //Util.log(sql, "sql");
        Connection conn = null;
        Statement statement = null;
        ResultSet resultset = null;
        int rtnInt = 0;
        try {
            conn = JdbcUtil.getConnection();
            statement = conn.createStatement();
            rtnInt = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            Util.log("-------------" + XmlDataCut.classify==null?"":XmlDataCut.classify + "---------------", "err", 0);
            Util.log(e.getSQLState(), "err", 0);
            Util.log(e.getCause(), "err", 0);
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw,true));
            Util.log(sw.toString(), "err", 0);
        } finally {
            JdbcUtil.free(resultset, statement, conn);
        }
        return rtnInt;
    }
    
    /**
     * JDBC批量操作
     * @param sqlOrder
     * @return
     */
    public int insertTran(String sqlOrder) {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultset = null;
        PreparedStatement ps = null;
        int rtn = 0;
        try {
            conn = JdbcUtil.getConnection();
            // 设置事务的提交方式为非自动提交：
            conn.setAutoCommit(false);
            String[] sqls = sqlOrder.split(";");
            for (String sql : sqls) {
                if(sql == null || sql.trim().equals("")){
                    continue;
                }
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
            }
            // 在try块内添加事务的提交操作，表示操作无异常，提交事务。
            conn.commit();
        } catch (SQLException e) {
            rtn = -1;
            try {
                // .在catch块内添加回滚事务，表示操作出现异常，撤销事务：
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                // 设置事务提交方式为自动提交：
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                rtn = -1;
            }
            JdbcUtil.free(resultset, statement, conn);
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                rtn = -1;
            }
        }
        return rtn;
    }
}
