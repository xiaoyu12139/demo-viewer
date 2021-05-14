package com.xiaoyu.test;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import com.xiaoyu.annotation.Demo;

public  class Demo02 {
	public static <T> void main(String[] args){
		try {
			File file = new File("target\\classes\\com\\xiaoyu\\test");
			System.out.println(file.getAbsolutePath());
			Queue<File> queue = new LinkedList<>();
			queue.offer(file);
			while (!queue.isEmpty()) {
				File poll = queue.poll();
				if (poll.isDirectory()) {
					for (File temp : poll.listFiles()) {
						queue.offer(temp);
					}
					continue;
				}
				if(poll.getName().endsWith(".class")) {
					Class<?> forName = Class.forName("com.xiaoyu.test." + poll.getName().substring(0, poll.getName().length() - 6));
					if (forName.isAnnotationPresent(Demo.class)) {
						T cast = (T) forName.cast(forName);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
