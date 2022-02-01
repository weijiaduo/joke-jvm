package com.wjd.classfile.cons;

import com.wjd.classfile.ClassReader;

/**
 * 32位浮点数
 *
 * CONSTANT_Float_info {
 *     u1 tag;
 *     u4 bytes;
 * }
 */
public class FloatConstantInfo implements LiteralConstantInfo {

    private float val;

    @Override
    public void readFrom(ClassReader reader) {
        val = reader.readFloat();
    }

    public float value() {
        return val;
    }

    @Override
    public String toString() {
        return "FloatConstantInfo{" +
                "val=" + val +
                '}';
    }
}
