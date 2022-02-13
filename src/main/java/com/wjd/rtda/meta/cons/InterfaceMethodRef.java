package com.wjd.rtda.meta.cons;

import com.wjd.classfile.cons.InterfaceMethodRefConstantInfo;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.MethodMeta;

/**
 * 接口方法引用
 * @since 2022/1/30
 */
public class InterfaceMethodRef extends MemberRef {

    private MethodMeta methodMeta;

    public static InterfaceMethodRef newInterfaceMethodRef(ConstantPool constantPool,
                                                           InterfaceMethodRefConstantInfo constantInfo) {
        InterfaceMethodRef ref = new InterfaceMethodRef();
        ref.constantPool = constantPool;
        ref.copyMemberRefInfo(constantInfo);
        return ref;
    }

    public MethodMeta resolvedInterfaceMethod() {
        if (methodMeta == null) {
            resolveInterfaceMethodRef();
        }
        return methodMeta;
    }

    private void resolveInterfaceMethodRef() {
        ClassMeta currentClassMeta = constantPool.getClazz();
        ClassMeta refClassMeta = resolvedClass();
        if (!refClassMeta.isInterface()) {
            throw new IncompatibleClassChangeError("Class: " + refClassMeta);
        }
        MethodMeta methodMeta = lookupInterfaceMethod(refClassMeta, name, descriptor);
        if (methodMeta == null) {
            throw new NoSuchMethodError("Method: " + name);
        }
        if (!methodMeta.isAccessibleTo(currentClassMeta)) {
            throw new IllegalAccessError("Method: " + methodMeta + " can not access by Class: " + currentClassMeta);
        }
        this.methodMeta = methodMeta;
    }

    public static MethodMeta lookupInterfaceMethod(ClassMeta clazz, String name, String descriptor) {
        for (MethodMeta methodMeta : clazz.getMethods()) {
            if (methodMeta.getName().equals(name) && methodMeta.getDescriptor().equals(descriptor)) {
                return methodMeta;
            }
        }
        return lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
    }

    private static MethodMeta lookupMethodInInterfaces(ClassMeta[] interfaces, String name, String descriptor) {
        for (ClassMeta iface : interfaces) {
            for (MethodMeta methodMeta : iface.getMethods()) {
                if (methodMeta.getName().equals(name) && methodMeta.getDescriptor().equals(descriptor)) {
                    return methodMeta;
                }
            }
            MethodMeta methodMeta = lookupMethodInInterfaces(iface.getInterfaces(), name, descriptor);
            if (methodMeta != null) {
                return methodMeta;
            }
        }
        return null;
    }

}
