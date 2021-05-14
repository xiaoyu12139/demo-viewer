package com.xiaoyu.listener;

import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.ContentModel;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.ui.panel.ContentPanel;

public class CatalogueTreeListener implements TreeSelectionListener {
	private JTree tree;
	@Autowired
	private DialogModel dialogModel;
	@Autowired
	private ContentPanel contentPanel;
	@Autowired
	private ContentModel contentModel;

	public CatalogueTreeListener() {
	}

	public CatalogueTreeListener(JTree tree) {
		super();
		this.tree = tree;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		dialogModel.setLastNode(node);
		this.dialogModel.setLastSelect("\\" + node.toString());
		long currentClickTime = System.currentTimeMillis();
		if (currentClickTime - this.dialogModel.getLastClickTime() > 500) {
			contentModel.setPath(dialogModel.getRoot() + dialogModel.getLastSelect());
			contentPanel.getPath2Copy().setText(contentModel.getPath());
			contentPanel.reload(contentModel.getPath());
		}
		this.dialogModel.setLastClickTime(currentClickTime);
	}

}
