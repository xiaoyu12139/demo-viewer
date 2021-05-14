package com.xiaoyu.modifyclass;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ModifyClassAdapter extends ClassVisitor  {
	
	private Class<?> cls;
	
    public ModifyClassAdapter(ClassVisitor cv, Class<?> cls) {
        super(Opcodes.ASM4, cv);
        this.cls = cls;
    }

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
			String[] exceptions) {
		if("<init>".equals(name))  //�˴���changeMethodContent��Ϊ��Ҫ�޸ĵķ���  ���޸ķ�������
	    {  
	        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);//�ȵõ�ԭʼ�ķ���  
	        MethodVisitor newMethod = null;  
	        newMethod = new ModifyMethodAdapter(mv, cls); //������Ҫ�޸ĵķ���  
	        return newMethod;  
	    }  
		return super.visitMethod(access, name, descriptor, signature, exceptions);
	}
    
}
