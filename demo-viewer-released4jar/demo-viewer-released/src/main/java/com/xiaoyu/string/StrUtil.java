package com.xiaoyu.string;

public class StrUtil {
//	public static String CONTEXT_PATH = StrUtil.class.getResource("/").toString().replace("/", "\\").substring(6);
	public static String CONTEXT_PATH = System.getProperty("user.dir") + "\\conf";
	public static String LOG_IMG = CONTEXT_PATH + "\\imags\\demo_viewer.png";
	public static String SEARCH_IMG = CONTEXT_PATH + "\\imags\\search.png";
	public static String COPY_IMG = CONTEXT_PATH + "\\imags\\copy.png";
	public static String BACK_IMG = CONTEXT_PATH + "\\imags\\back.png";
	public static String WELCOME_IMG = CONTEXT_PATH + "\\imags\\welcome.png";
	public static String FOLDER_IMG = CONTEXT_PATH + "\\imags\\folder.png";
	public static String REFRESH_IMG = CONTEXT_PATH + "\\imags\\refresh.png";
	//配置运行规则的输入框提示
	public static String MAIN_CLASS_INPUT_TIPS ="主类全路径，例：com.xiaoyu.demo";
	public static String ARGS_INPUT_TIPS  = "输入参数如 -d c:\\target";
	
	public static String JSON_MODEL = CONTEXT_PATH + "\\json_object\\model";
	
	public static String CMD_HOME = CONTEXT_PATH + "\\cmd";
	public static String VBS_HOME = CONTEXT_PATH + "\\vbs";
	
	public static String END_BAT = CONTEXT_PATH + "\\imags\\bat.png";
	public static String END_VBS = CONTEXT_PATH + "\\imags\\vbs.png";
	public static String END_JAVA = CONTEXT_PATH + "\\imags\\java.png";
	public static String END_CLASS = CONTEXT_PATH + "\\imags\\class.png";
	public static String END_JAR = CONTEXT_PATH + "\\imags\\jar.png";
	public static String END_JSON = CONTEXT_PATH + "\\imags\\json.png";
	public static String END_TXT = CONTEXT_PATH + "\\imags\\txt.png";
	public static String END_XML = CONTEXT_PATH + "\\imags\\xml.png";
	public static String END_EXE = CONTEXT_PATH + "\\imags\\exe.png";
	public static String END_IMG = CONTEXT_PATH + "\\imags\\image.png";
	public static String END_UNKNOW = CONTEXT_PATH + "\\imags\\unknow.png";
	public static String END_COMPRESS = CONTEXT_PATH + "\\imags\\compress.png";
	public static String WARNING_IMG = CONTEXT_PATH + "\\imags\\warning.png";
	
	
}
