package com.xiaoyu.test;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class Demo14 {
	public static void main(String[] args) {
		JFrame f = new JFrame();
		JPanel p = new JPanel();
		JButton b = new JButton("press");
		b.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println(e.getKeyCode());
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		p.setLayout(new BorderLayout());
		p.add(b, BorderLayout.CENTER);
		p.add(new JButton("12"), BorderLayout.SOUTH);
		f.getContentPane().add(p);
		f.setSize(200, 200);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
