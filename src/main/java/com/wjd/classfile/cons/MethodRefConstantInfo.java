package com.wjd.classfile.cons;

/**
 * 方法引用常量
 *
 * CONSTANT_Methodref_info {
 *     u1 tag;
 *     u2 class_index;
 *     u2 name_and_type_index;
 * }
 */
public class MethodRefConstantInfo extends MemberConstantInfo {

    @Override
    public String toString() {
        return "MethodRefConstantInfo{" +
                "classIndex=" + classIndex +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                '}';
    }

}
