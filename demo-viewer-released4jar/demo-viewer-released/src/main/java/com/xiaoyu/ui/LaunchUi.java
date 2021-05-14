package com.xiaoyu.ui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

import com.xiaoyu.annotation.SingletonBean;
import com.xiaoyu.string.StrUtil;

public class LaunchUi extends JWindow {

	public LaunchUi() {
//		String path = getClass().getResource("/imags/welcome.png").toString().substring(6).replace("/", "\\");
//		System.out.println(path);
		JLabel img = new JLabel(new ImageIcon(StrUtil.WELCOME_IMG));
		getContentPane().add(img, BorderLayout.CENTER);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
