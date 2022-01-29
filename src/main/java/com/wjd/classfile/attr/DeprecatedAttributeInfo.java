package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint32;

/**
 * 已过时的信息
 *
 * Deprecated_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 * }
 */
public class DeprecatedAttributeInfo implements AttributeInfo {

    private Uint32 length;    // 值必须是0

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
    }

    public Uint32 getLength() {
        return length;
    }
}
