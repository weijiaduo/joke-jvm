package com.wjd.rtda.meta.cons;

/**
 * 字符串常量
 * @since 2022/1/30
 */
public class StringConstant extends Constant {

    private String val;

    public StringConstant(String value) {
        this.val = value;
    }

    public String value() {
        return val;
    }

}
