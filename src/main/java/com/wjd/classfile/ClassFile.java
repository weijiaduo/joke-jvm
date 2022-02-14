package com.wjd.classfile;

import com.wjd.classfile.attr.AttributeInfo;
import com.wjd.classfile.attr.SourceFileAttributeInfo;
import com.wjd.classfile.cons.ClassConstantInfo;
import com.wjd.classfile.cons.ConstantInfo;
import com.wjd.classfile.member.FieldInfo;
import com.wjd.classfile.member.MethodInfo;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;

public class ClassFile {

    /**
     * 魔数
     */
    private Uint32 magic;
    /**
     * 次版本号
     */
    private Uint16 minorVersion;
    /**
     * 主版本号
     */
    private Uint16 majorVersion;
    /**
     * 常量池
     */
    private ConstantInfoPool constantPool;
    /**
     * 类访问标志
     */
    private Uint16 accessFlags;
    /**
     * 类名
     */
    private Uint16 className;
    /**
     * 父类名
     */
    private Uint16 superClassName;
    /**
     * 接口数量
     */
    private Uint16 interfaceCount;
    /**
     * 接口定义
     */
    private Uint16[] interfaces;
    /**
     * 字段数量
     */
    private Uint16 fieldCount;
    /**
     * 字段定义
     */
    private FieldInfo[] fields;
    /**
     * 方法数量
     */
    private Uint16 methodCount;
    /**
     * 方法定义
     */
    private MethodInfo[] methods;
    /**
     * 属性定义
     */
    private AttributeInfoTable attributeInfoTable;

    public static ClassFile parse(ClassReader reader) {
        ClassFile classFile = new ClassFile();
        classFile.readFrom(reader);
        return classFile;
    }

    public void readFrom(ClassReader reader) {
        reader.setClassFile(this);

        // 版本信息
        magic = reader.readUint32();
        minorVersion = reader.readUint16();
        majorVersion = reader.readUint16();

        // 常量池
        constantPool = new ConstantInfoPool();
        constantPool.readFrom(reader);

        // 类和超类
        accessFlags = reader.readUint16();
        className = reader.readUint16();
        superClassName = reader.readUint16();

        // 类接口
        interfaceCount = reader.readUint16();
        interfaces = reader.readUint16s(interfaceCount);

        // 成员变量
        fieldCount = reader.readUint16();
        fields = new FieldInfo[fieldCount.value()];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new FieldInfo();
            fields[i].readFrom(reader);
        }

        // 成员方法
        methodCount = reader.readUint16();
        methods = new MethodInfo[methodCount.value()];
        for (int i = 0; i < methods.length; i++) {
            methods[i] = new MethodInfo();
            methods[i].readFrom(reader);
        }

        // 属性表
        attributeInfoTable = new AttributeInfoTable();
        attributeInfoTable.readFrom(reader);
    }

    /**
     * 获取指定位置的常量
     * @param index 常量索引
     * @return 常量对象
     */
    public ConstantInfo getConstantInfo(Uint16 index) {
        return constantPool.getConstantInfo(index);
    }

    /**
     * 获取指定的UTF8字符串
     * @param index 索引位置
     * @return 字符串
     */
    public String getUTF8String(Uint16 index) {
        return constantPool.getUTF8String(index);
    }

    /**
     * 获取源文件
     */
    public String getSourceFile() {
        AttributeInfo[] attrs = attributeInfoTable.getAttributes();
        for (AttributeInfo attr : attrs) {
            if (attr instanceof SourceFileAttributeInfo) {
                return getUTF8String(((SourceFileAttributeInfo) attr).getNameIndex());
            }
        }
        return "Unknown";
    }

    /**
     * 类名
     */
    public String getClassName() {
        return findClassName(className);
    }

    /**
     * 父类名
     */
    public String getSuperClassName() {
        return findClassName(superClassName);
    }

    /**
     * 接口名称
     */
    public String[] getInterfaceNames() {
        String[] interfaceNames = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            interfaceNames[i] = findClassName(interfaces[i]);
        }
        return interfaceNames;
    }

    /**
     * 查找类名称
     */
    private String findClassName(Uint16 index) {
        if (index.value() == 0) {
            return "";
        }
        ClassConstantInfo info = (ClassConstantInfo) getConstantInfo(index);
        return getUTF8String(info.getIndex());
    }

    /**
     * 访问标志
     */
    public Uint16 getAccessFlags() {
        return accessFlags;
    }

    /**
     * 常量池
     */
    public ConstantInfoPool getConstantPool() {
        return constantPool;
    }

    /**
     * 字段
     */
    public FieldInfo[] getFields() {
        return fields;
    }

    /**
     * 获取所有方法
     * @return 所有方法信息
     */
    public MethodInfo[] getMethods() {
        return methods;
    }
}
