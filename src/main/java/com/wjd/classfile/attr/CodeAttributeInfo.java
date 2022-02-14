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
    private ExceptionInfoTable exceptionInfoTable;  // 异常表
    private AttributeInfoTable attributeInfoTable;  // 属性表

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
        maxStack = reader.readUint16();
        maxLocals = reader.readUint16();
        codeLength = reader.readUint32();
        codes = reader.readBytes(codeLength);

        exceptionInfoTable = new ExceptionInfoTable();
        exceptionInfoTable.readFrom(reader);

        attributeInfoTable = new AttributeInfoTable();
        attributeInfoTable.readFrom(reader);
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

    public ExceptionInfoTable getExceptionInfoTable() {
        return exceptionInfoTable;
    }

    public LineNumberTableAttributeInfo getLineNumberTableAttribute() {
        AttributeInfo[] attrs = attributeInfoTable.getAttributes();
        for (AttributeInfo attr : attrs) {
            if (attr instanceof LineNumberTableAttributeInfo) {
                return (LineNumberTableAttributeInfo) attr;
            }
        }
        return null;
    }
}
