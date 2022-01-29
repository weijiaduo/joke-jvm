package com.wjd.classfile.cons;

/**
 * 类名称引用常量
 *
 * CONSTANT_Class_info {
 *     u1 tag;
 *     u2 name_index;
 * }
 */
public class ClassConstantInfo extends NameConstantInfo {

    @Override
    public String toString() {
        return "ClassConstantInfo{" +
                "index=" + index +
                '}';
    }
}
