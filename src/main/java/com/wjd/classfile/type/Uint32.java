package com.wjd.classfile.type;

/**
 * 32比特无符号整数
 */
public class Uint32 {

    private long val;

    public Uint32(long val) {
        this.val = val;
    }

    public long value() {
        return val;
    }

    @Override
    public String toString() {
        return "" + val;
    }
}
