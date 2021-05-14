package com.xiaoyu.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyClassloder extends ClassLoader  {

    public MyClassloder(){
        super(null);
    }

    public Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("执行 findClass方法");

        String cname = "C:\\Users\\xiaoyu\\Desktop\\doudou2\\untitled\\target\\classes\\com\\xiaoyu\\demo\\Demo.class";
        System.out.println(cname);
        byte[] classBytes = new byte[0];
        try {
            classBytes = Files.readAllBytes(Paths.get(cname));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
        if (cl == null) {
            throw new ClassNotFoundException(name);
        }
        return cl;
    }

    public Class<?> findClass2(String name) throws ClassNotFoundException {
        System.out.println("执行 findClass方法");

        String cname = "C:\\Users\\xiaoyu\\Desktop\\doudou2\\untitled\\target\\classes\\com\\xiaoyu\\Demo02.class";
        System.out.println(cname);
        byte[] classBytes = new byte[0];
        try {
            classBytes = Files.readAllBytes(Paths.get(cname));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
        if (cl == null) {
            throw new ClassNotFoundException(name);
        }
        return cl;
    }
}
