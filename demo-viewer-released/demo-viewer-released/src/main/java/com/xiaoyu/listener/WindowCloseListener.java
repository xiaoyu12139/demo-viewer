package com.xiaoyu.listener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import com.alibaba.fastjson.JSON;
import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.model.ContentModel;
import com.xiaoyu.model.DialogModel;
import com.xiaoyu.model.RunModel;
import com.xiaoyu.string.StrUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WindowCloseListener extends WindowAdapter {

	@Autowired
	private RunModel runModel;
	@Autowired
	private DialogModel dialogModel;
	@Autowired
	private ContentModel contentModel;

	@Override
	public void windowClosing(WindowEvent e) {
		String jsonRun = JSON.toJSONString(runModel);
		String jsonDialog = JSON.toJSONString(dialogModel);
		String jsonContent = JSON.toJSONString(contentModel);
		File file = new File(StrUtil.JSON_MODEL);
		try {
			if (!file.exists())
				file.createNewFile();
			Writer w = new FileWriter(file);
			w.write(jsonRun + "\n");
			w.write(jsonDialog + "\n");
			w.write(jsonContent + "\n");
			w.close();
			log.info(jsonRun);
			log.info(jsonDialog);
			log.info(jsonContent);
			log.info("write end in file:" + StrUtil.JSON_MODEL);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
