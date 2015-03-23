package com.bob.sina_weibo.util;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONException;
import org.json.JSONObject;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.bob.sina_weibo.bean.UserInfo;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.AccountAPI;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.net.RequestListener;

/**
 * <a href="http://fangjie.sinaapp.com">http://fangjie.sinaapp.com</a>
 * @author Jay
 * @version 1.0
 * @describe 获取授权登录用户的信息工具类(从网络获取，微博API)
 */
public class LoginUserUtil {

	public static String UID="";
	private static UserInfo user;

	//通过授权拿到的AccessToken，获取用户的uid
	public static void reqUID(Oauth2AccessToken accessToken)
	{
		AccountAPI account=new AccountAPI(accessToken);
		account.getUid(new RequestListener(){
			//微博API异步回调方式
			public void onComplete(String result) {
				try {
					JSONObject object =new JSONObject(result);
					UID=object.getString("uid");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			public void onError(WeiboException arg0) {

			}
			public void onIOException(IOException arg0) {
				
			}
		});
	}
	
	//通过获得 Uid获取其他的LoginUser信息
	public static void reqUserInfo(final Oauth2AccessToken accessToken,long uid)
	{
		user=new UserInfo();
		UsersAPI userapi=new UsersAPI(accessToken);
		userapi.show(uid, new RequestListener() {
			public void onIOException(IOException arg0) {

			}
			public void onError(WeiboException arg0) {

			}
			//微博API异步回调方式
			public void onComplete(String arg0) {
				JSONObject object;
				try {
					object = new JSONObject(arg0);
					Bitmap bm=LoginUserUtil.getBitmap(object.getString("profile_image_url"));
					LoginUserUtil.user.setUserIcon(bm);
					LoginUserUtil.user.setToken(accessToken.getToken());
					LoginUserUtil.user.setUserName(object.getString("screen_name"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//获取授权用户的Uid
	public static String getUID()
	{
		String id=UID;
		UID="";
		return id;
	}
	
	//获取授权用户的Userinfo信息
	public static UserInfo getUserInfo()
	{
		return user;
	}
	
	//网络下载biturl 地址的图片资源，返回Bitmap
	public static Bitmap getBitmap(String biturl)
	{
		Bitmap bitmap=null;
		try {
			URL url=new URL(biturl);
			URLConnection conn=url.openConnection();
			InputStream in =conn.getInputStream();
			bitmap=BitmapFactory.decodeStream(new BufferedInputStream(in));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	//变换原Bitmap为 newWidth和newHeight大小的Bitmap返回 
	public static Bitmap scaleImg(Bitmap bm, int newWidth, int newHeight) 
	{
	    // 获得图片的宽高
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    // 设置想要的大小
	    int newWidth1 = newWidth;
	    int newHeight1 = newHeight;
	    // 计算缩放比例
	    float scaleWidth = ((float) newWidth1) / width;
	    float scaleHeight = ((float) newHeight1) / height;
	    // 取得想要缩放的matrix参数
	    Matrix matrix = new Matrix();
	    matrix.postScale(scaleWidth, scaleHeight);
	    // 得到新的图片
	    Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);
	    return newbm;
	 }
}
