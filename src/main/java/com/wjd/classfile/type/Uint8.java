package com.wjd.classfile.type;

/**
 * 8比特无符号整数
 */
public class Uint8 {

    private int val;

    public Uint8(int val) {
        this.val = val;
    }

    public int value() {
        return val;
    }

    @Override
    public String toString() {
        return "" + val;
    }
}
