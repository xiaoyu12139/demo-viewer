package com.xiaoyu.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class Demo12 {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//定义一个jaroutputstream流
		JarOutputStream stream=new JarOutputStream(new FileOutputStream("D:\\my\\resource\\project\\demo-viewer-released\\target\\swing-tips-0.0.1-SNAPSHOT.jar"));

		//jar中的每一个文件夹 每一个文件 都是一个jarEntry

		//如下表示在jar文件中创建一个文件夹bang bang下创建一个文件jj.txt
		JarEntry entry=new JarEntry("bang/jj.txt");

		//表示将该entry写入jar文件中 也就是创建该文件夹和文件
		stream.putNextEntry(entry);

		//然后就是往entry中的jj.txt文件中写入内容
		stream.write("我日你".getBytes("utf-8"));

		//创建另一个entry1 同上操作 可以利用循环将一个文件夹中的文件都写入jar包中 其实很简单
		JarEntry entry1=new JarEntry("bang/bb.xml");
		stream.putNextEntry(entry1);
		stream.write("<xml>abc</xml>".getBytes("utf-8"));

		//最后不能忘记关闭流
		stream.close();
	}
}
