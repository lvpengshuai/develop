package com.trs.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 */
public class JdbcUtil {
	private static String url = Config.getKey("jdbc.url");
	private static String user = Config.getKey("jdbc.username");
	private static String password = Config.getKey("jdbc.password");

	private JdbcUtil() {
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public static void free(ResultSet resultset, Statement statement, Connection conn) {
		try {
			if (resultset != null)
				resultset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	}
}