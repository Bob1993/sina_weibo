package com.bob.sina_weibo.db;

import java.io.ByteArrayOutputStream;

import com.bob.sina_weibo.bean.UserInfo;
import com.bob.sina_weibo.db.DBInfo.DB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 数据库操作的封装
 * @author Bob
 *
 */
public class UserInfoServices {
	private DBHelper helper;
	public UserInfoServices(Context context) {
		// TODO Auto-generated constructor stub
		helper= new DBHelper(context);
	}
	
	public void insertUserInfo(UserInfo user)
	{
		SQLiteDatabase db= helper.getWritableDatabase();
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		user.getUserIcon().compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] usericon=baos.toByteArray();
		
		ContentValues values= new ContentValues(5);
		values.put(UserInfo.USER_ID, user.getUserId());
		values.put(UserInfo.USER_NAME, user.getUserName());
		values.put(UserInfo.TOKEN, user.getToken());
		values.put(UserInfo.IS_DEFAULT, user.getIsDefault());
		values.put(UserInfo.USER_ICON, usericon);//后者为一个字节数组，BLOB
		db.insert(DBInfo.Table.TABLE_NAME, null, values);
		db.close();
	}
	
	public UserInfo getUserInfoByUserId(String userId)
	{
		SQLiteDatabase db= helper.getReadableDatabase();
		
		Cursor cursor =db.query(DB.DB_NAME, new String[]{UserInfo.ID, UserInfo.IS_DEFAULT,UserInfo.TOKEN,
				UserInfo.USER_ID,UserInfo.USER_NAME,UserInfo.USER_ICON},
				UserInfo.USER_ID +"=?",new String[]{userId}, null, null, null);
		UserInfo user =null;
		
		if(cursor!= null)
		{
			if(cursor.getCount() >0)
			{
				cursor.moveToFirst();
				user = new UserInfo();
				Long id =cursor.getLong(cursor.getColumnIndex(UserInfo.ID));//6个参数的查询
				String userName = cursor.getString(cursor.getColumnIndex(UserInfo.USER_NAME));
				String token = cursor.getString(cursor.getColumnIndex(UserInfo.TOKEN));
				String isDefault= cursor.getString(cursor.getColumnIndex(UserInfo.IS_DEFAULT));
				byte[] byteIcon = cursor.getBlob(cursor.getColumnIndex(UserInfo.USER_ICON));
				
				user= new UserInfo(id, userId, userName, token, isDefault);
				if(null !=byteIcon)
				{
					Bitmap userIcon=BitmapFactory.decodeByteArray(byteIcon, 0, byteIcon.length);
					user.setUserIcon(userIcon);
				}
			}
		}
		db.close();
		return user;
	}
	
	
}
