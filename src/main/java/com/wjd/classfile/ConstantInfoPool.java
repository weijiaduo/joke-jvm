package com.wjd.classfile;

import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;
import com.wjd.classfile.cons.*;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint8;

/**
 * 常量池信息
 */
public class ConstantInfoPool {

    private Uint16 constantCount;
    private ConstantInfo[] constantPool;

    private ClassFile classFile;

    public void readFrom(ClassReader reader) {
        classFile = reader.getClassFile();

        constantCount = reader.readUint16();
        constantPool = new ConstantInfo[constantCount.value()];
        // 常量池的索引从1开始
        for (int i = 1; i < constantPool.length; i++) {
            constantPool[i] = readConstantInfo(reader);

            // 双字类型（即8字节）占2个位置
            // http://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.5
            // All 8-byte constants take up two entries in the constant_pool table of the class file.
            // If a CONSTANT_Long_info or CONSTANT_Double_info structure is the item in the constant_pool
            // table at index n, then the next usable item in the pool is located at index n+2.
            // The constant_pool index n+1 must be valid but is considered unusable.
            if (constantPool[i] instanceof LongConstantInfo
                    || constantPool[i] instanceof DoubleConstantInfo) {
                constantPool[++i] = null;
            }
        }

        System.out.println("Constant Pool: ");
        for (int i = 1; i < constantPool.length; i++) {
            System.out.println(i + ": " + constantPool[i]);
        }
    }

    private ConstantInfo readConstantInfo(ClassReader reader) {
        ConstantInfo constantInfo = null;
        Uint8 tag = reader.readUint8();
        switch (tag.value()) {
            case Constant.ConstantUtf8:
                constantInfo = new Utf8ConstantInfo();
                break;
            case Constant.ConstantInteger:
                constantInfo = new IntegerConstantInfo();
                break;
            case Constant.ConstantFloat:
                constantInfo = new FloatConstantInfo();
                break;
            case Constant.ConstantLong:
                constantInfo = new LongConstantInfo();
                break;
            case Constant.ConstantDouble:
                constantInfo = new DoubleConstantInfo();
                break;
            case Constant.ConstantClass:
                constantInfo = new ClassConstantInfo();
                break;
            case Constant.ConstantString:
                constantInfo = new StringConstantInfo();
                break;
            case Constant.ConstantFieldRef:
                constantInfo = new FieldRefConstantInfo();
                break;
            case Constant.ConstantMethodRef:
                constantInfo = new MethodRefConstantInfo();
                break;
            case Constant.ConstantInterfaceMethodRef:
                constantInfo = new InterfaceMethodRefConstantInfo();
                break;
            case Constant.ConstantNameAndType:
                constantInfo = new NameAndTypeConstantInfo();
                break;
            case Constant.ConstantMethodHandle:
                constantInfo = new MethodHandleConstantInfo();
                break;
            case Constant.ConstantMethodType:
                constantInfo = new MethodTypeConstantInfo();
                break;
            case Constant.ConstantInvokeDynamic:
                constantInfo = new InvokeDynamicConstantInfo();
                break;
            case Constant.ConstantModule:
                constantInfo = new ModuleConstantInfo();
                break;
            case Constant.ConstantPackage:
                constantInfo = new PackageConstantInfo();
                break;
            case Constant.ConstantDynamic:
            default:
                throw new ClassFormatException("invalid constant pool tag: " + tag.value());
        }
        constantInfo.readFrom(reader);
        return constantInfo;
    }

    /**
     * 获取常量的数量
     * @return 数量
     */
    public Uint16 getConstantCount() {
        return constantCount;
    }

    /**
     * 获取指定位置的常量
     * @param index 常量索引
     * @return 常量对象
     */
    public ConstantInfo getConstantInfo(Uint16 index) {
        return constantPool[index.value()];
    }

    public ClassFile getClassFile() {
        return classFile;
    }

}
