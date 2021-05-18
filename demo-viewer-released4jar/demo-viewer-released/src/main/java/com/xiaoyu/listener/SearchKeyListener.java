package com.xiaoyu.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JTextField;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.ui.panel.CataloguePanel;

public class SearchKeyListener implements KeyListener{

	@Autowired
	private DialogModel dialogModel;
	@Autowired
	private CataloguePanel cataloguePanel;
	public static Set<String> store;
	private JTextField text;
	
	public SearchKeyListener() {
	}
	
	public SearchKeyListener(Set<String> set, JTextField text) {
		this.text = text;
		store = new HashSet<>();
		store.addAll(set);
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 10) {
			String key = text.getText();
			List<String> res = store.stream().filter(cname -> {
				if(cname.toLowerCase().contains(key.toLowerCase()))
					return true;
				return false;
			}).collect(Collectors.toList());
			Set<String> currentDialogFiles = dialogModel.getCurrentDialogFiles();
			currentDialogFiles.clear();
			currentDialogFiles.addAll(res);
			cataloguePanel.reload();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
