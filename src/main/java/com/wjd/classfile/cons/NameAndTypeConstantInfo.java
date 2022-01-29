package com.wjd.classfile.cons;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;

/**
 * 签名常量
 *
 * CONSTANT_NameAndType_info {
 *     u1 tag;
 *     u2 name_index;
 *     u2 descriptor_index;
 * }
 */
public class NameAndTypeConstantInfo implements RefConstantInfo {

    /**
     * 名称索引
     */
    private Uint16 nameIndex;
    /**
     * 描述符索引
     */
    private Uint16 descriptorIndex;

    @Override
    public void readFrom(ClassReader reader) {
        nameIndex = reader.readUint16();
        descriptorIndex = reader.readUint16();
    }

    public Uint16 getNameIndex() {
        return nameIndex;
    }

    public Uint16 getDescriptorIndex() {
        return descriptorIndex;
    }

    @Override
    public String toString() {
        return "NameAndTypeConstantInfo{" +
                "nameIndex=" + nameIndex +
                ", descriptorIndex=" + descriptorIndex +
                '}';
    }
}
