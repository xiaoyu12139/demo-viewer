package com.xiaoyu.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.ContentModel;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.ui.panel.CataloguePanel;
import com.xiaoyu.ui.panel.ContentPanel;

public class RefreshActionListener implements ActionListener {

	@Autowired
	private ContentModel contentModel;
	@Autowired
	private DialogModel dialogModel;
	@Autowired
	private ContentPanel contentPanel;
	@Autowired
	private CataloguePanel cataloguePanel;

	public RefreshActionListener() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String now = contentModel.getPath();
		contentPanel.getPath2Copy().setText(now);
		contentPanel.reload(now);
		cataloguePanel.reload();
	}

}
