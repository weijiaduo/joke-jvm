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
    private MethodDescriptor methodDescriptor;
    private int paramSlotCount;
    private int argSlotCount;
    private byte[] codes;

    public static Method[] newMethods(Class clazz, MethodInfo[] methodInfos) {
        Method[] methods = new Method[methodInfos.length];
        for (int i = 0; i < methods.length; i++) {
            methods[i] = new Method();
            methods[i].clazz = clazz;
            methods[i].copyMemberInfo(methodInfos[i]);
            methods[i].copyAttributes(methodInfos[i]);
            methods[i].calcArgSlotCount();
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

    private void calcArgSlotCount() {
        methodDescriptor = MethodDescriptorParser.parseMethodDescriptor(descriptor);
        paramSlotCount = methodDescriptor.getParamSlotCount();
        argSlotCount = paramSlotCount;
        if (!isStatic()) {
            // this对象
            argSlotCount++;
        }
    }

    public int getMaxStacks() {
        return maxStacks;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public int getParamSlotCount() {
        return paramSlotCount;
    }

    public int getArgSlotCount() {
        return argSlotCount;
    }

    public byte[] getCodes() {
        return codes;
    }
}
