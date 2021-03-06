package com.bob.sina_weibo.ui;

import java.util.HashMap;
import java.util.Map;

import com.bob.sina_weibo.R;
import com.bob.sina_weibo.bean.Task;
import com.bob.sina_weibo.logic.IWeiboActivity;
import com.bob.sina_weibo.logic.MainService;
import com.bob.sina_weibo.util.AuthUtil;
import com.weibo.sdk.android.Oauth2AccessToken;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/*
 * 用户授权活动
 */
public class AuthActivity extends Activity implements IWeiboActivity{

	private AuthUtil authUtil;
	private Dialog dialog;
	private Button bt_auth;
	private Oauth2AccessToken accessToken;
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authorize);
		
		authUtil= new AuthUtil(this);
		accessToken= authUtil.getAccessToken();
		
		if(accessToken!= null)//利用access是否为空来判断是将要进行认证还是认证回调
		{//认证回调方法
			progressDialog=new ProgressDialog(this);
			progressDialog.setMessage("正在获取用户信息，请稍候...");
			progressDialog.show();
			
			//新开任务：通过token获取登录用户信息
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("token", accessToken);//添加任务
			Task task=new Task(Task.GET_USERINFO_BY_TOKEN,params); 
			MainService.newTask(task);
			MainService.addActivity(AuthActivity.this);	
		}else//认证前的初始化
			init();
	}

	public void init()
	{
		Toast.makeText(AuthActivity.this, "access为空", 0).show();
		View view= View.inflate(this, R.layout.authorize_dialog,null);//加载对话框布局
		dialog= new Dialog(this, R.style.auth_dialog);//用自定义的风格初始化对话框   
		dialog.setContentView(view);//为对话框设置布局
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		bt_auth= (Button) dialog.findViewById(R.id.auth_begin);//findViewById 主要是从当前控件所处的View下寻找控件，所以一定要指定View
		
		bt_auth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				authUtil.reqAccessToken();//开始sso单点认证,其实这一点我们可以加入到祝服务的任务队列中，从而使得当前线程不会堵塞
			}
		});
	}

	//AuthActivity 任务更新
	public void refresh(Object... objects) {
		if(((String)objects[0]).equals("用户存储成功")){
			progressDialog.dismiss();
			MainService.removeActivity(AuthActivity.this);//每当处理完成一个任务，便移除，以防活动队列内容的重叠
			
			//授权成功，跳转到LoginActivity
			Intent intent=new Intent(AuthActivity.this,LoginActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
}
