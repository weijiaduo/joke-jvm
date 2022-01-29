package com.wjd.classfile.cons;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint8;

/**
 * 方法处理常量
 *
 * CONSTANT_MethodHandle_info {
 *     u1 tag;
 *     u1 reference_kind;
 *     u2 reference_index;
 * }
 */
public class MethodHandleConstantInfo implements RefConstantInfo {

    private Uint8 referenceKind;
    private Uint16 referenceIndex;

    @Override
    public void readFrom(ClassReader reader) {
        referenceKind = reader.readUint8();
        referenceIndex = reader.readUint16();
    }

    public Uint8 getReferenceKind() {
        return referenceKind;
    }

    public Uint16 getReferenceIndex() {
        return referenceIndex;
    }

    @Override
    public String toString() {
        return "MethodHandleConstantInfo{" +
                "referenceKind=" + referenceKind +
                ", referenceIndex=" + referenceIndex +
                '}';
    }
}
