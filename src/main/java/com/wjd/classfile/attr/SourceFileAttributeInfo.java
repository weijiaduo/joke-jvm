package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;

/**
 * 源文件属性
 *
 * SourceFile_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 sourcefile_index;
 * }
 */
public class SourceFileAttributeInfo implements AttributeInfo {

    private Uint32 length;
    private Uint16 nameIndex;

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
        nameIndex = reader.readUint16();
    }

    public Uint32 getLength() {
        return length;
    }

    public Uint16 getNameIndex() {
        return nameIndex;
    }
}
