package com.xiaoyu.ui.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Set;

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
import javax.swing.tree.TreeNode;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.annotation.SingletonBean;
import com.xiaoyu.init.Initializer;
import com.xiaoyu.listener.CardChangeClickListener;
import com.xiaoyu.listener.CatalogueFocusListener;
import com.xiaoyu.listener.CatalogueTreeListener;
import com.xiaoyu.listener.ChooseListener;
import com.xiaoyu.model.ContentModel;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.model.MatchModel;
import com.xiaoyu.model.ModelFactory;
import com.xiaoyu.model.RunModel;
import com.xiaoyu.string.StrUtil;

import lombok.Data;

@SingletonBean
@Data
public class CataloguePanel extends JPanel implements Initializer{
	
	@Autowired
	private DialogModel dialogModel;
	@Autowired
	private MatchModel matchModel;
	@Autowired
	private RunModel runModel;
	@Autowired
	private ContentModel contentModel;
	
	private JTree reflush;
	private DefaultMutableTreeNode root;
	private DefaultTreeModel r;
	private Set<String> currentDialogFiles;
	Thread t = new Thread();

	public CataloguePanel() {
		super();
	}

	@Override
	public void initializer() {
		this.currentDialogFiles  = dialogModel.getCurrentDialogFiles();
		this.setSize(200, 500);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(layout);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.gridheight = 2;
		setGridBagLayout(createTopComponent(), c, layout);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 1;
		setGridBagLayout(createTreeComponent(), c, layout);
		r = (DefaultTreeModel) reflush.getModel();
		root = (DefaultMutableTreeNode) r.getRoot();
	}

	public void setGridBagLayout(JComponent c, GridBagConstraints gc, GridBagLayout layout) {
		layout.setConstraints(c, gc);
		this.add(c);
	}

	public JComponent createTopComponent() {
		JButton choose = new JButton("Ñ¡Ôñ");
		choose.addActionListener(new ChooseListener(choose, this));
		JButton modelOn = new JButton("on");
		JButton modelOff = new JButton("off");
		JPanel model = new JPanel();
		JTextField field = new JTextField(10);
		field.addFocusListener(new CatalogueFocusListener(field));
		CardLayout cardLayout = new CardLayout();
		model.setLayout(cardLayout);
		model.add(modelOn, "on");
		model.add(modelOff, "off");
		cardLayout.show(model, "off");
		modelOn.addActionListener(new CardChangeClickListener(cardLayout, model, modelOn, modelOff));
		modelOff.addActionListener(new CardChangeClickListener(cardLayout, model, modelOn, modelOff));
		Box box = Box.createHorizontalBox();
		box.add(choose);
		try {
			box.add(new JLabel(new ImageIcon(new File(StrUtil.SEARCH_IMG).toURI().toURL())));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		box.add(field);
		box.add(model);
		return box;
	}

	public JComponent createTreeComponent() {
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		JPanel p = new JPanel(new BorderLayout());
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
						if (leaf) {
							try {
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
		reflush = tree;
		tree.setRootVisible(false);
		tree.addTreeSelectionListener(new CatalogueTreeListener(tree));
		ToolTipManager.sharedInstance().registerComponent(tree);
		scroll.setViewportView(tree);
		p.add(scroll);
//		p.setSize(200, 500);
		return p;
	}

	public void reload() {
		this.currentDialogFiles = dialogModel.getCurrentDialogFiles();
		DefaultMutableTreeNode node = dialogModel.getLastNode();
		Thread relo = new Thread(() -> {
			while (true) {
				try {
					if (Thread.currentThread().isInterrupted())
						break;
					r.reload();
					Thread.sleep(600);
					DefaultMutableTreeNode now = dialogModel.getLastNode();
					if(!now.toString().equals(node.toString())) {
						t.interrupt();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		});
		t = new Thread(() -> {
			try {
				root.removeAllChildren();
				makeTreeNode(root);
				r.reload();
				relo.interrupt();
			} catch (Exception e) {
				e.printStackTrace();
				r.reload();
			}
		});
		t.start();
		relo.setDaemon(true);
		relo.start();
	}

	private TreeNode makeTreeNode() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new File(this.dialogModel.getRoot()).getName());
		for (String name : currentDialogFiles) {
			root.add(new DefaultMutableTreeNode(name));
		}
		return root;
	}

	private void makeTreeNode(DefaultMutableTreeNode root) {
		for (String name : currentDialogFiles) {
			root.add(new DefaultMutableTreeNode(name));
		}
	}

}
