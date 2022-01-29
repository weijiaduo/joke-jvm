package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;

/**
 * 本地变量表
 *
 * LocalVariableTable_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 local_variable_table_length;
 *     {   u2 start_pc;
 *         u2 length;
 *         u2 name_index;
 *         u2 descriptor_index;
 *         u2 index;
 *     } local_variable_table[local_variable_table_length];
 * }
 */
public class LocalVariableTableAttributeInfo implements AttributeInfo {

    private Uint32 length;
    private Uint16 localVariableLength;
    private LocalVariableTableEntry[] localVariableEntries;

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
        localVariableLength = reader.readUint16();
        localVariableEntries = new LocalVariableTableEntry[localVariableLength.value()];
        for (int i = 0; i < localVariableEntries.length; i++) {
            localVariableEntries[i] = readLocalVariableTableEntry(reader);
        }
    }

    private LocalVariableTableEntry readLocalVariableTableEntry(ClassReader reader) {
        LocalVariableTableEntry entry = new LocalVariableTableEntry();
        entry.setStartPC(reader.readUint16());
        entry.setLength(reader.readUint16());
        entry.setNameIndex(reader.readUint16());
        entry.setDescriptorIndex(reader.readUint16());
        entry.setIndex(reader.readUint16());
        return entry;
    }

    public Uint32 getLength() {
        return length;
    }

    public Uint16 getLocalVariableLength() {
        return localVariableLength;
    }

    public LocalVariableTableEntry[] getLocalVariableEntries() {
        return localVariableEntries;
    }
}
