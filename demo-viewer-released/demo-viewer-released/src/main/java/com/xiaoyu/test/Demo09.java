package com.xiaoyu.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class Demo09 {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JButton btn06 = new JButton("showOptionDialog");
		btn06.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] selectionValues = new Object[] { "香蕉", "雪梨", "苹果" };

				// 显示输入对话框, 返回选择的内容, 点击取消或关闭, 则返回null
				Object inputContent = JOptionPane.showInputDialog(frame, "选择一项: ", "标题", JOptionPane.PLAIN_MESSAGE,
						null, selectionValues, selectionValues[0]);
				System.out.println("输入的内容: " + inputContent);
			}
		});
		frame.add(btn06);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(200, 200);
		frame.setVisible(true);
	}

	public void print(String str) {
		System.out.println(str);
		str = "heihei";
	}
}
