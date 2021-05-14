package com.xiaoyu.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;


/**
 * jarPath: jar������·��
 * jarFilePath: jar����Ҫ�޸��ļ����ڵ�·��
 * regex��������ʽ
 * replacement���滻���ַ���
 * ע�⣺Jar���ڵ�jar���ڵ��ļ������ã�
 */

public class JarUtil {

    public void change(String jarPath, String jarFilePath, String regex, String replacement) throws IOException {
        File file = new File(jarPath);
        JarFile jarFile = new JarFile(file);// ͨ��jar����·�� ����Jar��ʵ��
        change(jarFile, jarFilePath, regex, replacement);
    }

    public void change(JarFile jarFile, String jarFilePath, String regex, String replacement) throws IOException {
        JarEntry entry = jarFile.getJarEntry(jarFilePath);//ͨ��ĳ���ļ���jar���е�λ������ȡ����ļ�
        //�������ļ�������
        InputStream input = jarFile.getInputStream(entry);
        //��ȡentries����lists
        List<JarEntry> lists = new LinkedList<>();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            lists.add(jarEntry);
        }
        String s = readFile(input, regex, replacement);// ��ȡ���޸��ļ�����
        writeFile(lists, jarFilePath, jarFile, s);// ���޸ĺ������д��jar���е�ָ���ļ�
        jarFile.close();
    }

    private static String readFile(InputStream input, String regex, String replacement)
            throws IOException {
        InputStreamReader isr = new InputStreamReader(input);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder buf = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            // �˴�����ʵ����Ҫ�޸�ĳЩ�е�����
            buf.append(line);
            buf.append(System.getProperty("line.separator"));
        }
        br.close();
        return buf.toString().replaceAll(regex, replacement);
    }

    private static void writeFile(List<JarEntry> lists, String jarFilePath,
                                 JarFile jarFile, String content) throws IOException {
        FileOutputStream fos = new FileOutputStream(jarFile.getName(), true);
        JarOutputStream jos = new JarOutputStream(fos);
        try {
            for (JarEntry je : lists) {
                if (je.getName().equals(jarFilePath)) {
                    // ������д���ļ���
                    jos.putNextEntry(new JarEntry(jarFilePath));
                    jos.write(content.getBytes());
                } else {
                    //��ʾ����JarEntryд��jar�ļ��� Ҳ���Ǵ������ļ��к��ļ�
                    jos.putNextEntry(new JarEntry(je));
                    jos.write(streamToByte(jarFile.getInputStream(je)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // �ر���
            jos.close();
        }
    }

    private static byte[] streamToByte(InputStream inputStream) {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outSteam.toByteArray();
    }


    public static void main(String[] args) throws IOException {
        JarUtil jarTool = new JarUtil();
        jarTool.change("D:\\IDEA\\workSpace\\demo.jar"
                , "spring/spring-aop.xml", "expression=\".*\"", "expression=\"%%\"");
    }

}



   
   