package com.xiaoyu.listener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JTextField;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.ui.panel.CataloguePanel;

public class CatalogueFocusListener implements FocusListener{
	
	private JTextField text;
	private Thread thread;
	@Autowired
	private DialogModel dialogModel;
	@Autowired
	private CataloguePanel cataloguePanel;
	private Set<String> store;

	public CatalogueFocusListener() {
	}
	
	public CatalogueFocusListener(JTextField text) {
		super();
		this.text = text;
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		//获得焦点，根据输入的文本关键词匹配
		store = new HashSet<>();
		store.addAll(dialogModel.getCurrentDialogFiles());
		thread = new Thread(() -> {
			while(true) {
				try {
					if(Thread.currentThread().isInterrupted())
						break;
					String key = text.getText();
					List<String> res = store.stream().filter(cname -> {
						if(cname.toLowerCase().contains(key.toLowerCase()))
							return true;
						return false;
					}).collect(Collectors.toList());
					Set<String> currentDialogFiles = dialogModel.getCurrentDialogFiles();
					currentDialogFiles.removeAll(currentDialogFiles);
					currentDialogFiles.addAll(res);
					Thread.sleep(1000);
					cataloguePanel.reload();
					Thread.sleep(1000);
				} catch (Exception e1) {
					e1.printStackTrace();
					Thread.currentThread().interrupt();
				}
				
			}
		});
		thread.start();
	}

	@Override
	public void focusLost(FocusEvent e) {
		thread.interrupt();
		Set<String> currentDialogFiles = dialogModel.getCurrentDialogFiles();
		currentDialogFiles.removeAll(currentDialogFiles);
		currentDialogFiles.addAll(store);
		cataloguePanel.reload();
	}

}
