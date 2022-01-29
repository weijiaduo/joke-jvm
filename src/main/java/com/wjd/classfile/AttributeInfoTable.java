package com.wjd.classfile;

import com.wjd.classfile.attr.*;
import com.wjd.classfile.type.Uint16;

/**
 * 属性表
 */
public class AttributeInfoTable {

    /**
     * 属性数量
     */
    private Uint16 attributeCount;
    /**
     * 属性数组
     */
    private AttributeInfo[] attributes;

    private ClassFile classFile;

    public void readFrom(ClassReader reader) {
        classFile = reader.getClassFile();

        attributeCount = reader.readUint16();
        attributes = new AttributeInfo[attributeCount.value()];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = readAttributeInfo(reader);
        }
    }

    private AttributeInfo readAttributeInfo(ClassReader reader) {
        AttributeInfo info = null;
        Uint16 attrNameIndex = reader.readUint16();
        String attrName = classFile.getUTF8String(attrNameIndex);
        switch (attrName) {
            case AttributeNames.BootstrapMethods:
                info = new BootstrapMethodsAttributeInfo();
                break;
            case AttributeNames.Code:
                info = new CodeAttributeInfo();
                break;
            case AttributeNames.ConstantValue:
                info = new ConstantValueAttributeInfo();
                break;
            case AttributeNames.Deprecated:
                info = new DeprecatedAttributeInfo();
                break;
            case AttributeNames.EnclosingMethod:
                info = new EnclosingMethodAttributeInfo();
                break;
            case AttributeNames.Exceptions:
                info = new ExceptionsAttributeInfo();
                break;
            case AttributeNames.InnerClasses:
                info = new InnerClassesAttributeInfo();
                break;
            case AttributeNames.LineNumberTable:
                info = new LineNumberTableAttributeInfo();
                break;
            case AttributeNames.LocalVariableTable:
                info = new LocalVariableTableAttributeInfo();
                break;
            case AttributeNames.LocalVariableTypeTable:
                info = new LocalVariableTypeTableAttributeInfo();
                break;
            case AttributeNames.Module:
                info = new ModuleAttributeInfo();
                break;
            case AttributeNames.Signature:
                info = new SignatureAttributeInfo();
                break;
            case AttributeNames.SourceFile:
                info = new SourceFileAttributeInfo();
                break;
            case AttributeNames.Synthetic:
                info = new SyntheticAttributeInfo();
                break;
            default:
                info = new UnparsedAttributeInfo(attrName);
        }
        info.readFrom(reader);
        return info;
    }

    public Uint16 getAttributeCount() {
        return attributeCount;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public ClassFile getClassFile() {
        return classFile;
    }

}
