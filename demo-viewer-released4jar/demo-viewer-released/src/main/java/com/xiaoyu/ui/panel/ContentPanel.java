package com.xiaoyu.ui.panel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.annotation.SingletonBean;
import com.xiaoyu.init.Initializer;
import com.xiaoyu.listener.BackListener;
import com.xiaoyu.listener.ContentTreeListener;
import com.xiaoyu.listener.CopyListener;
import com.xiaoyu.listener.LastListener;
import com.xiaoyu.listener.NextListener;
import com.xiaoyu.listener.RunListener;
import com.xiaoyu.model.ContentModel;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.model.MatchModel;
import com.xiaoyu.model.RunModel;
import com.xiaoyu.string.StrUtil;

import lombok.Data;

@SingletonBean
@Data
public class ContentPanel extends JPanel implements Initializer {

	@Autowired
	private DialogModel dialogModel;
	@Autowired
	private MatchModel matchModel;
	@Autowired
	private RunModel runModel;
	@Autowired
	private ContentModel contentModel;

	private JTree forRefulsh;
	private DefaultTreeModel r;
	private DefaultMutableTreeNode root;
	private String path = "kong";
	private JTextField path2Copy;

	public ContentPanel() {
		super();
	}

	@Override
	public void initializer() {
		this.path = dialogModel.getRoot() + dialogModel.getLastSelect();
		System.out.println("contentpanel:" + path);
		this.setSize(400, 500);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(layout);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.gridheight = 2;
		try {
			setGridBagLayout(createTopComponent(), c, layout);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridy = 2;
		setGridBagLayout(createMidComponent(), c, layout);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridy = 5;
		setGridBagLayout(createBottomComponent(), c, layout);
		r = (DefaultTreeModel) forRefulsh.getModel();
		root = (DefaultMutableTreeNode) r.getRoot();
	}

	public void setGridBagLayout(JComponent c, GridBagConstraints gc, GridBagLayout layout) {
		layout.setConstraints(c, gc);
		this.add(c);
	}

	public JComponent createTopComponent() throws MalformedURLException {
		Box box = Box.createHorizontalBox();
		JButton back = new JButton(new ImageIcon(new File(StrUtil.BACK_IMG).toURI().toURL()));
		back.addActionListener(new BackListener());
		JTextField path = new JTextField(6);
		System.out.println(contentModel.getPath());
		path.setText(dialogModel.getRoot() + dialogModel.getLastSelect());
		path.setEditable(false);
		this.path2Copy = path;
		JButton copy;
		copy = new JButton(new ImageIcon(new File(StrUtil.COPY_IMG).toURI().toURL()));
		copy.addActionListener(new CopyListener());
		box.add(back);
		box.add(new JLabel("path:"));
		box.add(path);
		box.add(copy);
		return box;
	}

	public JComponent createMidComponent() {
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		@SuppressWarnings("serial")
		JTree tree = new JTree(new DefaultTreeModel(makeTreeNode())) {
			@Override
			public void updateUI() {
				setCellRenderer(null);
				super.updateUI();
				setCellRenderer(new DefaultTreeCellRenderer() {
					@Override
					public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
							boolean expanded, boolean leaf, int row, boolean hasFocus) {
						super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
						if(leaf) {
							try {
								DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
								String nname = node.toString();
								if (nname.endsWith(".class")) {
									setIcon(new ImageIcon(new File(StrUtil.END_CLASS).toURI().toURL()));
									return this;
								}
								if (nname.endsWith(".java")) {
									setIcon(new ImageIcon(new File(StrUtil.END_JAVA).toURI().toURL()));
									return this;
								}
								if (nname.endsWith(".exe")) {
									setIcon(new ImageIcon(new File(StrUtil.END_EXE).toURI().toURL()));
									return this;
								}
								if (nname.endsWith(".txt")) {
									setIcon(new ImageIcon(new File(StrUtil.END_TXT).toURI().toURL()));
									return this;
								}
								if (nname.endsWith(".xml")) {
									setIcon(new ImageIcon(new File(StrUtil.END_XML).toURI().toURL()));
									return this;
								}
								if (nname.endsWith(".json")) {
									setIcon(new ImageIcon(new File(StrUtil.END_JSON).toURI().toURL()));
									return this;
								}
								if (nname.endsWith(".jar")) {
									setIcon(new ImageIcon(new File(StrUtil.END_JAR).toURI().toURL()));
									return this;
								}
								if (nname.endsWith(".jpg") || nname.endsWith(".png")) {
									setIcon(new ImageIcon(new File(StrUtil.END_IMG).toURI().toURL()));
									return this;
								}

								if (nname.endsWith(".bat")) {
									setIcon(new ImageIcon(new File(StrUtil.END_BAT).toURI().toURL()));
									return this;
								}
								if (nname.endsWith(".vbs") || nname.endsWith(".vbe")) {
									setIcon(new ImageIcon(new File(StrUtil.END_VBS).toURI().toURL()));
									return this;
								}
								if (nname.endsWith(".zip") || nname.endsWith(".rar")) {
									setIcon(new ImageIcon(new File(StrUtil.END_COMPRESS).toURI().toURL()));
									return this;
								}
								if (nname.matches(".+\\..+")) {
									setIcon(new ImageIcon(new File(StrUtil.END_UNKNOW).toURI().toURL()));
									return this;
								}
								setIcon(new ImageIcon(new File(StrUtil.FOLDER_IMG).toURI().toURL()));
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}
						}
						return this;
					}
				});
			}
		};
		forRefulsh = tree;
		tree.addMouseListener(new ContentTreeListener(tree));
		tree.setRootVisible(false);
		ToolTipManager.sharedInstance().registerComponent(tree);
		scroll.setViewportView(tree);
		return scroll;
	}

	public void reload(String path) {
		this.path = path;
		Thread relo = new Thread(() -> {
			while (true) {
				try {
					r.reload();
					if (Thread.currentThread().isInterrupted())
						break;
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		});
		new Thread(() -> {
			root.removeAllChildren();
			makeTreeNode(root);
			r.reload();
			relo.interrupt();
		}).start();
		relo.setDaemon(true);
		relo.start();
	}

	private TreeNode makeTreeNode() {
		if (dialogModel.getLastSelect() == null)
			return new DefaultMutableTreeNode();
		File list = new File(path);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(list.getName());
		recursion(list, root);
		return root;
	}

	private void makeTreeNode(DefaultMutableTreeNode root) {
		File list = new File(path);
		recursion(list, root);
	}

	// 先遍历第一层，全部添加到root
	public void recursion(File file, DefaultMutableTreeNode root) {
		if (file == null)
			return;
		List<File> list = new ArrayList<>();
		for (File temp : file.listFiles()) {
			list.add(temp);
		}
		Collections.sort(list, new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				if (o1.isDirectory() && !o2.isDirectory()) {
					return -1;
				}
				if (o2.isDirectory() && !o1.isDirectory()) {
					return 1;
				}
				return o1.getName().compareTo(o2.getName());
			}

		});
		System.out.println(list);
		for (File temp : list) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(temp.getName());
			root.add(node);
		}
	}

	public JComponent createBottomComponent() {
		JButton last = new JButton("上一个");
		JButton run = new JButton("运行");
		JButton next = new JButton("下一个");
		Box box = Box.createHorizontalBox();

		run.addActionListener(new RunListener(box));
		last.addActionListener(new LastListener(box));
		next.addActionListener(new NextListener(box));

		box.add(box.createHorizontalStrut(120));
		box.add(last);
		box.add(box.createHorizontalStrut(10));
		box.add(run);
		box.add(box.createHorizontalStrut(10));
		box.add(next);
		box.add(box.createHorizontalStrut(10));
		return box;
	}

}
