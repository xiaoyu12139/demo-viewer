package com.xiaoyu.listener;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.string.StrUtil;
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
		String temp = dialogModel.getRoot();
		dialogModel.setRoot(chooser.getSelectedFile().getAbsolutePath());
		dialogModel.resetCurrentDialogFiles();
		SearchKeyListener.store.clear();
		SearchKeyListener.store.addAll(dialogModel.getCurrentDialogFiles());
		if (dialogModel.getCurrentDialogFiles().size() == 0) {
			showMessage(temp);
		}
		System.out.println(dialogModel.getCurrentDialogFiles());
		cataloguePanel.reload();
	}

	public void showMessage(String temp) {
		JDialog dialog = new JDialog((JFrame) null, "提示");
		try {
			URL url = new File(StrUtil.WARNING_IMG).toURI().toURL();
			System.out.println(url);
			ImageIcon imageIcon = new ImageIcon(url);
			dialog.setIconImage(imageIcon.getImage());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		dialog.setSize(280, 130);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JLabel label = new JLabel("<html><body>该侧边栏只列出选中文件夹下的所有文件夹！"
				+ "<br/>         目标文件夹下无文件夹，是否跳转</body></html>");
		panel.add(label, BorderLayout.CENTER);
		Box box = Box.createHorizontalBox();
		panel.add(box, BorderLayout.SOUTH);
		JButton cancelBtn = new JButton("否");
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogModel.setRoot(temp);
				dialogModel.resetCurrentDialogFiles();
				cataloguePanel.reload();
				dialog.dispose();
			}
		});
		JButton confirmBtn = new JButton("是");
		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cataloguePanel.reload();
				dialog.dispose();
			}
		});
		box.add(Box.createHorizontalStrut(50));
		box.add(confirmBtn);
		box.add(Box.createHorizontalGlue());
		box.add(cancelBtn);
		box.add(Box.createHorizontalStrut(50));
		dialog.setAlwaysOnTop(true);
		dialog.getContentPane().add(panel);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

}
