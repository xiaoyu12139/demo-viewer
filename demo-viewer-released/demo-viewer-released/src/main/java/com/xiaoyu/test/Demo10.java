package com.xiaoyu.test;

import java.io.File;
import java.io.IOException;

import lombok.ToString;

public class Demo10 {
	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();
		String javaHome = System.getenv("JAVA_HOME");
		File file = new File(javaHome + "\\bin");
		String output = Demo10.class.getResource("/").toString().substring(6).replace("/", "\\");
		String src = output.substring(0, output.length() - 15).replace("/", "\\") + "src\\main\\java\\" + "com\\xiaoyu\\test\\Demo09.java";
		try {
			String cmd = "javac -d " + output + " " + src;
			runtime.exec(cmd, null, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
