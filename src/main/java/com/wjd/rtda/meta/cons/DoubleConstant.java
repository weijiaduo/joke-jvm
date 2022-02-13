package com.wjd.rtda.meta.cons;

/**
 * 双精度浮点数常量
 * @since 2022/1/30
 */
public class DoubleConstant extends LiteralConstant {

    private double val;

    public DoubleConstant(double value) {
        this.val = value;
    }

    public double value() {
        return val;
    }

}
