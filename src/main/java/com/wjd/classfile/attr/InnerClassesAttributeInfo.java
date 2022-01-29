package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;

/**
 * 内部类属性
 *
 * InnerClasses_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 number_of_classes;
 *     {   u2 inner_class_info_index;
 *         u2 outer_class_info_index;
 *         u2 inner_name_index;
 *         u2 inner_class_access_flags;
 *     } classes[number_of_classes];
 * }
 */
public class InnerClassesAttributeInfo implements AttributeInfo {

    private Uint32 length;
    private Uint16 numberOfClass;
    private InnerClassInfo[] innerClasses;

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
        numberOfClass = reader.readUint16();
        innerClasses = new InnerClassInfo[numberOfClass.value()];
        for (int i = 0; i < innerClasses.length; i++) {
            innerClasses[i] = readInnerClassInfo(reader);
        }
    }

    private InnerClassInfo readInnerClassInfo(ClassReader reader) {
        InnerClassInfo info = new InnerClassInfo();
        info.setInnerClassInfoIndex(reader.readUint16());
        info.setOuterClassInfoIndex(reader.readUint16());
        info.setInnerNameIndex(reader.readUint16());
        info.setInnerClassAccessFlags(reader.readUint16());
        return info;
    }

    public Uint32 getLength() {
        return length;
    }

    public Uint16 getNumberOfClass() {
        return numberOfClass;
    }

    public InnerClassInfo[] getInnerClasses() {
        return innerClasses;
    }
}
