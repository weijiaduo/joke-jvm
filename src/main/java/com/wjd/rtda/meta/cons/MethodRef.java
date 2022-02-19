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
        ClassMeta currentClazz = constantPool.getClazz(); // 当前类
        ClassMeta refClazz = resolvedClass(); // 方法所在类
        if (refClazz.isInterface()) {
            throw new IncompatibleClassChangeError("Class: " + refClazz);
        }

        // 查找指定方法
        MethodMeta method = MethodHelper.lookupMethod(refClazz, name, descriptor);
        if (method == null) {
            throw new NoSuchMethodError("Method: " + name);
        }

        // 验证访问权限
        if (!method.isAccessibleTo(currentClazz)) {
            throw new IllegalAccessError("Method: " + method + " can not access by Class: " + currentClazz);
        }
        this.method = method;
    }

}
