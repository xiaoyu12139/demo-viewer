package com.xiaoyu.modifyclass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddCallBack {
	public void execute(Class<?> cls, Class<?> target) throws IOException {
		ClassReader cr = new ClassReader(target.getName());
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
		ClassVisitor cv = new ModifyClassAdapter(cw, cls);
		cr.accept(cv, Opcodes.ASM4);
		byte[] code = cw.toByteArray();
		String dir = Stream.of(target.getName().split("\\.")).filter(s -> {
			if(s.equals(target.getSimpleName())) return false;
			return true;
		}).reduce((x, y) -> {
			return x + "\\" + y;
		}).get();
		String path = System.getProperty("user.dir") + "\\tmp\\" + dir;
		new File(path).mkdirs();
		System.out.println("addcallback" + path);
		File file = new File(path + "\\" + target.getSimpleName() + ".class");
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(code);
		fos.close();
	}
}
