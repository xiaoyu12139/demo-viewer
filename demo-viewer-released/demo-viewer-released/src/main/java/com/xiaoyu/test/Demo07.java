package com.xiaoyu.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.xiaoyu.annotation.Demo;
import com.xiaoyu.listener.RunRuleListener;
import com.xiaoyu.model.RunModel;

public class Demo07 {
	public static void main(String[] args) {
		String in = Demo07.class.getResource("/json_object/model").toString().substring(6).replace("/", "\\");
		List<String> list = new ArrayList<>();
		File file = new File(in);
		if (file.exists()) {
			try {
				BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String temp = null;
				while ((temp = read.readLine()) != null) {
					list.add(temp);
					System.out.println(temp);
					RunModel parseObject = JSON.parseObject(temp, RunModel.class);
					System.out.println(parseObject.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
