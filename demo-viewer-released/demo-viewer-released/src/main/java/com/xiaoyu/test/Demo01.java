package com.xiaoyu.test;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xiaoyu.annotation.Demo;

import lombok.extern.slf4j.Slf4j;
import sun.font.CreatedFontTracker;

@Slf4j
@Demo
public class Demo01 {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Box p = new Demo01().createTopComponent();
		frame.setLayout(new BorderLayout());
//		Box box = Box.createHorizontalBox();
//		Button b1 = new Button();
//		b1.setBackground(Color.black);
//		JPanel b2 = new JPanel();
//		b2.setBackground(Color.blue);
//		
//		box.add(b1);
//		box.add(box.createHorizontalGlue());
//		box.add(b2);
		frame.add(p);
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	public Box createTopComponent() {
		Button choose = new Button("choose");
		Button modelOn = new Button("on");
		Button modelOff = new Button("off");
		JPanel model = new JPanel();
		model.setLayout(new CardLayout());
		model.add(modelOn);
		model.add(modelOff);
		Box box = Box.createHorizontalBox();
		box.add(choose);
		box.add(model);
		return box;
	}
}	
