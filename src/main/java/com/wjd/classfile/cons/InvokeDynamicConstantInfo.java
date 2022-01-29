package com.wjd.classfile.cons;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;

/**
 * 动态反射常量
 *
 * CONSTANT_InvokeDynamic_info {
 *     u1 tag;
 *     u2 bootstrap_method_attr_index;
 *     u2 name_and_type_index;
 * }
 */
public class InvokeDynamicConstantInfo implements RefConstantInfo {

    private Uint16 bootstrapMethodAttrIndex;
    private Uint16 nameAndTypeIndex;

    @Override
    public void readFrom(ClassReader reader) {
        bootstrapMethodAttrIndex = reader.readUint16();
        nameAndTypeIndex = reader.readUint16();
    }

    public Uint16 getBootstrapMethodAttrIndex() {
        return bootstrapMethodAttrIndex;
    }

    public Uint16 getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    @Override
    public String toString() {
        return "InvokeDynamicConstantInfo{" +
                "bootstrapMethodAttrIndex=" + bootstrapMethodAttrIndex +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                '}';
    }
}
