package com.xiaoyu.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.swing.tree.TreeNode;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.ContentModel;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.string.StrUtil;
import com.xiaoyu.ui.panel.ContentPanel;

public class BackListener implements ActionListener {

	@Autowired
	private ContentPanel contentPanel;
	@Autowired
	private ContentModel contentModel;
	@Autowired
	private DialogModel dialogModel;
	private Runtime runtime = Runtime.getRuntime();

	public BackListener() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<TreeNode> list = contentModel.getNodePath();
		if (list.size() == 0) {
			try {
				runtime.exec("wscript error.vbe \"当前根目录为:" + contentModel.getPath() + "!\" error", null, new File(StrUtil.VBS_HOME));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
			
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
