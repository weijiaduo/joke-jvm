package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;

/**
 * 常量属性
 *
 * ConstantValue_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 constantvalue_index;
 * }
 */
public class ConstantValueAttributeInfo implements AttributeInfo {

    private Uint32 length;        // 值必须是2
    private Uint16 constantIndex;

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
        constantIndex = reader.readUint16();
    }

    public Uint32 getLength() {
        return length;
    }

    public Uint16 getConstantIndex() {
        return constantIndex;
    }
}
