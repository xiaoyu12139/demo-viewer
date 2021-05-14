package com.xiaoyu.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.alibaba.fastjson.JSON;
import com.xiaoyu.annotation.InitModel;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@InitModel
@Slf4j
public class ModelFactory {
	private DialogModel dialogModel;
	private ContentModel contentModel;
	private MatchModel matchModel;
	private RunModel runModel;

	private String in = getClass().getResource("/json_object/model").toString().substring(6).replace("/", "\\");

	public void init() {
		log.info("ModelFactory init");
		File file = new File(in);
		List<String> list = new ArrayList<>();
		if (file.exists()) {
			try {
				BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String temp = null;
				while ((temp = read.readLine()) != null) {
					list.add(temp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			initRunModel(list.size() == 0 ? null : list.get(0));
			initDialogModel(list.size() == 0 ? null : list.get(1));
			initContentModel(list.size() == 0 ? null : list.get(2));
			return;
		}
	}

	private void initRunModel(String str) {
		if (str != null) {
			runModel.parse(JSON.parseObject(str, RunModel.class));
			return;
		}

	}

	public void initContentModel(String str) {
		if (str != null) {
			contentModel.parse(JSON.parseObject(str, ContentModel.class));
			checkContentModel();
			return;
		}
	}

	private void checkContentModel() {
		
	}

	public void initDialogModel(String str) {
		if (str != null) {
			dialogModel.parse(JSON.parseObject(str, DialogModel.class));
			checkDialogModel();
			return;
		}
		setDialogCurrent();
	}

	private void checkDialogModel() {
		if (dialogModel.getRoot() == null || !new File(dialogModel.getRoot()).exists()) {
			dialogModel.setRoot(System.getProperty("user.home"));
		}
		setDialogCurrent();
	}

	public void setDialogCurrent() {
		String root = this.dialogModel.getRoot();
		File rootFile = new File(root);
		File[] list = rootFile.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				if (file.isDirectory())
					return true;
				return false;
			}
		});
		Set<String> set = new HashSet<>();
		for (File file : list) {
			set.add(file.getName());
		}
		Set<String> sortSet = new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return sort(o1, o2);// Ωµ–Ú≈≈¡–
			}
		});
		sortSet.addAll(set);
		this.dialogModel.setCurrentDialogFiles(sortSet);
		System.out.println(dialogModel.getCurrentDialogFiles());
	}

	public int sort(String str1, String str2) {
		if (str1.startsWith(".") && !str2.startsWith(".")) {
			return -1;
		}
		if (!str1.startsWith(".") && str2.startsWith(".")) {
			return 1;
		}
		return str1.compareTo(str2);
	}
}
