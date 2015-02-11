package com.bob.sina_weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class LogoActivity extends Activity{
	private ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去除屏幕状态栏，设置全屏
		setContentView(R.layout.logo);
		
		imageView= (ImageView) findViewById(R.id.logo);
		AlphaAnimation animation= new AlphaAnimation(0.0f, 1.0f);//两个浮点数，分别表示动画的起点透明度和终点透明度，在0.0~1.0之间
		animation.setDuration(3000);//设置动画持续时间
		animation.setAnimationListener(new AnimationListener() {//设置动画监听,否则就会直接执行完后边的跳转语句，覆盖掉应有的动画效果
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(LogoActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		imageView.setAnimation(animation);
	}
}
