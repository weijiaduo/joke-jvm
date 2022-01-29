package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;

/**
 * 本地变量类型属性
 *
 * LocalVariableTypeTable_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 local_variable_type_table_length;
 *     {   u2 start_pc;
 *         u2 length;
 *         u2 name_index;
 *         u2 signature_index;
 *         u2 index;
 *     } local_variable_type_table[local_variable_type_table_length];
 * }
 */
public class LocalVariableTypeTableAttributeInfo implements AttributeInfo {

    private Uint32 length;
    private Uint16 localVariableLength;
    private LocalVariableTypeTableEntry[] localVariableTypeEntries;

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
        localVariableLength = reader.readUint16();
        localVariableTypeEntries = new LocalVariableTypeTableEntry[localVariableLength.value()];
        for (int i = 0; i < localVariableTypeEntries.length; i++) {
            localVariableTypeEntries[i] = readLocalVariableTypeTableEntry(reader);
        }
    }

    private LocalVariableTypeTableEntry readLocalVariableTypeTableEntry(ClassReader reader) {
        LocalVariableTypeTableEntry entry = new LocalVariableTypeTableEntry();
        entry.setStartPC(reader.readUint16());
        entry.setLength(reader.readUint16());
        entry.setNameIndex(reader.readUint16());
        entry.setSignatureIndex(reader.readUint16());
        entry.setIndex(reader.readUint16());
        return entry;
    }

    public Uint32 getLength() {
        return length;
    }

    public Uint16 getLocalVariableLength() {
        return localVariableLength;
    }

    public LocalVariableTypeTableEntry[] getLocalVariableTypeEntries() {
        return localVariableTypeEntries;
    }
}
