package com.wjd.classfile.cons;

/**
 * 字段引用常量
 *
 * CONSTANT_Fieldref_info {
 *     u1 tag;
 *     u2 class_index;
 *     u2 name_and_type_index;
 * }
 */
public class FieldRefConstantInfo extends MemberConstantInfo {

    @Override
    public String toString() {
        return "FieldRefConstantInfo{" +
                "classIndex=" + classIndex +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                '}';
    }
}
