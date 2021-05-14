package com.xiaoyu.test;

import java.io.File;
import java.io.IOException;

public class Demo08 {
	public static void main(String[] args) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		String target = Demo08.class.getResource("/cmd").toString().substring(6).replace("/", "\\");
		System.out.println(target);
		String cmd = "cmd /c run.bat useless C:\\Users\\xiaoyu\\Desktop\\swing_tips\\swing-example\\java-swing-tips\\AboveCellTreeToolTips\\src example.MainPanel";
		runtime.exec(cmd, null, new File(target));
		
	}
}