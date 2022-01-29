package com.wjd.classfile.cons;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;

/**
 * 方法类型常量
 *
 * CONSTANT_MethodType_info {
 *     u1 tag;
 *     u2 descriptor_index;
 * }
 */
public class MethodTypeConstantInfo implements RefConstantInfo {

    /**
     * 描述符索引
     */
    private Uint16 descriptorIndex;

    @Override
    public void readFrom(ClassReader reader) {
        descriptorIndex = reader.readUint16();
    }

    public Uint16 getDescriptorIndex() {
        return descriptorIndex;
    }

    @Override
    public String toString() {
        return "MethodTypeConstantInfo{" +
                "descriptorIndex=" + descriptorIndex +
                '}';
    }
}
