package com.bob.sina_weibo.ui;

import com.bob.sina_weibo.R;
import com.bob.sina_weibo.bean.Task;
import com.bob.sina_weibo.logic.IWeiboActivity;
import com.bob.sina_weibo.logic.MainService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity implements IWeiboActivity{
	private Button button;
	private TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		/*
		 * 启动主服务
		 */
		/*Intent intent= new Intent(this,MainService.class);
		startService(intent);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				MainService.newTask(new Task(Task.TASK_LOGIN, null));//为任务队列发送一条新任务，也就是插入
			}
		});
		MainService.addActivity(this);*/
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object... params) {//刷新当前活动的UI方法
		// TODO Auto-generated method stub
		textView.setText(params[0].toString());//可变参数是采用数组的形式来获取元素，其实本质也就是数组
	}
}
