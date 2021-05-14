package com.xiaoyu.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.tree.DefaultMutableTreeNode;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.model.RunModel;

public class LastListener implements ActionListener {

	@Autowired
	private RunModel runModel;
	@Autowired
	private DialogModel dialogModel;
	private Box box;

	public LastListener() {
	}
	
	public LastListener(Box box) {
		this.box = box;
	}

	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode current = dialogModel.getLastNode();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)current.getRoot();
		DefaultMutableTreeNode pre = (DefaultMutableTreeNode)root.getChildBefore(current);
		System.out.println(pre.toString());
		dialogModel.setLastNode(pre);
		dialogModel.setLastSelect("\\" + pre.toString());
		RunListener run = new RunListener(box);
		if (runModel.isFast()) {
			run.actionPerformed(null);
		}
	}

}
