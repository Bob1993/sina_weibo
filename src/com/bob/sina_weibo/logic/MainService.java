package com.bob.sina_weibo.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.bob.sina_weibo.bean.Task;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class MainService extends Service implements Runnable{
	/*
	 * 之所以要做成静态的，1是为了让静态方法能狗访问到。2是只能有一份的任务队列和活动数组
	 */
	private static Queue<Task> tasks= new LinkedList<Task>();//创建任务队列
	private static ArrayList<Activity> appActivities= new ArrayList<Activity>();//创建存储各种活动的数组，在这里不建议面向接口编程，会影响效率
	private boolean isRun= false;//判断是否启动扫描循环的标识符
	
	Handler handler= new Handler(){//使用匿名内部类来实现对类的继承
		
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Task.TASK_LOGIN://如果是在子线程中处理的是登录任务的话，
				IWeiboActivity activity= (IWeiboActivity) getActivityByName("LoginActivity");//强制向上转型
				activity.refresh(msg.obj);//因为UI更新需要活动做载体，所以在任何时候都别忘了将活动添加到队列中
				break;

			default:
				break;
			}
		};
	};
	@Override
	public void onCreate() {//在服务创建的时候就开始启动扫描任务队列的线程
		// TODO Auto-generated method stub
		super.onCreate();
		Thread thread= new Thread(this);
		thread.start();
		isRun= true;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Task task= null;
		while(isRun)//不断地从任务队列中检查是否有任务存在
		{
			if(!tasks.isEmpty())
			{
				task= tasks.poll();//等于C++里的pop，也就是为一个队列出队
				if(task!= null)//若取出的任务不是空的
				{
					doTask(task);
				}
			}
			
			try {
				Thread.sleep(1000);//让当前线程每次检查间隔1s，不至于时时刻刻都在检查，要不然cpu压力太大
			} catch (InterruptedException e) {
				
			}
		}
		
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void doTask(Task t)
	{
		Message msg= handler.obtainMessage();
		switch (t.getTaskId()) {//根据队列中任务的类型来分别做处理
		case Task.TASK_LOGIN://处理登录作业并将更新消息发给主线程里的Handle
			msg.what= Task.TASK_LOGIN;//模拟处理登陆任务并返回UI更新元素
			msg.obj= "登录成功";
			handler.sendMessage(msg);
			break;

		default:
			break;
		}
	}
	
	public static void newTask(Task t)//为任务队列添加任务
	{
		tasks.add(t);
	}
	
	public static void addActivity(Activity activity)//为活动数组添加新成员
	{
		appActivities.add(activity);
	}
	
	public Activity getActivityByName(String name)//根据活动名称来从活动数组中获取活动
	{
		if (!appActivities.isEmpty()) {//活动数组不为空
			for (Activity activity : appActivities) {
				if(activity!= null)
				{
					if(activity.getClass().getName().indexOf(name)>0)
						return activity;
				}
			}
		}
		return null;
	}
}
