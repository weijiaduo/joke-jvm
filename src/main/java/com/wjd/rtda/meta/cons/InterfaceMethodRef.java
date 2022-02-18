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

    /**
     * 解析接口方法符号引用
     */
    public MethodMeta resolvedInterfaceMethod() {
        if (methodMeta == null) {
            resolveInterfaceMethodRef();
        }
        return methodMeta;
    }

    /**
     * 解析接口方法符号引用
     */
    private void resolveInterfaceMethodRef() {
        ClassMeta currentClassMeta = constantPool.getClazz(); // 当前类
        ClassMeta refClassMeta = resolvedClass(); // 方法所在接口
        if (!refClassMeta.isInterface()) {
            throw new IncompatibleClassChangeError("Class: " + refClassMeta);
        }

        // 查找接口方法
        MethodMeta methodMeta = lookupInterfaceMethod(refClassMeta, name, descriptor);
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
     * 查找接口方法元数据
     * @param clazz 类元数据
     * @param name 接口方法名称
     * @param descriptor 接口方法描述符
     * @return 方法元数据
     */
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
