package com.wjd.rtda.meta;

import com.wjd.classfile.attr.CodeAttributeInfo;
import com.wjd.classfile.member.MethodInfo;

/**
 * 方法成员
 * @since 2022/1/30
 */
public class MethodMeta extends MemberMeta {

    private int maxStacks;
    private int maxLocals;
    private MethodDescriptor methodDescriptor;
    private int paramSlotCount;
    private int argSlotCount;
    private byte[] codes;

    public static MethodMeta[] newMethods(ClassMeta clazz, MethodInfo[] methodInfos) {
        MethodMeta[] methodMetas = new MethodMeta[methodInfos.length];
        for (int i = 0; i < methodMetas.length; i++) {
            methodMetas[i] = newMethod(clazz, methodInfos[i]);
        }
        return methodMetas;
    }

    public static MethodMeta newMethod(ClassMeta classMeta, MethodInfo methodInfo) {
        MethodMeta methodMeta = new MethodMeta();
        methodMeta.clazz = classMeta;
        methodMeta.copyMemberInfo(methodInfo);
        methodMeta.copyAttributes(methodInfo);
        methodMeta.calcArgSlotCount();
        if (methodMeta.isNative()) {
            methodMeta.injectCodeAttribute(methodMeta.methodDescriptor.getReturnType());
        }
        return methodMeta;
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

    /**
     * 本地方法字节码注入
     * @param returnType 本地方法返回值类型
     */
    private void injectCodeAttribute(String returnType) {
        maxStacks = 4; // TODO: 暂时赋值为4
        maxLocals = argSlotCount;
        switch (returnType.charAt(0)) {
            case 'V':
                codes = new byte[] {(byte) 0xfe, (byte) 0xb1}; // return
                break;
            case 'D':
                codes = new byte[] {(byte) 0xfe, (byte) 0xaf}; // dreturn
                break;
            case 'F':
                codes = new byte[] {(byte) 0xfe, (byte) 0xae}; // freturn
                break;
            case 'J':
                codes = new byte[] {(byte) 0xfe, (byte) 0xad}; // lreturn
                break;
            case 'L':
                codes = new byte[] {(byte) 0xfe, (byte) 0xb0}; // areturn
                break;
            default:
                codes = new byte[] {(byte) 0xfe, (byte) 0xac}; // ireturn
                break;
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
