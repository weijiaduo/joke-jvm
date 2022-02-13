package com.wjd.rtda.meta.cons;

import com.wjd.classfile.cons.MethodRefConstantInfo;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.MethodMeta;

/**
 * 方法引用
 * @since 2022/1/30
 */
public class MethodRef extends MemberRef {

    private MethodMeta methodMeta;

    public static MethodRef newMethodRef(ConstantPool constantPool, MethodRefConstantInfo constantInfo) {
        MethodRef ref = new MethodRef();
        ref.constantPool = constantPool;
        ref.copyMemberRefInfo(constantInfo);
        return ref;
    }

    public MethodMeta resolvedMethod() {
        if (methodMeta == null) {
            resolveMethodRef();
        }
        return methodMeta;
    }

    private void resolveMethodRef() {
        ClassMeta currentClassMeta = constantPool.getClazz();
        ClassMeta refClassMeta = resolvedClass();
        if (refClassMeta.isInterface()) {
            throw new IncompatibleClassChangeError("Class: " + refClassMeta);
        }
        MethodMeta methodMeta = lookupMethod(refClassMeta, name, descriptor);
        if (methodMeta == null) {
            throw new NoSuchMethodError("Method: " + name);
        }
        if (!methodMeta.isAccessibleTo(currentClassMeta)) {
            throw new IllegalAccessError("Method: " + methodMeta + " can not access by Class: " + currentClassMeta);
        }
        this.methodMeta = methodMeta;
    }

    public static MethodMeta lookupMethod(ClassMeta clazz, String name, String descriptor) {
        MethodMeta methodMeta = lookupMethodInClass(clazz, name, descriptor);
        if (methodMeta == null) {
            methodMeta = lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
        }
        return methodMeta;
    }

    public static MethodMeta lookupMethodInClass(ClassMeta clazz, String name, String descriptor) {
        for (ClassMeta c = clazz; c != null; c = c.getSuperClass()) {
            for (MethodMeta methodMeta : c.getMethods()) {
                if (methodMeta.getName().equals(name) && methodMeta.getDescriptor().equals(descriptor)) {
                    return methodMeta;
                }
            }
        }
        return null;
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
