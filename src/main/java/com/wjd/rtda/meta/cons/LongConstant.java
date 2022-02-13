package com.wjd.rtda.meta.cons;

/**
 * 长整型常量
 * @since 2022/1/30
 */
public class LongConstant extends LiteralConstant {

    private long val;

    public LongConstant(long value) {
        this.val = value;
    }

    public long value() {
        return val;
    }

}
