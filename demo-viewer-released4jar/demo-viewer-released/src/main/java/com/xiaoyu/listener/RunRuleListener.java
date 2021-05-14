package com.xiaoyu.listener;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.RunModel;
import com.xiaoyu.string.StrUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunRuleListener implements ActionListener {

	@Autowired
	private RunModel runModel;
	private JFrame frame;

	public RunRuleListener() {
	}

	public RunRuleListener(JFrame frame, JButton runRule) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		showCustomDialog(frame, frame);
	}

	private  void showCustomDialog(Frame owner, Component parentComponent) {
		// ����һ��ģ̬�Ի���
		JDialog dialog = new JDialog(owner, "�������й���", true);
		// ���öԻ���Ŀ��
		dialog.setSize(250, 120);
		// ���öԻ����С���ɸı�
		dialog.setResizable(false);
		// ���öԻ��������ʾ��λ��
		dialog.setLocationRelativeTo(parentComponent);

		// ����һ����ǩ��ʾ��Ϣ����
		JLabel l1 = new JLabel("�������ã�");
		JTextField mainClass = new JTextField(20);

		JLabel l2 = new JLabel("�������ã�");
		JTextField args = new JTextField(20);
		
		
		// ����һ����ť���ڹرնԻ���
		JButton okBtn = new JButton("ȷ��");
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!mainClass.getText().equals(StrUtil.MAIN_CLASS_INPUT_TIPS)){
					runModel.setMainClass(mainClass.getText());
				}
				if(!args.getText().equals(StrUtil.ARGS_INPUT_TIPS)) {
					runModel.setArgs(args.getText());
				}
				// �رնԻ���
				dialog.dispose();
			}
		});

		// �����Ի�����������, ������ڿ��Ը����Լ�����Ҫ����κ�������������ǲ���
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		// �����������
		panel.add(l1);
		panel.add(mainClass);
		panel.add(l2);
		panel.add(args);
		panel.add(okBtn);
		
		panel.setFocusable(true);
		panel.setRequestFocusEnabled(true);
		panel.requestFocus();

		
		args.setText(StrUtil.ARGS_INPUT_TIPS);
		mainClass.setText(StrUtil.MAIN_CLASS_INPUT_TIPS);
		mainClass.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				mainClass.setText("");
			}
		});
		args.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				args.setText("");
			}
		});
		
		// ���öԻ�����������
		dialog.setContentPane(panel);
		// ��ʾ�Ի���
		dialog.setVisible(true);
		dialog.setAutoRequestFocus(false);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		log.info(mainClass.getText());
		log.info(args.getText());
	}

}
