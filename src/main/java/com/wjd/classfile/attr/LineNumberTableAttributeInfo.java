package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;

/**
 * 行号表属性
 *
 * LineNumberTable_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 line_number_table_length;
 *     {   u2 start_pc;
 *         u2 line_number;
 *     } line_number_table[line_number_table_length];
 * }
 */
public class LineNumberTableAttributeInfo implements AttributeInfo {

    private Uint32 length;
    private Uint16 lineNumberLength;
    private LineNumberTableEntry[] lineNumberEntries;

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
        lineNumberLength = reader.readUint16();
        lineNumberEntries = new LineNumberTableEntry[lineNumberLength.value()];
        for (int i = 0; i < lineNumberEntries.length; i++) {
            lineNumberEntries[i] = readLineNumberTableEntry(reader);
        }
    }

    private LineNumberTableEntry readLineNumberTableEntry(ClassReader reader) {
        LineNumberTableEntry entry = new LineNumberTableEntry();
        entry.setStartPC(reader.readUint16());
        entry.setLineNumber(reader.readUint16());
        return entry;
    }

    public Uint32 getLength() {
        return length;
    }

    public Uint16 getLineNumberLength() {
        return lineNumberLength;
    }

    public LineNumberTableEntry[] getLineNumberEntries() {
        return lineNumberEntries;
    }

    public int getLineNumber(int pc) {
        for (int i = lineNumberEntries.length - 1; i >= 0; i--) {
            if (pc >= lineNumberEntries[i].getStartPC().value()) {
                return lineNumberEntries[i].getLineNumber().value();
            }
        }
        return -1;
    }
}
