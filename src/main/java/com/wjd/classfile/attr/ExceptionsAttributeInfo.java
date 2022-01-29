package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;

/**
 * 异常属性
 *
 * Exceptions_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 number_of_exceptions;
 *     u2 exception_index_table[number_of_exceptions];
 * }
 */
public class ExceptionsAttributeInfo implements AttributeInfo {

    private Uint32 length;
    private Uint16 numberOfExceptions;
    private Uint16[] exceptionIndexTable;

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
        numberOfExceptions = reader.readUint16();
        exceptionIndexTable = reader.readUint16s(numberOfExceptions);
    }

    public Uint32 getLength() {
        return length;
    }

    public Uint16 getNumberOfExceptions() {
        return numberOfExceptions;
    }

    public Uint16[] getExceptionIndexTable() {
        return exceptionIndexTable;
    }
}
