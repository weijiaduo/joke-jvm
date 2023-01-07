package com.wjd.rtda.meta;

import com.wjd.classfile.attr.CodeAttributeInfo;
import com.wjd.classfile.attr.ExceptionsAttributeInfo;
import com.wjd.classfile.attr.LineNumberTableAttributeInfo;
import com.wjd.classfile.MethodInfo;
import com.wjd.classfile.type.Uint16;
import com.wjd.rtda.heap.Heap;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.cons.ClassRef;
import com.wjd.rtda.meta.ex.ExceptionHandler;
import com.wjd.rtda.meta.ex.ExceptionTable;
import com.wjd.rtda.meta.util.MethodDescriptorParser;
import com.wjd.util.ClassHelper;

/**
 * 方法成员
 * @since 2022/1/30
 */
public class MethodMeta extends MemberMeta {

    /** 方法的操作数栈最大深度 */
    protected int maxStacks;
    /** 局部变量表的最大长度 */
    protected int maxLocals;

    /** 方法描述符 */
    protected MethodDescriptor methodDescriptor;
    /** 方法的参数插槽数量，不包括this */
    protected int paramSlotCount;
    /** 传参时的实际插槽数量，非静态方法会包括this */
    protected int argSlotCount;
    /** 方法的异常表索引，指向方法抛出的异常类型 */
    protected Uint16[] exIndexTable;

    /** 方法的代码字节码 */
    protected byte[] codes;
    /** 代码中捕获的异常类型，即没有往方法外抛的异常类型 */
    protected ExceptionTable exceptionTable;
    /** 代码的行号表，指向ClassFile的常量 */
    protected LineNumberTableAttributeInfo lineNumberTable;

    public static MethodMeta[] newMethods(ClassMeta classMeta, MethodInfo[] methodInfos) {
        MethodMeta[] methods = new MethodMeta[methodInfos.length];
        for (int i = 0; i < methods.length; i++) {
            methods[i] = newMethod(classMeta, methodInfos[i]);
        }
        return methods;
    }

    public static MethodMeta newMethod(ClassMeta classMeta, MethodInfo methodInfo) {
        MethodMeta method = new MethodMeta();
        method.classMeta = classMeta;
        method.copyMemberInfo(methodInfo);
        method.copyAttributes(methodInfo);
        method.calcArgSlotCount();
        if (method.isNative()) {
            method.injectCodeAttribute(method.methodDescriptor.getReturnType());
        }
        return method;
    }

    /**
     * 复制属性
     */
    private void copyAttributes(MethodInfo methodInfo) {
        CodeAttributeInfo codeAttr = methodInfo.getCodeAttributeInfo();
        if (codeAttr != null) {
            ExceptionsAttributeInfo exAttributeInfo = methodInfo.getExceptionsAttributeInfo();
            if (exAttributeInfo != null) {
                exIndexTable = exAttributeInfo.getExceptionIndexTable();
            }
            maxStacks = codeAttr.getMaxStack().value();
            maxLocals = codeAttr.getMaxLocals().value();
            codes = codeAttr.getCodes();
            exceptionTable = ExceptionTable.newExceptionTable(codeAttr.getExceptionInfoTable(), classMeta.getConstantPool());
            lineNumberTable = codeAttr.getLineNumberTableAttribute();
        }
    }

    /**
     * 计算参数的插槽数量
     */
    private void calcArgSlotCount() {
        methodDescriptor = MethodDescriptorParser.parseFrom(descriptor);
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
            case '[':
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
        if (exceptionTable != null) {
            ExceptionHandler handler = exceptionTable.findExceptionHandler(exClass, pc);
            if (handler != null) {
                return handler.getHandlerPC();
            }
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

    /**
     * 获取参数类型元数据
     */
    public ClassMeta[] getParameterTypes() {
        if (paramSlotCount == 0) {
            return new ClassMeta[0];
        }
        String[] paramTypes = methodDescriptor.getParameterTypes();
        ClassMeta[] paramClasses = new ClassMeta[paramTypes.length];
        for (int i = 0; i < paramClasses.length; i++) {
            String paramClassName = ClassHelper.getClassName(paramTypes[i]);
            paramClasses[i] = classMeta.getLoader().loadClass(paramClassName);
        }
        return paramClasses;
    }

    /**
     * 获取参数数组对象
     */
    public HeapObject getParameterTypeArr() {
        return toJClassArr(getParameterTypes());
    }

    /**
     * 获取异常类型
     */
    public ClassMeta[] getExceptionTypes() {
        if (exIndexTable == null) {
            return null;
        }

        ConstantPool cp = classMeta.constantPool;
        ClassMeta[] handlerClasses = new ClassMeta[exIndexTable.length];
        for (int i = 0; i < handlerClasses.length; i++) {
            ClassRef exClass = (ClassRef) cp.getConstant(exIndexTable[i].value());
            handlerClasses[i] = exClass.resolvedClass();
        }

        return handlerClasses;
    }

    /**
     * 获取异常数组对象
     */
    public HeapObject getExceptionTypeArr() {
        ClassMeta[] exClasses = getExceptionTypes();
        if (exClasses == null) {
            return null;
        }
        return toJClassArr(exClasses);
    }

    /**
     * 转成java.lang.Class数组对象
     * @param classes 类元数据数组
     * @return java.lang.Class数组对象
     */
    private HeapObject toJClassArr(ClassMeta[] classes) {
        int count = classes.length;
        ClassMeta jlClassClass = classMeta.getLoader().loadClass("java/lang/Class");
        ClassMeta arrClass = jlClassClass.getArrayClass();
        HeapObject arr = Heap.newArray(arrClass, count);

        if (count > 0) {
            HeapObject[] refs = arr.getRefs();
            for (int i = 0; i < count; i++) {
                refs[i] = classes[i].getjClass();
            }
        }
        return arr;
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

    @Override
    public String toString() {
        return "MethodMeta{" +
                "name='" + name + '\'' +
                ", descriptor='" + descriptor + '\'' +
                ", signature='" + signature + '\'' +
                ", classMeta=" + classMeta +
                ", maxStacks=" + maxStacks +
                ", maxLocals=" + maxLocals +
                ", paramSlotCount=" + paramSlotCount +
                '}';
    }
}
