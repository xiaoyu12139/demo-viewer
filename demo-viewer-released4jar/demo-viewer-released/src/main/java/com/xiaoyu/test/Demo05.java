package com.xiaoyu.test;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;

import com.sun.tools.attach.VirtualMachine;
import com.xiaoyu.init.context.ContextInitializer;
import com.xiaoyu.modifyclass.AddCallBack;

public class Demo05{
	
	public Demo05 demo;
	
	public static void main(String[] args) throws Exception {
		AddCallBack addCallBack = new AddCallBack();
		addCallBack.execute(ContextInitializer.class, Demo06.class);
//		Class cls = load.findClass("com.xiaoyu.test.Demo06");
//		cls.newInstance();
//		 System.setProperty("java.library.path",
//	                "D:\\my\\ruanjian\\jdk1.8.0_112\\jre\\bin");
//	        Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths"); 
//	        fieldSysPath.setAccessible(true);
//	        fieldSysPath.set(null, null);
		VirtualMachine vm = VirtualMachine.attach(getProcessID() + "");
		vm.loadAgent(Demo05.class.getResource("/agent/Agent-0.0.1-SNAPSHOT.jar").toString(),
				"D:\\my\\resource\\project\\swing-tips5-10\\swing-tips\\target\\classes\\com\\xiaoyu\\test\\Demo06.class");
		new Demo06();
	}
	public static final int getProcessID() { 
	    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
	    System.out.println(runtimeMXBean.getName());
	    return Integer.valueOf(runtimeMXBean.getName().split("@")[0]) 
	        .intValue(); 
	  } 
}
