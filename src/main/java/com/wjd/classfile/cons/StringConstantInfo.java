package com.wjd.classfile.cons;

/**
 * 字符串引用常量
 *
 * CONSTANT_String_info {
 *     u1 tag;
 *     u2 string_index;
 * }
 */
public class StringConstantInfo extends NameConstantInfo {

    @Override
    public String toString() {
        return "StringConstantInfo{" +
                "index=" + index +
                '}';
    }
}
