package com.wjd.rtda.heap.member;

import com.wjd.classfile.attr.CodeAttributeInfo;
import com.wjd.classfile.member.MethodInfo;
import com.wjd.rtda.heap.Class;

/**
 * 方法成员
 * @since 2022/1/30
 */
public class Method extends ClassMember {

    private int maxStacks;
    private int maxLocals;
    private byte[] codes;

    public static Method[] newMethods(Class clazz, MethodInfo[] methodInfos) {
        Method[] methods = new Method[methodInfos.length];
        for (int i = 0; i < methods.length; i++) {
            methods[i] = new Method();
            methods[i].clazz = clazz;
            methods[i].copyMemberInfo(methodInfos[i]);
            methods[i].copyAttributes(methodInfos[i]);
        }
        return methods;
    }

    private void copyAttributes(MethodInfo methodInfo) {
        CodeAttributeInfo codeAttr = methodInfo.getCodeAttributeInfo();
        if (codeAttr != null) {
            maxStacks = codeAttr.getMaxStack().value();
            maxLocals = codeAttr.getMaxLocals().value();
            codes = codeAttr.getCodes();
        }
    }

    public int getMaxStacks() {
        return maxStacks;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public byte[] getCodes() {
        return codes;
    }
}
