package com.wjd.rtda.meta.cons;

import com.wjd.classfile.cons.MethodRefConstantInfo;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.MethodMeta;
import com.wjd.util.MethodHelper;

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
        ClassMeta currentClassMeta = constantPool.getClassMeta(); // 当前类
        ClassMeta refClassMeta = resolvedClass(); // 方法所在类
        if (refClassMeta.isInterface()) {
            throw new IncompatibleClassChangeError("Class: " + refClassMeta);
        }

        // 查找指定方法
        MethodMeta method = MethodHelper.lookupMethod(refClassMeta, name, descriptor);
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
