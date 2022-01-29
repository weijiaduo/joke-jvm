package com.wjd.classfile.cons;

import com.wjd.classfile.ClassReader;

/**
 * 64位浮点数
 *
 * CONSTANT_Double_info {
 *     u1 tag;
 *     u4 high_bytes;
 *     u4 low_bytes;
 * }
 */
public class DoubleConstantInfo implements LiteralConstantInfo {

    private double val;

    @Override
    public void readFrom(ClassReader reader) {
        val = reader.readDouble();
    }

    public double value() {
        return val;
    }

    @Override
    public String toString() {
        return "DoubleConstantInfo{" +
                "val=" + val +
                '}';
    }
}
