package com.bob.sina_weibo.bean;

import java.util.Map;

public class Task {
	/*
	 * 定义多个静态常量来区分task的种类
	 */
	public static final int TASK_LOGIN= 0;
	
	private int taskId;//任务id
	private Map<String ,Object> taskParams;//任务内容

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Map<String, Object> getTaskParams() {
		return taskParams;
	}

	public void setTaskParams(Map<String, Object> taskParams) {
		this.taskParams = taskParams;
	}

	public Task(int taskId, Map<String , Object> taskParams) {
		// TODO Auto-generated constructor stub
		this.taskId= taskId;
		this.taskParams=taskParams;
	}
}
