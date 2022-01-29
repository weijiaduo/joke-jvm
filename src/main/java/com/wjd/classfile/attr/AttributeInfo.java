package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;

/**
 * attribute_info {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u1 info[attribute_length];
 * }
 */
public interface AttributeInfo {

    void readFrom(ClassReader reader);

}
