package com.xiaoyu.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xiaoyu.string.StrUtil;

public class Demo13 {
	public static void main(String[] args) {
		Demo13 main = new Demo13();
		main.showMessage("");
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
		JLabel label = new JLabel("<html><body>该侧边栏只显示该选中文件夹下的文件夹！"
				+ "<br/>         是否跳转</body></html>");
		panel.add(label, BorderLayout.CENTER);
		Box box = Box.createHorizontalBox();
		panel.add(box, BorderLayout.SOUTH);
		JButton cancelBtn = new JButton("否");
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				dialogModel.setRoot(temp);
//				dialogModel.resetCurrentDialogFiles();
//				cataloguePanel.reload();
				dialog.dispose();
			}
		});
		JButton confirmBtn = new JButton("是");
		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				cataloguePanel.reload();
				dialog.dispose();
			}
		});
		box.add(Box.createHorizontalStrut(50));
		box.add(confirmBtn);
		box.add(Box.createHorizontalGlue());
		box.add(cancelBtn);
		box.add(Box.createHorizontalStrut(50));
		dialog.getContentPane().add(panel);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
