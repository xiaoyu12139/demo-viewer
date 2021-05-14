package com.xiaoyu.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.model.RunModel;

public class NextListener implements ActionListener{

	@Autowired
	private RunModel runModel;
	@Autowired
	private DialogModel dialogModel;
	private Box box;
	
	public NextListener() {
	}
	
	public NextListener(Box box) {
		this.box = box;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode current = dialogModel.getLastNode();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)current.getRoot();
		DefaultMutableTreeNode next = (DefaultMutableTreeNode)root.getChildAfter(current);
		System.out.println(next.toString());
		dialogModel.setLastNode(next);
		dialogModel.setLastSelect("\\" + next.toString());
		RunListener run = new RunListener(box);
		if (runModel.isFast()) {
			run.actionPerformed(null);
		}
	}

}
