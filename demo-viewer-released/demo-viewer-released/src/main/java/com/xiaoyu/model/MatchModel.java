package com.xiaoyu.model;

import com.xiaoyu.annotation.SingletonBean;

import lombok.Data;

@SingletonBean
@Data
public class MatchModel {
	// 搜索项目名字的匹配的规则
	private String regex;
	// 当前匹配的规则，是从根目录下的文件的名字匹配
	private boolean fromRoot;
	//根目录下的所有所有文件的名字匹配
	private boolean fromRootChild;
	//是否匹配文件
	private boolean fromFile;
}
