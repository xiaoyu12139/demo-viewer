package com.xiaoyu.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaoyu.annotation.SingletonBean;

import lombok.Data;

@Data
@SingletonBean
public class ContentModel {
	//��ǰѡ�еĽڵ�
	@JSONField(serialize=false)
	private DefaultMutableTreeNode currentSelect;
	//��¼back��ť��һ�ε��ʱ��
	private long lastBackClickTime;
	private String path;
	@JSONField(serialize=false)
	private List<TreeNode> nodePath = new ArrayList<>(1);
	
	public void parse(ContentModel json) {
		this.lastBackClickTime = json.getLastBackClickTime();
		this.path = json.getPath();
	}
}
