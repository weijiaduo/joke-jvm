package com.wjd.rtda.meta.cons;

import com.wjd.classfile.cons.MemberConstantInfo;
import com.wjd.rtda.meta.ConstantPool;

/**
 * 成员引用
 * @since 2022/1/30
 */
public class MemberRef extends SymbolRef {

    protected String name;
    protected String descriptor;

    public static MemberRef newMemberRef(ConstantPool constantPool, MemberConstantInfo constantInfo) {
        MemberRef ref = new MemberRef();
        ref.constantPool = constantPool;
        ref.copyMemberRefInfo(constantInfo);
        return ref;
    }

    protected void copyMemberRefInfo(MemberConstantInfo memberConstantInfo) {
        className = memberConstantInfo.getClassName();
        name = memberConstantInfo.getName();
        descriptor = memberConstantInfo.getDescriptor();
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }
}