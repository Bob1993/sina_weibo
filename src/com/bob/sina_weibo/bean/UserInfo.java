package com.bob.sina_weibo.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * 用户实体
 * @author Bob
 *
 */
public class UserInfo {
	private long id;
	private String userId;
	private String userName;
	private String token;
	private String isDefault;
	private Bitmap userIcon;//图片格式为Bitmap,在存库和取出的时候，需要进行压缩为二进制编码和解压缩回Bitmap

	
	public static final String ID="_id";
	public static final String USER_ID="userId";
	public static final String USER_NAME="userName";
	public static final String TOKEN="token";
	public static final String IS_DEFAULT="isDefault";
	public static final String USER_ICON="userIcon";
	
	public UserInfo(long id, String userId, String userName, String token,
			String isDefault) {//暂时就先不存储图像了
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.token = token;
		this.isDefault = isDefault;
	}

	public UserInfo() {
		super();
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public long getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getToken() {
		return token;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public Bitmap getUserIcon() {
		return userIcon;
	}
	
	public void setUserIcon(Bitmap userIcon){
		this.userIcon= userIcon;
	}
}
