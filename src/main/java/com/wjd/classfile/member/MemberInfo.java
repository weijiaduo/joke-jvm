package com.wjd.classfile.member;

import com.wjd.classfile.AttributeInfoTable;
import com.wjd.classfile.ClassFile;
import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;

/**
 * 成员信息
 */
public abstract class MemberInfo {

    /**
     * 访问标志
     */
    private Uint16 accessFlags;
    /**
     * 名称索引
     */
    private Uint16 nameIndex;
    /**
     * 描述索引
     */
    private Uint16 descriptorIndex;
    /**
     * 属性表
     */
    private AttributeInfoTable attributeTable;

    private ClassFile classFile;

    public void readFrom(ClassReader reader) {
        classFile = reader.getClassFile();

        accessFlags = reader.readUint16();
        nameIndex = reader.readUint16();
        descriptorIndex = reader.readUint16();
        attributeTable = new AttributeInfoTable();
        attributeTable.readFrom(reader);
    }

    public Uint16 getAccessFlags() {
        return accessFlags;
    }

    public Uint16 getNameIndex() {
        return nameIndex;
    }

    public Uint16 getDescriptorIndex() {
        return descriptorIndex;
    }

    public AttributeInfoTable getAttributeTable() {
        return attributeTable;
    }

    public ClassFile getClassFile() {
        return classFile;
    }
}
