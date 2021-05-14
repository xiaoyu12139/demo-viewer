package com.xiaoyu.test;

import java.io.File;

public class Demo04 {
	public static void main(String[] args) {
		File file = new File(System.getProperty("user.dir") + "\\target\\tmp");
		new Demo04().delete(file);
	}
	private void delete(File file) {
		if(file.isFile()) {
			file.delete();
		}else {
			for(File temp : file.listFiles()) {
				delete(temp);
			}
			file.delete();
		}
	}
}
