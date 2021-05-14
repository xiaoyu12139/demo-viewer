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
	//ƥ�����й���,set MAIN_CLASS=example.MainPanel
	private String mainClass = "example.MainPanel";
	//���д��ݵĲ���
	private String args;
	//�Ƿ�Ϊ�������ģʽ
	private boolean isFast = false;
	//�Զ������е�����
	private String other;
	
	public void parse(RunModel runModel) {
		this.mainClass = runModel.getMainClass();
		this.args = runModel.getArgs();
		this.other = runModel.getOther();
	}
}
