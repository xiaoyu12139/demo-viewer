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
		JButton runRule = new JButton("��������");
		runRule.addActionListener(new RunRuleListener(mFrame, runRule));
		file.add(runRule);
		menuBar.add(file);
		JMenu help = new JMenu("help");
		JButton toHelp = new JButton("�����ĵ�");
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
		// ����һ��ģ̬�Ի���
		JDialog dialog = new JDialog(owner, "ʹ�ý���", true);
		// ���öԻ���Ŀ��
		dialog.setSize(350, 450);
		// ���öԻ����С���ɸı�
		dialog.setResizable(false);
		// ���öԻ��������ʾ��λ��
		dialog.setLocationRelativeTo(parentComponent);

		JTextArea info = new JTextArea("information of Demo_Viewer:\n");
		info.setLineWrap(true);
		info.append("1����Ŀʵ��Ϊ�˽����Ϊ����java���С����Ŀ���в鿴��"
				+ "����һ����һ������ʮ���鷳������������С����Ŀ�鿴����\n"
				+ "2�����Ϸ���ѡ��ť������ѡ�����������Ŀ�ĸ�Ŀ¼\n"
				+ "3������������������ݣ�ʵʱˢ�¸�Ŀ¼�¹ؼ���ƥ�����Ŀ\n"
				+ "4���������Աߵİ�ťΪ���������ģʽ������ť��Ĭ��������Ӧ��ʱΪoff��������󣬵����һ����Ŀ��"
				+ "������һ����Ŀ��Ŀ�Զ����У�����Ҫ������к�����������\n"
				+ "5��fileѡ���£������������й��򡣰���ָ�����࣬�����в�����\n"
				+ "ps:��Ŀʵ��ʹ����ע��+����+asm+javaAgent+swing��ͨ�������ȡע�⣬"
				+ "ģ����spring��@autowired�������͵���ע�롣asm��̬�޸�class�ļ���"
				+ "javaAgent�Ȳ������asm�޸ĺ��class�ļ���jvm������Ҫ����jvm����");
		
		// ���öԻ�����������
		dialog.setContentPane(info);
		// ��ʾ�Ի���
		dialog.setVisible(true);
		dialog.setAutoRequestFocus(false);
	}


}
