package com.wjd.rtda.heap.cons;

import com.wjd.classfile.cons.MethodRefConstantInfo;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.member.Method;

/**
 * 方法引用
 * @since 2022/1/30
 */
public class MethodRef extends MemberRef {

    private Method method;

    public static MethodRef newMethodRef(ConstantPool constantPool, MethodRefConstantInfo constantInfo) {
        MethodRef ref = new MethodRef();
        ref.constantPool = constantPool;
        ref.copyMemberRefInfo(constantInfo);
        return ref;
    }

    public Method resolvedMethod() {
        if (method == null) {
            resolveMethodRef();
        }
        return method;
    }

    private void resolveMethodRef() {
        Class currentClass = constantPool.getClazz();
        Class refClass = resolvedClass();
        if (refClass.isInterface()) {
            throw new IncompatibleClassChangeError("Class: " + refClass);
        }
        Method method = lookupMethod(refClass, name, descriptor);
        if (method == null) {
            throw new NoSuchMethodError("Method: " + name);
        }
        if (!method.isAccessibleTo(currentClass)) {
            throw new IllegalAccessError("Method: " + method + " can not access by Class: " + currentClass);
        }
        this.method = method;
    }

    public static Method lookupMethod(Class clazz, String name, String descriptor) {
        Method method = lookupMethodInClass(clazz, name, descriptor);
        if (method == null) {
            method = lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
        }
        return method;
    }

    public static Method lookupMethodInClass(Class clazz, String name, String descriptor) {
        for (Class c = clazz; c != null; c = c.getSuperClass()) {
            for (Method method: c.getMethods()) {
                if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                    return method;
                }
            }
        }
        return null;
    }

    private static Method lookupMethodInInterfaces(Class[] interfaces, String name, String descriptor) {
        for (Class iface : interfaces) {
            for (Method method : iface.getMethods()) {
                if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                    return method;
                }
            }
            Method method = lookupMethodInInterfaces(iface.getInterfaces(), name, descriptor);
            if (method != null) {
                return method;
            }
        }
        return null;
    }

}
