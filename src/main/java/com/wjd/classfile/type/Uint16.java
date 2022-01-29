package com.wjd.classfile.type;

/**
 * 16比特无符号整数
 */
public class Uint16 {

    private int val;

    public Uint16(int val) {
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
