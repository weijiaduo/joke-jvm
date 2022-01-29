package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;

/**
 * 闭包函数属性
 *
 * EnclosingMethod_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 class_index;
 *     u2 method_index;
 * }
 */
public class EnclosingMethodAttributeInfo implements AttributeInfo {

    private Uint32 length;
    private Uint16 classIndex;
    private Uint16 methodIndex;

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
        classIndex = reader.readUint16();
        methodIndex = reader.readUint16();
    }

    public Uint32 getLength() {
        return length;
    }

    public Uint16 getClassIndex() {
        return classIndex;
    }

    public Uint16 getMethodIndex() {
        return methodIndex;
    }
}
