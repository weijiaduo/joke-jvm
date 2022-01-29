package com.wjd.classfile.cons;

/**
 * 包引用常量
 *
 * CONSTANT_Package_info {
 *     u1 tag;
 *     u2 name_index;
 * }
 */
public class PackageConstantInfo extends NameConstantInfo {

    @Override
    public String toString() {
        return "PackageConstantInfo{" +
                "index=" + index +
                '}';
    }
}
