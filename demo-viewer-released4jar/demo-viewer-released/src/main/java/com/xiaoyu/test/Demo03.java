package com.xiaoyu.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Demo03 {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		root.add(new DefaultMutableTreeNode("1"));
		DefaultTreeModel r = new DefaultTreeModel(root);
		JTree tree = new JTree(new DefaultTreeModel(root));
		frame.add(tree,  BorderLayout.NORTH);
		frame.setLocationRelativeTo(null);
		frame.setSize(500, 500);
		JButton b = new JButton("test");
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				root.add(new DefaultMutableTreeNode("2"));
				System.out.println(1);
				DefaultTreeModel re = (DefaultTreeModel)tree.getModel();
				re.reload();
			}
		});
		frame.add(b, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
}
