package com.bob.sina_weibo.ui;

import com.bob.sina_weibo.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

public class AuthActivity extends Activity{

	private Dialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authorize);
		View view= View.inflate(this, R.layout.authorize_dialog,null);//加载对话框布局
		dialog= new Dialog(this, R.style.auth_dialog);//用自定义的风格初始化对话框
		dialog.setContentView(view);//为对话框设置布局
		dialog.show();
	}
}
