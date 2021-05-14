package com.xiaoyu.ui.panel;

import javax.swing.JSplitPane;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.annotation.SingletonBean;
import com.xiaoyu.init.Initializer;

@SingletonBean
public class MainPanel extends JSplitPane implements Initializer{
	
	@Autowired
	private CataloguePanel cataloguePanel;
	@Autowired
	private ContentPanel contentPanel;
	
	public MainPanel() {
		super();
	}

	@Override
	public void initializer() {
		this.setResizeWeight(0.35);
		this.setLeftComponent(cataloguePanel);
		this.setRightComponent(contentPanel);
		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		this.setDividerSize(5);
		this.setDividerLocation(200);
	}
	
	
}
