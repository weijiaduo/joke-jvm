package com.wjd.classfile;

import com.wjd.classfile.attr.AttributeInfo;
import com.wjd.classfile.attr.SignatureAttributeInfo;
import com.wjd.classfile.type.Uint16;

/**
 * 成员信息
 */
public abstract class MemberInfo {

    /**
     * 访问标志
     */
    protected Uint16 accessFlags;
    /**
     * 名称索引
     */
    protected Uint16 nameIndex;
    /**
     * 描述索引
     */
    protected Uint16 descriptorIndex;
    /**
     * 属性表
     */
    protected AttributeInfoTable attributeTable;

    protected ClassFile classFile;

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

    public String getName() {
        return classFile.getUTF8String(nameIndex);
    }

    public String getDescriptor() {
        return classFile.getUTF8String(descriptorIndex);
    }

    public ClassFile getClassFile() {
        return classFile;
    }

    public AttributeInfo[] getAttributes() {
        return attributeTable.getAttributes();
    }

    /**
     * 获取签名属性
     */
    public SignatureAttributeInfo getSignatureAttributeInfo() {
        for (AttributeInfo attr : attributeTable.getAttributes()) {
            if (attr instanceof SignatureAttributeInfo) {
                return (SignatureAttributeInfo) attr;
            }
        }
        return null;
    }
}
