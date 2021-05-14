package com.xiaoyu.modifyclass;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ModifyMethodAdapter extends MethodVisitor implements Opcodes{
	
	private Class<?> cls;

	public ModifyMethodAdapter(MethodVisitor mv, Class<?> cls) {
		super(Opcodes.ASM4, mv);
		this.cls = cls;
	}

	@Override
	public void visitInsn(int opcode) {
		if (opcode == Opcodes.RETURN) {
//			mv.visitMethodInsn(INVOKESTATIC, cls.getName().replace(".", "/"), "callBack", "()V", false);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(8, l2);
			mv.visitTypeInsn(NEW, cls.getName().replace(".", "/"));
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, cls.getName().replace(".", "/"), "<init>", "()V", false);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKEVIRTUAL, cls.getName().replace(".", "/"), "callback", "(Ljava/lang/Object;)V", false);

		}
		super.visitInsn(opcode);
	}

}
