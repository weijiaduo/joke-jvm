package com.wjd.rtda.heap.cons;

import com.wjd.classfile.cons.MethodRefConstantInfo;
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

}
