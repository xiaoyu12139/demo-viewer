package com.xiaoyu.test;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Demo11 {
	public static void main(String[] args) throws IOException {
		// 项目中jar包所在物理路径
//		String jarName = "D:\\my\\resource\\project\\demo-viewer-released\\target\\swing-tips-0.0.1-SNAPSHOT.jar";
//		JarFile jarFile = new JarFile(jarName);
//		Enumeration<JarEntry> entrys = jarFile.entries();
//		while (entrys.hasMoreElements()) {
//			JarEntry jarEntry = entrys.nextElement();
//			System.out.println(jarEntry.getName());
//		}
		try {
			Class.forName("com.xiaoyu.string.StrUtil");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("error");
		}
	}
}
