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

    /**
     * 解析方法符号引用
     */
    public MethodMeta resolvedMethod() {
        if (methodMeta == null) {
            resolveMethodRef();
        }
        return methodMeta;
    }

    /**
     * 解析方法符号引用
     */
    private void resolveMethodRef() {
        ClassMeta currentClassMeta = constantPool.getClazz(); // 当前类
        ClassMeta refClassMeta = resolvedClass(); // 方法所在类
        if (refClassMeta.isInterface()) {
            throw new IncompatibleClassChangeError("Class: " + refClassMeta);
        }

        // 查找指定方法
        MethodMeta methodMeta = lookupMethod(refClassMeta, name, descriptor);
        if (methodMeta == null) {
            throw new NoSuchMethodError("Method: " + name);
        }

        // 验证访问权限
        if (!methodMeta.isAccessibleTo(currentClassMeta)) {
            throw new IllegalAccessError("Method: " + methodMeta + " can not access by Class: " + currentClassMeta);
        }
        this.methodMeta = methodMeta;
    }

    /**
     * 寻找指定方法元数据
     * @param clazz 类元数据
     * @param name 方法名称
     * @param descriptor 描述符
     * @return 方法元数据
     */
    public static MethodMeta lookupMethod(ClassMeta clazz, String name, String descriptor) {
        MethodMeta methodMeta = lookupMethodInClass(clazz, name, descriptor);
        if (methodMeta == null) {
            methodMeta = lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
        }
        return methodMeta;
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
            for (MethodMeta methodMeta : c.getMethods()) {
                if (methodMeta.getName().equals(name) && methodMeta.getDescriptor().equals(descriptor)) {
                    return methodMeta;
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
