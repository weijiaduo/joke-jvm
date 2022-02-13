package com.wjd.rtda.meta.cons;

/**
 * 单精度浮点数常量
 * @since 2022/1/30
 */
public class FloatConstant extends LiteralConstant {

    private float val;

    public FloatConstant(float value) {
        this.val = value;
    }

    public float value() {
        return val;
    }

}
