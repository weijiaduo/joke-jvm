package com.wjd.rtda.heap.cons;

import com.wjd.classfile.cons.InterfaceMethodRefConstantInfo;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.member.Method;

/**
 * 接口方法引用
 * @since 2022/1/30
 */
public class InterfaceMethodRef extends MemberRef {

    private Method method;

    public static InterfaceMethodRef newInterfaceMethodRef(ConstantPool constantPool,
                                                           InterfaceMethodRefConstantInfo constantInfo) {
        InterfaceMethodRef ref = new InterfaceMethodRef();
        ref.constantPool = constantPool;
        ref.copyMemberRefInfo(constantInfo);
        return ref;
    }

    public Method resolvedInterfaceMethod() {
        if (method == null) {
            resolveInterfaceMethodRef();
        }
        return method;
    }

    private void resolveInterfaceMethodRef() {
        Class currentClass = constantPool.getClazz();
        Class refClass = resolvedClass();
        if (!refClass.isInterface()) {
            throw new IncompatibleClassChangeError("Class: " + refClass);
        }
        Method method = lookupInterfaceMethod(refClass, name, descriptor);
        if (method == null) {
            throw new NoSuchMethodError("Method: " + name);
        }
        if (!method.isAccessibleTo(currentClass)) {
            throw new IllegalAccessError("Method: " + method + " can not access by Class: " + currentClass);
        }
        this.method = method;
    }

    public static Method lookupInterfaceMethod(Class clazz, String name, String descriptor) {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                return method;
            }
        }
        return lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
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
