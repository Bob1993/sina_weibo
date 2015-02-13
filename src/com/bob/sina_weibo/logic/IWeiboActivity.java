package com.bob.sina_weibo.logic;
/*
 * 作为所有活动必须要实现的接口，用来初始化和刷新所有活动的界面用的
 */
public interface IWeiboActivity {
	public void init();
	public void refresh(Object...params);
}
