package com.wjd.classfile.cons;

import com.wjd.classfile.ClassReader;

/**
 * 64位长整数常量
 *
 * CONSTANT_Long_info {
 *     u1 tag;
 *     u4 high_bytes;
 *     u4 low_bytes;
 * }
 */
public class LongConstantInfo implements LiteralConstantInfo {

    private long val;

    @Override
    public void readFrom(ClassReader reader) {
        val = reader.readLong();
    }

    public long value() {
        return val;
    }

    @Override
    public String toString() {
        return "LongConstantInfo{" +
                "val=" + val +
                '}';
    }
}
