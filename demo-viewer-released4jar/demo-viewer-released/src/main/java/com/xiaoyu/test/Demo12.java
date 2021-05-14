package com.xiaoyu.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class Demo12 {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//����һ��jaroutputstream��
		JarOutputStream stream=new JarOutputStream(new FileOutputStream("D:\\my\\resource\\project\\demo-viewer-released\\target\\swing-tips-0.0.1-SNAPSHOT.jar"));

		//jar�е�ÿһ���ļ��� ÿһ���ļ� ����һ��jarEntry

		//���±�ʾ��jar�ļ��д���һ���ļ���bang bang�´���һ���ļ�jj.txt
		JarEntry entry=new JarEntry("bang/jj.txt");

		//��ʾ����entryд��jar�ļ��� Ҳ���Ǵ������ļ��к��ļ�
		stream.putNextEntry(entry);

		//Ȼ�������entry�е�jj.txt�ļ���д������
		stream.write("������".getBytes("utf-8"));

		//������һ��entry1 ͬ�ϲ��� ��������ѭ����һ���ļ����е��ļ���д��jar���� ��ʵ�ܼ�
		JarEntry entry1=new JarEntry("bang/bb.xml");
		stream.putNextEntry(entry1);
		stream.write("<xml>abc</xml>".getBytes("utf-8"));

		//��������ǹر���
		stream.close();
	}
}
