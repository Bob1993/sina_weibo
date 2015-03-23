package com.bob.sina_weibo.db;

/**
 * 数据库信息
 * 
 * @author Bob
 * 
 */

public class DBInfo {

	public static class DB {
		public static String DB_NAME = "sina_weibo";
		public static int VERSION = 1;
	}

	public static class Table {
		public static String TABLE_NAME = "UserInfo";
		public static String USER_INFO_CREATE = "CREATE TABLE IF NOT EXITS"
				+ TABLE_NAME + "(_id INTEGER PRIMARY KEY,"
				+ "userId TEXT, token TEXT, isDefault TEXT, userIcon BLOB)";
	}
}
