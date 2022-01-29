package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint32;

/**
 * 未解析的属性
 *
 * attribute_info {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u1 info[attribute_length];
 * }
 */
public class UnparsedAttributeInfo implements AttributeInfo {

    private String name;
    private Uint32 length;
    private byte[] info;

    public UnparsedAttributeInfo(String name) {
        this.name = name;
    }

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
        info = reader.readBytes(length);
    }

    public String getName() {
        return name;
    }

    public Uint32 getLength() {
        return length;
    }

    public byte[] getInfo() {
        return info;
    }
}
