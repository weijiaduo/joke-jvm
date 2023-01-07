package com.wjd.rtda.meta.cons;

import com.wjd.classfile.cons.InterfaceMethodRefConstantInfo;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.MethodMeta;
import com.wjd.util.MethodHelper;

/**
 * 接口方法引用
 * @since 2022/1/30
 */
public class InterfaceMethodRef extends MemberRef {

    private MethodMeta method;

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
        if (method == null) {
            resolveInterfaceMethodRef();
        }
        return method;
    }

    /**
     * 解析接口方法符号引用
     */
    private void resolveInterfaceMethodRef() {
        ClassMeta currentClassMeta = constantPool.getClassMeta(); // 当前类
        ClassMeta refClassMeta = resolvedClass(); // 方法所在接口
        if (!refClassMeta.isInterface()) {
            throw new IncompatibleClassChangeError("Class: " + refClassMeta);
        }

        // 查找接口方法
        MethodMeta method = MethodHelper.lookupInterfaceMethod(refClassMeta, name, descriptor);
        if (method == null) {
            throw new NoSuchMethodError("Method: " + name);
        }

        // 验证访问权限
        if (!method.isAccessibleTo(currentClassMeta)) {
            throw new IllegalAccessError("Method: " + method + " can not access by Class: " + currentClassMeta);
        }
        this.method = method;
    }

}
