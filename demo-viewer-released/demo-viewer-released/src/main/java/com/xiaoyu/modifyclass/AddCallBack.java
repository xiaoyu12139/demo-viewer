package com.xiaoyu.modifyclass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
		log.info(new File("target/classes").getAbsoluteFile() + "\\" + target.getName()
		.replace(".", "\\") + ".class");
		FileOutputStream fos = new FileOutputStream(new File("target/classes").getAbsoluteFile() + "\\" + target.getName()
		.replace(".", "\\") + ".class");
		fos.write(code);
		fos.close();
	}
}
