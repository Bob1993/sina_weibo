package com.bob.sina_weibo.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.bob.sina_weibo.ui.AuthActivity;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.sso.SsoHandler;

/**
 * 
 * @author Bob AppKey：19870586
 * 
 */
public class AuthUtil {// 认证工具类
	private Weibo weibo;
	public static Oauth2AccessToken accessToken;//这里的静态只是为了保证程序中只有一个access
	private Context context;
	//public static SsoHandler mSsoHandler;//实现sso单点登录，暂未实现

	public AuthUtil(Context context) {
		this.context = context;
	}

	
	// 获取授权
	public void reqAccessToken() {
		accessToken = null;
		weibo = Weibo.getInstance(Constant.APPKEY, Constant.REDIRECTURL);
		weibo.authorize(context,new AuthDialogListener());
	}

	// 获取accessToken，这个就是授权的结果，可以用来存库，也可用来临时存储
	public Oauth2AccessToken getAccessToken() {
		Oauth2AccessToken token = accessToken;
		accessToken = null;//每次获取完毕，都将这里原有的置空，因为每次认证都是需要不同账号最新生成access
		return token;
	}

	// 授权回调
	class AuthDialogListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {//处理认证结果，都包含在values里
			String token = values.getString("access_token");
			
			String expires_in = values.getString("expires_in");
			accessToken = new Oauth2AccessToken(token, expires_in);//包装生成access
			
			 /*去除Cookie，防止拿原来授权的帐号自动授权*/
			CookieSyncManager.createInstance(context);
			CookieSyncManager.getInstance().startSync();
			CookieManager.getInstance().removeAllCookie();

			if (AuthUtil.accessToken.isSessionValid()) {
				// 认证成功！！
				LogUtil.i("re", "accesstaken from oncomplete"
						+ AuthUtil.accessToken.getToken());

				Intent intent = new Intent(context, AuthActivity.class);
				context.startActivity(intent);
			}
		}

		@Override
		public void onError(WeiboDialogError e) {

		}

		@Override
		public void onCancel() {

		}

		@Override
		public void onWeiboException(WeiboException e) {

		}
	}
}
