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
		JDialog dialog = new JDialog((JFrame) null, "��ʾ");
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
		JLabel label = new JLabel("<html><body>�ò����ֻ��ʾ��ѡ���ļ����µ��ļ��У�"
				+ "<br/>         �Ƿ���ת</body></html>");
		panel.add(label, BorderLayout.CENTER);
		Box box = Box.createHorizontalBox();
		panel.add(box, BorderLayout.SOUTH);
		JButton cancelBtn = new JButton("��");
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				dialogModel.setRoot(temp);
//				dialogModel.resetCurrentDialogFiles();
//				cataloguePanel.reload();
				dialog.dispose();
			}
		});
		JButton confirmBtn = new JButton("��");
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
