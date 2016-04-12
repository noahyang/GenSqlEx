package org.noah.gen.sql.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author noah.yang
 * sqlite 工具类
 */
public class SqliteUtil {
	private static final String DRIVER_NAME = "org.sqlite.JDBC";
	private static final String CONN_PREFIX = "jdbc:sqlite:";
	private Connection conn = null;
	private Statement stmt = null;
	/**
	 * @param absPathDb
	 */
	public SqliteUtil(String absPathDb) {
		// load the sqlite-JDBC driver using the current class loader
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			// create a database connection
			conn = DriverManager.getConnection(CONN_PREFIX + absPathDb);
			stmt = conn.createStatement();
			stmt.setQueryTimeout(30); // set timeout to 30 sec.
		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}
	}
	/**
	 * @param strSql
	 */
	public void execSql(String strSql) {
		if (null == stmt) {
			System.out.println("null == stmt");
			return;
		}
		try {
			stmt.executeUpdate(strSql);
		} catch (SQLException e) {
			System.out.println(">>> Failed to execSql:" + GsFunc.getReturn() + strSql);
			System.out.println(">>> Reason:" + GsFunc.getReturn() + e);
		}
	}
	public void closeConn() {
		if (null == conn) {
			System.out.println("null == conn");
			return;
		}
		try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SqliteUtil su = new SqliteUtil("/Users/yang/Desktop/chess.db");
		su.execSql("DROP TABLE IF EXISTS test;");
		su.execSql("CREATE TABLE test(id integer primary key, var text);");
		su.closeConn();
		System.out.println("done!");
	}
}
