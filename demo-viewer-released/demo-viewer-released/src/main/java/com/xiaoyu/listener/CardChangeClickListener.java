package com.xiaoyu.listener;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.RunModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CardChangeClickListener implements ActionListener{
	
	private CardLayout cardLayout;
	private JPanel panel;
	private JButton on;
	private JButton off;
	@Autowired
	private RunModel runModel;
	
	public CardChangeClickListener() {
	}
	
	public CardChangeClickListener(CardLayout cardLayout, JPanel panel, JButton on, JButton off) {
		super();
		this.cardLayout = cardLayout;
		this.panel = panel;
		this.on = on;
		this.off = off;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cardLayout.next(panel);
		boolean isFast = runModel.isFast();
		isFast ^= true;
		runModel.setFast(isFast);
		log.info(isFast + "");
		
	}

	
}
