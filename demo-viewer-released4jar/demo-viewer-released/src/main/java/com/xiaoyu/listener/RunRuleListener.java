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
		// 创建一个模态对话框
		JDialog dialog = new JDialog(owner, "配置运行规则", true);
		// 设置对话框的宽高
		dialog.setSize(250, 120);
		// 设置对话框大小不可改变
		dialog.setResizable(false);
		// 设置对话框相对显示的位置
		dialog.setLocationRelativeTo(parentComponent);

		// 创建一个标签显示消息内容
		JLabel l1 = new JLabel("主类设置：");
		JTextField mainClass = new JTextField(20);

		JLabel l2 = new JLabel("参数设置：");
		JTextField args = new JTextField(20);
		
		
		// 创建一个按钮用于关闭对话框
		JButton okBtn = new JButton("确定");
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!mainClass.getText().equals(StrUtil.MAIN_CLASS_INPUT_TIPS)){
					runModel.setMainClass(mainClass.getText());
				}
				if(!args.getText().equals(StrUtil.ARGS_INPUT_TIPS)) {
					runModel.setArgs(args.getText());
				}
				// 关闭对话框
				dialog.dispose();
			}
		});

		// 创建对话框的内容面板, 在面板内可以根据自己的需要添加任何组件并做任意是布局
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		// 添加组件到面板
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
		
		// 设置对话框的内容面板
		dialog.setContentPane(panel);
		// 显示对话框
		dialog.setVisible(true);
		dialog.setAutoRequestFocus(false);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		log.info(mainClass.getText());
		log.info(args.getText());
	}

}
