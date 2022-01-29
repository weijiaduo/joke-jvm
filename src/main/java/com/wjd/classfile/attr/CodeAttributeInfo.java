package com.wjd.classfile.attr;

import com.wjd.classfile.AttributeInfoTable;
import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;

/**
 * 代码属性
 *
 * Code_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 max_stack;
 *     u2 max_locals;
 *     u4 code_length;
 *     u1 code[code_length];
 *     u2 exception_table_length;
 *     {   u2 start_pc;
 *         u2 end_pc;
 *         u2 handler_pc;
 *         u2 catch_type;
 *     } exception_table[exception_table_length];
 *     u2 attributes_count;
 *     attribute_info attributes[attributes_count];
 * }
 */
public class CodeAttributeInfo implements AttributeInfo {

    private Uint32 length;
    private Uint16 maxStack;                        // 操作数栈的最大深度
    private Uint16 maxLocals;                       // 局部变量表大小
    private Uint32 codeLength;
    private byte[] codes;                           // 字节码
    private Uint16 exceptionLength;
    private ExceptionTableEntry[] exceptionEntries; // 异常处理表
    private AttributeInfoTable attributeInfoTable;  // 属性表

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
        maxStack = reader.readUint16();
        maxLocals = reader.readUint16();
        codeLength = reader.readUint32();
        codes = reader.readBytes(codeLength);
        exceptionLength = reader.readUint16();
        exceptionEntries = new ExceptionTableEntry[exceptionLength.value()];
        for (int i = 0; i < exceptionEntries.length; i++) {
            exceptionEntries[i] = readExceptionEntry(reader);
        }
        attributeInfoTable = new AttributeInfoTable();
        attributeInfoTable.readFrom(reader);
    }

    private ExceptionTableEntry readExceptionEntry(ClassReader reader) {
        ExceptionTableEntry entry = new ExceptionTableEntry();
        entry.setStartPC(reader.readUint16());
        entry.setEndPC(reader.readUint16());
        entry.setHandlerPC(reader.readUint16());
        entry.setCatchPC(reader.readUint16());
        return entry;
    }

    public Uint32 getLength() {
        return length;
    }

    public Uint16 getMaxStack() {
        return maxStack;
    }

    public Uint16 getMaxLocals() {
        return maxLocals;
    }

    public Uint32 getCodeLength() {
        return codeLength;
    }

    public byte[] getCodes() {
        return codes;
    }

    public Uint16 getExceptionLength() {
        return exceptionLength;
    }

    public ExceptionTableEntry[] getExceptionEntries() {
        return exceptionEntries;
    }

    public AttributeInfoTable getAttributeInfoTable() {
        return attributeInfoTable;
    }
}
