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
				Object[] selectionValues = new Object[] { "�㽶", "ѩ��", "ƻ��" };

				// ��ʾ����Ի���, ����ѡ�������, ���ȡ����ر�, �򷵻�null
				Object inputContent = JOptionPane.showInputDialog(frame, "ѡ��һ��: ", "����", JOptionPane.PLAIN_MESSAGE,
						null, selectionValues, selectionValues[0]);
				System.out.println("���������: " + inputContent);
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
