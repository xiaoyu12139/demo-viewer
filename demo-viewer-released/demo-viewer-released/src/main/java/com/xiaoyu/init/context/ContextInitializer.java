package com.xiaoyu.init.context;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

import com.sun.tools.attach.VirtualMachine;
import com.xiaoyu.annotation.ApplicationBoot;
import com.xiaoyu.annotation.Autowired;
import com.xiaoyu.annotation.InitModel;
import com.xiaoyu.annotation.SingletonBean;
import com.xiaoyu.init.Initializer;
import com.xiaoyu.modifyclass.AddCallBack;
import com.xiaoyu.test.Demo10;

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
				scanClass();
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
			String agentPath = getClass().getResource("/agent/ReloadAsmClassAgent-1.0.0-SNAPSHOT.jar").toString()
					.substring(6).replace("/", "\\");
			String[] callbackArgs = getCallbackArgs().split(",");
			for (String temp : callbackArgs) {
				vm.loadAgent(agentPath, temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getCallbackArgs() {
		List<String> list = new ArrayList<String>(1);
		String base = getClass().getResource("/").toString().replace("/", "\\");
		for (Class<?> cls : callBackClass) {
			String path = base + cls.getName().replace(".", "\\") + ".class";
			String cname = cls.getName();
			String temp = path.substring(6) + "?" + cname;
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

	// ??????????????????class
	public void asmCallBackClass() {
		AddCallBack addCallBack = new AddCallBack();
		boolean flag = false;
		List<String> list = new ArrayList<>();
		for (Class<?> call : callBackClass) {
			try {
				call.newInstance();// ??????????asm??????????????isAsm = true
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
			log.error("????????????:" + list.toString());
			log.error("application exit.");
			System.exit(0);
		}
	}

	// ??????????????????
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

	// ??????????singletonbean????????????????????????
	public void getInjectionAnnotation(Class<?> cls, Class<Autowired> anno, List<Class<?>> list) {
		if (cls.isAnnotationPresent(InitModel.class))
			list.add(cls);
		Field[] declaredFields = cls.getDeclaredFields();
		Stream.of(declaredFields).forEach(field -> {
			if (field.isAnnotationPresent(anno))
				list.add(cls);
		});
	}

	// ??????singletonbean????????autowired????????????????????.
	// ??????????????class????????newinstane ????????????????isAsm=true????asm??????isASm=false
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
