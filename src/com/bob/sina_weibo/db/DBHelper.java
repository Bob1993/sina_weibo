package com.bob.sina_weibo.db;

import com.bob.sina_weibo.db.DBInfo.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	public DBHelper(Context context) {
		super(context, DB.DB_NAME, null, DB.VERSION);//初始化创建数据库
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DBInfo.Table.USER_INFO_CREATE);//建表
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
