package com.xiaoyu.listener;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.ui.panel.ContentPanel;

public class CopyListener implements ActionListener{
	
	@Autowired
	private ContentPanel contentPanel;

	public CopyListener() {
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField path2Copy = contentPanel.getPath2Copy();
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); //得到系统剪贴板
		String text = path2Copy.getText();  //获取文本框的内容
		StringSelection selection = new StringSelection(text);  
		clipboard.setContents(selection, null); 
	}

}
