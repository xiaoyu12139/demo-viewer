package com.xiaoyu.init.context;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

import com.sun.tools.attach.VirtualMachine;
import com.xiaoyu.annotation.ApplicationBoot;
import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.annotation.InitModel;
import com.xiaoyu.annotation.SingletonBean;
import com.xiaoyu.init.Initializer;
import com.xiaoyu.modifyclass.AddCallBack;
import com.xiaoyu.string.StrUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextInitializer implements Initializer {

	private Class<?> source;
	private String scanPath;
	private Class<?>[] singletonClass;
	private Class<?>[] needInjectionClass;
	private Map<String, Object> alreadyCreateSingletonBean;
	private static Map<String, Object> alreadyCreateSingletonBeanForCall;
	private Class<?> initModel;
	private Class<?>[] callBackClass;
	private String absolutePath;
	private static boolean isAsm = false;

	public ContextInitializer() {
	}

	public ContextInitializer(Class<?> source) {
		this.source = source;
	}

	@Override
	public void initializer() {
		try {
			if (source.isAnnotationPresent(ApplicationBoot.class)) {
				ApplicationBoot an = source.getAnnotation(ApplicationBoot.class);
				if ("".equals(an.value())) {
					String str = source.getResource("").toString();
					String str2 = str.substring(0, str
							.lastIndexOf(source.getName().replace("." + source.getSimpleName(), "").replace(".", "/")));
					this.absolutePath = str.substring(6, str2.length()).replace("/", "\\");
					this.scanPath = str.substring(6, str.length() - 1);
				} else {
					String scanPackage = an.value();
					this.scanPath = getClass().getResource("/") + "\\" + scanPackage.replace(".", "\\");
				}
//				scanClass();
				scanClass4Jar();
				createSingletonBean();
				asmCallBackClass();
				reloadAsmClass();
				injectionSingletonBean();
				Object model = this.alreadyCreateSingletonBean.get(initModel.getName());
				Method initMethod = initModel.getDeclaredMethod("init");
				initMethod.invoke(model);
				initBean();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void reloadAsmClass() {
		try {
			VirtualMachine vm = VirtualMachine.attach(getProcessID() + "");
			String agentPath = System.getProperty("user.dir") + "\\conf\\agent\\ReloadAsmClassAgent-1.0.0-SNAPSHOT.jar";
			System.out.println("agentPath:" + agentPath);
			String[] callbackArgs = getCallbackArgs().split(",");
			for (String temp : callbackArgs) {
				vm.loadAgent(agentPath, temp);
			}
			File file = new File(System.getProperty("user.dir") + "\\tmp\\");
			delete(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete(File file) {
		if(file.isFile()) {
			file.delete();
		}else {
			for(File temp : file.listFiles()) {
				delete(temp);
			}
			file.delete();
		}
	}

	private String getCallbackArgs() {
		List<String> list = new ArrayList<String>(1);
		String base = System.getProperty("user.dir") + "\\tmp\\";
		System.out.println("getCallbackArgs-base:" + base);
		for (Class<?> cls : callBackClass) {
			String path = base + cls.getName().replace(".", "\\") + ".class";
			String cname = cls.getName();
			String temp = path + "?" + cname;
			list.add(temp);
		}
		return list.stream().reduce((one, two) -> {
			return one + "," + two;
		}).get();
	}

	public static final int getProcessID() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		System.out.println(runtimeMXBean.getName());
		return Integer.valueOf(runtimeMXBean.getName().split("@")[0]).intValue();
	}

	private void initBean() {
		Stream.of(singletonClass).forEach(cls -> {
			try {
				Method init = cls.getDeclaredMethod("initializer");
				Object obj = alreadyCreateSingletonBean.get(cls.getName());
				init.invoke(obj);
			} catch (Exception e) {
				log.info(cls.getName() + " not hava the method initializer");
			}
		});
	}

	/**
	 * 扫描未打包的class
	 */
	public void scanClass() {
		List<Class<?>> listSingleton = new ArrayList<>();
		List<Class<?>> listInjection = new ArrayList<>();
		List<Class<?>> callBackClass = new ArrayList<>();
		try {
			File file = new File(this.scanPath);
			Queue<File> queue = new LinkedList<>();
			queue.offer(file);
			while (!queue.isEmpty()) {
				File poll = queue.poll();
				if (poll.isDirectory()) {
					for (File temp : poll.listFiles()) {
						queue.offer(temp);
					}
					continue;
				}
				String temp = poll.getAbsolutePath().replace(this.absolutePath, "").replace("\\", ".");
				String clsName = temp.substring(0, temp.length() - 6);
				Class<?> forName = Class.forName(clsName);
				getSingletonClass(forName, SingletonBean.class, listSingleton, callBackClass);
				getInjectionAnnotation(forName, Autowired.class, listInjection);
			}
			setSingletonClass(listSingleton);
			setInjectionAnnotation(listInjection);
			setCallBackClass(callBackClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 扫描jar下的class
	 */
	public void scanClass4Jar() {
		List<Class<?>> listSingleton = new ArrayList<>();
		List<Class<?>> listInjection = new ArrayList<>();
		List<Class<?>> callBackClass = new ArrayList<>();
		String jarName = StrUtil.JAR_NAME;
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jarName);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Enumeration<JarEntry> entrys = jarFile.entries();
		while (entrys.hasMoreElements()) {
			try {
				JarEntry jarEntry = entrys.nextElement();
				String tmpName = jarEntry.getName();
				log.info(tmpName);
				if (tmpName.endsWith(".class")) {
					String clsName = tmpName.replace("/", ".").substring(0, tmpName.length() - 6);
					System.out.println(clsName);
					Class<?> forName = Class.forName(clsName);
					getSingletonClass(forName, SingletonBean.class, listSingleton, callBackClass);
					getInjectionAnnotation(forName, Autowired.class, listInjection);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		setSingletonClass(listSingleton);
		setInjectionAnnotation(listInjection);
		setCallBackClass(callBackClass);
	}

	private void setCallBackClass(List<Class<?>> list) {
		this.callBackClass = list.toArray(new Class<?>[list.size()]);
	}

	private void setInjectionAnnotation(List<Class<?>> list) {
		this.needInjectionClass = list.toArray(new Class<?>[list.size()]);
	}

	public void getSingletonClass(Class<?> cls, Class<SingletonBean> anno, List<Class<?>> list,
			List<Class<?>> callList) {
		if (cls.isAnnotationPresent(anno)) {
			list.add(cls);
		} else {
			for (Field field : cls.getDeclaredFields()) {
				if (field.isAnnotationPresent(Autowired.class)) {
					callList.add(cls);
					break;
				}
			}
		}
		if (cls.isAnnotationPresent(InitModel.class)) {
			list.add(cls);
			initModel = cls;
		}
	}

	public void setSingletonClass(List<Class<?>> list) {
		this.singletonClass = list.toArray(new Class[list.size()]);
	}

	public void createSingletonBean() {
		this.alreadyCreateSingletonBean = new HashMap<>(1);
		alreadyCreateSingletonBeanForCall = new HashMap<>(1);
		Class<?>[] needCreateBean = this.singletonClass;
		for (Class<?> cls : needCreateBean) {
			try {
				Object instance = cls.newInstance();
				this.alreadyCreateSingletonBean.put(cls.getName(), instance);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		alreadyCreateSingletonBeanForCall.putAll(this.alreadyCreateSingletonBean);
	}

	// 修改需要回调接口的class
	public void asmCallBackClass() {
		AddCallBack addCallBack = new AddCallBack();
		boolean flag = false;
		List<String> list = new ArrayList<>();
		for (Class<?> call : callBackClass) {
			try {
				call.newInstance();// 试算，如果asm过，那么回调后isAsm = true
				if (!isAsm)
					addCallBack.execute(getClass(), call);
				isAsm = false;
			} catch (InstantiationException e1) {
				flag = true;
				list.add(call.getName());
			} catch (IOException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if (flag) {
			log.error("没有无参构造:" + list.toString());
			log.error("application exit.");
			System.exit(0);
		}
	}

	// 按照类型注入单例类
	public void injectionSingletonBean() {
		for (Class<?> cls : needInjectionClass) {
			Field[] declaredFields = cls.getDeclaredFields();
			Stream.of(declaredFields).forEach(field -> {
				if (field.isAnnotationPresent(Autowired.class) || cls.isAnnotationPresent(InitModel.class)) {
					String targetName = cls.getName();
					Object target = alreadyCreateSingletonBean.get(targetName);
					String objName = field.getType().getTypeName();
					Object obj = alreadyCreateSingletonBean.get(objName);
					try {
						field.setAccessible(true);
						if (obj != null && target != null)
							field.set(target, obj);
					} catch (Exception e) {
						log.info(field.getType() + ":" + cls.getName() + "\tinjection faild.");
					}
				}
			});
		}
	}

	// 扫描得到有singletonbean注解的类的需要注入的字段
	public void getInjectionAnnotation(Class<?> cls, Class<Autowired> anno, List<Class<?>> list) {
		if (cls.isAnnotationPresent(InitModel.class))
			list.add(cls);
		Field[] declaredFields = cls.getDeclaredFields();
		Stream.of(declaredFields).forEach(field -> {
			if (field.isAnnotationPresent(anno))
				list.add(cls);
		});
	}

	// 没有加singletonbean注解，有autowired注解的类，回调该函数.
	// 判断是否修改过class，先调用newinstane 试算。回调进入就isAsm=true，在asm后在将isASm=false
	public void callback(Object obj) {
		isAsm = true;
		Class<?> cls = obj.getClass();
		log.info(cls.getName() + " start callback");
		Stream.of(cls.getDeclaredFields()).forEach(field -> {
			if (field.isAnnotationPresent(Autowired.class)) {
				Object need = alreadyCreateSingletonBeanForCall.get(field.getType().getName());
				try {
					field.setAccessible(true);
					field.set(obj, need);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
