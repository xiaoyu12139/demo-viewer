# demo-viewer 注意：jdk1.8能完美运行，其他版本可能会报错。因为tools.jar在jdk9开始就取消了，引入了jdk.hotspot.agent模块。原来的javaagent使用方式可能会出错
ps:直接使用打包的jar时，不要在有中文的路径下
项目demo快速查看器：开发运行环境java1.8<br/>
1、项目实现为了解决因为各种java类的小型项目运行查看，但是一波又一波导入十分麻烦所以设计了这个小型项目查看器。<br/>
2、左上方的选择按钮，可以选择包含所有项目的父目录<br/>
3、点击搜索框输入内容，实时刷新该目录下关键字匹配的项目<br/>
4、搜索框旁边的按钮为，快速浏览模式开启按钮，默认启动该应用时为off。点击器后，点击下一个项目，或者上一个项目项目自动运行，不需要点击运行后在启动运行<br/>
5、file选项下，可以配置运行规则。包括指定主类，和运行参数。<br/>
ps:项目实现使用了注解+反射+asm+javaAgent+swing。通过反射获取注解，模拟了spring的@autowired按照类型单例注入。asm动态修改class文件，javaAgent热部署加载asm修改后的class文件到jvm（不需要重启jvm）。<br/>
ps:打包的zip解压就可运行，只要安装配置了jdk1.8
