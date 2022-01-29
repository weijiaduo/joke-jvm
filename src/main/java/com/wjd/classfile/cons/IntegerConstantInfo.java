package com.wjd.classfile.cons;

import com.wjd.classfile.ClassReader;

/**
 * 32位整数常量（包括 boolean、byte、char、short）
 *
 * CONSTANT_Integer_info {
 *     u1 tag;
 *     u4 bytes;
 * }
 */
public class IntegerConstantInfo implements LiteralConstantInfo {

    private int val;

    @Override
    public void readFrom(ClassReader reader) {
        val = reader.readInt();
    }

    public int value() {
        return val;
    }

    @Override
    public String toString() {
        return "IntegerConstantInfo{" +
                "val=" + val +
                '}';
    }
}
