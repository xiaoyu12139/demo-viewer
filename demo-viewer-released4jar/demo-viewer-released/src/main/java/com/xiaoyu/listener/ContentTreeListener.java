package com.xiaoyu.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.ContentModel;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.ui.panel.ContentPanel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContentTreeListener extends MouseAdapter {

	@Autowired
	private ContentPanel contentPanel;
	@Autowired
	private ContentModel contentModel;
	@Autowired
	private DialogModel dialogModel;
	private JTree tree;

	public ContentTreeListener() {
	}

	public ContentTreeListener(JTree tree) {
		this.tree = tree;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// 如果在这棵树上点击了2次,即双击
		if (e.getSource() == tree && e.getClickCount() == 2) {
			TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
			if (selPath != null)// 谨防空指针异常!双击空白处是会这样
			{
				System.out.println(selPath);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
				List<TreeNode> list = new ArrayList<>();
				list.addAll(contentModel.getNodePath());
				list.add(node);
				String res = "";
				for (TreeNode temp : list) {
					res += "\\" + temp.toString();
				}
				File quotion = new File(dialogModel.getRoot() + dialogModel.getLastSelect() + res);
				System.out.println(quotion.toString());
				if (quotion.isFile()) {
					openFile(quotion);
					return ;
				} else if (!quotion.exists()) {
					return;
				}
				List<TreeNode> temp = contentModel.getNodePath();
				temp.removeAll(temp);
				temp.addAll(list);
				contentModel.setPath(quotion.getAbsolutePath());
				String now = contentModel.getPath();
				contentPanel.getPath2Copy().setText(now);
				contentPanel.reload(now);
			}
		}
	}

	public void openFile(File file) {
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			log.info("open\t" + file.getName());
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("rundll32 url.dll FileProtocolHandler " + file.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(tree, "目前并未处理windows外，其他平台兼容处理.");
		}
	}

}
