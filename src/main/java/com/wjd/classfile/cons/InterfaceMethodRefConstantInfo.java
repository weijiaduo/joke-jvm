package com.wjd.classfile.cons;

/**
 * 接口方法引用常量
 *
 * CONSTANT_InterfaceMethodref_info {
 *     u1 tag;
 *     u2 class_index;
 *     u2 name_and_type_index;
 * }
 */
public class InterfaceMethodRefConstantInfo extends MemberConstantInfo {

    @Override
    public String toString() {
        return "InterfaceMethodRefConstantInfo{" +
                "classIndex=" + classIndex +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                '}';
    }

}
