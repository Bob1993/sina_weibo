package com.bob.sina_weibo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaScriptInterface {
	private static String PIN;
	public void getPin(String html)
	{
		String reg= "[1-9]{6}";//构建正则表达式
		Pattern pattern= Pattern.compile(reg);//创建匹配模板
		Matcher matcher= pattern.matcher(html);//利用模板在源文件html字符串中进行匹配
		if (matcher.find()) {//如果匹配到了，就返回true
			PIN= matcher.group(0);//返回第一个结果
		}
	}
}
