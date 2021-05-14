package com.xiaoyu.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.Box;
import javax.swing.JOptionPane;

import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.model.RunModel;
import com.xiaoyu.string.StrUtil;

public class RunListener implements ActionListener {

	@Autowired
	private RunModel runModel;
	@Autowired
	private DialogModel dialogModel;
	private Box box;
	private Runtime runtime = Runtime.getRuntime();
	

	public RunListener() {
	}
	
	public RunListener(Box box) {
		this.box = box;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String home = System.getProperty("java.home");
			String mainClass = runModel.getMainClass();
			String args = runModel.getArgs();
			String projectSrc = "";
			List<File> list = findSrc();
			if (list.size() == 0) {
				runtime.exec("wscript error.vbe \"当前选择的路径没有src目录，请重新选择\" error", null, new File(StrUtil.VBS_HOME));
				return;
			}
			projectSrc = list.get(0).toString();
			if (list.size() > 1) {
				Map<String, File> map = new HashMap<>();
				for (File temp : list) {
					map.put(temp.toString(), temp);
				}
				Object[] selectionValues = map.keySet().toArray();

				// 显示输入对话框, 返回选择的内容, 点击取消或关闭, 则返回null
				Object inputContent = JOptionPane.showInputDialog(box, "选择一项(取消或关闭，则使用默认选项): ", "指定src目录",
						JOptionPane.PLAIN_MESSAGE, null, selectionValues, selectionValues[0]);
				if (inputContent != null)
					projectSrc = map.get(inputContent).toString();
			}
			if (home == null) {
				runtime.exec("wscript error.vbe \"not find java.home\" error", null, new File(StrUtil.VBS_HOME));
				return;
			}
			if (mainClass == null) {
				runtime.exec("wscript error.vbe \"please set main class first\" error", null, new File(StrUtil.VBS_HOME));
				return;
			}
			String cmd = "cmd /c run.bat" + " useless " + projectSrc + " " + mainClass;
			if(args != null) cmd += " " + args;
			runtime.exec(cmd, null, new File(StrUtil.CMD_HOME));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public List<File> findSrc() throws IOException {
		List<File> list = new ArrayList<>();
		File base = new File(dialogModel.getRoot() + dialogModel.getLastSelect());
		Queue<File> queue = new LinkedList<>();
		queue.offer(base);
		while (!queue.isEmpty()) {
			File poll = queue.poll();
			if(!poll.isDirectory()) continue;
			if (poll.getName().equalsIgnoreCase("src")) {
				list.add(poll);
			}
			for (File temp : poll.listFiles()) {
				if (temp.isDirectory())
					queue.add(temp);
			}
		}
		return list;
	}

}
