package com.wjd.rtda.meta;

import com.wjd.classfile.attr.CodeAttributeInfo;
import com.wjd.classfile.attr.LineNumberTableAttributeInfo;
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
    private ExceptionTable exceptionTable;
    private LineNumberTableAttributeInfo lineNumberTable;

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
            exceptionTable = ExceptionTable.newExceptionTable(codeAttr.getExceptionInfoTable(), clazz.getConstantPool());
            lineNumberTable = codeAttr.getLineNumberTableAttribute();
        }
    }

    /**
     * 计算参数的插槽数量
     */
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

    /**
     * 寻找异常处理
     * @param exClass 抛出异常的类
     * @param pc 抛出异常的pc地址
     * @return 异常处理的pc地址
     */
    public int findExceptionHandler(ClassMeta exClass, int pc) {
        ExceptionHandler handler = exceptionTable.findExceptionHandler(exClass, pc);
        if (handler != null) {
            return handler.getHandlerPC();
        }
        return -1;
    }

    /**
     * 获取代码行号
     * @param pc 指定的pc地址
     */
    public int getLineNumber(int pc) {
        if (isNative()) {
            return -1;
        }
        if (lineNumberTable == null) {
            return -1;
        }
        return lineNumberTable.getLineNumber(pc);
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
