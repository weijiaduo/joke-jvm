package com.wjd.rtda.meta;

import com.wjd.classfile.ConstantInfoPool;
import com.wjd.classfile.cons.*;
import com.wjd.rtda.meta.cons.*;

/**
 * @since 2022/1/30
 */
public class ConstantPool {

    private ClassMeta classMeta;
    private Constant[] constants;

    public static ConstantPool newConstantPool(ClassMeta classMeta, ConstantInfoPool constantInfoPool) {
        ConstantPool constantPool = new ConstantPool();
        constantPool.classMeta = classMeta;
        ConstantInfo[] constantInfos = constantInfoPool.getConstantInfos();
        constantPool.constants = new Constant[constantInfos.length];
        for (int i = 0; i < constantPool.constants.length; i++) {
            ConstantInfo constantInfo = constantInfos[i];
            if (constantInfo == null) {
                continue;
            }
            Constant constant = null;
            String constantName = constantInfo.getClass().getSimpleName();
            switch (constantName) {
                case "IntegerConstantInfo":
                {
                    // 整型
                    IntegerConstantInfo info = (IntegerConstantInfo) constantInfo;
                    constant = new IntegerConstant(info.value());
                    break;
                }
                case "LongConstantInfo":
                {
                    // 长整型
                    LongConstantInfo info = (LongConstantInfo) constantInfo;
                    constant = new LongConstant(info.value());
                    break;
                }
                case "FloatConstantInfo":
                {
                    // 单精度浮点数
                    FloatConstantInfo info = (FloatConstantInfo) constantInfo;
                    constant = new FloatConstant(info.value());
                    break;
                }
                case "DoubleConstantInfo":
                {
                    // 双精度浮点数
                    DoubleConstantInfo info = (DoubleConstantInfo) constantInfo;
                    constant = new DoubleConstant(info.value());
                    break;
                }
                case "StringConstantInfo":
                {
                    // 字符串引用
                    StringConstantInfo info = (StringConstantInfo) constantInfo;
                    constant = new StringConstant(info.getName());
                    break;
                }
                case "ClassConstantInfo":
                {
                    // 类符号引用
                    ClassConstantInfo info = (ClassConstantInfo) constantInfo;
                    constant = ClassRef.newClassRef(constantPool, info);
                    break;
                }
                case "FieldRefConstantInfo":
                {
                    // 字段符号引用
                    FieldRefConstantInfo info = (FieldRefConstantInfo) constantInfo;
                    constant = FieldRef.newFieldRef(constantPool, info);
                    break;
                }
                case "MethodRefConstantInfo":
                {
                    // 方法符号引用
                    MethodRefConstantInfo info = (MethodRefConstantInfo) constantInfo;
                    constant = MethodRef.newMethodRef(constantPool, info);
                    break;
                }
                case "InterfaceMethodRefConstantInfo":
                {
                    // 接口方法符号引用
                    InterfaceMethodRefConstantInfo info = (InterfaceMethodRefConstantInfo) constantInfo;
                    constant = InterfaceMethodRef.newInterfaceMethodRef(constantPool, info);
                    break;
                }
                default:
                    // System.out.println("UnSupported constant: " + constantInfo);
            }
            constantPool.constants[i] = constant;
        }
        return constantPool;
    }

    public Constant getConstant(int index) {
        return constants[index];
    }

    public ClassMeta getClassMeta() {
        return classMeta;
    }
}
