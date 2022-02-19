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

    private MethodMeta method;

    public static MethodRef newMethodRef(ConstantPool constantPool, MethodRefConstantInfo constantInfo) {
        MethodRef ref = new MethodRef();
        ref.constantPool = constantPool;
        ref.copyMemberRefInfo(constantInfo);
        return ref;
    }

    /**
     * 解析方法符号引用
     */
    public MethodMeta resolvedMethod() {
        if (method == null) {
            resolveMethodRef();
        }
        return method;
    }

    /**
     * 解析方法符号引用
     */
    private void resolveMethodRef() {
        ClassMeta currentClazz = constantPool.getClazz(); // 当前类
        ClassMeta refClazz = resolvedClass(); // 方法所在类
        if (refClazz.isInterface()) {
            throw new IncompatibleClassChangeError("Class: " + refClazz);
        }

        // 查找指定方法
        MethodMeta method = lookupMethod(refClazz, name, descriptor);
        if (method == null) {
            throw new NoSuchMethodError("Method: " + name);
        }

        // 验证访问权限
        if (!method.isAccessibleTo(currentClazz)) {
            throw new IllegalAccessError("Method: " + method + " can not access by Class: " + currentClazz);
        }
        this.method = method;
    }

    /**
     * 寻找指定方法元数据
     * @param clazz 类元数据
     * @param name 方法名称
     * @param descriptor 描述符
     * @return 方法元数据
     */
    public static MethodMeta lookupMethod(ClassMeta clazz, String name, String descriptor) {
        MethodMeta method = lookupMethodInClass(clazz, name, descriptor);
        if (method == null) {
            method = lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
        }
        return method;
    }

    /**
     * 在类中查找指定方法
     * @param clazz 类元数据
     * @param name 方法名称
     * @param descriptor 描述符
     * @return 方法元数据
     */
    public static MethodMeta lookupMethodInClass(ClassMeta clazz, String name, String descriptor) {
        for (ClassMeta c = clazz; c != null; c = c.getSuperClass()) {
            for (MethodMeta method : c.getMethods()) {
                if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 在接口中查找指定方法
     * @param interfaces 界面元数据
     * @param name 方法名称
     * @param descriptor 描述符
     * @return 方法元数据
     */
    private static MethodMeta lookupMethodInInterfaces(ClassMeta[] interfaces, String name, String descriptor) {
        for (ClassMeta iface : interfaces) {
            for (MethodMeta method : iface.getMethods()) {
                if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                    return method;
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
