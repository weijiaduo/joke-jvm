package com.wjd.rtda.meta.cons;

/**
 * 整型常量
 * @since 2022/1/30
 */
public class IntegerConstant extends LiteralConstant {

    private int val;

    public IntegerConstant(int val) {
        this.val = val;
    }

    public int value() {
        return val;
    }

}
