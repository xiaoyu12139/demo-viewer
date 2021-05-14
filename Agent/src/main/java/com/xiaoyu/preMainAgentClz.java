package com.xiaoyu;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

import com.sun.tools.attach.VirtualMachine;

//�����д��ݽ�ȥ���������pom�ļ���ָ�����ȫ·��
//ʹ�ô��Σ�-javaagent:ȫ·��/java-agent-demo-1.0-SNAPSHOT.jar
public class preMainAgentClz {
    private static Instrumentation instrumentation;
    public static void premain(String agentArgs, Instrumentation inst) {
        instrumentation = inst;
        System.err.println("����main����֮ǰ����");
        try
        {
          System.out.println("args: " + agentArgs);
          System.out.println("���¶��� test.User -- ��ʼ");
          
          //���µ�User���ļ������ݶ�����
        File f = new File(agentArgs);
        byte[] reporterClassFile = new byte[(int) f.length()];
        DataInputStream in = new DataInputStream(new FileInputStream(f)); 
        in.readFully(reporterClassFile);
        in.close();
       
        //��User��Ķ������µ����ļ���������
        ClassDefinition reporterDef =
        new ClassDefinition(Class.forName("com.xiaoyu.test.Demo06"), reporterClassFile);
        //���¶���User�࣬ ��ѽ�� ̫����
        inst.redefineClasses(reporterDef);
        System.out.println("���¶��� test.User -- ���");
        }
        catch(Exception e)
        {
        System.out.println(e);
        e.printStackTrace();
        }
    }
}