package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint32;

/**
 * Synthetic属性用来标记源文件中不存在、由编译器生成的类成员，引入Synthetic属性主要是为了支持嵌套类和嵌套接口
 *
 * Synthetic_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 * }
 */
public class SyntheticAttributeInfo implements AttributeInfo {

    private Uint32 length; // 值必须是0

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
    }

    public Uint32 getLength() {
        return length;
    }

}
