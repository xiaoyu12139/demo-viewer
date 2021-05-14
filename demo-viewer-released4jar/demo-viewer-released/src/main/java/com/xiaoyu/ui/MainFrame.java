package com.xiaoyu.ui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.annotation.SingletonBean;
import com.xiaoyu.init.Initializer;
import com.xiaoyu.listener.RefreshActionListener;
import com.xiaoyu.listener.RunRuleListener;
import com.xiaoyu.listener.WindowCloseListener;
import com.xiaoyu.string.StrUtil;
import com.xiaoyu.ui.panel.MainPanel;

import lombok.Data;

@SingletonBean
@Data
public class MainFrame extends JFrame implements Initializer {

	@Autowired
	private MainPanel mainPanel;
	@Autowired
	private static MainFrame mFrame;

	public MainFrame() {
		super();
	}

	@Override
	public void initializer() {
		this.setTitle("Demo Viewer");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("file");
		JButton runRule = new JButton("运行配置");
		runRule.addActionListener(new RunRuleListener(mFrame, runRule));
		file.add(runRule);
		menuBar.add(file);
		JMenu help = new JMenu("help");
		JButton toHelp = new JButton("帮助文档");
		help.add(toHelp);
		toHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showCustomDialog(mFrame, mFrame);
			}
		});
		menuBar.add(help);
		JButton refresh = new JButton("refresh");
		refresh.addActionListener(new RefreshActionListener());
		menuBar.add(refresh);
		this.getRootPane().setJMenuBar(menuBar);
		setWindowIcon();
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.getContentPane().add(mainPanel);
		this.addWindowListener(new WindowCloseListener());
	}

	public static void run() {
		mFrame.setVisible(true);
	}

	public void setWindowIcon() {
		try {
			ImageIcon imageIcon = new ImageIcon(new File(StrUtil.LOG_IMG).toURI().toURL());
			System.out.println("log:" + StrUtil.LOG_IMG);
			this.setIconImage(imageIcon.getImage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
	
	private  void showCustomDialog(Frame owner, Component parentComponent) {
		// 创建一个模态对话框
		JDialog dialog = new JDialog(owner, "使用介绍", true);
		// 设置对话框的宽高
		dialog.setSize(350, 450);
		// 设置对话框大小不可改变
		dialog.setResizable(false);
		// 设置对话框相对显示的位置
		dialog.setLocationRelativeTo(parentComponent);

		JTextArea info = new JTextArea("information of Demo_Viewer:\n");
		info.setLineWrap(true);
		info.append("1、项目实现为了解决因为各种java类的小型项目运行查看，"
				+ "但是一波又一波导入十分麻烦所以设计了这个小型项目查看器。\n"
				+ "2、左上方的选择按钮，可以选择包含所有项目的父目录\n"
				+ "3、点击搜索框输入内容，实时刷新该目录下关键字匹配的项目\n"
				+ "4、搜索框旁边的按钮为，快速浏览模式开启按钮，默认启动该应用时为off。点击器后，点击下一个项目，"
				+ "或者上一个项目项目自动运行，不需要点击运行后在启动运行\n"
				+ "5、file选项下，可以配置运行规则。包括指定主类，和运行参数。\n"
				+ "ps:项目实现使用了注解+反射+asm+javaAgent+swing。通过反射获取注解，"
				+ "模拟了spring的@autowired按照类型单例注入。asm动态修改class文件，"
				+ "javaAgent热部署加载asm修改后的class文件到jvm（不需要重启jvm）。");
		
		// 设置对话框的内容面板
		dialog.setContentPane(info);
		// 显示对话框
		dialog.setVisible(true);
		dialog.setAutoRequestFocus(false);
	}


}
