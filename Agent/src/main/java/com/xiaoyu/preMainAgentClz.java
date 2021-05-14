package com.xiaoyu;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

import com.sun.tools.attach.VirtualMachine;

//命令行传递进去调用这个，pom文件中指定类的全路径
//使用传参：-javaagent:全路径/java-agent-demo-1.0-SNAPSHOT.jar
public class preMainAgentClz {
    private static Instrumentation instrumentation;
    public static void premain(String agentArgs, Instrumentation inst) {
        instrumentation = inst;
        System.err.println("我在main启动之前启动");
        try
        {
          System.out.println("args: " + agentArgs);
          System.out.println("重新定义 test.User -- 开始");
          
          //把新的User类文件的内容读出来
        File f = new File(agentArgs);
        byte[] reporterClassFile = new byte[(int) f.length()];
        DataInputStream in = new DataInputStream(new FileInputStream(f)); 
        in.readFully(reporterClassFile);
        in.close();
       
        //把User类的定义与新的类文件关联起来
        ClassDefinition reporterDef =
        new ClassDefinition(Class.forName("com.xiaoyu.test.Demo06"), reporterClassFile);
        //重新定义User类， 妈呀， 太简单了
        inst.redefineClasses(reporterDef);
        System.out.println("重新定义 test.User -- 完成");
        }
        catch(Exception e)
        {
        System.out.println(e);
        e.printStackTrace();
        }
    }
}