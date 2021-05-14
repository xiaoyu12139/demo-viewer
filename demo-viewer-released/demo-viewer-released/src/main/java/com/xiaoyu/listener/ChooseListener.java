package com.xiaoyu.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.ui.panel.CataloguePanel;

public class ChooseListener implements ActionListener {
	
	private JButton c;
	private JPanel panel;
	
	@Autowired
	private CataloguePanel cataloguePanel;
	@Autowired
	private DialogModel dialogModel;
	
	public ChooseListener() {
	}
	
	public ChooseListener(JButton choose, JPanel panel) {
		super();
		this.c = choose;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.showOpenDialog(panel);
		dialogModel.setRoot(chooser.getSelectedFile().getAbsolutePath());
		dialogModel.resetCurrentDialogFiles();
		System.out.println(dialogModel.getCurrentDialogFiles());
		cataloguePanel.reload();
	}

}
