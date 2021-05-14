package com.xiaoyu.model;

import com.xiaoyu.annotation.SingletonBean;

import lombok.Data;

@SingletonBean
@Data
public class MatchModel {
	// ������Ŀ���ֵ�ƥ��Ĺ���
	private String regex;
	// ��ǰƥ��Ĺ����ǴӸ�Ŀ¼�µ��ļ�������ƥ��
	private boolean fromRoot;
	//��Ŀ¼�µ����������ļ�������ƥ��
	private boolean fromRootChild;
	//�Ƿ�ƥ���ļ�
	private boolean fromFile;
}
