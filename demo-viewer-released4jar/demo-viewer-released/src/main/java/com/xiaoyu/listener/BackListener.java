package com.xiaoyu.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.swing.tree.TreeNode;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.ContentModel;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.ui.panel.ContentPanel;

public class BackListener implements ActionListener {

	@Autowired
	private ContentPanel contentPanel;
	@Autowired
	private ContentModel contentModel;
	@Autowired
	private DialogModel dialogModel;

	public BackListener() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<TreeNode> list = contentModel.getNodePath();
		if (list.size() == 0)
			return;
		list.remove(list.size() - 1);
		String res = "";
		for (TreeNode temp : list) {
			res += "\\" + temp.toString();
		}
		contentModel.setPath(dialogModel.getRoot() + dialogModel.getLastSelect() + res);
		contentPanel.getPath2Copy().setText(contentModel.getPath());
		contentPanel.reload(contentModel.getPath());
	}

}
