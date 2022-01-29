package com.wjd.classfile.cons;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;

/**
 * UTF-8字符串常量值
 *
 * CONSTANT_Utf8_info {
 *     u1 tag;
 *     u2 length;
 *     u1 bytes[length];
 * }
 */
public class Utf8ConstantInfo implements LiteralConstantInfo {

    private Uint16 length;
    private byte[] val;

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint16();
        val = reader.readBytes(length);
    }

    public byte[] value() {
        return val;
    }

    @Override
    public String toString() {
        return "Utf8ConstantInfo{" +
                "length=" + length +
                ", val=" + (new String(val)) +
                '}';
    }
}
