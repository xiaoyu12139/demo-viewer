package com.xiaoyu.model;

import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.tree.DefaultMutableTreeNode;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaoyu.annotation.SingletonBean;

import lombok.Data;

@Data
@SingletonBean
public class DialogModel {
	//根目录，默认C:\Users\xiaoyu
	private String root = System.getProperty("user.home");

//	private String root = "C:\\Users\\xiaoyu\\Desktop\\swing_tips\\swing-example\\java-swing-tips";
	//根目录下的所有项目文件的名字
	private Set<String> currentDialogFiles;//<dialogs name in root path>
	//当前选中的节点，路径格式root + \\doc
	private String lastSelect;
	@JSONField(serialize=false)
	private DefaultMutableTreeNode lastNode;
	//记录上次点击时间
	private long lastClickTime;
	
	public void resetCurrentDialogFiles() {
		File rootFile = new File(root);
		File[] list = rootFile.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				if(file.isDirectory()) return true;
				return false;
			}
		});
		Set<String> set = new HashSet<>();
		for(File file : list) {
			set.add(file.getName());
		}
		Set<String> sortSet = new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return sort(o1, o2);// 降序排列
			}
		});
		sortSet.addAll(set);
		this.setCurrentDialogFiles(sortSet);
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
	
	public void parse(DialogModel json) {
		this.currentDialogFiles = json.getCurrentDialogFiles();
		this.root = json.getRoot();
		this.lastClickTime = json.getLastClickTime();
		this.lastSelect = json.getLastSelect();
		this.lastNode = json.getLastNode();
	}
}
