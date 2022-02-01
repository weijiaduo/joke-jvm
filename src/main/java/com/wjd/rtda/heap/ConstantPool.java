package com.wjd.rtda.heap;

import com.wjd.classfile.ConstantInfoPool;
import com.wjd.classfile.cons.*;
import com.wjd.rtda.heap.cons.*;

/**
 * @since 2022/1/30
 */
public class ConstantPool {

    private Class clazz;
    private Constant[] constants;

    public static ConstantPool newConstantPool(Class clazz, ConstantInfoPool constantInfoPool) {
        ConstantPool constantPool = new ConstantPool();
        constantPool.clazz = clazz;
        ConstantInfo[] constantInfos = constantInfoPool.getConstantInfos();
        constantPool.constants = new Constant[constantInfos.length];
        for (int i = 0; i < constantPool.constants.length; i++) {
            ConstantInfo constantInfo = constantInfos[i];
            if (constantInfo instanceof IntegerConstantInfo) {
                // 整型
                IntegerConstantInfo info = (IntegerConstantInfo) constantInfo;
                constantPool.constants[i] = new IntegerConstant(info.value());
            } else if (constantInfo instanceof LongConstantInfo) {
                // 长整型
                LongConstantInfo info = (LongConstantInfo) constantInfo;
                constantPool.constants[i] = new LongConstant(info.value());
                i++;
            } else if (constantInfo instanceof FloatConstantInfo) {
                // 单精度浮点数
                FloatConstantInfo info = (FloatConstantInfo) constantInfo;
                constantPool.constants[i] = new FloatConstant(info.value());
            } else if (constantInfo instanceof DoubleConstantInfo) {
                // 双精度浮点数
                DoubleConstantInfo info = (DoubleConstantInfo) constantInfo;
                constantPool.constants[i] = new DoubleConstant(info.value());
                i++;
            } else if (constantInfo instanceof StringConstantInfo) {
                // 字符串常量
                StringConstantInfo info = (StringConstantInfo) constantInfo;
                constantPool.constants[i] = new StringConstant(info.getName());
            } else if (constantInfo instanceof ClassConstantInfo) {
                // 类符号引用
                ClassConstantInfo info = (ClassConstantInfo) constantInfo;
                constantPool.constants[i] = ClassRef.newClassRef(constantPool, info);
            } else if (constantInfo instanceof FieldRefConstantInfo) {
                // 字段符号引用
                FieldRefConstantInfo info = (FieldRefConstantInfo) constantInfo;
                constantPool.constants[i] = FieldRef.newFieldRef(constantPool, info);
            } else if (constantInfo instanceof MethodRefConstantInfo) {
                // 方法符号引用
                MethodRefConstantInfo info = (MethodRefConstantInfo) constantInfo;
                constantPool.constants[i] = MethodRef.newMethodRef(constantPool, info);
            } else if (constantInfo instanceof InterfaceMethodRefConstantInfo) {
                // 接口方法符号引用
                InterfaceMethodRefConstantInfo info = (InterfaceMethodRefConstantInfo) constantInfo;
                constantPool.constants[i] = InterfaceMethodRef.newInterfaceMethodRef(constantPool, info);
            } else {
                System.out.println("UnSupported constant: " + constantInfo);
            }
        }
        return constantPool;
    }

    public Constant getConstant(int index) {
        return constants[index];
    }

    public Class getClazz() {
        return clazz;
    }
}
