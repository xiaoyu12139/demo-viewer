package com.xiaoyu.model;

import com.xiaoyu.annotation.SingletonBean;

import lombok.Data;

@Data
@SingletonBean
public class RunModel {
	/*
	 * "%JAVA_HOME%\bin\javac.exe" "%MAIN_JAVA%"
"%JAVA_HOME%\bin\java.exe" -classpath "%CLASSPATH%" "%MAIN_CLASS%"
	 * */
	//匹配运行规则,set MAIN_CLASS=example.MainPanel
	private String mainClass = "example.MainPanel";
	//运行传递的参数
	private String args;
	//是否为快速浏览模式
	private boolean isFast = false;
	//自定义运行的命令
	private String other;
	
	public void parse(RunModel runModel) {
		this.mainClass = runModel.getMainClass();
		this.args = runModel.getArgs();
		this.other = runModel.getOther();
	}
}
