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
	//��Ŀ¼��Ĭ��C:\Users\xiaoyu
	private String root = System.getProperty("user.home");

//	private String root = "C:\\Users\\xiaoyu\\Desktop\\swing_tips\\swing-example\\java-swing-tips";
	//��Ŀ¼�µ�������Ŀ�ļ�������
	private Set<String> currentDialogFiles;//<dialogs name in root path>
	//��ǰѡ�еĽڵ㣬·����ʽroot + \\doc
	private String lastSelect;
	@JSONField(serialize=false)
	private DefaultMutableTreeNode lastNode;
	//��¼�ϴε��ʱ��
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
				return sort(o1, o2);// ��������
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
